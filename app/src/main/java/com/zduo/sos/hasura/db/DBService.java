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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zduo.sos.hasura.core.Call;
import com.zduo.sos.hasura.core.Converter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class DBService {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(Date.class, GsonTypeConverters.dateJsonSerializer)
            .registerTypeAdapter(Date.class, GsonTypeConverters.dateJsonDeserializer)
            .registerTypeAdapter(Time.class, GsonTypeConverters.timeJsonSerializer)
            .registerTypeAdapter(Time.class, GsonTypeConverters.timeJsonDeserializer)
            .registerTypeAdapter(Timestamp.class, GsonTypeConverters.tsJsonSerializer)
            .registerTypeAdapter(Timestamp.class, GsonTypeConverters.tsJsonDeserializer)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    private OkHttpClient client;

    private String dbUrl;
    private String dbPrefix;

    public DBService(String dbUrl, String dbPrefix, OkHttpClient client) {
        this.dbUrl = dbUrl;
        this.dbPrefix = dbPrefix;
        this.client = client;
    }

    public <T, E extends Exception> Call<T, E> mkCall(
            String url, String jsonBody, Converter<T, E> converter) {
        RequestBody reqBody = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .url(this.dbUrl + this.dbPrefix + url)
                .post(reqBody)
                .build();
        return new Call<>(client.newCall(request), converter);
    }

    public <R> SelectQuery<R> select(Table<R> table) {
        return new SelectQuery<R>(this, table);
    }

    public <R> InsertQuery<R> insert(Table<R> table) {
        return new InsertQuery<R>(this, table);
    }

    public <R> UpdateQuery<R> update(Table<R> table) {
        return new UpdateQuery<R>(this, table);
    }

    public <R> DeleteQuery<R> delete(Table<R> table) {
        return new DeleteQuery<R>(this, table);
    }
}
