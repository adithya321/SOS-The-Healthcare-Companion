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

package com.zduo.sos.db;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PressureReading extends RealmObject {
    @PrimaryKey
    private long id;

    private int minReading;
    private int maxReading;
    private Date created;

    public PressureReading() {
    }

    public PressureReading(int minReading, int maxReading, Date created) {
        // mm/Hg
        this.minReading = minReading;
        this.maxReading = maxReading;
        this.created = created;
    }

    public int getMinReading() {
        return minReading;
    }

    public void setMinReading(int minReading) {
        this.minReading = minReading;
    }

    public int getMaxReading() {
        return maxReading;
    }

    public void setMaxReading(int maxReading) {
        this.maxReading = maxReading;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
