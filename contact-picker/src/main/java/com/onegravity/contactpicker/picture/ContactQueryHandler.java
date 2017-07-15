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

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.onegravity.contactpicker.Helper;

public class ContactQueryHandler extends AsyncQueryHandler {

    private final String[] mExcludeMimes;
    private int mToken;
    private boolean mCancelled;
    private ContactQueryHandlerCallback mCallback;

    public ContactQueryHandler(Context context, String[] excludeMimes) {
        super(context.getContentResolver());
        mExcludeMimes = excludeMimes;
    }

    public void startQuery(int token, Object cookie, Uri uri,
                           String[] projection, String selection, String[] selectionArgs,
                           String orderBy, ContactQueryHandlerCallback callback) {
        mToken = token;
        mCallback = callback;
        super.startQuery(token, cookie, uri, projection, selection, selectionArgs, orderBy);
    }

    public final void cancelOperation() {
        mCancelled = true;
        super.cancelOperation(mToken);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        if (mCancelled) return;

        Uri lookupUri = null;
        Uri createUri = null;
        boolean trigger = false;
        Bundle extras = (cookie != null) ? (Bundle) cookie : new Bundle();
        try {
            switch (token) {
                case Constants.TOKEN_PHONE_LOOKUP_AND_TRIGGER:
                    trigger = true;
                    createUri = Uri.fromParts("tel", extras.getString(Constants.EXTRA_URI_CONTENT), null);

                    // $FALL-THROUGH$

                case Constants.TOKEN_PHONE_LOOKUP: {
                    if (cursor != null && cursor.moveToFirst()) {
                        long contactId = cursor.getLong(Constants.PHONE_ID_COLUMN_INDEX);
                        String lookupKey = cursor.getString(Constants.PHONE_LOOKUP_STRING_COLUMN_INDEX);
                        lookupUri = ContactsContract.Contacts.getLookupUri(contactId, lookupKey);
                    }

                    break;
                }

                case Constants.TOKEN_EMAIL_LOOKUP_AND_TRIGGER:
                    trigger = true;
                    createUri = Uri.fromParts("mailto", extras.getString(Constants.EXTRA_URI_CONTENT), null);

                    // $FALL-THROUGH$

                case Constants.TOKEN_EMAIL_LOOKUP: {
                    if (cursor != null && cursor.moveToFirst()) {
                        long contactId = cursor.getLong(Constants.EMAIL_ID_COLUMN_INDEX);
                        String lookupKey = cursor.getString(Constants.EMAIL_LOOKUP_STRING_COLUMN_INDEX);
                        lookupUri = ContactsContract.Contacts.getLookupUri(contactId, lookupKey);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            Log.w(getClass().getSimpleName(), "Failed to get data: " + e.getMessage());
        } finally {
            Helper.closeQuietly(cursor);
        }

        if (mCancelled) return;

        mCallback.onQueryComplete(token, lookupUri, extras, trigger, createUri);
    }

    public interface ContactQueryHandlerCallback {
        void onQueryComplete(int token, Uri uri, Bundle extras, boolean trigger, Uri createUri);
    }

}
