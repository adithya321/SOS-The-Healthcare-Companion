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

package com.zduo.sos.object;

import java.text.DecimalFormat;

public class GlucoseData implements Comparable<GlucoseData> {

    public long realDate;
    public String sensorId;
    public long sensorTime;
    public int glucoseLevel = -1;
    public long phoneDatabaseId;

    public GlucoseData() {
    }

    public static String glucose(int mgdl, boolean mmol) {
        return mmol ? new DecimalFormat("##.0").format(mgdl / 18f) : String.valueOf(mgdl);
    }

    public String glucose(boolean mmol) {
        return glucose(glucoseLevel, mmol);
    }

    @Override
    public int compareTo(GlucoseData another) {
        return (int) (realDate - another.realDate);
    }
}
