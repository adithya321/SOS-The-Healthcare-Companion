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

import android.app.Application;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.karumi.dexter.Dexter;
import com.zduo.sos.db.DatabaseHandler;
import com.zduo.sos.models.Reminder;
import com.zduo.sos.tools.Preferences;
import com.zduo.sos.utils.NotificationUtil;

public class MyApplication extends Application {

    private static MyApplication sInstance;
    @Nullable
    private Preferences preferences;

    public static MyApplication getInstance() {
        if (sInstance == null) {
            sInstance = new MyApplication();
        }
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Dexter.initialize(getApplicationContext());
        sInstance = this;
    }

    public void createSOSNotification() {
        SharedPreferences sharedPreferences = getSharedPreferences("Profile", 0);
        if (sharedPreferences.getBoolean("sos", true)) {
            Reminder reminder = new Reminder();
            reminder.setId(999999);
            reminder.setColour("#315e97");
            reminder.setTitle("SOS");
            reminder.setIcon("ic_notification");

            NotificationUtil.createSOSNotification(this, reminder);
        } else
            NotificationUtil.cancelNotification(this, 999999);
    }

    @NonNull
    public DatabaseHandler getDBHandler() {
        return new DatabaseHandler(getApplicationContext());
    }
}
