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

package com.onegravity.contactpicker.core;

import com.onegravity.contactpicker.contact.Contact;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * GroupImpl is the concrete Group implementation.
 * It can be instantiated and modified only within its own package to prevent modifications from
 * classes outside the package.
 */
class GroupImpl extends ContactElementImpl {

    private Map<Long, Contact> mContacts = new HashMap<>();

    private GroupImpl(long id, String displayName) {
        super(id, displayName);
    }

    public Collection<Contact> getContacts() {
        return mContacts.values();
    }

    void addContact(Contact contact) {
        long contactId = contact.getId();
        if (!mContacts.keySet().contains(contactId)) {
            mContacts.put(contact.getId(), contact);
        }
    }

    boolean hasContacts() {
        return mContacts.size() > 0;
    }

}
