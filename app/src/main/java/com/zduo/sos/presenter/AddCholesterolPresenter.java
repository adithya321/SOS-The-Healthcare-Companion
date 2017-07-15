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

import com.zduo.sos.activity.AddCholesterolActivity;
import com.zduo.sos.db.CholesterolReading;
import com.zduo.sos.db.DatabaseHandler;

import java.util.Date;

public class AddCholesterolPresenter extends AddReadingPresenter {
    private DatabaseHandler dB;
    private AddCholesterolActivity activity;

    public AddCholesterolPresenter(AddCholesterolActivity addCholesterolActivity) {
        this.activity = addCholesterolActivity;
        dB = new DatabaseHandler(addCholesterolActivity.getApplicationContext());
    }

    public void dialogOnAddButtonPressed(String time, String date, String totalCho, String LDLCho, String HDLCho) {
        if (validateDate(date) && validateTime(time) && validateCholesterol(totalCho) && validateCholesterol(LDLCho) && validateCholesterol(HDLCho)) {
            CholesterolReading cReading = generateCholesterolReading(totalCho, LDLCho, HDLCho);
            dB.addCholesterolReading(cReading);
            activity.finishActivity();
        } else {
            activity.showErrorMessage();
        }
    }

    public void dialogOnAddButtonPressed(String time, String date, String totalCho, String LDLCho, String HDLCho, long oldId) {
        if (validateDate(date) && validateTime(time) && validateCholesterol(totalCho) && validateCholesterol(LDLCho) && validateCholesterol(HDLCho)) {
            CholesterolReading cReading = generateCholesterolReading(totalCho, LDLCho, HDLCho);
            dB.editCholesterolReading(oldId, cReading);
            activity.finishActivity();
        } else {
            activity.showErrorMessage();

        }
    }

    private CholesterolReading generateCholesterolReading(String totalCho, String LDLCho, String HDLCho) {
        Date finalDateTime = getReadingTime();
        int totalChoFinal = Integer.parseInt(totalCho);
        int LDLChoFinal = Integer.parseInt(LDLCho);
        int HDLChoFinal = Integer.parseInt(HDLCho);
        return new CholesterolReading(totalChoFinal, LDLChoFinal, HDLChoFinal, finalDateTime);
    }

    public String getUnitMeasuerement() {
        return dB.getUser(1).getPreferred_unit();
    }

    public CholesterolReading getCholesterolReadingById(Long id) {
        return dB.getCholesterolReading(id);
    }

    // Validator
    private boolean validateCholesterol(String reading) {
        return validateText(reading);
    }
}
