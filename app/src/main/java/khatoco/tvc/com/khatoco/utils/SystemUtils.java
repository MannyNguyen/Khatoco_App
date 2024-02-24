package khatoco.tvc.com.khatoco.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;

import khatoco.tvc.com.khatoco.ui.objects.UserInfo;


/**
 * Created by Quysunam on 11/10/2016.
 */

public class SystemUtils {


    public static void hideSoftKeyboardForEditText(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void saveRememberMe(boolean isCheck, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("rememberMe", isCheck);
        editor.commit();
    }

    public static Boolean getRememberMe(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getBoolean("rememberMe", false);
    }

    public static void saveUserInfo(UserInfo userInfo, Context context) {
        Gson gson = new Gson();
        String json = gson.toJson(userInfo);
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userInfo", json);
        editor.commit();
    }

    public static UserInfo getUserInfo(Context context) {
        Gson gson = new Gson();
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String userInfo = pref.getString("userInfo", null);
        if(userInfo == null)
            return null;
        return gson.fromJson(userInfo, UserInfo.class);
    }

    public static void saveSessionId(String sessionId, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("sessionId", sessionId);
        editor.commit();
    }

    public static String getSessionId(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getString("sessionId", null);
    }

}
