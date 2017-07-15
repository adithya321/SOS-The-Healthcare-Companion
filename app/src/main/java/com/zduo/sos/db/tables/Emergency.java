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

package com.zduo.sos.db.tables;

import com.google.gson.reflect.TypeToken;
import com.zduo.sos.db.tables.records.EmergencyRecord;
import com.zduo.sos.db.tables.records.UsersRecord;
import com.zduo.sos.hasura.db.DeleteResult;
import com.zduo.sos.hasura.db.InsertResult;
import com.zduo.sos.hasura.db.PGField;
import com.zduo.sos.hasura.db.Table;
import com.zduo.sos.hasura.db.UpdateResult;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Emergency extends Table<EmergencyRecord> {
    public static final Emergency EMERGENCY = new Emergency();
    public final PGField<EmergencyRecord, Integer> ID = new PGField<>("id");
    public final PGField<EmergencyRecord, String> SOS_ID = new PGField<>("sos_id");
    public final PGField<EmergencyRecord, String> TYPE = new PGField<>("type");
    public final PGField<EmergencyRecord, String> LOCATION = new PGField<>("location");
    public final PGField<EmergencyRecord, String> TIME = new PGField<>("time");

    public Emergency() {
        super("emergency");
    }

    public Type getInsResType() {
        return new TypeToken<InsertResult<UsersRecord>>() {
        }.getType();
    }

    public Type getSelResType() {
        return new TypeToken<ArrayList<UsersRecord>>() {
        }.getType();
    }

    public Type getUpdResType() {
        return new TypeToken<UpdateResult<UsersRecord>>() {
        }.getType();
    }

    public Type getDelResType() {
        return new TypeToken<DeleteResult<UsersRecord>>() {
        }.getType();
    }
}
