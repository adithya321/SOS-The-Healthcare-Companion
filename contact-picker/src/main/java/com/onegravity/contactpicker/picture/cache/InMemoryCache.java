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

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is an in-memory implementation of the Cache interface
 */
public abstract class InMemoryCache<K, V> implements Cache<K, V> {

    // Both hard and soft caches are purged after n seconds idling.
    private final int mDelayBeforePurge;
    private final HardLruCache mHardCacheMap;
    protected boolean mDebug;
    private Handler mPurgeHandler;
    private Runnable mPurger;
    // Soft object cache for objects removed from the hard cache
    // this gets cleared by the Garbage Collector every time we get low on memory
    private ConcurrentHashMap<K, SoftReference<V>> mSoftCache;
    // this cache keeps track of misses
    // the caller can use this to decide whether to attempt to retrieve the value
    // (which might be very expensive) or not depending on whether a previous miss has occurred
    private Set<K> mMissCache;

    protected InMemoryCache(int delayBeforePurge, int cacheCapacity) {
        mDelayBeforePurge = delayBeforePurge;

        try {
            mPurgeHandler = new Handler();
        } catch (RuntimeException e) {
            // can't create handler inside thread that has not called Looper.prepare()
            // --> we need to create our own Looper thread
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    mPurgeHandler = new Handler();
                    Looper.loop();
                }
            }).start();
        }

        mPurger = new Purger();

        mMissCache = new HashSet<K>();
        mSoftCache = new ConcurrentHashMap<>();
        mHardCacheMap = createHardLruCache(cacheCapacity);
    }

    // ****************************************** Public Methods *******************************************

    protected HardLruCache createHardLruCache(int cacheCapacity) {
        return new HardLruCache(cacheCapacity);
    }

    /**
     * Purges & clears the caches.
     */
    @Override
    public synchronized void evictAll() {
        stopPurgeTimer();
        clearCaches();
    }

    @Override
    public synchronized void put(K key, V value) {
        if (key != null) {
            if (mDebug)
                Log.e("1gravity", getClass().getSimpleName() + ".put(" + key + "): " + value);
            mHardCacheMap.put(key, value);
            mMissCache.remove(key);
        }
    }

    /**
     * As the name suggests, this method attempts to obtain a Object stored in one of the caches.
     * First it checks the hard cache for the key.
     * If a key is found, it moves the cached Object to the head of the cache so it gets moved to the soft cache last.
     * <p>
     * If the hard cache doesn't contain the Object, it checks the soft cache for the cached Object.
     * If neither of the caches contain the Object, this returns null.
     */
    @Override
    public synchronized V get(K key) {
        // we reset the caches after every 30 or so seconds of inactivity for memory efficiency
        resetPurgeTimer();

        if (key != null) {
            V value = mHardCacheMap.get(key);
            if (value != null) {
                if (mDebug)
                    Log.e("1gravity", getClass().getSimpleName() + ".get(" + key + "): hit");
                return value;
            }

            SoftReference<V> objectRef = mSoftCache.get(key);
            if (objectRef != null) {
                value = objectRef.get();
                if (value != null) {
                    if (mDebug)
                        Log.e("1gravity", getClass().getSimpleName() + ".get(" + key + "): hit");
                    return value;
                } else {
                    // must have been collected by the Garbage Collector so we remove the bucket from the cache.
                    mSoftCache.remove(key);
                }
            }
        }

        mMissCache.add(key);
        return null;
    }

    /**
     * This get method returns:
     * 1) the cached value if one exists in the cache
     * 2) the missedValue if a previous cache miss has occurred before
     * 3) null in every other case
     */
    public synchronized V get(K key, V missedValue) {
        if (key != null) {
            boolean hadMiss = mMissCache.contains(key);
            V result = get(key);
            if (result != null) {
                return result;
            }
            if (hadMiss) {
                if (mDebug)
                    Log.e("1gravity", getClass().getSimpleName() + ".get(" + key + "): miss repeatedly");
                return missedValue;
            }
            if (mDebug) Log.e("1gravity", getClass().getSimpleName() + ".get(" + key + "): miss");
        }
        return null;
    }

    private void clearCaches() {
        mMissCache.clear();
        mSoftCache.clear();
        mHardCacheMap.evictAll();
    }

    // ****************************************** Private Classes + Methods *******************************************

    /**
     * Stops the cache purger from running until it is reset again.
     */
    private void stopPurgeTimer() {
        if (mPurgeHandler != null) mPurgeHandler.removeCallbacks(mPurger);
    }

    /**
     * Purges the cache every (DELAY_BEFORE_PURGE) milliseconds.
     */
    private void resetPurgeTimer() {
        if (mPurgeHandler != null) {
            mPurgeHandler.removeCallbacks(mPurger);
            mPurgeHandler.postDelayed(mPurger, mDelayBeforePurge);
        }
    }

    protected class HardLruCache extends LruCache<K, V> {
        public HardLruCache(int initialCapacity) {
            super(initialCapacity);
        }

        @Override
        protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
            // move the removed item to the soft cache
            mSoftCache.put(key, new SoftReference<V>(newValue));
        }
    }

    private class Purger implements Runnable {
        @Override
        public void run() {
            clearCaches();
        }
    }

}