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

import com.zduo.sos.activity.AddPressureActivity;
import com.zduo.sos.db.DatabaseHandler;
import com.zduo.sos.db.PressureReading;

import java.util.Date;

public class AddPressurePresenter extends AddReadingPresenter {
    private DatabaseHandler dB;
    private AddPressureActivity activity;


    public AddPressurePresenter(AddPressureActivity addPressureActivity) {
        this.activity = addPressureActivity;
        dB = new DatabaseHandler(addPressureActivity.getApplicationContext());
    }

    public void dialogOnAddButtonPressed(String time, String date, String minReading, String maxReading) {
        if (validateDate(date) && validateTime(time) && validatePressure(minReading) && validatePressure(maxReading)) {
            PressureReading pReading = generatePressureReading(minReading, maxReading);
            dB.addPressureReading(pReading);
            activity.finishActivity();
        } else {
            activity.showErrorMessage();
        }
    }

    public void dialogOnAddButtonPressed(String time, String date, String minReading, String maxReading, long oldId) {
        if (validateDate(date) && validateTime(time) && validatePressure(minReading) && validatePressure(maxReading)) {
            PressureReading pReading = generatePressureReading(minReading, maxReading);
            dB.editPressureReading(oldId, pReading);
            activity.finishActivity();
        } else {
            activity.showErrorMessage();
        }
    }

    private PressureReading generatePressureReading(String minReading, String maxReading) {
        Date finalDateTime = getReadingTime();
        int minFinalReading = Integer.parseInt(minReading);
        int maxFinalReading = Integer.parseInt(maxReading);
        return new PressureReading(minFinalReading, maxFinalReading, finalDateTime);
    }

    // Getters and Setters

    public String getUnitMeasuerement() {
        return dB.getUser(1).getPreferred_unit();
    }

    public PressureReading getPressureReadingById(long editId) {
        return dB.getPressureReading(editId);
    }

    // Validator
    private boolean validatePressure(String reading) {
        return validateText(reading);
    }
}
