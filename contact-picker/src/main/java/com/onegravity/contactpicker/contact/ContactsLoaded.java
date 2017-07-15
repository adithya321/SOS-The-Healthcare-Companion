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

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * All contacts have been loaded (including details).
 * <p>
 * Publisher: ContactPickerActivity
 * Subscriber: ContactFragment
 */
public class ContactsLoaded {

    final private List<? extends Contact> mContacts;

    private ContactsLoaded(List<? extends Contact> contacts) {
        mContacts = contacts;
    }

    public static void post(List<? extends Contact> contacts) {
        ContactsLoaded event = new ContactsLoaded(contacts);
        EventBus.getDefault().postSticky(event);
    }

    public List<? extends Contact> getContacts() {
        return mContacts;
    }

}
