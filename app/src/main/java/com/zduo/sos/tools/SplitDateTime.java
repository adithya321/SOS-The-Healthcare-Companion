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

package com.zduo.sos.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SplitDateTime {

    private Date origDateTime; // Example "yyyy-MM-dd HH:mm"
    private DateFormat inputFormat;

    public SplitDateTime(Date origDatetime, DateFormat origDateFormat) {
        this.origDateTime = origDatetime;
        this.inputFormat = origDateFormat;
    }

    public String getHour() {
        DateFormat finalFormat = new SimpleDateFormat("HH");
        return finalFormat.format(origDateTime);
    }

    public String getMinute() {
        DateFormat finalFormat = new SimpleDateFormat("mm");
        return finalFormat.format(origDateTime);
    }

    public String getYear() {
        DateFormat finalFormat = new SimpleDateFormat("yyyy");
        return finalFormat.format(origDateTime);
    }

    public String getMonth() {
        DateFormat finalFormat = new SimpleDateFormat("MM");
        return finalFormat.format(origDateTime);
    }

    public String getDay() {
        DateFormat finalFormat = new SimpleDateFormat("dd");
        return finalFormat.format(origDateTime);
    }
}
