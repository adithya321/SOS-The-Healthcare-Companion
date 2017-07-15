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

package com.onegravity.contactpicker.picture.cache;

import android.net.Uri;

/**
 * Used to cache the uri for contact photos
 */
public class ContactUriCache extends InMemoryCache<String, Uri> {

    private static ContactUriCache sInstance;

    private ContactUriCache() {
        // purge after 10 minutes of being idle, holds a maximum of 100 URIs
        super(1000 * 60 * 10, 100);
    }

    // we need to synchronize this to make sure there's no race condition instantiating the cache
    public synchronized static ContactUriCache getInstance() {
        if (sInstance == null) {
            sInstance = new ContactUriCache();
        }
        return sInstance;
    }

    /**
     * Get a photo Uri from the cache.
     *
     * @return Null if the Uri is not in the cache.
     * Uri.Empty if it's not in the cache with a previous miss meaning we already tried to
     * retrieve the query before and we failed, so there's really no point trying again
     * A valid Uri that can be used to retrieve the image.
     */
    public static Uri getUriFromCache(String key) {
        return getInstance().get(key, Uri.EMPTY);
    }

}
