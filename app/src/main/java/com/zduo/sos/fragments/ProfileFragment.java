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

package com.zduo.sos.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.onegravity.contactpicker.contact.Contact;
import com.onegravity.contactpicker.contact.ContactDescription;
import com.onegravity.contactpicker.contact.ContactSortOrder;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.picture.ContactPictureType;
import com.zduo.sos.Hasura;
import com.zduo.sos.R;
import com.zduo.sos.utils.CustomTextWatcher;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final int REQUEST_CONTACT = 0;
    @BindView(R.id.your_name)
    EditText yourName;
    @BindView(R.id.blood_group)
    Spinner bloodGroup;
    @BindView(R.id.allergies)
    EditText allergies;
    @BindView(R.id.doctor_name)
    EditText doctorName;
    @BindView(R.id.contact_no)
    EditText contact_no;
    @BindView(R.id.emergency_contact)
    TextView emergencyContact;
    @BindView(R.id.weight)
    EditText weight;
    @BindView(R.id.height)
    EditText height;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.toolbar_shadow)
    View toolbarShadow;
    @BindView(R.id.sos_id)
    TextView sosId;
    @BindView(R.id.create_coordinator)
    CoordinatorLayout createCoordinator;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        sharedPreferences = getActivity().getSharedPreferences("Profile", MODE_PRIVATE);
        EditText[] editTexts = {yourName, allergies, doctorName, contact_no, weight, height};
        for (EditText editText : editTexts) {
            editText.setText(sharedPreferences.getString(editText.getTag().toString(), ""));
            editText.addTextChangedListener(new CustomTextWatcher(editText, sharedPreferences));
        }

        sosId.setText("SOS id : " + Hasura.getCurrentUserId());

        emergencyContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Dexter.checkPermissions(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                Intent intent = new Intent(getActivity(), ContactPickerActivity.class)
                                        .putExtra(ContactPickerActivity.EXTRA_THEME, R.style.Theme_Light)
                                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE,
                                                ContactPictureType.ROUND.name())
                                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION,
                                                ContactDescription.PHONE.name())
                                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER,
                                                ContactSortOrder.AUTOMATIC.name())
                                        .putExtra(ContactPickerActivity.EXTRA_ONLY_CONTACTS_WITH_PHONE, true)
                                        .putExtra(ContactPickerActivity.EXTRA_SELECT_CONTACTS_LIMIT, 1)
                                        .putExtra(ContactPickerActivity.EXTRA_LIMIT_REACHED_MESSAGE,
                                                "Pick any one contact");

                                startActivityForResult(intent, REQUEST_CONTACT);
                            } else Toast.makeText(getActivity(),
                                    "Grant permission to access contacts in settings",
                                    Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                    }, Manifest.permission.READ_CONTACTS);
                } catch (Exception e) {
                    Toast.makeText(getActivity(),
                            "Grant permission to access contacts in settings",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.blood_groups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroup.setAdapter(adapter);
        bloodGroup.setOnItemSelectedListener(this);
        String compareValue = sharedPreferences.getString("blood_group", "A1 Negative (A1 −ve)");
        if (!compareValue.equals(null)) {
            int spinnerPosition = adapter.getPosition(compareValue);
            bloodGroup.setSelection(spinnerPosition);
        }

        emergencyContact.setFocusable(false);
        emergencyContact.setClickable(true);
        if (!sharedPreferences.getString(getString(R.string.emergency_contact), "").isEmpty())
            emergencyContact.setText(sharedPreferences.getString("e_contact_name", "") + " ( " +
                    sharedPreferences.getString(getString(R.string.emergency_contact), "") + " )");

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CONTACT && resultCode == Activity.RESULT_OK && data != null &&
                (data.hasExtra(ContactPickerActivity.RESULT_GROUP_DATA) ||
                        data.hasExtra(ContactPickerActivity.RESULT_CONTACT_DATA))) {

            List<Contact> mContacts = (List<Contact>) data.getSerializableExtra(ContactPickerActivity.RESULT_CONTACT_DATA);
            populateContactList(mContacts);
        }
    }

    private void populateContactList(List<Contact> contacts) {
        try {
            if (contacts != null && !contacts.isEmpty()) {
                for (Contact contact : contacts) {
                    editor = sharedPreferences.edit();
                    editor.putString("e_contact_name", contact.getDisplayName());
                    editor.putString(getString(R.string.emergency_contact), contact.getPhone(1));
                    editor.apply();
                    emergencyContact.setText(contact.getDisplayName() + " ( " + contact.getPhone(1) + " )");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("blood_group", parent.getItemAtPosition(position).toString());
        editor.apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}