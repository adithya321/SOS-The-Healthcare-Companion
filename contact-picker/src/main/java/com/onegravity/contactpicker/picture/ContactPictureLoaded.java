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

import android.graphics.Bitmap;

import org.greenrobot.eventbus.EventBus;

/**
 * This event is sent from the ContactPictureLoader to the ContactPictureManager.
 * The latter will then set the ContactBadge's contact picture (if the keys match).
 */
public class ContactPictureLoaded {

    private final String mKey;
    private final ContactBadge mBadge;
    private final Bitmap mBitmap;

    private ContactPictureLoaded(String key, ContactBadge badge, Bitmap bitmap) {
        mKey = key;
        mBadge = badge;
        mBitmap = bitmap;
    }

    static void post(String key, ContactBadge badge, Bitmap bitmap) {
        ContactPictureLoaded event = new ContactPictureLoaded(key, badge, bitmap);
        EventBus.getDefault().post(event);
    }

    ContactBadge getBadge() {
        return mBadge;
    }

    String getKey() {
        return mKey;
    }

    Bitmap getBitmap() {
        return mBitmap;
    }

}
