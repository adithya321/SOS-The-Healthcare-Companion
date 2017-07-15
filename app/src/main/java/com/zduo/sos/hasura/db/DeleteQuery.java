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
import com.zduo.sos.hasura.core.Call;
import com.zduo.sos.hasura.core.Converter;

import java.util.HashSet;

public class DeleteQuery<R> extends QueryWithReturning<DeleteQuery<R>, R> {
    private JsonObject whereExp;
    private DBService db;
    private Table<R> table;

    public DeleteQuery(DBService db, Table<R> table) {
        super();
        this.whereExp = null;
        this.table = table;
        this.db = db;
    }

    public DeleteQuery<R> fromRetSet(HashSet<String> retSet) {
        this.retSet = retSet;
        return this;
    }

    public DeleteQuery<R> where(Condition<R> c) {
        this.whereExp = c.getBoolExp();
        return this;
    }

    public Call<DeleteResult<R>, DBException> build() {
        /* Create the query object */
        JsonObject query = new JsonObject();
        if (this.whereExp != null)
            query.add("where", this.whereExp);

        if (this.retSet.size() != 0) {
            JsonArray retArr = new JsonArray();
            for (String retCol : this.retSet)
                retArr.add(new JsonPrimitive(retCol));
            query.add("returning", retArr);
        }

        String opUrl = "/table/" + table.getTableName() + "/delete";
        Converter<DeleteResult<R>, DBException> converter
                = new DBResponseConverter<>(table.getDelResType());
        return db.mkCall(opUrl, db.gson.toJson(query), converter);
    }
}
