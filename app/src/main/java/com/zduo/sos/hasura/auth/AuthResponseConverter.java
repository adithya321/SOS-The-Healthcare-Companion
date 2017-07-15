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

package com.zduo.sos.hasura.auth;


import com.zduo.sos.hasura.core.Converter;
import com.zduo.sos.hasura.core.HasuraJsonException;
import com.zduo.sos.hasura.core.Util;

import java.io.IOException;
import java.lang.reflect.Type;

public class AuthResponseConverter<T> implements Converter<T, AuthException> {

    private final Type resType;

    public AuthResponseConverter(Type resType) {
        this.resType = resType;
    }

    @Override
    public T fromResponse(okhttp3.Response response) throws AuthException {
        int code = response.code();

        try {
            if (code == 200) {
                return Util.parseJson(AuthService.gson, response, resType);
            } else {
                AuthErrorResponse err = Util.parseJson(AuthService.gson, response, AuthErrorResponse.class);
                AuthError errCode;
                switch (code) {
                    case 400:
                        errCode = AuthError.BAD_REQUEST;
                        break;
                    case 401:
                        errCode = AuthError.UNAUTHORIZED;
                        break;
                    case 402:
                        errCode = AuthError.REQUEST_FAILED;
                        break;
                    case 403:
                        errCode = AuthError.INVALID_SESSION;
                        break;
                    case 500:
                        errCode = AuthError.INTERNAL_ERROR;
                        break;
                    default:
                        errCode = AuthError.UNEXPECTED_CODE;
                        break;
                }
                throw new AuthException(errCode, err.getMessage());
            }
        } catch (HasuraJsonException e) {
            throw new AuthException(AuthError.INTERNAL_ERROR, e);
        }
    }

    @Override
    public AuthException fromIOException(IOException e) {
        return new AuthException(AuthError.CONNECTION_ERROR, e);
    }

    @Override
    public AuthException castException(Exception e) {
        return (AuthException) e;
    }

    static class AuthErrorResponse {
        private int code;
        private String message;

        public int getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }
    }
}
