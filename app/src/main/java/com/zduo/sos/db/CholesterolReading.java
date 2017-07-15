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

public class CholesterolReading extends RealmObject {
    @PrimaryKey
    private long id;

    private int totalReading;
    private int LDLReading;
    private int HDLReading;
    private Date created;

    public CholesterolReading() {
    }

    public CholesterolReading(int totalReading, int LDLReading, int HDLReading, Date created) {
        // mg/dL
        // 0-200
        this.totalReading = totalReading;
        this.LDLReading = LDLReading;
        this.HDLReading = HDLReading;
        this.created = created;
    }

    public int getTotalReading() {
        return totalReading;
    }

    public void setTotalReading(int totalReading) {
        this.totalReading = totalReading;
    }

    public int getLDLReading() {
        return LDLReading;
    }

    public void setLDLReading(int LDLReading) {
        this.LDLReading = LDLReading;
    }

    public int getHDLReading() {
        return HDLReading;
    }

    public void setHDLReading(int HDLReading) {
        this.HDLReading = HDLReading;
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
