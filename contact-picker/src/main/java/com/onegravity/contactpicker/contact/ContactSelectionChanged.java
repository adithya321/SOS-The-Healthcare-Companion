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

/**
 * The contact selection has changed.
 * <p>
 * We need to:
 * - recalculate the number of selected contacts
 * - deselect groups if no contact is selected
 * <p>
 * We could just use the regular listener mechanism to propagate changes for checked/un-checked
 * contacts but if the user selects "Check All / Un-check All" this would trigger a call for each
 * contact. Therefore the listener call is suppressed and a ContactSelectionChanged fired once all
 * contacts are checked / un-checked.
 * <p>
 * Publisher: ContactFragment
 * Subscriber: ContactPickerActivity
 */
public class ContactSelectionChanged {

    private static final ContactSelectionChanged sEvent = new ContactSelectionChanged();

    static void post() {
        EventBus.getDefault().post(sEvent);
    }

}
