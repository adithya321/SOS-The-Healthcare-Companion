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

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.zduo.sos.R;
import com.zduo.sos.utils.ColorUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BloodDetailActivity extends AppCompatActivity {

    @BindView(R.id.spinner_blood_group)
    com.zduo.sos.tools.LabelledSpinner spinnerBloodGroup;
    @BindView(R.id.contact_no)
    EditText contactNo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.toolbar_shadow)
    View toolbarShadow;
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
        setContentView(R.layout.activity_blood_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        spinnerBloodGroup.setItemsArray(R.array.blood_groups);
        contactNo.setText(sharedPreferences.getString(contactNo.getTag().toString(), ""));

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ColorUtils.getDarkVariant(getResources()
                    .getColor(R.color.shortcut_blood)));
        }
    }

    public void sendBloodRequest(View view) {
        String[] bg = getResources().getStringArray(R.array.blood_groups_alias);
        String topic = bg[spinnerBloodGroup.getSpinner().getSelectedItemPosition()];

        String json = "{\n" +
                "  \"to\": \"/topics/" + topic + "\",\n" +
                "  \"data\":{\n" +
                "  \t\t\"title\":\"Blood Request " + spinnerBloodGroup.getSpinner()
                .getSelectedItem().toString() + "\"\n" +
                "   \t\t\"contact_no\":\"" + contactNo.getText().toString() + "\"\n" +
                "   \t\t\"content\":\"Tap to accept request\"\n" +
                "   \t\t\"color\":\"#F27F2F\"\n" +
                "   }\n" +
                "}";

        Log.e("JSON", json);

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, json);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .addHeader("Authorization", "key=" + getString(R.string.server_key))
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BloodDetailActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BloodDetailActivity.this, "Request sent successfully", Toast.LENGTH_SHORT).show();
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
