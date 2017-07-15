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

import com.onegravity.contactpicker.ContactElement;
import com.onegravity.contactpicker.Helper;
import com.onegravity.contactpicker.OnContactCheckedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The concrete but abstract implementation of ContactElement.
 */
abstract class ContactElementImpl implements ContactElement {

    final private long mId;
    private String mDisplayName;

    transient private List<OnContactCheckedListener> mListeners = new ArrayList<>();
    transient private boolean mChecked = false;

    ContactElementImpl(long id, String displayName) {
        mId = id;
        mDisplayName = Helper.isNullOrEmpty(displayName) ? "---" : displayName;
    }

    @Override
    public long getId() {
        return mId;
    }

    @Override
    public String getDisplayName() {
        return mDisplayName != null ? mDisplayName : "";
    }

    protected void setDisplayName(String value) {
        mDisplayName = value;
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void setChecked(boolean checked, boolean suppressListenerCall) {
        boolean wasChecked = mChecked;
        mChecked = checked;
        if (!mListeners.isEmpty() && wasChecked != checked && !suppressListenerCall) {
            for (OnContactCheckedListener listener : mListeners) {
                listener.onContactChecked(this, wasChecked, checked);
            }
        }
    }

    @Override
    public void addOnContactCheckedListener(OnContactCheckedListener listener) {
        mListeners.add(listener);
    }

    @Override
    public boolean matchesQuery(String[] queryStrings) {
        String dispName = getDisplayName();
        if (Helper.isNullOrEmpty(dispName)) return false;

        dispName = dispName.toLowerCase(Locale.getDefault());
        for (String queryString : queryStrings) {
            if (!dispName.contains(queryString)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return mId + ": " + mDisplayName;
    }

}
