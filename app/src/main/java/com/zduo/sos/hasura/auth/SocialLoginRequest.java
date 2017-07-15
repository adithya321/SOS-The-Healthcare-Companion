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

public class SocialLoginRequest {
    String provider;
    String accessToken;
    String idToken;

    public SocialLoginRequest(String provider, String token) {
        this.provider = provider;
        if (provider == "google") {
            this.idToken = token;
        } else {
            this.accessToken = token;
        }
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setAccessToken(String token) {
        this.accessToken = token;
    }

    public void setIdToken(String token) {
        this.idToken = token;
    }

    public String prepareRequestURL() {
        if (idToken != null) {
            return "/" + provider + "/authenticate?id_token=" + idToken;
        } else {
            return "/" + provider + "/authenticate?access_token=" + accessToken;
        }
    }
}
