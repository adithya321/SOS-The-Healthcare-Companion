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

package com.onegravity.contactpicker;

import java.io.Serializable;

/**
 * A ContactElement (single contact or group) always has a unique id and a display name.
 * It also can be checked/unchecked and can be observer by attaching an OnContactCheckedListener.
 */
public interface ContactElement extends Serializable {

    long getId();

    String getDisplayName();

    boolean isChecked();

    void setChecked(boolean checked, boolean suppressListenerCall);

    void addOnContactCheckedListener(OnContactCheckedListener listener);

    boolean matchesQuery(String[] queryStrings);

}
