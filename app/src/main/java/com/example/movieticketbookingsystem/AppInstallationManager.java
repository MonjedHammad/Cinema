package com.example.movieticketbookingsystem;

import android.content.Context;
import android.content.SharedPreferences;

public class AppInstallationManager {

    private static final String PREF_NAME = "app_installation_prefs";
    private static final String PREF_KEY_FIRST_INSTALL = "is_first_install";

    private SharedPreferences sharedPreferences;

    public AppInstallationManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isFirstInstall() {
        return sharedPreferences.getBoolean(PREF_KEY_FIRST_INSTALL, true);
    }

    public void setFirstInstall(boolean isFirstInstall) {
        sharedPreferences.edit().putBoolean(PREF_KEY_FIRST_INSTALL, isFirstInstall).apply();
    }
}

