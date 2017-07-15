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

import android.Manifest;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.zduo.sos.MyApplication;
import com.zduo.sos.R;
import com.zduo.sos.adapters.A1cEstimateAdapter;
import com.zduo.sos.db.CholesterolReading;
import com.zduo.sos.db.DatabaseHandler;
import com.zduo.sos.db.GlucoseReading;
import com.zduo.sos.db.HB1ACReading;
import com.zduo.sos.db.KetoneReading;
import com.zduo.sos.db.PressureReading;
import com.zduo.sos.db.WeightReading;
import com.zduo.sos.presenter.OverviewPresenter;
import com.zduo.sos.tools.FormatDateTime;
import com.zduo.sos.tools.GlucoseRanges;
import com.zduo.sos.tools.GlucosioConverter;
import com.zduo.sos.view.OverviewView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class OverviewFragment extends Fragment implements OverviewView {

    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    private ImageButton HB1ACMoreButton;
    private LineChart chart;
    private TextView lastReadingTextView;
    private TextView lastDateTextView;
    private TextView HB1ACTextView;
    private TextView HB1ACDateTextView;
    private Spinner graphSpinnerRange;
    private OverviewPresenter presenter;

    private CheckBox graphCheckboxGlucose;
    private CheckBox graphCheckboxKetones;
    private CheckBox graphCheckboxCholesterol;
    private CheckBox graphCheckboxA1c;
    private CheckBox graphCheckboxWeight;
    private CheckBox graphCheckboxPressure;
    private View mFragmentView;


    public static OverviewFragment newInstance() {
        return new OverviewFragment();
    }

    public static void disableTouchTheft(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences("Profile", MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("fake", false)) createFakeGraphData();

        MyApplication app = (MyApplication) getActivity().getApplicationContext();
        presenter = new OverviewPresenter(this, app.getDBHandler());
        if (!presenter.isdbEmpty()) {
            presenter.loadDatabase(isNewGraphEnabled());
        }

        mFragmentView = inflater.inflate(R.layout.fragment_overview, container, false);

        chart = (LineChart) mFragmentView.findViewById(R.id.chart);
        disableTouchTheft(chart);
        Legend legend = chart.getLegend();

        lastReadingTextView = (TextView) mFragmentView.findViewById(R.id.item_history_reading);
        lastDateTextView = (TextView) mFragmentView.findViewById(R.id.fragment_overview_last_date);
        graphSpinnerRange = (Spinner) mFragmentView.findViewById(R.id.chart_spinner_range);
        Spinner graphSpinnerMetric = (Spinner) mFragmentView.findViewById(R.id.chart_spinner_metrics);
        ImageButton graphExport = (ImageButton) mFragmentView.findViewById(R.id.fragment_overview_graph_export);
        HB1ACTextView = (TextView) mFragmentView.findViewById(R.id.fragment_overview_hb1ac);
        HB1ACDateTextView = (TextView) mFragmentView.findViewById(R.id.fragment_overview_hb1ac_date);
        HB1ACMoreButton = (ImageButton) mFragmentView.findViewById(R.id.fragment_overview_a1c_more);
        graphCheckboxGlucose = (CheckBox) mFragmentView.findViewById(R.id.fragment_overview_graph_glucose);
        graphCheckboxKetones = (CheckBox) mFragmentView.findViewById(R.id.fragment_overview_graph_ketones);
        graphCheckboxCholesterol = (CheckBox) mFragmentView.findViewById(R.id.fragment_overview_graph_cholesterol);
        graphCheckboxA1c = (CheckBox) mFragmentView.findViewById(R.id.fragment_overview_graph_a1c);
        graphCheckboxWeight = (CheckBox) mFragmentView.findViewById(R.id.fragment_overview_graph_weight);
        graphCheckboxPressure = (CheckBox) mFragmentView.findViewById(R.id.fragment_overview_graph_pressure);

        graphCheckboxGlucose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setData();
                graphCheckboxWeight.setChecked(false);
                graphCheckboxCholesterol.setChecked(false);
                graphCheckboxKetones.setChecked(false);
                graphCheckboxPressure.setChecked(false);
                graphCheckboxWeight.setChecked(false);
                graphCheckboxA1c.setChecked(false);
                graphCheckboxGlucose.setChecked(b);
            }
        });

        graphCheckboxA1c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setData();
                graphCheckboxGlucose.setChecked(false);
                graphCheckboxWeight.setChecked(false);
                graphCheckboxCholesterol.setChecked(false);
                graphCheckboxKetones.setChecked(false);
                graphCheckboxPressure.setChecked(false);
                graphCheckboxWeight.setChecked(false);
                graphSpinnerRange.setEnabled(!b);
                graphCheckboxA1c.setChecked(b);
            }
        });

        graphCheckboxKetones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setData();
                graphCheckboxGlucose.setChecked(false);
                graphCheckboxWeight.setChecked(false);
                graphCheckboxCholesterol.setChecked(false);
                graphCheckboxPressure.setChecked(false);
                graphCheckboxWeight.setChecked(false);
                graphCheckboxA1c.setChecked(false);
                graphSpinnerRange.setEnabled(!b);
                graphCheckboxKetones.setChecked(b);
            }
        });

        graphCheckboxWeight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setData();
                graphCheckboxGlucose.setChecked(false);
                graphCheckboxWeight.setChecked(false);
                graphCheckboxCholesterol.setChecked(false);
                graphCheckboxKetones.setChecked(false);
                graphCheckboxPressure.setChecked(false);
                graphCheckboxA1c.setChecked(false);
                graphSpinnerRange.setEnabled(!b);
                graphCheckboxWeight.setChecked(b);
            }
        });

        graphCheckboxPressure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setData();
                graphCheckboxGlucose.setChecked(false);
                graphCheckboxWeight.setChecked(false);
                graphCheckboxCholesterol.setChecked(false);
                graphCheckboxKetones.setChecked(false);
                graphCheckboxWeight.setChecked(false);
                graphCheckboxA1c.setChecked(false);
                graphSpinnerRange.setEnabled(!b);
                graphCheckboxPressure.setChecked(b);
            }
        });

        graphCheckboxCholesterol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setData();
                graphCheckboxGlucose.setChecked(false);
                graphCheckboxWeight.setChecked(false);
                graphCheckboxKetones.setChecked(false);
                graphCheckboxPressure.setChecked(false);
                graphCheckboxWeight.setChecked(false);
                graphCheckboxA1c.setChecked(false);
                graphSpinnerRange.setEnabled(!b);
                graphCheckboxCholesterol.setChecked(b);
            }
        });

        HB1ACMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showA1cDialog();
            }
        });
        // Set array and adapter for graphSpinnerRange
        String[] selectorRangeArray = getActivity().getResources().getStringArray(R.array.fragment_overview_selector_range);
        String[] selectorMetricArray = getActivity().getResources().getStringArray(R.array.fragment_overview_selector_metric);
        ArrayAdapter<String> dataRangeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, selectorRangeArray);
        ArrayAdapter<String> dataMetricAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, selectorMetricArray);
        dataRangeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataMetricAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        graphSpinnerRange.setAdapter(dataRangeAdapter);
        graphSpinnerRange.setSelection(1);
        graphSpinnerMetric.setAdapter(dataMetricAdapter);

        graphSpinnerRange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!presenter.isdbEmpty()) {
                    setData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        graphSpinnerRange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!presenter.isdbEmpty()) {
                    setData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(getResources().getColor(R.color.glucosio_text_light));
        xAxis.setAvoidFirstLastClipping(true);

        int minGlucoseValue = presenter.getGlucoseMinValue();
        int maxGlucoseValue = presenter.getGlucoseMaxValue();

        LimitLine ll1;
        LimitLine ll2;

        if (("mg/dL").equals(presenter.getUnitMeasuerement())) {
            ll1 = new LimitLine(minGlucoseValue);
            ll2 = new LimitLine(maxGlucoseValue);
        } else {
            ll1 = new LimitLine((float) GlucosioConverter.glucoseToMmolL(maxGlucoseValue), getString(R.string.reading_high));
            ll2 = new LimitLine((float) GlucosioConverter.glucoseToMmolL(minGlucoseValue), getString(R.string.reading_low));
        }

        ll1.setLineWidth(0.8f);
        ll1.setLineColor(getResources().getColor(R.color.glucosio_reading_low));

        ll2.setLineWidth(0.8f);
        ll2.setLineColor(getResources().getColor(R.color.glucosio_reading_high));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(getResources().getColor(R.color.glucosio_text_light));
        leftAxis.setStartAtZero(false);
        leftAxis.disableGridDashedLine();
        leftAxis.setDrawGridLines(false);
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setDrawLimitLinesBehindData(true);

        chart.getAxisRight().setEnabled(false);
        chart.setBackgroundColor(Color.parseColor("#FFFFFF"));
        chart.setDescription("");
        chart.setGridBackgroundColor(Color.parseColor("#FFFFFF"));
        if (!presenter.isdbEmpty()) {
            setData();
        }
        legend.setEnabled(false);

        graphExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // If we don't have permission, ask the user

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                    Snackbar.make(mFragmentView, getString(R.string.fragment_overview_permission_storage), Snackbar.LENGTH_SHORT).show();
                } else {
                    // else save the image to gallery
                    exportGraphToGallery();
                }
            }
        });

        loadLastReading();
        loadHB1AC();

        return mFragmentView;
    }

    private void createFakeGraphData() {
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        DatabaseHandler databaseHandler = myApplication.getDBHandler();
        Random random = new Random();

        //Glucose
        for (int i = 0; i < 2; i++)
            for (int j = 1; j < 30; j++)
                databaseHandler.addGlucoseReading(new GlucoseReading(
                        random.nextInt((200 - 100) + 1) + 100, "Night",
                        new Date(116, 9 + i, j, 0, 0), ""));
        for (int k = 1; k < 15; k++)
            databaseHandler.addGlucoseReading(new GlucoseReading(
                    random.nextInt((200 - 100) + 1) + 100, "Night",
                    new Date(116, 11, k, 0, 0), ""));

        //Weight
        for (int i = 0; i < 2; i++)
            for (int j = 1; j < 30; j += 5)
                databaseHandler.addWeightReading(new WeightReading(
                        random.nextInt((80 - 50) + 1) + 50,
                        new Date(116, 9 + i, j, 0, 0)));
        for (int k = 1; k < 15; k += 5)
            databaseHandler.addWeightReading(new WeightReading(
                    random.nextInt((80 - 50) + 1) + 50,
                    new Date(116, 11, k, 0, 0)));

        //HbA1c
        for (int i = 0; i < 2; i++)
            for (int j = 1; j < 30; j += 5)
                databaseHandler.addHB1ACReading(new HB1ACReading(
                        random.nextInt((8 - 4) + 1) + 4,
                        new Date(116, 9 + i, j, 0, 0)));
        for (int k = 1; k < 15; k += 5)
            databaseHandler.addHB1ACReading(new HB1ACReading(
                    random.nextInt((8 - 4) + 1) + 4,
                    new Date(116, 11, k, 0, 0)));

        //Pressure
        for (int i = 0; i < 2; i++)
            for (int j = 1; j < 30; j += 5)
                databaseHandler.addPressureReading(new PressureReading(
                        random.nextInt((140 - 100) + 1) + 100,
                        random.nextInt((100 - 60) + 1) + 60,
                        new Date(116, 9 + i, j, 0, 0)));
        for (int k = 1; k < 15; k += 5)
            databaseHandler.addPressureReading(new PressureReading(
                    random.nextInt((140 - 100) + 1) + 100,
                    random.nextInt((100 - 60) + 1) + 60,
                    new Date(116, 11, k, 0, 0)));

        //Ketones
        for (int i = 0; i < 2; i++)
            for (int j = 1; j < 30; j += 5)
                databaseHandler.addKetoneReading(new KetoneReading(
                        random.nextInt((3) + 1) + 1,
                        new Date(116, 9 + i, j, 0, 0)));
        for (int k = 1; k < 15; k += 5)
            databaseHandler.addKetoneReading(new KetoneReading(
                    random.nextInt((3) + 1) + 1,
                    new Date(116, 11, k, 0, 0)));

        //Cholestrol
        for (int i = 0; i < 2; i++)
            for (int j = 1; j < 30; j += 5)
                databaseHandler.addCholesterolReading(new CholesterolReading(
                        random.nextInt((260 - 180) + 1) + 180,
                        random.nextInt((200 - 80) + 1) + 80,
                        random.nextInt((80 - 20) + 1) + 20,
                        new Date(116, 9 + i, j, 0, 0)));
        for (int k = 1; k < 15; k += 5)
            databaseHandler.addCholesterolReading(new CholesterolReading(
                    random.nextInt((260 - 180) + 1) + 180,
                    random.nextInt((200 - 80) + 1) + 80,
                    random.nextInt((80 - 20) + 1) + 20,
                    new Date(116, 11, k, 0, 0)));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("fake", true);
        editor.apply();
    }

    private void exportGraphToGallery() {
        long timestamp = System.currentTimeMillis() / 1000;
        boolean saved = chart.saveToGallery("glucosio_" + timestamp, 50);
        if (saved) {
            Snackbar.make(mFragmentView, R.string.fragment_overview_graph_export_true, Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(mFragmentView, R.string.fragment_overview_graph_export_false, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        LineData data = new LineData();
        if (graphCheckboxGlucose.isChecked()) {
            data = generateGlucoseData();
        }

        if (graphCheckboxA1c.isChecked()) {
            data = generateA1cData();
        }

        if (graphCheckboxKetones.isChecked()) {
            data = generateKetonesData();
        }

        if (graphCheckboxWeight.isChecked()) {
            data = generateWeightData();
        }

        if (graphCheckboxPressure.isChecked()) {
            data = generatePressureData();
        }

        if (graphCheckboxCholesterol.isChecked()) {
            data = generateCholesterolData();
        }

        chart.setData(data);
        chart.setPinchZoom(true);
        chart.setHardwareAccelerationEnabled(true);
        chart.animateY(1000, Easing.EasingOption.EaseOutCubic);
        chart.invalidate();
        chart.notifyDataSetChanged();
        chart.fitScreen();
        chart.setVisibleXRangeMaximum(20);
        chart.moveViewToX(data.getXValCount());
    }

    private LineData generateGlucoseData() {
        List<String> xVals = new ArrayList<>();
        List<Entry> yVals = new ArrayList<>();

        if (graphSpinnerRange.getSelectedItemPosition() == 0) {
            // Day view
            for (int i = 0; i < presenter.getGlucoseReadings().size(); i++) {
                if (presenter.getUnitMeasuerement().equals("mg/dL")) {
                    float val = Float.parseFloat(presenter.getGlucoseReadings().get(i).toString());
                    yVals.add(new Entry(val, i));
                } else {
                    double val = GlucosioConverter.glucoseToMmolL(Double.parseDouble(presenter.getGlucoseReadings().get(i).toString()));
                    float converted = (float) val;
                    yVals.add(new Entry(converted, i));
                }
            }
        } else if (graphSpinnerRange.getSelectedItemPosition() == 1) {
            // Week view
            for (int i = 0; i < presenter.getGlucoseReadingsWeek().size(); i++) {
                if (presenter.getUnitMeasuerement().equals("mg/dL")) {
                    float val = Float.parseFloat(presenter.getGlucoseReadingsWeek().get(i) + "");
                    yVals.add(new Entry(val, i));
                } else {
                    double val = GlucosioConverter.glucoseToMmolL(Double.parseDouble(presenter.getGlucoseReadingsWeek().get(i) + ""));
                    float converted = (float) val;
                    yVals.add(new Entry(converted, i));
                }
            }
        } else {
            // Month view
            for (int i = 0; i < presenter.getGlucoseReadingsMonth().size(); i++) {
                if (presenter.getUnitMeasuerement().equals("mg/dL")) {
                    float val = Float.parseFloat(presenter.getGlucoseReadingsMonth().get(i) + "");
                    yVals.add(new Entry(val, i));
                } else {
                    double val = GlucosioConverter.glucoseToMmolL(Double.parseDouble(presenter.getGlucoseReadingsMonth().get(i) + ""));
                    float converted = (float) val;
                    yVals.add(new Entry(converted, i));
                }
            }
        }

        if (graphSpinnerRange.getSelectedItemPosition() == 0) {
            // Day view
            for (int i = 0; i < presenter.getGraphGlucoseDateTime().size(); i++) {
                String date = presenter.convertDate(presenter.getGraphGlucoseDateTime().get(i));
                xVals.add(date + "");
            }
        } else if (graphSpinnerRange.getSelectedItemPosition() == 1) {
            // Week view
            for (int i = 0; i < presenter.getGlucoseReadingsWeek().size(); i++) {
                String date = presenter.convertDate(presenter.getGlucoseDatetimeWeek().get(i));
                xVals.add(date + "");
            }
        } else {
            // Month view
            for (int i = 0; i < presenter.getGlucoseReadingsMonth().size(); i++) {
                String date = presenter.convertDateToMonth(presenter.getGlucoseDatetimeMonth().get(i));
                xVals.add(date + "");
            }
        }

        return new LineData(xVals,
                generateLineDataSet(yVals, getResources().getColor(R.color.glucosio_pink)));
    }

    private LineData generateA1cData() {
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < presenter.getA1cReadings().size(); i++) {
            float val = Float.parseFloat(presenter.getA1cReadings().get(i).toString());
            yVals.add(new Entry(val, i));
        }

        xVals.clear();
        for (int i = 0; i < presenter.getA1cReadingsDateTime().size(); i++) {
            String date = presenter.convertDate(presenter.getA1cReadingsDateTime().get(i));
            xVals.add(date + "");
        }

        // create a data object with the datasets
        return new LineData(xVals,
                generateLineDataSet(yVals, getResources().getColor(R.color.glucosio_fab_HB1AC)));
    }

    private LineData generateKetonesData() {
        List<String> xVals = new ArrayList<>();
        List<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < presenter.getKetonesReadings().size(); i++) {
            float val = Float.parseFloat(presenter.getKetonesReadings().get(i).toString());
            yVals.add(new Entry(val, i));
        }

        xVals.clear();
        for (int i = 0; i < presenter.getKetonesReadingsDateTime().size(); i++) {
            String date = presenter.convertDate(presenter.getKetonesReadingsDateTime().get(i));
            xVals.add(date + "");
        }

        // create a data object with the datasets
        return new LineData(xVals,
                generateLineDataSet(yVals, getResources().getColor(R.color.glucosio_fab_ketones)));
    }

    private LineData generateWeightData() {
        List<String> xVals = new ArrayList<>();
        List<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < presenter.getWeightReadings().size(); i++) {
            float val = Float.parseFloat(presenter.getWeightReadings().get(i).toString());
            yVals.add(new Entry(val, i));
        }

        xVals.clear();
        for (int i = 0; i < presenter.getWeightReadingsDateTime().size(); i++) {
            String date = presenter.convertDate(presenter.getWeightReadingsDateTime().get(i));
            xVals.add(date + "");
        }

        // create a data object with the datasets
        return new LineData(xVals,
                generateLineDataSet(yVals, getResources().getColor(R.color.glucosio_fab_weight)));
    }

    private LineData generatePressureData() {
        List<String> xVals = new ArrayList<>();
        List<Entry> yValsMax = new ArrayList<>();
        List<Entry> yValsMin = new ArrayList<>();

        for (int i = 0; i < presenter.getMaxPressureReadings().size(); i++) {
            float val = Float.parseFloat(presenter.getMaxPressureReadings().get(i).toString());
            yValsMax.add(new Entry(val, i));
        }

        for (int i = 0; i < presenter.getMinPressureReadings().size(); i++) {
            float val = Float.parseFloat(presenter.getMinPressureReadings().get(i).toString());
            yValsMin.add(new Entry(val, i));
        }

        xVals.clear();
        for (int i = 0; i < presenter.getPressureReadingsDateTime().size(); i++) {
            String date = presenter.convertDate(presenter.getPressureReadingsDateTime().get(i));
            xVals.add(date + "");
        }

        LineData data = new LineData(xVals,
                generateLineDataSet(yValsMax, getResources().getColor(R.color.glucosio_fab_pressure)));
        data.addDataSet(generateLineDataSet(yValsMin, getResources().getColor(R.color.glucosio_fab_pressure)));
        // create a data object with the datasets
        return data;
    }

    private LineData generateCholesterolData() {
        List<String> xVals = new ArrayList<>();
        List<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < presenter.getCholesterolReadings().size(); i++) {
            float val = Float.parseFloat(presenter.getCholesterolReadings().get(i).toString());
            yVals.add(new Entry(val, i));
        }

        xVals.clear();
        for (int i = 0; i < presenter.getCholesterolReadingsDateTime().size(); i++) {
            String date = presenter.convertDate(presenter.getCholesterolReadingsDateTime().get(i));
            xVals.add(date + "");
        }

        // create a data object with the datasets
        return new LineData(xVals,
                generateLineDataSet(yVals, getResources().getColor(R.color.glucosio_fab_cholesterol)));
    }

    private LineDataSet generateLineDataSet(List<Entry> yVals, int color) {
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "");
        List<Integer> colors = new ArrayList<>();

        if (color == getResources().getColor(R.color.glucosio_pink)) {
            for (Entry yVal : yVals) {
                if (yVal.getVal() == (0)) {
                    colors.add(Color.TRANSPARENT);
                } else {
                    colors.add(color);
                }
            }
            set1.setCircleColors(colors);
        } else {
            set1.setCircleColor(color);
        }

        set1.setColor(color);
        set1.setLineWidth(2f);
        set1.setCircleSize(4f);
        set1.setDrawCircleHole(true);
        set1.disableDashedLine();
        set1.setFillAlpha(255);
        set1.setDrawFilled(true);
        set1.setValueTextSize(0);
        set1.setValueTextColor(Color.parseColor("#FFFFFF"));
        set1.setFillDrawable(getResources().getDrawable(R.drawable.graph_gradient));
        set1.setHighLightColor(getResources().getColor(R.color.glucosio_gray_light));
        set1.setCubicIntensity(0.2f);

        // TODO: Change this to true when a fix is available
        // https://github.com/PhilJay/MPAndroidChart/issues/1541
        set1.setDrawCubic(false);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            set1.setDrawFilled(false);
            set1.setLineWidth(2f);
            set1.setCircleSize(4f);
            set1.setDrawCircleHole(true);
        }

        return set1;
    }

    private void showA1cDialog() {
        final Dialog a1CDialog = new Dialog(getActivity(), R.style.GlucosioTheme);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(a1CDialog.getWindow().getAttributes());
        a1CDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        a1CDialog.setContentView(R.layout.dialog_a1c);
        a1CDialog.getWindow().setAttributes(lp);
        a1CDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        a1CDialog.getWindow().setDimAmount(0.5f);
        a1CDialog.setCanceledOnTouchOutside(true);
        a1CDialog.show();

        ListView a1cListView = (ListView) a1CDialog.findViewById(R.id.dialog_a1c_listview);

        A1cEstimateAdapter customAdapter = new A1cEstimateAdapter(
                getActivity(), R.layout.dialog_a1c_item, presenter.getA1cEstimateList());

        a1cListView.setAdapter(customAdapter);
    }

    private void loadHB1AC() {
        if (!presenter.isdbEmpty()) {
            HB1ACTextView.setText(presenter.getHB1AC());
            HB1ACDateTextView.setText(presenter.getA1cMonth());
            // We show the A1C more button only if 2 or more A1C estimates are available
            if (!presenter.isA1cAvailable(2)) {
                HB1ACMoreButton.setVisibility(View.GONE);
            }
        }
    }

    private void loadLastReading() {
        if (!presenter.isdbEmpty()) {
            if (presenter.getUnitMeasuerement().equals("mg/dL")) {
                String reading = presenter.getLastReading();
                lastReadingTextView.setText(getString(R.string.mg_dL_value, reading));
            } else {
                String mgdl = presenter.getLastReading();
                double mmol = GlucosioConverter.glucoseToMmolL(Double.parseDouble(mgdl));
                String reading = NumberFormat.getInstance().format(mmol);
                lastReadingTextView.setText(getString(R.string.mmol_L_value, reading));
            }

            FormatDateTime dateTime = new FormatDateTime(getActivity().getApplicationContext());

            lastDateTextView.setText(dateTime.convertDate(presenter.getLastDateTime()));
            GlucoseRanges ranges = new GlucoseRanges(getActivity().getApplicationContext());
            String color = ranges.colorFromReading(Integer.parseInt(presenter.getLastReading()));
            lastReadingTextView.setTextColor(ranges.stringToColor(color));
        }
    }

    private boolean isNewGraphEnabled() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        return !sharedPref.getBoolean("pref_graph_old", false);
    }

    @NonNull
    public String convertDate(@NonNull final String date) {
        FormatDateTime dateTime = new FormatDateTime(getActivity().getApplicationContext());
        return dateTime.convertDate(date);
    }

    @NonNull
    public String convertDateToMonth(@NonNull final String date) {
        FormatDateTime dateTime = new FormatDateTime((getActivity().getApplication()));
        return dateTime.convertDateToMonthOverview(date);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    exportGraphToGallery();
                } else {
                    Snackbar.make(mFragmentView, R.string.fragment_overview_permission_storage, Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }
}