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
import com.zduo.sos.db.tables.records.UsersRecord;
import com.zduo.sos.hasura.db.DeleteResult;
import com.zduo.sos.hasura.db.InsertResult;
import com.zduo.sos.hasura.db.PGField;
import com.zduo.sos.hasura.db.Table;
import com.zduo.sos.hasura.db.UpdateResult;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Users extends Table<UsersRecord> {
    public static final Users USERS = new Users();
    public final PGField<UsersRecord, String> ID = new PGField<>("sos_id");
    public final PGField<UsersRecord, String> DOB = new PGField<>("dob");
    public final PGField<UsersRecord, String> CONTACT_NO = new PGField<>("contact_no");
    public final PGField<UsersRecord, String> NAME = new PGField<>("name");
    public final PGField<UsersRecord, String> DOC_ID = new PGField<>("doc_id");
    public final PGField<UsersRecord, String> BG = new PGField<>("bg");
    public final PGField<UsersRecord, String> USERNAME = new PGField<>("username");

    public Users() {
        super("user");
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
