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

public class Condition<R> {
    private JsonObject boolExp;

    Condition(JsonObject boolExp) {
        this.boolExp = boolExp;
    }

    public JsonObject getBoolExp() {
        return this.boolExp;
    }

    public Condition<R> and(Condition<R> c2) {
        JsonObject newBoolExp = new JsonObject();
        JsonArray andExpArr = new JsonArray();
        andExpArr.add(this.boolExp);
        andExpArr.add(c2.getBoolExp());
        newBoolExp.add("$and", andExpArr);
        return new Condition<R>(newBoolExp);
    }

    public Condition<R> or(Condition<R> c2) {
        JsonObject newBoolExp = new JsonObject();
        JsonArray orExpArr = new JsonArray();
        orExpArr.add(this.boolExp);
        orExpArr.add(c2.getBoolExp());
        newBoolExp.add("$or", orExpArr);
        return new Condition<R>(newBoolExp);
    }
}
