package com.nupuit.vetmed.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by razon30 on 07-04-17.
 */

public class SharedPrefsSingleton {

    private static SharedPrefsSingleton sharedPreferencesGlobal;
    private SharedPreferences sharedPreferences;

    public static SharedPrefsSingleton getInstance(Context context) {
        if (sharedPreferencesGlobal == null) {
            sharedPreferencesGlobal = new SharedPrefsSingleton(context);
        }
        return sharedPreferencesGlobal;
    }

    private SharedPrefsSingleton(Context context) {
        sharedPreferences = context.getSharedPreferences("nupuit",Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public void saveBoolean(String key, Boolean bool){
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, bool);
        prefsEditor.apply();
    }

    public String getString(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "0");
        }
        return "0";
    }

    public Boolean getBoolean(String key){

        if(sharedPreferences!= null){
            return  sharedPreferences.getBoolean(key, false);
        }
        return false;
    }

    public void clearData(){
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.clear();
        prefsEditor.apply();
    }

}