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

package com.zduo.sos.presenter;

import com.zduo.sos.activity.AddWeightActivity;
import com.zduo.sos.db.DatabaseHandler;
import com.zduo.sos.db.WeightReading;
import com.zduo.sos.tools.GlucosioConverter;

import java.util.Date;

public class AddWeightPresenter extends AddReadingPresenter {
    private DatabaseHandler dB;
    private AddWeightActivity activity;


    public AddWeightPresenter(AddWeightActivity addWeightActivity) {
        this.activity = addWeightActivity;
        dB = new DatabaseHandler(addWeightActivity.getApplicationContext());
    }

    public void dialogOnAddButtonPressed(String time, String date, String reading) {
        if (validateDate(date) && validateTime(time) && validateWeight(reading)) {

            WeightReading wReading = generateWeightReading(reading);
            dB.addWeightReading(wReading);

            activity.finishActivity();
        } else {
            activity.showErrorMessage();
        }
    }

    public void dialogOnAddButtonPressed(String time, String date, String reading, long oldId) {
        if (validateDate(date) && validateTime(time) && validateWeight(reading)) {

            WeightReading wReading = generateWeightReading(reading);
            dB.editWeightReading(oldId, wReading);

            activity.finishActivity();
        } else {
            activity.showErrorMessage();
        }
    }

    private WeightReading generateWeightReading(String reading) {
        Date finalDateTime = getReadingTime();

        int finalReading;

        if ("kilograms".equals(getWeightUnitMeasuerement())) {
            finalReading = Integer.parseInt(reading);
        } else {
            finalReading = GlucosioConverter.lbToKg(Integer.parseInt(reading));
        }

        return new WeightReading(finalReading, finalDateTime);
    }

    // Getters and Setters

    public String getWeightUnitMeasuerement() {
        return dB.getUser(1).getPreferred_unit_weight();
    }

    public WeightReading getWeightReadingById(Long id) {
        return dB.getWeightReadingById(id);
    }

    // Validator
    private boolean validateWeight(String reading) {
        return validateText(reading);
    }

}
