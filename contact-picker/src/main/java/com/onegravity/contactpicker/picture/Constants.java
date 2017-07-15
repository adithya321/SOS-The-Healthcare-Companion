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

package com.onegravity.contactpicker.picture;

import android.provider.ContactsContract;

/**
 * Some constans used in the ContactBadge and the ContactQueryHandler.
 */
class Constants {

    static final int TOKEN_EMAIL_LOOKUP = 0;
    static final int TOKEN_PHONE_LOOKUP = 1;
    static final int TOKEN_EMAIL_LOOKUP_AND_TRIGGER = 2;
    static final int TOKEN_PHONE_LOOKUP_AND_TRIGGER = 3;

    static final String EXTRA_URI_CONTENT = "uri_content";

    static final String[] EMAIL_LOOKUP_PROJECTION = new String[]{
            ContactsContract.RawContacts.CONTACT_ID,
            ContactsContract.Contacts.LOOKUP_KEY,
    };
    static final int EMAIL_ID_COLUMN_INDEX = 0;
    static final int EMAIL_LOOKUP_STRING_COLUMN_INDEX = 1;

    static final String[] PHONE_LOOKUP_PROJECTION = new String[]{
            ContactsContract.PhoneLookup._ID,
            ContactsContract.PhoneLookup.LOOKUP_KEY,
    };
    static final int PHONE_ID_COLUMN_INDEX = 0;
    static final int PHONE_LOOKUP_STRING_COLUMN_INDEX = 1;

}
