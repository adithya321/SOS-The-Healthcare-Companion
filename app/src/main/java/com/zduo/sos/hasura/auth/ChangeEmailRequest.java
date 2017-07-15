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

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ChangeEmailRequest {
    @SerializedName("email")
    String email;

    @SerializedName("info")
    JsonObject info;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setInfo(JsonObject info) {
        this.info = info;
    }
}
