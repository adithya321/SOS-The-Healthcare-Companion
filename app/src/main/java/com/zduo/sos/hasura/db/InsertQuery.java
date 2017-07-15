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

package com.zduo.sos.hasura.db;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.zduo.sos.hasura.core.Call;
import com.zduo.sos.hasura.core.Converter;

import java.lang.reflect.Type;
import java.util.HashSet;

public class InsertQuery<R> extends QueryWithReturning<InsertQuery<R>, R> {
    private JsonObject currentObj;
    private JsonArray insObjs;
    private DBService db;
    private Table<R> table;

    public InsertQuery(DBService db, Table<R> table) {
        super();
        this.currentObj = new JsonObject();
        this.insObjs = new JsonArray();
        this.table = table;
        this.db = db;
    }

    public InsertQuery<R> fromRetSet(HashSet<String> retSet) {
        this.retSet = retSet;
        return this;
    }

    public <T> InsertQuery<R> set(PGField<R, T> fld, T val) {
        Type valType = new TypeToken<T>() {
        }.getType();
        this.currentObj.add(fld.getColumnName(), db.gson.toJsonTree(val, valType));
        return this;
    }

    public <T> InsertQuery<R> newRecord() {
        this.insObjs.add(this.currentObj);
        this.currentObj = new JsonObject();
        return this;
    }

    public Call<InsertResult<R>, DBException> build() {
        /* Create the query object */
        this.insObjs.add(this.currentObj);
        JsonObject query = new JsonObject();
        query.add("objects", this.insObjs);
        JsonArray retArr = new JsonArray();
        for (String retCol : this.retSet)
            retArr.add(new JsonPrimitive(retCol));
        query.add("returning", retArr);

        String opUrl = "/table/" + table.getTableName() + "/insert";

        Converter<InsertResult<R>, DBException> converter
                = new DBResponseConverter<>(table.getInsResType());
        return db.mkCall(opUrl, db.gson.toJson(query), converter);
    }

    public Call<InsertResult<R>, DBException> build(String json) {
        Converter<InsertResult<R>, DBException> converter
                = new DBResponseConverter<>(table.getInsResType());
        return db.mkCall("", json, converter);
    }
}
