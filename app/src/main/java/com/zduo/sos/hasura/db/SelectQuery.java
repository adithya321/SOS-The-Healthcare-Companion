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

import java.util.List;

public class SelectQuery<R> extends QueryWithOrder<SelectQuery<R>, R> {

    private JsonObject whereExp;
    private int limit;
    private int offset;
    private DBService db;

    private Table<R> table;

    public SelectQuery(DBService db, Table<R> table) {
        super();
        this.whereExp = null;
        this.limit = -1;
        this.offset = -1;
        this.table = table;
        this.db = db;
    }

    public SelectQuery<R> fromColumns(JsonArray columns) {
        this.columns = columns;
        return this;
    }

    public SelectQuery<R> fromOrderByCols(JsonArray orderByCols) {
        this.orderByCols = orderByCols;
        return this;
    }

    public SelectQuery<R> where(Condition<R> c) {
        this.whereExp = c.getBoolExp();
        return this;
    }

    public SelectQuery<R> limit(int limit) {
        this.limit = limit;
        return this;
    }

    public SelectQuery<R> offset(int offset) {
        this.offset = offset;
        return this;
    }

    public Call<List<R>, DBException> build() {
        /* Create the query object */
        JsonObject query = new JsonObject();
        query.add("columns", this.columns);
        if (this.whereExp != null)
            query.add("where", this.whereExp);
        if (this.limit != -1)
            query.add("limit", new JsonPrimitive(this.limit));
        if (this.offset != -1)
            query.add("offset", new JsonPrimitive(this.offset));
        if (this.orderByCols.size() > 0)
            query.add("order_by", this.orderByCols);

        String opUrl = "/table/" + table.getTableName() + "/select";
        Converter<List<R>, DBException> converter
                = new DBResponseConverter<>(table.getSelResType());
        return db.mkCall(opUrl, db.gson.toJson(query), converter);
    }

    public Call<List<R>, DBException> build(String json) {
        Converter<List<R>, DBException> converter
                = new DBResponseConverter<>(table.getSelResType());
        return db.mkCall("", json, converter);
    }
}
