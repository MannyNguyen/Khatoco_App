package khatoco.tvc.com.khatoco.ui;

import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import khatoco.tvc.com.khatoco.api.APIConnectionRequest;
import khatoco.tvc.com.khatoco.api.CallBackAPI;
import khatoco.tvc.com.khatoco.api.object_request_api.ActionRequest;
import khatoco.tvc.com.khatoco.api.object_request_api.GetCheckinAgencyRequest;
import khatoco.tvc.com.khatoco.api.object_request_api.GetProductOfTagRequest;
import khatoco.tvc.com.khatoco.api.object_request_api.GetRoutesRequest;
import khatoco.tvc.com.khatoco.api.object_request_api.GetProductRequest;
import khatoco.tvc.com.khatoco.api.object_request_api.GetPromotionRequest;
import khatoco.tvc.com.khatoco.api.object_request_api.GetAgencyRequest;
import khatoco.tvc.com.khatoco.api.object_request_api.LoginRequest;
import khatoco.tvc.com.khatoco.api.object_request_api.LoginWithSessionRequest;
import khatoco.tvc.com.khatoco.api.object_request_api.SubmitRequest;
import khatoco.tvc.com.khatoco.api.object_response_api.GetAgencisResponse;
import khatoco.tvc.com.khatoco.api.object_response_api.GetCheckinAgencyResponse;

import khatoco.tvc.com.khatoco.api.object_response_api.GetProductOfTagResponse;
import khatoco.tvc.com.khatoco.api.object_response_api.GetRoutesResponse;
import khatoco.tvc.com.khatoco.api.object_response_api.GetProductResponse;
import khatoco.tvc.com.khatoco.api.object_response_api.GetPromotionResponse;
import khatoco.tvc.com.khatoco.api.object_response_api.GetSubmitResponse;
import khatoco.tvc.com.khatoco.api.object_response_api.LoginResponse;
import khatoco.tvc.com.khatoco.service.GPSTracker;
import khatoco.tvc.com.khatoco.ui.enums.ModelEvent;
import khatoco.tvc.com.khatoco.ui.objects.AgencyInfo;
import khatoco.tvc.com.khatoco.ui.objects.ProductOfTagInfo;
import khatoco.tvc.com.khatoco.ui.objects.RouteInfo;
import khatoco.tvc.com.khatoco.ui.objects.CurrentUserInfo;
import khatoco.tvc.com.khatoco.ui.objects.ModelError;
import khatoco.tvc.com.khatoco.ui.objects.ModelObject;
import khatoco.tvc.com.khatoco.ui.objects.ProductInfo;
import khatoco.tvc.com.khatoco.ui.objects.PromotionInfo;
import khatoco.tvc.com.khatoco.ui.objects.SubmitFormat;
import khatoco.tvc.com.khatoco.ui.objects.UserInfo;
import khatoco.tvc.com.khatoco.ui.objects.WardInfo;
import khatoco.tvc.com.khatoco.utils.TaxiLoyDebug;

/**
 * Created by prosoft on 11/11/16.
 */

public class Model extends Observable implements Observer {

    //Create only one object Model for app
    private static Model _instance;
    private String messageError;
    private String title;
    private String orderno = "";
    private LoginResponse loginResponse;
    private CurrentUserInfo currentUserInfo;
    private ArrayList<RouteInfo> routeInfoArrayList;
    private ArrayList<WardInfo> wardInfoArrayList;
    private ArrayList<AgencyInfo> agencyInfoArrayList;
    private ArrayList<ProductOfTagInfo> productOfTagInfoArrayList;

    private ArrayList<ProductInfo> productInfoArrayList;
    private ArrayList<PromotionInfo> promotionInfoArrayList;

    public static Model getInstance() {
        if (_instance == null) {
            _instance = new Model();
        }
        return _instance;
    }

    public void notifyObserversOfEvent(ModelEvent mEvent) {
        setChanged();
        TaxiLoyDebug.d("Model notifying observers: " + mEvent);
        notifyObservers(mEvent);
    }

    @Override
    public void update(Observable observable, Object object) {

    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public CurrentUserInfo getCurrentUserInfo() {
        return currentUserInfo;
    }

    public void setCurrentUserInfo(CurrentUserInfo currentUserInfo) {
        this.currentUserInfo = currentUserInfo;
    }

    public ArrayList<RouteInfo> getRouteInfoArrayList() {
        if (routeInfoArrayList == null) {
            routeInfoArrayList = new ArrayList<>();
        }
        return routeInfoArrayList;
    }

    public ArrayList<WardInfo> getWardInfoArrayList() {
        if (wardInfoArrayList == null) {
            wardInfoArrayList = new ArrayList<>();
        }
        return wardInfoArrayList;
    }

    public ArrayList<AgencyInfo> getAgencyInfoArrayList() {
        if (agencyInfoArrayList == null) {
            agencyInfoArrayList = new ArrayList<>();
        }
        return agencyInfoArrayList;
    }

    public ArrayList<ProductInfo> getProductInfoArrayList() {
        if (productInfoArrayList == null) {
            productInfoArrayList = new ArrayList<>();
        }
        return productInfoArrayList;
    }

    public ArrayList<PromotionInfo> getPromotionInfoArrayList() {
        if (promotionInfoArrayList == null) {
            promotionInfoArrayList = new ArrayList<>();
        }
        return promotionInfoArrayList;

    }

    public ArrayList<ProductOfTagInfo> getProductOfTagInfoArrayList() {
        if (productOfTagInfoArrayList == null) {
            productOfTagInfoArrayList = new ArrayList<>();
        }
        return productOfTagInfoArrayList;

    }

    public void callAPILoginPasserger(UserInfo userInfo, String securityCode, String oldSessionId) {

        //Response
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    try {
                        JSONObject data = result.getJSONObject("result").getJSONObject("data");
                        boolean isLogin = data.getBoolean("login");
                        if (isLogin) {
                            Gson gson = new Gson();
                            //Parse json from result
                            setLoginResponse(gson.fromJson(result.toString(), LoginResponse.class));

                            CurrentUserInfo currentUserInfo = new CurrentUserInfo();
                            currentUserInfo.setCompany(getLoginResponse().result.data.company);
                            currentUserInfo.setOperatorid(getLoginResponse().result.data.operatorid);
                            currentUserInfo.setTabname(getLoginResponse().result.data.tabname);
                            currentUserInfo.setUserName(getLoginResponse().result.data.operatorname);
                            currentUserInfo.setUserName(getLoginResponse().result.data.operatorname);
                            currentUserInfo.setEmployee(getLoginResponse().result.data.employee);
                            setCurrentUserInfo(currentUserInfo);
                            notifyObserversOfEvent(ModelEvent.LOGIN_SUCCESS);
                        } else {
                            String message = result.getJSONObject("result").getString("message");
                            Log.i("Message", "" + message);
                            if (message.contains("Logon ")) {
                                notifyObserversOfEvent(ModelEvent.LOGIN_ALREADY);
                            } else {
                                notifyObserversOfEvent(ModelEvent.LOGIN_FAIL);
                            }


                        }

                    } catch (Exception ex) {
                        Log.e("Error ", "" + ex);
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);

            }

            @Override
            public void onComplete() {

            }
        });

        ActionRequest actionRequest = new ActionRequest("ConnectDB", "getConfig");

        //Request API
        if (oldSessionId == null) {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.id = 1;
            loginRequest.password = userInfo.getPassword();
            loginRequest.username = userInfo.getEmail();
            loginRequest.securitycode = securityCode;
            APIConnectionRequest.API_LOGIN(callBackDone, loginRequest, actionRequest);
        } else {
            LoginWithSessionRequest loginRequest = new LoginWithSessionRequest();
            loginRequest.id = 1;
            loginRequest.password = userInfo.getPassword();
            loginRequest.username = userInfo.getEmail();
            loginRequest.securitycode = securityCode;
            loginRequest.oldSessionId = oldSessionId;
            APIConnectionRequest.API_LOGIN(callBackDone, loginRequest, actionRequest);
        }

    }

    public void getRoutes() {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetRoutesResponse response = gson.fromJson(result.toString(), GetRoutesResponse.class);

                    //get idpassenger from json to setIdPassenger.
                    if (response.result.success) {
                        getRouteInfoArrayList().clear();
                        for (GetRoutesResponse.Data data : response.result.data) {
                            RouteInfo route = new RouteInfo(data.code, data.name);
                            getRouteInfoArrayList().add(route);
                        }
                        notifyObserversOfEvent(ModelEvent.GET_ROUTE_SUCCESS);
                    }

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
                setMessageError(error.getMessage());
                setTitle(error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API
        GetRoutesRequest commentRequest = new GetRoutesRequest();
        commentRequest.category = "ROUTE";

        ActionRequest actionRequest = new ActionRequest("FrmCsCodeDictionary", "get");
        APIConnectionRequest.API_GET_ROUTE(callBackDone, commentRequest, actionRequest);

    }

    public void getAgencies(String value, double latitute, double longtitute) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();

                    //Parse json from result
                    GetAgencisResponse response = gson.fromJson(result.toString(), GetAgencisResponse.class);

                    //get idpassenger from json to setIdPassenger.
                    if (response.result.success) {
                        getAgencyInfoArrayList().clear();
                        for (GetAgencisResponse.Data data : response.result.data) {
                            AgencyInfo agency = new AgencyInfo(data.nadcode, data.name, data.address5, data.address6);
                            getAgencyInfoArrayList().add(agency);
                        }
                        notifyObserversOfEvent(ModelEvent.GET_AGENCY_SUCCESS);
                    }

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
                setMessageError(error.getMessage());
                setTitle(error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API
        GetAgencyRequest commentRequest = new GetAgencyRequest();
        commentRequest.fixedFilters.add(commentRequest.new Filter("anal_n9", "eq", value, "string"));
        commentRequest.fixedFilters.add(commentRequest.new Filter("anal_n0", "eq", loginResponse.result.data.employee, "string"));
        ArrayList<Double> latlong = new ArrayList<>();
        latlong.add(latitute);
        latlong.add(longtitute);
        latlong.add(Double.valueOf(50));
        commentRequest.fixedFilters.add(commentRequest.new Filter("address5,address6", "geo",latlong, "string" ));

        ActionRequest actionRequest = new ActionRequest("FrmCsNameAddress", "get");
        //Call BaseUrl to run this
        APIConnectionRequest.API_GET_STORE(callBackDone, commentRequest, actionRequest);

    }

    public void getAgencyCheckin(String nadcode, String latitute, String longtitute, String checkindate) {
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    GetCheckinAgencyResponse response = gson.fromJson(result.toString(), GetCheckinAgencyResponse.class);

                    //get idpassenger from json to setIdPassenger.
                    if (response.result.success) {
                        notifyObserversOfEvent(ModelEvent.POST_CHECKIN_AGENCY_SUCCESS);
                    }
                    //Parse json from result

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
                setMessageError(error.getMessage());
                setTitle(error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        GetCheckinAgencyRequest getCheckinAgencyRequest = new GetCheckinAgencyRequest();
        getCheckinAgencyRequest.formid = "checkin";
        getCheckinAgencyRequest.systemno = "";
        getCheckinAgencyRequest.nadcode = nadcode;
        getCheckinAgencyRequest.operator = loginResponse.result.data.employee ;
        getCheckinAgencyRequest.latitude = latitute;
        getCheckinAgencyRequest.longitude = longtitute;
        getCheckinAgencyRequest.orderno = getOrderno();

        ActionRequest actionRequest = new ActionRequest("FrmFdUserForm", "add");
        APIConnectionRequest.API_GET_AGENCY_CHECKIN(callBackDone, getCheckinAgencyRequest, actionRequest);
    }

    public void getProducts() {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();

                    //Parse json from result
                    GetProductResponse response = gson.fromJson(result.toString(), GetProductResponse.class);

                    //get idpassenger from json to setIdPassenger.
                    if (response.result.success) {
                        getProductInfoArrayList().clear();
                        for (GetProductResponse.Data data : response.result.data) {
                            ProductInfo product = new ProductInfo(data.itemcode, data.itemname, data.baseprice1);
                            getProductInfoArrayList().add(product);
                        }
                        notifyObserversOfEvent(ModelEvent.GET_LIST_PRODUCT_SUCCESS);
                    }


                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
                setMessageError(error.getMessage());
                setTitle(error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API
        GetProductRequest commentRequest = new GetProductRequest();

        ActionRequest actionRequest = new ActionRequest("FrmIcItem", "get");
        APIConnectionRequest.API_GET_PRODUCT(callBackDone, commentRequest, actionRequest);

    }

    public void getPromotions() {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();

                    //Parse json from result
                    GetPromotionResponse response = gson.fromJson(result.toString(), GetPromotionResponse.class);

                    //get idpassenger from json to setIdPassenger.
                    if (response.result.success) {
                        getPromotionInfoArrayList().clear();
                        for (GetPromotionResponse.Data data : response.result.data) {
                            PromotionInfo promotion = new PromotionInfo(data.promotioncode, data.name, data.price);
                            getPromotionInfoArrayList().add(promotion);
                        }
                        notifyObserversOfEvent(ModelEvent.GET_LIST_PROMOTION_SUCCESS);
                    }


                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
                setMessageError(error.getMessage());
                setTitle(error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API
        GetPromotionRequest commentRequest = new GetPromotionRequest();

        ActionRequest actionRequest = new ActionRequest("FrmFdUserForm", "get");
        APIConnectionRequest.API_GET_PRODUCT(callBackDone, commentRequest, actionRequest);

    }

    public void getProductsOfTag() {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();

                    //Parse json from result
                    GetProductOfTagResponse response = gson.fromJson(result.toString(), GetProductOfTagResponse.class);

                    //get idpassenger from json to setIdPassenger.
                    if (response.result.success) {
                        getProductOfTagInfoArrayList().clear();
                        for (GetProductOfTagResponse.Data data : response.result.data) {
                            ProductOfTagInfo promotion = new ProductOfTagInfo(data.promotioncode, data.name, data.price);
                            getProductOfTagInfoArrayList().add(promotion);
                        }
                        notifyObserversOfEvent(ModelEvent.GET_LIST_PRODUCT_TAG_SUCCESS);
                    }


                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
                setMessageError(error.getMessage());
                setTitle(error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API
        GetProductOfTagRequest commentRequest = new GetProductOfTagRequest();
        commentRequest.formid = "promotion";
        commentRequest.fixedFilters.add(commentRequest.new Filter("type", "eq", "T", "string"));

        ActionRequest actionRequest = new ActionRequest("FrmFdUserForm", "get");
        APIConnectionRequest.API_GET_PRODUCT(callBackDone, commentRequest, actionRequest);

    }

    public void submit(SubmitFormat submitFormat) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();

                    //Parse json from result
                    GetSubmitResponse response = gson.fromJson(result.toString(), GetSubmitResponse.class);

                    //get idpassenger from json to setIdPassenger.
                    if (response.result.success) {
                        setOrderno("");
                        if(response.result.data.size() > 0) {
                            setOrderno(response.result.data.get(0).orderno);
                        }
                        notifyObserversOfEvent(ModelEvent.POST_SUBMIT_SUCCESS);
                    }


                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
                setMessageError(error.getMessage());
                setTitle(error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API
        SubmitRequest commentRequest = new SubmitRequest(submitFormat);


        ActionRequest actionRequest = new ActionRequest("FrmSoTransHeader", "add");
        APIConnectionRequest.API_SUBMIT(callBackDone, commentRequest, actionRequest);
    }
}
