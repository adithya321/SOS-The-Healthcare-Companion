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

import com.zduo.sos.hasura.core.Converter;
import com.zduo.sos.hasura.core.HasuraJsonException;
import com.zduo.sos.hasura.core.Util;

import java.io.IOException;
import java.lang.reflect.Type;

public class DBResponseConverter<T> implements Converter<T, DBException> {

    private final Type resType;

    public DBResponseConverter(Type resType) {
        this.resType = resType;
    }

    @Override
    public T fromResponse(okhttp3.Response response) throws DBException {
        int code = response.code();

        try {
            if (code == 200) {
                return Util.parseJson(DBService.gson, response, resType);
            } else {
                DBErrorResponse err = Util.parseJson(DBService.gson, response, DBErrorResponse.class);
                DBError errCode;
                switch (code) {
                    case 400:
                        errCode = DBError.BAD_REQUEST;
                        break;
                    case 401:
                        errCode = DBError.UNAUTHORIZED;
                        break;
                    case 402:
                        errCode = DBError.REQUEST_FAILED;
                        break;
                    case 403:
                        errCode = DBError.INVALID_SESSION;
                        break;
                    case 500:
                        errCode = DBError.INTERNAL_ERROR;
                        break;
                    default:
                        errCode = DBError.UNEXPECTED_CODE;
                        break;
                }
                throw new DBException(errCode, err.getError());
            }
        } catch (HasuraJsonException e) {
            throw new DBException(DBError.INTERNAL_ERROR, e);
        }
    }

    @Override
    public DBException fromIOException(IOException e) {
        return new DBException(DBError.CONNECTION_ERROR, e);
    }

    @Override
    public DBException castException(Exception e) {
        return (DBException) e;
    }
}
