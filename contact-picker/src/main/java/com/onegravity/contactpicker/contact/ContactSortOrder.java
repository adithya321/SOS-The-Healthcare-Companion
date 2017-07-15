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

package com.onegravity.contactpicker.contact;

import android.util.Log;

public enum ContactSortOrder {
    FIRST_NAME,     // sort by first name
    LAST_NAME,      // sort by last name
    AUTOMATIC;      // sort by display name (device specific)

    public static ContactSortOrder lookup(String name) {
        try {
            return ContactSortOrder.valueOf(name);
        } catch (IllegalArgumentException ignore) {
            Log.e(ContactSortOrder.class.getSimpleName(), ignore.getMessage());
            return AUTOMATIC;
        }
    }

}
