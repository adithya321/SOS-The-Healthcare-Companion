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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onegravity.contactpicker.BaseFragment;
import com.onegravity.contactpicker.R;
import com.onegravity.contactpicker.picture.ContactPictureType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends BaseFragment {

    private ContactSortOrder mSortOrder;
    private ContactPictureType mPictureType;
    private ContactDescription mDescription;
    private int mDescriptionType;

    /**
     * The list of all contacts.
     * This is only used as a reference to the original data set while we actually use
     * mFilteredContacts.
     */
    private List<? extends Contact> mContacts = new ArrayList<>();

    /**
     * The list of all visible and filtered contacts.
     */
    private List<? extends Contact> mFilteredContacts = new ArrayList<>();

    private ContactAdapter mAdapter;

    public ContactFragment() {
    }

    public static ContactFragment newInstance(ContactSortOrder sortOrder,
                                              ContactPictureType pictureType,
                                              ContactDescription contactDescription,
                                              int descriptionType) {
        Bundle args = new Bundle();
        args.putString("sortOrder", sortOrder.name());
        args.putString("pictureType", pictureType.name());
        args.putString("contactDescription", contactDescription.name());
        args.putInt("descriptionType", descriptionType);
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mSortOrder = ContactSortOrder.lookup(args.getString("sortOrder"));
        mPictureType = ContactPictureType.lookup(args.getString("pictureType"));
        mDescription = ContactDescription.lookup(args.getString("contactDescription"));
        mDescriptionType = args.getInt("descriptionType");
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAdapter = new ContactAdapter(getActivity(), null, mSortOrder, mPictureType, mDescription, mDescriptionType);

        return super.createView(inflater, R.layout.cp_contact_list, mAdapter, mContacts);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ContactsLoaded event) {
        EventBus.getDefault().removeStickyEvent(event);

        mContacts = event.getContacts();
        mFilteredContacts = mContacts;
        mAdapter.setData(mFilteredContacts);

        updateEmptyViewVisibility(mContacts);
    }

    @Override
    protected void performFiltering(String[] queryStrings) {
        if (mContacts == null) return;

        if (queryStrings == null || queryStrings.length == 0) {
            mFilteredContacts = mContacts;
        } else {
            List<Contact> filteredElements = new ArrayList<>();
            for (Contact contact : mContacts) {
                if (contact.matchesQuery(queryStrings)) {
                    filteredElements.add(contact);
                }
            }
            mFilteredContacts = filteredElements;
        }

        mAdapter.setData(mFilteredContacts);
    }

}
