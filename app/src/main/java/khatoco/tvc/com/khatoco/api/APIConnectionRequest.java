package khatoco.tvc.com.khatoco.api;

import android.util.Log;

import khatoco.tvc.com.khatoco.api.object_request_api.ActionRequest;
import khatoco.tvc.com.khatoco.ui.enums.API_Method;
import khatoco.tvc.com.khatoco.utils.Config;

/**
 * Created by prosoft on 1/11/16.
 */
public class APIConnectionRequest {

    public static void API_LOGIN(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_LOGIN);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_LOGIN, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_ROUTE(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_ROUTE);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_ROUTE, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_STORE(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_STORE);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_STORE, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_AGENCY(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_AGENCY);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_AGENCY, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_AGENCY_CHECKIN(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest){
        Log.v("TestLog", Config.BASEURL + Config.API_GET_AGENCY_CHECKIN);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_AGENCY_CHECKIN, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_PRODUCT(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_PRODUCT);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_PRODUCT, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_PROMOTION(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_PROMOTION);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_PROMOTION, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_SUBMIT(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_SUBMIT);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_SUBMIT, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }
}
