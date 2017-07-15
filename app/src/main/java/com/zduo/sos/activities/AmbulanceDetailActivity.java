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

package com.zduo.sos.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zduo.sos.Hasura;
import com.zduo.sos.R;
import com.zduo.sos.db.tables.records.EmergencyRecord;
import com.zduo.sos.hasura.core.Callback;
import com.zduo.sos.hasura.db.DBException;
import com.zduo.sos.hasura.db.InsertQuery;
import com.zduo.sos.hasura.db.InsertResult;
import com.zduo.sos.utils.ColorUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zduo.sos.db.Tables.EMERGENCY;

public class AmbulanceDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.toolbar_shadow)
    View toolbarShadow;
    @BindView(R.id.calm_circle)
    ImageView calmCircle;
    @BindView(R.id.notification_icon)
    ImageView notificationIcon;
    @BindView(R.id.notification_title)
    TextView notificationTitle;
    @BindView(R.id.notification_content)
    TextView notificationContent;
    @BindView(R.id.buy_more_button)
    Button buyMoreButton;
    @BindView(R.id.notification_card)
    CardView notificationCard;
    @BindView(R.id.fire_circle)
    ImageView fireCircle;
    @BindView(R.id.directions_card)
    CardView directionsCard;
    @BindView(R.id.header_separator)
    TextView headerSeparator;
    @BindView(R.id.detail_layout)
    LinearLayout detailLayout;
    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.view_coordinator)
    CoordinatorLayout viewCoordinator;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        directionsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=hospital");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        GradientDrawable bgShape = (GradientDrawable) calmCircle.getDrawable();
        bgShape.setColor(getResources().getColor(R.color.fab_green));
        GradientDrawable fireShape = (GradientDrawable) fireCircle.getDrawable();
        fireShape.setColor(getResources().getColor(R.color.shortcut_ambulance));

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ColorUtils.getDarkVariant(getResources()
                    .getColor(R.color.shortcut_ambulance)));
        }

        sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);

        String time = new Date().toString();
        InsertQuery<EmergencyRecord> query = Hasura.db.insert(EMERGENCY);
        String json = "{\n" +
                "\t\"type\":\"insert\",\n" +
                "\t\"args\":{\n" +
                "\t\t\"table\":\"emergency\",\n" +
                "\t\t\"objects\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"sos_id\":\"" + Hasura.getCurrentUserId() + "\",\n" +
                "\t\t\t\t\"type\":\"" + "Ambulance" + "\",\n" +
                "\t\t\t\t\"location\":\"" + sharedPreferences.getString("location", "Longitude: 19.134707, Latitude: 72.912948") + "\",\n" +
                "\t\t\t\t\"time\":\"" + time + "\"\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";

        query.build(json).enqueue(new Callback<InsertResult<EmergencyRecord>, DBException>() {
            @Override
            public void onSuccess(InsertResult<EmergencyRecord> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AmbulanceDetailActivity.this, "Emergency reported successfully", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(final DBException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String errMsg = e.getCode().toString() + " : " + e.getLocalizedMessage();
                        Toast.makeText(AmbulanceDetailActivity.this, errMsg, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
