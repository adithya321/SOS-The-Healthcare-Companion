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

package com.zduo.sos.tools;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;

import com.zduo.sos.R;
import com.zduo.sos.activity.AddGlucoseActivity;

import java.util.Random;

public class GlucosioNotificationManager {
    //private static final String REMOTE_INPUT_KEY = "glucosio_remote_key";
    private static final int NOTIFICATION_ID = 11;
    private Context context;

    public GlucosioNotificationManager(Context context) {
        this.context = context;
    }

    public void sendReminderNotification(String label) {
        String notificationTitle = label + " " + "\u23f0";
        String[] arrayString = context.getResources().getStringArray(R.array.reminder_title_array);
        String notificationText = arrayString[generateRandomNumber(0, 1)];
        //String NOTIFICATION_ACTION = context.getString(R.string.reminders_notification_action);

        Intent intent = new Intent(context, AddGlucoseActivity.class);
        intent.putExtra("glucose_reminder_notification", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder notificationBuilder;
/*
        // ADD LATER TO SUPPORT NOUGAT DIRECT REPLY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            RemoteInput remoteInput = new RemoteInput.Builder(REMOTE_INPUT_KEY)
                    .setLabel(NOTIFICATION_ACTION)
                    .build();

            Notification.Action actionNotification = new Notification.Action.Builder(
                    R.drawable.ic_stat_glucosio,
                    NOTIFICATION_ACTION, pendingIntent)
                    .addRemoteInput(remoteInput)
                    .build();

            notification = new Notification.Builder(context)
                    .setContentTitle("\u23f0")
                    .setContentText(NOTIFICATION_TEXT)
                    .setSmallIcon(R.drawable.ic_stat_glucfosio)
                    .setColor(context.getColor(R.color.glucosio_pink))
                    .setActions(actionNotification)
                    .build();
        } else {*/
        notificationBuilder = new Notification.Builder(context)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1000, 1000})
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_stat_glucosio);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notificationBuilder.setColor(context.getColor(R.color.glucosio_pink));
        }

        Notification notification = notificationBuilder.build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, notification);
    }

    private int generateRandomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }
}
