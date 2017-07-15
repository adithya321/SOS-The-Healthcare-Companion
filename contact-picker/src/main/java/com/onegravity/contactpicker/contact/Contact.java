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

import android.net.Uri;

import com.onegravity.contactpicker.ContactElement;

import java.util.Set;

/**
 * This interface describes a single contact.
 * It only provides read methods to make sure no class outside this package can modify it.
 * Write access is only possible through the ContactImpl class which has package access.
 */
public interface Contact extends ContactElement {

    String getFirstName();

    String getLastName();

    String getEmail(int type);

    String getPhone(int type);

    String getAddress(int type);

    /**
     * The contact letter is used in the ContactBadge (if no contact picture can be found).
     */
    char getContactLetter();

    /**
     * The contact letter according to the sort order is used in the SectionIndexer for the fast
     * scroll indicator.
     */
    char getContactLetter(ContactSortOrder sortOrder);

    /**
     * The contact color is used in the ContactBadge (if no contact picture can be found) as
     * background color.
     */
    int getContactColor();

    /**
     * Unique key across all contacts that won't change even if the column id changes.
     */
    String getLookupKey();

    Uri getPhotoUri();

    Set<Long> getGroupIds();
}
