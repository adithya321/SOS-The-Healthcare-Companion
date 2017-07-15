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

package com.zduo.sos.hasura.core;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;

public class Util {
    public static <R> R parseJson(Gson gson, okhttp3.Response response, Type bodyType) throws
            HasuraJsonException {
        int code = response.code();
        try {
            String rawBody = response.body().string();
            System.out.println(rawBody);
            return gson.fromJson(rawBody, bodyType);
        } catch (JsonSyntaxException e) {
            String msg
                    = "FATAL : JSON structure not as expected. Schema changed maybe? : "
                    + e.getMessage();
            throw new HasuraJsonException(code, msg, e);
        } catch (JsonParseException e) {
            String msg = "FATAL : Server didn't return valid JSON : " + e.getMessage();
            throw new HasuraJsonException(code, msg, e);
        } catch (IOException e) {
            String msg = "FATAL : Decoding response body failed : " + e.getMessage();
            throw new HasuraJsonException(code, msg, e);
        }
    }
}
