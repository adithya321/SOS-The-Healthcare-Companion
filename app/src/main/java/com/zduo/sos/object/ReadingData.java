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

import java.util.ArrayList;
import java.util.List;

public class ReadingData {

    public PredictionData prediction;
    public List<GlucoseData> trend;
    public List<GlucoseData> history;

    public ReadingData(PredictionData.Result result) {
        this.prediction = new PredictionData();
        this.prediction.realDate = System.currentTimeMillis();
        this.prediction.errorCode = result;
        this.trend = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    public ReadingData(PredictionData prediction, List<GlucoseData> trend, List<GlucoseData> history) {
        this.prediction = prediction;
        this.trend = trend;
        this.history = history;
    }

    public ReadingData() {
    }

    public static class TransferObject {
        public long id;
        public ReadingData data;

        public TransferObject() {
        }

        public TransferObject(long id, ReadingData data) {
            this.id = id;
            this.data = data;
        }
    }
}
