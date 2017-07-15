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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ObjectRelationship<R1, R2>
        extends QueryWithProjection<ObjectRelationship<R1, R2>, R2>
        implements SelectField<R1> {

    private String columnName;

    public ObjectRelationship(String columnName) {
        super();
        this.columnName = columnName;
    }

    public ObjectRelationship<R1, R2> fromColumns(JsonArray columns) {
        this.columns = columns;
        return this;
    }

    public JsonElement toQCol() {
        JsonObject col = new JsonObject();
        col.add("name", new JsonPrimitive(this.columnName));
        col.add("columns", this.columns);
        return col;
    }

    public Condition<R1> has(Condition<R2> c) {
        JsonObject boolExp = new JsonObject();
        boolExp.add(this.columnName, c.getBoolExp());
        return new Condition<R1>(boolExp);
    }
}
