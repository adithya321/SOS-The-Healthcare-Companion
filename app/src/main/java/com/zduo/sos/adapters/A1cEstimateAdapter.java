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

package com.zduo.sos.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zduo.sos.R;
import com.zduo.sos.db.DatabaseHandler;
import com.zduo.sos.object.A1cEstimate;
import com.zduo.sos.tools.GlucosioConverter;

import java.text.NumberFormat;
import java.util.List;

public class A1cEstimateAdapter extends ArrayAdapter<A1cEstimate> {

    private DatabaseHandler db;

    public A1cEstimateAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public A1cEstimateAdapter(Context context, int resource, List<A1cEstimate> items) {
        super(context, resource, items);
        db = new DatabaseHandler(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.dialog_a1c_item, parent, false);
        }

        A1cEstimate p = getItem(position);

        if (p != null) {
            TextView value = (TextView) v.findViewById(R.id.dialog_a1c_item_value);
            TextView month = (TextView) v.findViewById(R.id.dialog_a1c_item_month);
            TextView glucoseAverage = (TextView) v.findViewById(R.id.dialog_a1c_item_glucose_value);

            if (value != null) {
                if ("percentage".equals(db.getUser(1).getPreferred_unit_a1c())) {
                    String stringValue = p.getValue() + " %";
                    value.setText(stringValue);
                } else {
                    String stringValue = GlucosioConverter.a1cNgspToIfcc(p.getValue()) + " mmol/mol";
                    value.setText(stringValue);
                }
            }

            if (month != null) {
                month.setText(p.getMonth());
            }

            if (glucoseAverage != null) {
                if ("mg/dL".equals(db.getUser(1).getPreferred_unit())) {
                    glucoseAverage.setText(getContext().getString(R.string.mg_dL_value, p.getGlucoseAverage()));
                } else {
                    int mmol = GlucosioConverter.glucoseToMgDl(Double.parseDouble(p.getGlucoseAverage()));
                    String reading = NumberFormat.getInstance().format(mmol);
                    glucoseAverage.setText(getContext().getString(R.string.mmol_L_value, reading));
                }
            }
        }

        return v;
    }

}
