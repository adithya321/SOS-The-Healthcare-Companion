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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GsonTypeConverters {

    public final static JsonDeserializer<Date> dateJsonDeserializer = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
            return json == null ? null : Date.valueOf(json.getAsString());
        }
    };
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public final static JsonSerializer<Date> dateJsonSerializer = new JsonSerializer<Date>() {
        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? null : new JsonPrimitive(dateFormat.format(src));
        }
    };
    private final static SimpleDateFormat tsFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ");

    public final static JsonSerializer<Timestamp> tsJsonSerializer = new JsonSerializer<Timestamp>() {
        @Override
        public JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? null : new JsonPrimitive(tsFormat.format(src));
        }
    };

    public final static JsonDeserializer<Timestamp> tsJsonDeserializer = new JsonDeserializer<Timestamp>() {
        @Override
        public Timestamp deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {
            try {
                String inp = json.getAsString();
                if (inp.length() > 10) {
                    inp = inp.substring(0, 11) + normaliseTime(inp.substring(11));
                }
                return json == null ? null : new Timestamp(tsFormat.parse(inp).getTime());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    };

    private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSSSSSZ");

    public final static JsonSerializer<Time> timeJsonSerializer = new JsonSerializer<Time>() {
        @Override
        public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? null : new JsonPrimitive(timeFormat.format(src));
        }
    };

    public final static JsonDeserializer<Time> timeJsonDeserializer = new JsonDeserializer<Time>() {
        @Override
        public Time deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
            try {
                String inp = normaliseTime(json.getAsString());
                return json == null ? null : new Time(timeFormat.parse(inp).getTime());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    };

    private static String normaliseTime(String s) {
        StringBuilder norm = new StringBuilder(s);
        int sLen = norm.length();
        // No padding required
        if (sLen < 9)
            return s;
        if (sLen == 21)
            return norm.deleteCharAt(18).toString();
        // ends with Z
        if (s.charAt(sLen - 1) == 'Z') {
            norm.replace(sLen - 1, sLen, "+00:00");
        } else {
            // Check the last 3rd char for *+00
            char l3 = s.charAt(sLen - 3);
            if (l3 == '+' || l3 == '-') {
                norm.append(":00");
            }
        }
        char msBegin = norm.charAt(8);
        if (msBegin != '.') {
            norm.insert(8, ".000000");
        } else {
            int colPos = norm.indexOf(":", 9);
            // 18 is the actual pos in normalised string
            String zeroPad = new String(new char[18 - colPos]).replace('\0', '0');
            norm.insert(colPos - 3, zeroPad);
        }
        return norm.deleteCharAt(18).toString();
    }

}
