package khatoco.tvc.com.khatoco.ui.activity;

import com.jaredrummler.materialspinner.MaterialSpinner;

import android.Manifest;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import khatoco.tvc.com.khatoco.R;
import khatoco.tvc.com.khatoco.service.GPSTracker;
import khatoco.tvc.com.khatoco.ui.Model;
import khatoco.tvc.com.khatoco.ui.adapter.ItemAdapter;
import khatoco.tvc.com.khatoco.ui.enums.ModelEvent;
import khatoco.tvc.com.khatoco.ui.objects.AgencyInfo;
import khatoco.tvc.com.khatoco.ui.objects.ItemProduct;
import khatoco.tvc.com.khatoco.ui.objects.ProductFormat;
import khatoco.tvc.com.khatoco.ui.objects.ProductInfo;
import khatoco.tvc.com.khatoco.ui.objects.SubmitFormat;
import khatoco.tvc.com.khatoco.utils.SystemUtils;
import khatoco.tvc.com.khatoco.utils.Utilities;

public class MainActivity extends BaseActivity {

    private MaterialSpinner spinner_rout, spinner_store, spinner_product_name, spinner_tag_product, spinner_promotion;
    private EditText edt_number_product, edt_number_tag_product, edt_number_promotion;
    private TextView tv_title_main_product, vt_title_tag_product, vt_title_title_promotion, txt_price;
    private View line_title_main_product, line_title_tag_product, line_title_title_promotion;
    private ImageView arrow_down;
    private Button btnAdd;
    private List<ItemProduct> itemProductList = new ArrayList<>();
    private LinearLayout ll_title_main_product, ll_product, ll_main_product;
    private LinearLayout ll_tag_product, ll_title_tag_product, ll_content_tag_product;
    private LinearLayout ll_promotion, ll_content_promotion, ll_title_promotion;
    private LinearLayout ll_store;
    private ItemAdapter itemAdapter;
    private String[] route, agencis, products, promotions, productoftag;
    private RecyclerView rvProduct;
    private CheckBox chkPromotion;
    private Toolbar toolbar;
    private ImageButton imgBtnCheckin;
    private Button btn_submit;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private String currentCodeOfRoute, currentCodeOfWard;
    GPSTracker gps;
    private int fullResponse = 0;
    private int stateOfAddView = 0;
    private Calendar dobCalendar = Calendar.getInstance();
    private int finalMount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addControl();
        addEvent();

//        areThereMockPermissionApps(MainActivity.this);
//        isMockSettingsON(this);
        showPermissionForUser();
        gpsLocation();

    }

    private void addEvent() {
        hideKeyboard();
        txt_price.setText(String.valueOf(finalMount));
        getBusyIndicator(R.string.please_wait).show();
        Model.getInstance().getRoutes();
        Model.getInstance().getProducts();
        Model.getInstance().getPromotions();
        Model.getInstance().getProductsOfTag();

        spinner_rout.setItems("Tuyến ---");
        spinner_store.setItems("Cửa hàng ---");
        spinner_product_name.setItems("Sản phẩm ---");
        spinner_promotion.setItems("Sản phẩm KM ---");
        spinner_tag_product.setItems("Sản phẩm đổi tem ---");

        //RouteIndex
        spinner_rout.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position > 0) {
                    currentCodeOfRoute = Model.getInstance().getRouteInfoArrayList().get(position - 1).getCode();
                    getAgencis();

                }
            }
        });

        spinner_store.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position > 0 && currentCodeOfRoute != null) {

                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rvProduct.setVisibility(View.VISIBLE);
                if (stateOfAddView == 3) {
                    addPromotion();
                } else if (stateOfAddView == 1) {
                    addProduct();
                } else if (stateOfAddView == 2) {
                    addTagProduct();
                }


            }
        });

        rvProduct.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (ll_store.getVisibility() != View.GONE) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_up_out);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            ll_store.setVisibility(View.GONE);
                            spinner_rout.setVisibility(View.GONE);
                            spinner_store.setVisibility(View.GONE);
                            arrow_down.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    ll_store.startAnimation(animation);

                }

                stateOfAddView = 0;
                ll_content_tag_product.setVisibility(View.GONE);
                ll_content_promotion.setVisibility(View.GONE);
                ll_product.setVisibility(View.GONE);
                vt_title_tag_product.setSelected(false);
                vt_title_title_promotion.setSelected(false);
                tv_title_main_product.setSelected(false);
                tv_title_main_product.setTypeface(tv_title_main_product.getTypeface(), Typeface.NORMAL);
                vt_title_tag_product.setTypeface(vt_title_tag_product.getTypeface(), Typeface.NORMAL);
                vt_title_title_promotion.setTypeface(vt_title_title_promotion.getTypeface(), Typeface.NORMAL);
                ll_main_product.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_promotion.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_tag_product.setBackgroundColor(getResources().getColor(R.color.transparent));
                line_title_main_product.setBackgroundResource(R.drawable.view_color);
                line_title_tag_product.setBackgroundResource(R.drawable.view_color);
                line_title_title_promotion.setBackgroundResource(R.drawable.view_color);

                return false;
            }
        });

        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (ll_store.getVisibility() == View.GONE) {
                    Animation animblink = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_down_in);

                    ll_store.startAnimation(animblink);
                    ll_store.setVisibility(View.VISIBLE);
                    spinner_rout.setVisibility(View.VISIBLE);
                    spinner_store.setVisibility(View.VISIBLE);
                    arrow_down.setVisibility(View.GONE);
                }
                return false;
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doSubmit();

            }
        });

        ll_title_main_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddMainProduct();
            }
        });

        ll_title_tag_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTagProduct();
            }
        });

        ll_promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPromotionProduct();
            }
        });
    }

    private void addControl() {
        ll_title_main_product = (LinearLayout) findViewById(R.id.ll_title_main_product);
        ll_product = (LinearLayout) findViewById(R.id.ll_product);
        ll_main_product = (LinearLayout) findViewById(R.id.ll_main_product);
        tv_title_main_product = (TextView) findViewById(R.id.tv_title_main_product);
        line_title_main_product = (View) findViewById(R.id.line_title_main_product);

        ll_title_tag_product = (LinearLayout) findViewById(R.id.ll_title_tag_product);
        ll_tag_product = (LinearLayout) findViewById(R.id.ll_tag_product);
        ll_content_tag_product = (LinearLayout) findViewById(R.id.ll_content_tag_product);
        vt_title_tag_product = (TextView) findViewById(R.id.vt_title_tag_product);
        line_title_tag_product = (View) findViewById(R.id.line_title_tag_product);

        ll_promotion = (LinearLayout) findViewById(R.id.ll_promotion);
        ll_content_promotion = (LinearLayout) findViewById(R.id.ll_content_promotion);
        ll_title_promotion = (LinearLayout) findViewById(R.id.ll_title_promotion);
        vt_title_title_promotion = (TextView) findViewById(R.id.vt_title_title_promotion);
        line_title_title_promotion = (View) findViewById(R.id.line_title_title_promotion);

        spinner_rout = (MaterialSpinner) findViewById(R.id.spinner_rout);
        spinner_store = (MaterialSpinner) findViewById(R.id.spinner_store);

        spinner_product_name = (MaterialSpinner) findViewById(R.id.spinner_product_name);
        spinner_tag_product = (MaterialSpinner) findViewById(R.id.spinner_tag_product);
        spinner_promotion = (MaterialSpinner) findViewById(R.id.spinner_promotion);

        ll_store = (LinearLayout) findViewById(R.id.ll_store);
        btnAdd = (Button) findViewById(R.id.btn_add);
        rvProduct = (RecyclerView) findViewById(R.id.rv_product);

        arrow_down = (ImageView) findViewById(R.id.arrow_down);

        edt_number_product = (EditText) findViewById(R.id.edt_number_product);
        edt_number_promotion = (EditText) findViewById(R.id.edt_number_promotion);
        edt_number_tag_product = (EditText) findViewById(R.id.edt_number_tag_product);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        imgBtnCheckin = (ImageButton) findViewById(R.id.imgbtn_checkin);
        txt_price = (TextView) findViewById(R.id.txt_price);
        itemAdapter = new ItemAdapter(itemProductList, this);
        rvProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvProduct.setItemAnimator(new DefaultItemAnimator());

        rvProduct.setAdapter(itemAdapter);
    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);

        if (evt == ModelEvent.GET_ROUTE_SUCCESS) {
            fullResponse++;
            route = new String[Model.getInstance().getRouteInfoArrayList().size() + 1];
            route[0] = "Chọn tuyến cửa  ---";
            for (int m = 0; m < Model.getInstance().getRouteInfoArrayList().size(); m++) {
                route[m + 1] = Model.getInstance().getRouteInfoArrayList().get(m).getName();
            }
            spinner_rout.setItems(route);
        } else if (evt == ModelEvent.GET_AGENCY_SUCCESS) {
            agencis = new String[Model.getInstance().getAgencyInfoArrayList().size() + 1];
            agencis[0] = "Chọn cửa hàng ---";
            for (int m = 0; m < Model.getInstance().getAgencyInfoArrayList().size(); m++) {
                agencis[m + 1] = Model.getInstance().getAgencyInfoArrayList().get(m).getName();
            }
            spinner_store.setSelectedIndex(0);
            spinner_store.setMaxHeight(280);
            spinner_store.setItems(agencis);
        } else if (evt == ModelEvent.GET_LIST_PRODUCT_SUCCESS) {
            fullResponse++;
            products = new String[Model.getInstance().getProductInfoArrayList().size() + 1];
            products[0] = "Tên sản phẩm ---";
            for (int m = 0; m < Model.getInstance().getProductInfoArrayList().size(); m++) {
                products[m + 1] = Model.getInstance().getProductInfoArrayList().get(m).getName();
            }
            spinner_product_name.setItems(products);
        } else if (evt == ModelEvent.GET_LIST_PROMOTION_SUCCESS) {
            fullResponse++;
            promotions = new String[Model.getInstance().getPromotionInfoArrayList().size() + 1];
            promotions[0] = "Tên sản phẩm khuyến mãi---";
            for (int m = 0; m < Model.getInstance().getPromotionInfoArrayList().size(); m++) {
                promotions[m + 1] = Model.getInstance().getPromotionInfoArrayList().get(m).getName();
            }
            spinner_promotion.setItems(promotions);
        } else if (evt == ModelEvent.GET_LIST_PRODUCT_TAG_SUCCESS) {
            fullResponse++;
            productoftag = new String[Model.getInstance().getProductOfTagInfoArrayList().size() + 1];
            productoftag[0] = "Tên sản phẩm đổi ---";
            for (int m = 0; m < Model.getInstance().getProductOfTagInfoArrayList().size(); m++) {
                productoftag[m + 1] = Model.getInstance().getProductOfTagInfoArrayList().get(m).getName();
            }
            spinner_tag_product.setItems(productoftag);
        } else if (evt == ModelEvent.POST_SUBMIT_SUCCESS) {
            Toast.makeText(this, "Submit successfully", Toast.LENGTH_SHORT).show();
            AgencyInfo agencyInfo = Model.getInstance().getAgencyInfoArrayList().get(spinner_store.getSelectedIndex() - 1);
            Model.getInstance().getAgencyCheckin(agencyInfo.getCode(), agencyInfo.getLatitute(), agencyInfo.getLongtitute(), updateDob());
            return;
        } else if (evt == ModelEvent.POST_CHECKIN_AGENCY_SUCCESS) {
            Toast.makeText(this, "Check in successfully", Toast.LENGTH_SHORT).show();
        }

        if (fullResponse >= 4) {Log.i("qwert","qwert");
            getBusyIndicator(R.string.please_wait).dismiss();
        }
    }

    //Check mock location is ON
    public void isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {
        } else {
            showMockAlert();
            return;
        }
    }

    public void showMockAlert() {
        //Note: context and this, know activity to dialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        // Setting Dialog Title
        alertDialog.setTitle(getString(R.string.title_mock_location));

        // Setting Dialog Message
        alertDialog.setMessage(getString(R.string.mock_location_off));

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                MainActivity.this.startActivity(intent);
            }
        });

        // on pressing cancel button
//        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
        // Showing Alert Message
        alertDialog.show();
    }

    private void gpsLocation() {

        imgBtnCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinner_rout.getSelectedIndex() == 0) {
                    spinner_rout.setError("Choose option");
                    if (ll_store.getVisibility() == View.GONE) {
                        Animation animblink = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.slide_down_in);

                        ll_store.startAnimation(animblink);
                        ll_store.setVisibility(View.VISIBLE);
                        spinner_rout.setVisibility(View.VISIBLE);
                        spinner_store.setVisibility(View.VISIBLE);
                        arrow_down.setVisibility(View.GONE);
                    }
                    return;
                } else {
                    spinner_rout.setError(null);
                }

                if (spinner_store.getSelectedIndex() == 0) {
                    if (ll_store.getVisibility() == View.GONE) {
                        Animation animblink = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.slide_down_in);

                        ll_store.startAnimation(animblink);
                        ll_store.setVisibility(View.VISIBLE);
                        spinner_rout.setVisibility(View.VISIBLE);
                        spinner_store.setVisibility(View.VISIBLE);
                        arrow_down.setVisibility(View.GONE);
                    }
                    spinner_store.setError("Choose option");
                    return;
                } else {
                    spinner_store.setError(null);
                }
                AgencyInfo agencyInfo = Model.getInstance().getAgencyInfoArrayList().get(spinner_store.getSelectedIndex() - 1);
                Model.getInstance().getAgencyCheckin(agencyInfo.getCode(), agencyInfo.getLatitute(), agencyInfo.getLongtitute(), updateDob());
                getBusyIndicator(R.string.please_wait).show();
            }
        });

    }

    public void getAgencis() {
//        if (Settings.Secure.getString(MainActivity.this.getContentResolver(),
//                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {
        gps = new GPSTracker(this);
        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            //Request API
            Model.getInstance().getAgencies(currentCodeOfRoute, latitude, longitude);
            getBusyIndicator(R.string.please_wait).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settingsa
            gps.showSettingsAlert();
            spinner_rout.setSelectedIndex(0);
        }

//        } else {
//            showMockAlert();
//            spinner_rout.setSelectedIndex(0);
//        }
//        isMockSettingsON(MainActivity.this);
    }

    private void hideKeyboard() {

        //Hide keyboard when tap on MaterialSpinner
        spinner_rout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(spinner_rout.getWindowToken(), 0);
                return false;
            }
        });

        spinner_store.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SystemUtils.hideSoftKeyboardForEditText(spinner_store);
                return false;
            }
        });

        spinner_product_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SystemUtils.hideSoftKeyboardForEditText(spinner_product_name);
                return false;
            }
        });

        spinner_tag_product.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SystemUtils.hideSoftKeyboardForEditText(spinner_tag_product);
                return false;
            }
        });

        spinner_promotion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SystemUtils.hideSoftKeyboardForEditText(spinner_promotion);
                return false;
            }
        });

        //hide keyboard product
        ll_title_main_product.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SystemUtils.hideSoftKeyboardForEditText(ll_title_main_product);
                return false;
            }
        });

        ll_title_tag_product.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SystemUtils.hideSoftKeyboardForEditText(ll_title_tag_product);
                return false;
            }
        });

        ll_title_promotion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SystemUtils.hideSoftKeyboardForEditText(ll_title_promotion);
                return false;
            }
        });


    }

    public void addProduct() {
        if (spinner_product_name.getSelectedIndex() == 0) {
            spinner_product_name.setError("Chose option");
            return;
        } else {
            spinner_product_name.setError(null);
        }

        final String num = edt_number_product.getText().toString();
        if (num.isEmpty()) {
            edt_number_product.setError("Enter the number");
            return;
        } else {
            edt_number_product.setError(null);
        }
        String nameProduct = products[spinner_product_name.getSelectedIndex()];
        String codeProduct = Model.getInstance().getProductInfoArrayList().get(spinner_product_name.getSelectedIndex() - 1).getCode();
        int price = 0;
        for (int i = 0; i < Model.getInstance().getProductInfoArrayList().size(); i++) {
            if (nameProduct.equalsIgnoreCase(Model.getInstance().getProductInfoArrayList().get(i).getName())) {
                price = Integer.valueOf(Model.getInstance().getProductInfoArrayList().get(i).getPrice());
            }
        }

        final int totalPrice = price * Integer.valueOf(num);
        ItemProduct item = new ItemProduct(nameProduct, Integer.valueOf(num), totalPrice, price, 1, codeProduct);
        for (int i = 0; i < itemProductList.size(); i++) {
            final int position = i;
            if (itemProductList.get(position).getType() == 1) {
                if (itemProductList.get(i).getProductName().equalsIgnoreCase(item.getProductName())) {
                    showErrorMessage(R.string.caution_product_add, R.string.message, R.string.ok, R.string.cancel, new Runnable() {
                        @Override
                        public void run() {
                            ItemProduct product = itemProductList.get(position);
                            itemProductList.remove(position);
                            product.setPriceProduct(product.getPriceProduct() + totalPrice);
                            product.setNumberProduct(product.getNumberProduct() + Integer.valueOf(num));
                            itemProductList.add(product);
                            addItemWithType(null);
                            finalMount = finalMount + totalPrice;
                            itemAdapter.notifyDataSetChanged();
                            txt_price.setText(String.valueOf(finalMount));
                            SystemUtils.hideSoftKeyboardForEditText(btnAdd);
                        }
                    }, new Runnable() {
                        @Override
                        public void run() {

                        }
                    }).show();
                    return;
                }

            }
        }
        addItemWithType(item);
        finalMount = finalMount + totalPrice;
        itemAdapter.notifyDataSetChanged();
        txt_price.setText(String.valueOf(finalMount));
        SystemUtils.hideSoftKeyboardForEditText(btnAdd);
    }

    public void addPromotion() {
        if (spinner_promotion.getSelectedIndex() == 0) {
            spinner_promotion.setError("Chose option");
            return;
        } else {
            spinner_promotion.setError(null);
        }

        final String num = edt_number_promotion.getText().toString();
        if (num.isEmpty()) {
            edt_number_promotion.setError("Enter the number");
            return;
        } else {
            edt_number_promotion.setError(null);
        }
        String namePromotion = promotions[spinner_promotion.getSelectedIndex()];
        String codePromition = Model.getInstance().getPromotionInfoArrayList().get(spinner_promotion.getSelectedIndex() - 1).getCode();
        int price = 0;
        for (int i = 0; i < Model.getInstance().getPromotionInfoArrayList().size(); i++) {
            if (namePromotion.equalsIgnoreCase(Model.getInstance().getPromotionInfoArrayList().get(i).getName())) {
                price = Integer.valueOf(Model.getInstance().getPromotionInfoArrayList().get(i).getPrice());
            }
        }
        final int totalPrice = price * Integer.valueOf(num);
        ItemProduct item = new ItemProduct(namePromotion, Integer.valueOf(num), totalPrice, price, 3, codePromition);
        for (int i = 0; i < itemProductList.size(); i++) {
            final int position = i;
            if (itemProductList.get(position).getType() == 3) {
                if (itemProductList.get(i).getProductName().equalsIgnoreCase(item.getProductName())) {
                    showErrorMessage(R.string.caution_product_add, R.string.message, R.string.ok, R.string.cancel, new Runnable() {
                        @Override
                        public void run() {
                            ItemProduct product = itemProductList.get(position);
                            itemProductList.remove(position);
                            product.setPriceProduct(product.getPriceProduct() + totalPrice);
                            product.setNumberProduct(product.getNumberProduct() + Integer.valueOf(num));
                            itemProductList.add(product);
                            addItemWithType(null);
                            finalMount = finalMount + totalPrice;
                            itemAdapter.notifyDataSetChanged();
                            txt_price.setText(String.valueOf(finalMount));
                            SystemUtils.hideSoftKeyboardForEditText(btnAdd);
                        }
                    }, new Runnable() {
                        @Override
                        public void run() {

                        }
                    }).show();
                    return;
                }

            }
        }
        addItemWithType(item);
        finalMount = finalMount + totalPrice;
        itemAdapter.notifyDataSetChanged();
        txt_price.setText(String.valueOf(finalMount));
        SystemUtils.hideSoftKeyboardForEditText(btnAdd);

    }

    public void addTagProduct() {
        if (spinner_tag_product.getSelectedIndex() == 0) {
            spinner_tag_product.setError("Chose option");
            return;
        } else {
            spinner_tag_product.setError(null);
        }

        final String num = edt_number_tag_product.getText().toString();
        if (num.isEmpty()) {
            edt_number_tag_product.setError("Enter the number");
            return;
        } else {
            edt_number_tag_product.setError(null);
        }
        String nameTagProduct = productoftag[spinner_tag_product.getSelectedIndex()];
        String codeTagProduct = Model.getInstance().getProductOfTagInfoArrayList().get(spinner_tag_product.getSelectedIndex() - 1).getCode();
        int price = 0;
        for (int i = 0; i < Model.getInstance().getPromotionInfoArrayList().size(); i++) {
            if (nameTagProduct.equalsIgnoreCase(Model.getInstance().getPromotionInfoArrayList().get(i).getName())) {
                price = Integer.valueOf(Model.getInstance().getPromotionInfoArrayList().get(i).getPrice());
            }
        }
        final int totalPrice = price * Integer.valueOf(num);
        ItemProduct item = new ItemProduct(nameTagProduct, Integer.valueOf(num), totalPrice, price, 2, codeTagProduct);
        for (int i = 0; i < itemProductList.size(); i++) {
            final int position = i;
            if (itemProductList.get(position).getType() == 2) {
                if (itemProductList.get(i).getProductName().equalsIgnoreCase(item.getProductName())) {
                    showErrorMessage(R.string.caution_product_add, R.string.message, R.string.ok, R.string.cancel, new Runnable() {
                        @Override
                        public void run() {
                            ItemProduct product = itemProductList.get(position);
                            itemProductList.remove(position);
                            product.setPriceProduct(product.getPriceProduct() + totalPrice);
                            product.setNumberProduct(product.getNumberProduct() + Integer.valueOf(num));
                            itemProductList.add(product);
                            addItemWithType(null);
                            finalMount = finalMount + totalPrice;
                            itemAdapter.notifyDataSetChanged();
                            txt_price.setText(String.valueOf(finalMount));
                            SystemUtils.hideSoftKeyboardForEditText(btnAdd);
                        }
                    }, new Runnable() {
                        @Override
                        public void run() {

                        }
                    }).show();
                    return;
                }

            }
        }
        addItemWithType(item);
        finalMount = finalMount + totalPrice;
        itemAdapter.notifyDataSetChanged();
        txt_price.setText(String.valueOf(finalMount));
        SystemUtils.hideSoftKeyboardForEditText(btnAdd);

    }

    private void showAddMainProduct() {
        if (stateOfAddView != 1) {
            stateOfAddView = 1;
            Animation animblink = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_down_in);

            tv_title_main_product.setSelected(true);
            tv_title_main_product.setTypeface(tv_title_main_product.getTypeface(), Typeface.BOLD);
            ll_product.startAnimation(animblink);
            ll_product.setVisibility(View.VISIBLE);
            ll_main_product.setBackgroundColor(getResources().getColor(R.color.green_main));
            line_title_main_product.setBackgroundResource(R.drawable.view_color_focused);


            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up_out);

            vt_title_tag_product.setSelected(false);
            vt_title_title_promotion.setSelected(false);
            vt_title_tag_product.setTypeface(vt_title_tag_product.getTypeface(), Typeface.NORMAL);
            vt_title_title_promotion.setTypeface(vt_title_title_promotion.getTypeface(), Typeface.NORMAL);
            ll_content_tag_product.startAnimation(animation);
            ll_content_promotion.startAnimation(animation);
            ll_tag_product.setBackgroundColor(getResources().getColor(R.color.transparent));
            ll_promotion.setBackgroundColor(getResources().getColor(R.color.transparent));
            ll_content_tag_product.setVisibility(View.GONE);
            ll_content_promotion.setVisibility(View.GONE);
            line_title_tag_product.setBackgroundResource(R.drawable.view_color);
            line_title_title_promotion.setBackgroundResource(R.drawable.view_color);
        }


    }

    private void showAddTagProduct() {
        if (stateOfAddView != 2) {
            stateOfAddView = 2;
            Animation animblink = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_down_in);

            vt_title_tag_product.setSelected(true);
            vt_title_tag_product.setTypeface(vt_title_tag_product.getTypeface(), Typeface.BOLD);
            ll_content_tag_product.startAnimation(animblink);
            ll_content_tag_product.setVisibility(View.VISIBLE);
            ll_tag_product.setBackgroundColor(getResources().getColor(R.color.green_main));
            line_title_tag_product.setBackgroundResource(R.drawable.view_color_focused);


            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up_out);

            vt_title_title_promotion.setSelected(false);
            tv_title_main_product.setSelected(false);
            vt_title_title_promotion.setTypeface(vt_title_title_promotion.getTypeface(), Typeface.NORMAL);
            tv_title_main_product.setTypeface(tv_title_main_product.getTypeface(), Typeface.NORMAL);
            ll_product.startAnimation(animation);
            ll_content_promotion.startAnimation(animation);
            ll_main_product.setBackgroundColor(getResources().getColor(R.color.transparent));
            ll_promotion.setBackgroundColor(getResources().getColor(R.color.transparent));
            ll_product.setVisibility(View.GONE);
            ll_content_promotion.setVisibility(View.GONE);
            line_title_main_product.setBackgroundResource(R.drawable.view_color);
            line_title_title_promotion.setBackgroundResource(R.drawable.view_color);
        }


    }

    private void showAddPromotionProduct() {
        if (stateOfAddView != 3) {
            stateOfAddView = 3;
            Animation animblink = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_down_in);

            vt_title_title_promotion.setSelected(true);
            vt_title_title_promotion.setTypeface(vt_title_title_promotion.getTypeface(), Typeface.BOLD);
            ll_content_promotion.startAnimation(animblink);
            ll_content_promotion.setVisibility(View.VISIBLE);
            ll_promotion.setBackgroundColor(getResources().getColor(R.color.green_main));
            line_title_title_promotion.setBackgroundResource(R.drawable.view_color_focused);


            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up_out);

            vt_title_tag_product.setSelected(false);
            tv_title_main_product.setSelected(false);
            vt_title_tag_product.setTypeface(vt_title_tag_product.getTypeface(), Typeface.NORMAL);
            tv_title_main_product.setTypeface(tv_title_main_product.getTypeface(), Typeface.NORMAL);
            ll_product.startAnimation(animation);
            ll_content_tag_product.startAnimation(animation);
            ll_main_product.setBackgroundColor(getResources().getColor(R.color.transparent));
            ll_tag_product.setBackgroundColor(getResources().getColor(R.color.transparent));
            ll_product.setVisibility(View.GONE);
            ll_content_tag_product.setVisibility(View.GONE);
            line_title_main_product.setBackgroundResource(R.drawable.view_color);
            line_title_tag_product.setBackgroundResource(R.drawable.view_color);
        }


    }

    private void addItemWithType(final ItemProduct item) {
        ArrayList<ItemProduct> itemProducts = new ArrayList<>();
        if (item != null && item.getType() == 1) {
            itemProducts.add(item);
        }
        for (int i = 0; i < itemProductList.size(); i++) {
            if (itemProductList.get(i).getType() == 1) {
                itemProducts.add(itemProductList.get(i));
            }
        }
        if (item != null && item.getType() == 2) {
            itemProducts.add(item);
        }
        for (int i = 0; i < itemProductList.size(); i++) {
            if (itemProductList.get(i).getType() == 2) {
                itemProducts.add(itemProductList.get(i));
            }
        }
        if (item != null && item.getType() == 3) {
            itemProducts.add(item);
        }
        for (int i = 0; i < itemProductList.size(); i++) {
            if (itemProductList.get(i).getType() == 3) {
                itemProducts.add(itemProductList.get(i));
            }
        }
        itemProductList.clear();
        for (int i = 0; i < itemProducts.size(); i++) {
            itemProductList.add(itemProducts.get(i));
        }

    }

    private void doSubmit() {
        if (spinner_rout.getSelectedIndex() == 0) {
            spinner_rout.setError("Chose option");
            if (ll_store.getVisibility() == View.GONE) {
                Animation animblink = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_down_in);

                ll_store.startAnimation(animblink);
                ll_store.setVisibility(View.VISIBLE);
                spinner_rout.setVisibility(View.VISIBLE);
                spinner_store.setVisibility(View.VISIBLE);
                arrow_down.setVisibility(View.GONE);
            }
            return;
        } else {
            spinner_rout.setError(null);
        }

        if (spinner_store.getSelectedIndex() == 0) {
            if (ll_store.getVisibility() == View.GONE) {
                Animation animblink = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_down_in);

                ll_store.startAnimation(animblink);
                ll_store.setVisibility(View.VISIBLE);
                spinner_rout.setVisibility(View.VISIBLE);
                spinner_store.setVisibility(View.VISIBLE);
                arrow_down.setVisibility(View.GONE);
            }
            spinner_store.setError("Chose option");
            return;
        } else {
            spinner_store.setError(null);
        }

        if (itemProductList.size() == 0) {
            Toast.makeText(this, "We dont have any product to submit", Toast.LENGTH_SHORT).show();
            return;
        }

        String codeAgency = Model.getInstance().getAgencyInfoArrayList().get(spinner_store.getSelectedIndex() - 1).getCode();
        String nameAgency = Model.getInstance().getAgencyInfoArrayList().get(spinner_store.getSelectedIndex() - 1).getName();

        SubmitFormat submitFormat = new SubmitFormat();
        submitFormat.setOrderno("");
        submitFormat.setOrderdate(updateDob());
        submitFormat.setStatus("H");
        submitFormat.setCurrencycode("VND");
        submitFormat.setCurrencyrate(1);
        submitFormat.setCustomercode(codeAgency);
        submitFormat.setCustomername(nameAgency);
        submitFormat.setNetamount(finalMount);
        submitFormat.setDisamount(0);
        submitFormat.setFinamount(finalMount);
        submitFormat.setVatamount(0);
        submitFormat.setGrossamount(finalMount);
        submitFormat.setLatitude(spinner_store.getSelectedIndex() - 1);
        submitFormat.setCheckindate("");
        ArrayList<ProductFormat> list = new ArrayList<>();
        for (int i = 0; i < itemProductList.size(); i++) {
            ItemProduct item = itemProductList.get(i);
            ProductFormat productFormat = new ProductFormat();
            productFormat.setOrderno("");
            productFormat.setOrderline(i);
            productFormat.setItemcode(item.getCode());
            productFormat.setItemname(item.getProductName());
            if (item.getType() == 1) {
                productFormat.setValue9(0);
            } else if (item.getType() == 2) {
                productFormat.setValue9(2);
            } else if (item.getType() == 3) {
                productFormat.setValue9(1);
            }
            productFormat.setSalequantity(item.getNumberProduct());
            if (item.getType() == 1) {
                productFormat.setUnitprice(item.getPrice());
            } else {
                productFormat.setUnitprice(0);
            }
            productFormat.setNetamount(productFormat.getSalequantity() * productFormat.getUnitprice());
            productFormat.setDisamount(0);
            productFormat.setFinamount(productFormat.getNetamount() - productFormat.getDisamount());
            productFormat.setVatamount(0);
            productFormat.setGrossamount(productFormat.getFinamount() + productFormat.getVatamount());
            list.add(productFormat);
        }
        submitFormat.setProductFormatArrayList(list);
        Model.getInstance().submit(submitFormat);
        getBusyIndicator(R.string.please_wait).show();
    }

    private String updateDob() {
        String myFormat = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(dobCalendar.getTime());
    }

    public void caculateTotalMount(ItemProduct info) {
        finalMount = finalMount - info.getPriceProduct();
        txt_price.setText(String.valueOf(finalMount));
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return false;
            }

        }
        return true;
    }

    private void showPermissionForUser() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionsNeeded.add("GPS");
        }
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        }).show();
                return;
            }
            ActivityCompat.requestPermissions(MainActivity.this, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);

                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                } else {
                    showPermissionForUser();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
