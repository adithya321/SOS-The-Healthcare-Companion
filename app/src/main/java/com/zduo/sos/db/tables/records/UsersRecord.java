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

package com.zduo.sos.db.tables.records;

import com.google.gson.annotations.SerializedName;

public class UsersRecord {
    @SerializedName("sos_id")
    public String id;

    @SerializedName("dob")
    public String dob;

    @SerializedName("name")
    public String name;

    @SerializedName("doc_id")
    public String doc_id;

    @SerializedName("bg")
    public String bg;

    @SerializedName("username")
    public String username;

    @SerializedName("gender")
    public String gender;

    @SerializedName("height")
    public String height;

    @SerializedName("weight")
    public String weight;

    @SerializedName("reports")
    public String reports;

    @SerializedName("contact_no")
    public String contact_no;

    @SerializedName("firebase_token")
    public String firebase_token;

    @SerializedName("doc_name")
    public String doc_name;

    @SerializedName("allergies")
    public String allergies;

    @SerializedName("emergency_contact")
    public String emergency_contact;

    @Override
    public String toString() {
        return "UsersRecord{" +
                "id='" + id + '\'' +
                ", dob='" + dob + '\'' +
                ", name='" + name + '\'' +
                ", doc_id='" + doc_id + '\'' +
                ", bg='" + bg + '\'' +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", reports='" + reports + '\'' +
                ", contact_no='" + contact_no + '\'' +
                ", firebase_token='" + firebase_token + '\'' +
                ", doc_name='" + doc_name + '\'' +
                ", allergies='" + allergies + '\'' +
                ", emergency_contact='" + emergency_contact + '\'' +
                '}';
    }
}
