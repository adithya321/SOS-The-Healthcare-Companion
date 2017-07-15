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

import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.onegravity.contactpicker.Helper;
import com.onegravity.contactpicker.R;
import com.onegravity.contactpicker.picture.ContactBadge;
import com.onegravity.contactpicker.picture.ContactPictureManager;
import com.onegravity.contactpicker.picture.ContactPictureType;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    final private ContactPictureType mContactPictureType;
    final private ContactDescription mContactDescription;
    final private int mContactDescriptionType;
    final private ContactPictureManager mContactPictureLoader;
    private View mRoot;
    private TextView mName;
    private TextView mDescription;
    private ContactBadge mBadge;
    private CheckBox mSelect;

    ContactViewHolder(View root, ContactPictureManager contactPictureLoader, ContactPictureType contactPictureType,
                      ContactDescription contactDescription, int contactDescriptionType) {
        super(root);

        mRoot = root;
        mName = (TextView) root.findViewById(R.id.name);
        mDescription = (TextView) root.findViewById(R.id.description);
        mBadge = (ContactBadge) root.findViewById(R.id.contact_badge);
        mSelect = (CheckBox) root.findViewById(R.id.select);

        mContactPictureType = contactPictureType;
        mContactDescription = contactDescription;
        mContactDescriptionType = contactDescriptionType;
        mContactPictureLoader = contactPictureLoader;

        mBadge.setBadgeType(mContactPictureType);
    }

    void bind(final Contact contact) {
        mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelect.toggle();
            }
        });

        // main text / title
        mName.setText(contact.getDisplayName());

        // description
        String description = "";
        switch (mContactDescription) {
            case EMAIL:
                description = contact.getEmail(mContactDescriptionType);
                break;
            case PHONE:
                description = contact.getPhone(mContactDescriptionType);
                break;
            case ADDRESS:
                description = contact.getAddress(mContactDescriptionType);
                break;
        }
        mDescription.setText(description);
        mDescription.setVisibility(Helper.isNullOrEmpty(description) ? View.GONE : View.VISIBLE);

        // contact picture
        if (mContactPictureType == ContactPictureType.NONE) {
            mBadge.setVisibility(View.GONE);
        } else {
            mContactPictureLoader.loadContactPicture(contact, mBadge);
            mBadge.setVisibility(View.VISIBLE);

            String lookupKey = contact.getLookupKey();
            if (lookupKey != null) {
                Uri contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                mBadge.assignContactUri(contactUri);
            }
        }

        // check box
        mSelect.setOnCheckedChangeListener(null);
        mSelect.setChecked(contact.isChecked());
        mSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                contact.setChecked(isChecked, false);
            }
        });
    }

    void onRecycled() {
        mBadge.onDestroy();
    }

}
