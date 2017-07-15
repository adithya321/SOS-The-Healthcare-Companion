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
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.JsonSyntaxException;
import com.zduo.sos.Hasura;
import com.zduo.sos.MyApplication;
import com.zduo.sos.R;
import com.zduo.sos.activity.AddA1CActivity;
import com.zduo.sos.activity.AddCholesterolActivity;
import com.zduo.sos.activity.AddGlucoseActivity;
import com.zduo.sos.activity.AddKetoneActivity;
import com.zduo.sos.activity.AddPressureActivity;
import com.zduo.sos.activity.AddWeightActivity;
import com.zduo.sos.adapters.ReminderAdapter;
import com.zduo.sos.adapters.ReportAdapter;
import com.zduo.sos.adapters.ViewPageAdapter;
import com.zduo.sos.db.tables.records.UsersRecord;
import com.zduo.sos.fragments.ReportsFragment;
import com.zduo.sos.hasura.core.Callback;
import com.zduo.sos.hasura.db.DBException;
import com.zduo.sos.hasura.db.SelectQuery;
import com.zduo.sos.hasura.db.UpdateQuery;
import com.zduo.sos.hasura.db.UpdateResult;
import com.zduo.sos.models.Reminder;
import com.zduo.sos.models.Report;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zduo.sos.db.Tables.USERS;
import static com.zduo.sos.hasura.db.DBService.gson;

public class MainActivity extends AppCompatActivity implements ReminderAdapter.RecyclerListener {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip pagerSlidingTabStrip;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.fab_button)
    FloatingActionButton floatingActionButton;
    List<Report> reportList;
    private boolean fabIsHidden = false;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetDialog bottomSheetAddDialog;
    private View bottomSheetAddDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        Hasura.setUserId(sharedPreferences.getInt("id", 172539));

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }

        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

        pagerSlidingTabStrip.setViewPager(viewPager);
        int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);

        if (getIntent().getBooleanExtra("glucosio", false)) {
            viewPager.setCurrentItem(3);
            floatingActionButton.setImageResource(R.drawable.ic_add_white_24dp);
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.accent)));
        }
        if (getIntent().getBooleanExtra("sos", false)) {
            viewPager.setCurrentItem(4);
            floatingActionButton.setImageResource(R.drawable.ic_announcement_white_24dp);
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.fab_red)));
        }

        View view = new View(MainActivity.this);
        if (getIntent().getBooleanExtra("sos_fire", false)) {
            view.setTag("Fire");
            startActivity(new Intent(this, FireDetailActivity.class));
        }
        if (getIntent().getBooleanExtra("sos_police", false)) {
            view.setTag("Police");
            startActivity(new Intent(this, PoliceDetailActivity.class));
        }
        if (getIntent().getBooleanExtra("sos_ambulance", false)) {
            view.setTag("Ambulance");
            startActivity(new Intent(this, AmbulanceDetailActivity.class));
        }

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                showFab();
                if (position == 5) {
                    floatingActionButton.setImageResource(R.drawable.ic_save_white_24dp);
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.fab_green)));
                } else if (position == 4) {
                    floatingActionButton.setImageResource(R.drawable.ic_announcement_white_24dp);
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.fab_red)));
                } else if (position == 3) {
                    floatingActionButton.setImageResource(R.drawable.ic_add_white_24dp);
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.accent)));
                } else if (position == 2) {
                    floatingActionButton.setImageResource(R.drawable.ic_refresh_white_24dp);
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.fab_yellow)));
                } else if (position == 1) {
                    floatingActionButton.setImageResource(R.drawable.ic_add_white_24dp);
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.accent)));
                } else if (position == 0) {
                    floatingActionButton.setImageResource(R.drawable.ic_announcement_white_24dp);
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.fab_red)));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                hideFab();
                if (state == ViewPager.SCROLL_STATE_IDLE)
                    showFab();
            }
        });

        ((MyApplication) getApplication()).createSOSNotification();

        if (getIntent().getExtras() != null) {
            for (final String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.e("Main Activity KeySet", "Key: " + key + " Value: " + value);
                if (key.equals("contact_no")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + value));
                    startActivity(intent);
                    finish();
                }
            }
        }

        bottomSheetAddDialogView = getLayoutInflater().inflate(R.layout.fragment_add_bottom_dialog, null);
        bottomSheetAddDialog = new BottomSheetDialog(this);
        bottomSheetAddDialog.setContentView(bottomSheetAddDialogView);
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetAddDialogView.getParent());
        bottomSheetBehavior.setHideable(false);
    }

    @OnClick(R.id.fab_button)
    public void fabClicked() {
        switch (viewPager.getCurrentItem()) {
            case 0:
                viewPager.setCurrentItem(4);
                break;
            case 1:
                startActivity(new Intent(this, CreateEditActivity.class));
                break;
            case 2:
                getReports();
                break;
            case 3:
                bottomSheetAddDialog.show();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case 4:
                sendSMS();
                break;
            case 5:
                saveToDB();
                break;
        }
    }

    private void sendSMS() {
        String message = "SOS : " + sharedPreferences.getString("location", "Longitude: 19.134707, " +
                "Latitude: 72.912948");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" +
                sharedPreferences.getString(getString(R.string.emergency_contact), "")));
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }

    private void getReports() {
        Toast.makeText(this, "Syncing...", Toast.LENGTH_SHORT).show();

        SelectQuery<UsersRecord> q = Hasura.db.select(USERS);
        String json = "{\n" +
                "\t\"type\":\"select\",\n" +
                "\t\"args\":{\n" +
                "\t\t\"table\":\"users\",\n" +
                "\t\t\"columns\":[\n" +
                "\t\t\t\"reports\"\n" +
                "\t\t],\n" +
                "\t\t\"where\":{\n" +
                "\t\t\t\"sos_id\":\"" + Hasura.getCurrentUserId() + "\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        q.build(json).enqueue(new Callback<List<UsersRecord>, DBException>() {
            @Override
            public void onSuccess(final List<UsersRecord> usersRecords) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        for (UsersRecord usersRecord : usersRecords) {
                            editor = sharedPreferences.edit();
                            editor.putString("reports", usersRecord.reports);
                            editor.apply();

                            Log.e("UR", usersRecord.toString());
                            reportList = getReportList();
                            if (reportList.size() == 0)
                                ReportsFragment.REPORTS_EMPTY_VIEW.setVisibility(View.VISIBLE);
                            else ReportsFragment.REPORTS_EMPTY_VIEW.setVisibility(View.GONE);
                            ReportAdapter reportAdapter = new ReportAdapter(MainActivity.this, reportList);
                            ReportsFragment.REPORTS_RECYCLER_VIEW.setAdapter(reportAdapter);
                        }
                    }
                });
            }

            @Override
            public void onFailure(final DBException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private List<Report> getReportList() {
        reportList = new ArrayList<>();
        String reports = sharedPreferences.getString("reports", "");
        try {
            JSONArray jsonArray = new JSONArray(reports);
            for (int i = 0; i < jsonArray.length(); i++) {
                Report report = new Report();
                report.setDocName(jsonArray.getJSONObject(i).getString("doc_name"));
                report.setDocAddress(jsonArray.getJSONObject(i).getString("doc_address"));
                report.setPatientName(jsonArray.getJSONObject(i).getString("patient_name"));
                List<Reminder> reminderList = new ArrayList<>();
                JSONArray jArray = jsonArray.getJSONArray(1);
                int j = 0;
                while (true) {
                    try {
                        Reminder reminder = gson.fromJson(jArray.getString(j), Reminder.class);
                        if (reminder.getTitle() == null) break;
                        else reminderList.add(reminder);
                        j++;
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        break;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                report.setPrescriptions(reminderList);
                reportList.add(report);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reportList;
    }

    private void saveToDB() {
        floatingActionButton.setEnabled(false);
        Toast.makeText(this, "Syncing...", Toast.LENGTH_SHORT).show();

        UpdateQuery<UsersRecord> query
                = Hasura.db.update(USERS);
        String json = "{\n" +
                "\t\"type\":\"update\",\n" +
                "\t\"args\":{\n" +
                "\t\t\"table\":\"users\",\n" +
                "\t\t\"where\":{\n" +
                "\t\t\t\"sos_id\":\"" + Hasura.getCurrentUserId() + "\"\n" +
                "\t\t},\n" +
                "\t\t\"$set\":{\n" +
                "\t\t\t\"name\":\"" + sharedPreferences.getString(getString(R.string.your_name), "") + "\",\n" +
                "\t\t\t\"bg\":\"" + sharedPreferences.getString("blood_group", "") + "\",\n" +
                "\t\t\t\"height\":\"" + sharedPreferences.getString(getString(R.string.height), "") + "\",\n" +
                "\t\t\t\"weight\":\"" + sharedPreferences.getString(getString(R.string.weight), "") + "\",\n" +
                "\t\t\t\"doc_name\":\"" + sharedPreferences.getString(getString(R.string.doctor_name), "") + "\",\n" +
                "\t\t\t\"contact_no\":\"" + sharedPreferences.getString(getString(R.string.contact_no), "") + "\",\n" +
                "\t\t\t\"allergies\":\"" + sharedPreferences.getString(getString(R.string.allergies), "") + "\",\n" +
                "\t\t\t\"emergency_contact\":\"" + sharedPreferences.getString(getString(R.string.emergency_contact), "") + "\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        query.build(json).enqueue(new Callback<UpdateResult<UsersRecord>, DBException>() {
            @Override
            public void onSuccess(UpdateResult<UsersRecord> response) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        floatingActionButton.setEnabled(true);
                    }
                });
            }

            @Override
            public void onFailure(final DBException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String errMsg = e.getCode().toString() + " : " + e.getLocalizedMessage();
                        Toast.makeText(MainActivity.this, errMsg, Toast.LENGTH_LONG).show();
                        floatingActionButton.setEnabled(true);
                    }
                });
            }
        });
    }

    @Override
    public void hideFab() {
        floatingActionButton.hide();
        fabIsHidden = true;
    }

    public void showFab() {
        if (fabIsHidden) {
            floatingActionButton.show();
            fabIsHidden = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showFab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent preferenceIntent = new Intent(this, PreferenceActivity.class);
                startActivity(preferenceIntent);
                return true;
            case R.id.action_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startFire(View view) {
        startActivity(new Intent(this, FireDetailActivity.class));
    }

    public void startPolice(View view) {
        startActivity(new Intent(this, PoliceDetailActivity.class));
    }

    public void startAmbulance(View view) {
        startActivity(new Intent(this, AmbulanceDetailActivity.class));
    }

    public void startHelp(View view) {
        startActivity(new Intent(this, HelpDetailActivity.class));
    }

    public void sendBloodRequest(View view) {
        startActivity(new Intent(this, BloodDetailActivity.class));
    }

    public CoordinatorLayout getFabView() {
        return (CoordinatorLayout) findViewById(R.id.activity_main_coordinator_layout);
    }

    public void onGlucoseFabClicked(View v) {
        openNewAddActivity(AddGlucoseActivity.class);
    }

    public void onKetoneFabClicked(View v) {
        openNewAddActivity(AddKetoneActivity.class);
    }

    public void onPressureFabClicked(View v) {
        openNewAddActivity(AddPressureActivity.class);
    }

    public void onHB1ACFabClicked(View v) {
        openNewAddActivity(AddA1CActivity.class);
    }

    public void onCholesterolFabClicked(View v) {
        openNewAddActivity(AddCholesterolActivity.class);
    }

    public void onWeightFabClicked(View v) {
        openNewAddActivity(AddWeightActivity.class);
    }

    private void openNewAddActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("glucosio", true);
        startActivity(intent);
        finishActivity();
    }

    public void finishActivity() {
        bottomSheetAddDialog.dismiss();
        finish();
    }
}