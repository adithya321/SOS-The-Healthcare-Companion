/*
 * SOS
 * Copyright (C) 2016  zDuo (Adithya J, Vazbloke)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.zduo.sos;

import android.util.Log;

import com.zduo.sos.hasura.auth.AuthService;
import com.zduo.sos.hasura.db.DBService;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class Hasura {
    public final static OkHttpClient okHttpClient = buildOkHttpClient();
    public final static AuthService auth = new AuthService("https://auth.smelting86.hasura-app.io/", okHttpClient);
    public final static DBService db = new DBService("https://data.smelting86.hasura-app.io/v1/query", "", okHttpClient);
    private static final Hasura currentCtx = new Hasura();
    private Integer userId;
    private String sessionId;
    private String role;

    public static Hasura getInstance() {
        return currentCtx;
    }

    static OkHttpClient buildOkHttpClient() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(new HasuraTokenInterceptor())
                .addInterceptor(logging)
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
    }

    public static Integer getCurrentUserId() {
        return currentCtx.userId;
    }

    public static String getCurrentRole() {
        return currentCtx.role;
    }

    public static String getCurrentSessionId() {
        return currentCtx.sessionId;
    }

    public static void setUserId(Integer userId) {
        currentCtx.userId = userId;
    }

    public static void setSessionId(String sessionId) {
        currentCtx.sessionId = sessionId;
    }

    public static void setRole(String role) {
        currentCtx.role = role;
    }

    public static void setCurrentSession(Integer userId, String role, String sessionId) {
        Log.d("{{HASURA :: AUTH", "Setting current session");

        if (role == null) {
            role = "anonymous";
        }

        currentCtx.userId = userId;
        currentCtx.sessionId = sessionId;
        currentCtx.role = role;
    }
}
