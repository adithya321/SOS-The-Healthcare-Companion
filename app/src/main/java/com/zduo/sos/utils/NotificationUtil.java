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

package com.zduo.sos.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.zduo.sos.R;
import com.zduo.sos.activities.MainActivity;
import com.zduo.sos.activities.ViewActivity;
import com.zduo.sos.models.Reminder;
import com.zduo.sos.receivers.DismissReceiver;
import com.zduo.sos.receivers.NagReceiver;
import com.zduo.sos.receivers.SnoozeActionReceiver;

import java.util.Calendar;

public class NotificationUtil {

    public static void createNotification(Context context, Reminder reminder) {
        // Create intent for notification onClick behaviour
        Intent viewIntent = new Intent(context, ViewActivity.class);
        viewIntent.putExtra("NOTIFICATION_ID", reminder.getId());
        PendingIntent pending = PendingIntent.getActivity(context, reminder.getId(), viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create intent for notification snooze click behaviour
        Intent snoozeIntent = new Intent(context, SnoozeActionReceiver.class);
        snoozeIntent.putExtra("NOTIFICATION_ID", reminder.getId());
        PendingIntent pendingSnooze = PendingIntent.getBroadcast(context, reminder.getId(), snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int imageResId = context.getResources().getIdentifier(reminder.getIcon(), "drawable", context.getPackageName());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(imageResId)
                .setColor(Color.parseColor(reminder.getColour()))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(reminder.getContent()))
                .setContentTitle(reminder.getTitle())
                .setContentText(reminder.getContent())
                .setTicker(reminder.getTitle())
                .setContentIntent(pending);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (sharedPreferences.getBoolean("checkBoxNagging", false)) {
            Intent swipeIntent = new Intent(context, DismissReceiver.class);
            swipeIntent.putExtra("NOTIFICATION_ID", reminder.getId());
            PendingIntent pendingDismiss = PendingIntent.getBroadcast(context, reminder.getId(), swipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setDeleteIntent(pendingDismiss);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, sharedPreferences.getInt("nagMinutes", context.getResources().getInteger(R.integer.default_nag_minutes)));
            calendar.add(Calendar.SECOND, sharedPreferences.getInt("nagSeconds", context.getResources().getInteger(R.integer.default_nag_seconds)));
            Intent alarmIntent = new Intent(context, NagReceiver.class);
            AlarmUtil.setAlarm(context, alarmIntent, reminder.getId(), calendar);
        }

        String soundUri = sharedPreferences.getString("NotificationSound", "content://settings/system/notification_sound");
        if (soundUri.length() != 0) {
            builder.setSound(Uri.parse(soundUri));
        }
        if (sharedPreferences.getBoolean("checkBoxLED", true)) {
            builder.setLights(Color.BLUE, 700, 1500);
        }
        if (sharedPreferences.getBoolean("checkBoxOngoing", true)) {
            builder.setOngoing(true);
        }
        if (sharedPreferences.getBoolean("checkBoxVibrate", true)) {
            long[] pattern = {0, 300, 0};
            builder.setVibrate(pattern);
        }
        if (sharedPreferences.getBoolean("checkBoxMarkAsDone", true)) {
            Intent intent = new Intent(context, DismissReceiver.class);
            intent.putExtra("NOTIFICATION_ID", reminder.getId());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminder.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.addAction(R.drawable.ic_done_white_24dp, context.getString(R.string.mark_as_done), pendingIntent);
        }
        if (sharedPreferences.getBoolean("checkBoxSnooze", true)) {
            builder.addAction(R.drawable.ic_snooze_white_24dp, context.getString(R.string.snooze), pendingSnooze);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(reminder.getId(), builder.build());
    }

    public static void createSOSNotification(Context context, Reminder reminder) {
        // Create intent for notification onClick behaviour
        Intent viewIntent = new Intent(context, MainActivity.class);
        viewIntent.putExtra("sos", true);
        PendingIntent pending = PendingIntent.getActivity(context, reminder.getId() - 1, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int imageResId = context.getResources().getIdentifier("ic_notification", "drawable", context.getPackageName());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(imageResId)
                .setColor(Color.parseColor(reminder.getColour()))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(reminder.getContent()))
                .setContentTitle(reminder.getTitle())
                .setContentText(context.getString(R.string.sos_notification_detail))
                .setTicker(reminder.getTitle())
                .setContentIntent(pending);

        builder.setOngoing(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(Notification.PRIORITY_HIGH);
        }

        Intent fireIntent = new Intent(context, MainActivity.class);
        fireIntent.putExtra("sos_fire", true);
        fireIntent.putExtra("sos", true);
        PendingIntent pendingFire = PendingIntent.getActivity(context, reminder.getId(), fireIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.ic_whatshot_fire_24dp, "FIRE", pendingFire);

        Intent ambulanceIntent = new Intent(context, MainActivity.class);
        ambulanceIntent.putExtra("sos_ambulance", true);
        ambulanceIntent.putExtra("sos", true);
        PendingIntent pendingAmbulance = PendingIntent.getActivity(context, reminder.getId() + 1, ambulanceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.ic_add_box_ambulance_24dp, "AMBULANCE", pendingAmbulance);

        Intent policeIntent = new Intent(context, MainActivity.class);
        policeIntent.putExtra("sos_police", true);
        policeIntent.putExtra("sos", true);
        PendingIntent pendingPolice = PendingIntent.getActivity(context, reminder.getId() + 2, policeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.ic_report_police_24dp, "POLICE", pendingPolice);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(reminder.getId(), builder.build());
    }

    public static void cancelNotification(Context context, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }
}