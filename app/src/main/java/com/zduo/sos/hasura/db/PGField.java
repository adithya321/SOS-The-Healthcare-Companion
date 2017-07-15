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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PGField<R, T> implements SelectField<R> {

    private String columnName;

    public PGField(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public JsonElement toQCol() {
        return new JsonPrimitive(this.columnName);
    }

    private Condition<R> op(String opRepr, T val) {
        Type valType = new TypeToken<T>() {
        }.getType();
        JsonObject opExp = new JsonObject();
        opExp.add(opRepr, DBService.gson.toJsonTree(val, valType));
        JsonObject colExp = new JsonObject();
        colExp.add(this.columnName, opExp);
        return new Condition<R>(colExp);
    }

    public Condition<R> eq(T val) {
        return this.op("$eq", val);
    }

    public Condition<R> neq(T val) {
        return this.op("$neq", val);
    }

    public Condition<R> gt(T val) {
        return this.op("$gt", val);
    }

    public Condition<R> gte(T val) {
        return this.op("$gte", val);
    }

    public Condition<R> lt(T val) {
        return this.op("$lt", val);
    }

    public Condition<R> lte(T val) {
        return this.op("$lte", val);
    }

    public SortField<R> asc() {
        return new SortFieldImpl<>(this.columnName, SortOrder.ASC, NullsOrder.LAST);
    }

    public SortField<R> desc() {
        return new SortFieldImpl<>(this.columnName, SortOrder.DESC, NullsOrder.FIRST);
    }
}
