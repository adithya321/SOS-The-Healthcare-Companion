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

package com.zduo.sos.receivers;

import android.content.Context;
import android.content.Intent;

import com.zduo.sos.tools.GlucosioAlarmManager;
import com.zduo.sos.tools.GlucosioNotificationManager;

public class GlucosioBroadcastReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            setAlarms(context);
        } else {
            if (intent.getBooleanExtra("glucosio_reminder", false)) {
                GlucosioNotificationManager notificationManager = new GlucosioNotificationManager(context);
                String reminderLabel = intent.getStringExtra("reminder_label");
                notificationManager.sendReminderNotification(reminderLabel);
            } else {
                setAlarms(context);
            }
        }
    }

    private void setAlarms(Context context) {
        GlucosioAlarmManager alarmManager = new GlucosioAlarmManager(context);
        alarmManager.setAlarms();
    }
}