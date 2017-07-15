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

package com.zduo.sos.fragments;

import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.JsonSyntaxException;
import com.zduo.sos.R;
import com.zduo.sos.adapters.ReportAdapter;
import com.zduo.sos.models.Reminder;
import com.zduo.sos.models.Report;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static com.zduo.sos.hasura.db.DBService.gson;

public class ReportsFragment extends Fragment {

    public static RecyclerView REPORTS_RECYCLER_VIEW;
    public static CardView REPORTS_EMPTY_VIEW;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    CardView emptyView;
    @BindView(R.id.notification_circle)
    ImageView notificationCircle;
    private SharedPreferences sharedPreferences;
    private List<Report> reportList;
    private ReportAdapter reportAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        sharedPreferences = getActivity().getSharedPreferences("Profile", MODE_PRIVATE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        GradientDrawable bgShape = (GradientDrawable) notificationCircle.getDrawable();
        bgShape.setColor(getResources().getColor(R.color.glucosio_reading_ok));

        reportList = getReportList();
        if (reportList.size() == 0) emptyView.setVisibility(View.VISIBLE);
        else emptyView.setVisibility(View.GONE);
        reportAdapter = new ReportAdapter(getActivity(), reportList);
        recyclerView.setAdapter(reportAdapter);

        REPORTS_RECYCLER_VIEW = recyclerView;
        REPORTS_EMPTY_VIEW = emptyView;
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
                Log.e("RL", reminderList.toString());
                report.setPrescriptions(reminderList);
                reportList.add(report);
            }
        } catch (JSONException e) {
            Log.e("JE", e.toString());
        }
        return reportList;
    }
}