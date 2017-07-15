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

package com.zduo.sos.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
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
import com.zduo.sos.MyApplication;
import com.zduo.sos.R;
import com.zduo.sos.db.DatabaseHandler;
import com.zduo.sos.db.User;
import com.zduo.sos.db.tables.records.UsersRecord;
import com.zduo.sos.hasura.core.Callback;
import com.zduo.sos.hasura.db.DBException;
import com.zduo.sos.hasura.db.SelectQuery;
import com.zduo.sos.hasura.db.UpdateQuery;
import com.zduo.sos.hasura.db.UpdateResult;
import com.zduo.sos.tools.LabelledSpinner;
import com.zduo.sos.utils.CustomTextWatcher;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zduo.sos.R.array.gender;
import static com.zduo.sos.db.Tables.USERS;

public class HelloActivity extends AppCompatActivity {

    private static final int REQUEST_CONTACT = 0;
    @BindView(R.id.activity_hello_spinner_gender)
    LabelledSpinner genderSpinner;
    @BindView(R.id.activity_hello_spinner_blood_group)
    LabelledSpinner bloodGroupSpinner;
    @BindView(R.id.activity_hello_check_sos_notification)
    CheckBox notificationCheckbox;
    @BindView(R.id.activity_hello_button_start)
    Button startButton;
    @BindView(R.id.activity_hello_name)
    EditText activityHelloName;
    @BindView(R.id.activity_hello_age)
    EditText activityHelloAge;
    @BindView(R.id.activity_hello_height)
    EditText activityHelloHeight;
    @BindView(R.id.activity_hello_weight)
    EditText activityHelloWeight;
    @BindView(R.id.pick_contact)
    Button pickContact;
    @BindView(R.id.activity_hello_contact_no)
    EditText activityHelloContactNo;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        ButterKnife.bind(this);

        // Prevent SoftKeyboard to pop up on start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        genderSpinner.setItemsArray(gender);
        bloodGroupSpinner.setItemsArray(R.array.blood_groups);

        initStartButton();

        sharedPreferences = getSharedPreferences("Profile", 0);
        notificationCheckbox.setChecked(sharedPreferences.getBoolean("sos", true));
        ((MyApplication) getApplication()).createSOSNotification();

        EditText[] editTexts = {activityHelloName, activityHelloAge, activityHelloHeight,
                activityHelloWeight, activityHelloContactNo};
        for (EditText editText : editTexts) {
            editText.setText(sharedPreferences.getString(editText.getTag().toString(), ""));
            editText.addTextChangedListener(new CustomTextWatcher(editText, sharedPreferences));
        }

        genderSpinner.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                editor = sharedPreferences.edit();
                editor.putString("gender", adapterView.getItemAtPosition(position).toString());
                editor.apply();
            }

            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {
            }
        });
        bloodGroupSpinner.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                editor = sharedPreferences.edit();
                editor.putString("blood_group", adapterView.getItemAtPosition(position).toString());
                editor.apply();
            }

            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {
            }
        });

        pickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dexter.checkPermissions(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                Intent intent = new Intent(HelloActivity.this, ContactPickerActivity.class)
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
                            } else Toast.makeText(HelloActivity.this,
                                    "Grant permission to access contacts in settings",
                                    Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                    }, Manifest.permission.READ_CONTACTS);
                } catch (Exception e) {
                    Toast.makeText(HelloActivity.this,
                            "Grant permission to access contacts in settings",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        SelectQuery<UsersRecord> q = Hasura.db.select(USERS);
        String json = "{\n" +
                "\t\"type\":\"select\",\n" +
                "\t\"args\":{\n" +
                "\t\t\"table\":\"users\",\n" +
                "\t\t\"columns\":[\n" +
                "\t\t\t\"sos_id\",\"dob\",\"contact_no\",\"name\", \"doc_id\", \"bg\", \"username\"," +
                " \"gender\", \"height\", \"weight\", \"reports\", \"doc_name\", \"allergies\", \"emergency_contact\"\n" +
                "\t\t],\n" +
                "\t\t\"where\":{\n" +
                "\t\t\t\"sos_id\":\"" + Hasura.getCurrentUserId() + "\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        q.build(json).enqueue(new Callback<List<UsersRecord>, DBException>() {
            @Override
            public void onSuccess(final List<UsersRecord> usersRecords) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        for (UsersRecord usersRecord : usersRecords) {
                            String[] values = {usersRecord.name, usersRecord.dob, usersRecord.height,
                                    usersRecord.weight, usersRecord.contact_no};
                            EditText[] editTexts = {activityHelloName, activityHelloAge,
                                    activityHelloHeight, activityHelloWeight, activityHelloContactNo};
                            for (int i = 0; i < values.length; i++)
                                setEditText(values[i], editTexts[i]);

                            setSpinner(usersRecord.gender, genderSpinner);
                            setSpinner(usersRecord.bg, bloodGroupSpinner);

                            editor = sharedPreferences.edit();
                            editor.putString("reports", usersRecord.reports);
                            editor.putString(getString(R.string.contact_no), usersRecord.contact_no);
                            editor.putString(getString(R.string.doctor_name), usersRecord.doc_name);
                            editor.putString(getString(R.string.allergies), usersRecord.allergies);
                            editor.putString(getString(R.string.emergency_contact), usersRecord.emergency_contact);
                            editor.apply();
                        }
                    }
                });
            }

            @Override
            public void onFailure(final DBException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(HelloActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void setEditText(String value, EditText editText) {
        if (!value.equals("-1")) editText.setText(value);
    }

    public void setSpinner(String value, LabelledSpinner spinner) {
        if (!value.equals("-1")) {
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter) spinner.getSpinner().getAdapter();
            int spinnerPosition = adapter.getPosition(value);
            spinner.setSelection(spinnerPosition);
        }
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
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initStartButton() {
        final Drawable pinkArrow = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_navigate_next_accent, null);
        if (pinkArrow != null) {
            pinkArrow.setBounds(0, 0, 60, 60);
            startButton.setCompoundDrawables(null, null, pinkArrow, null);
        }
    }

    @OnClick(R.id.activity_hello_check_sos_notification)
    void onNotificationClicked() {
        editor = sharedPreferences.edit();
        editor.putBoolean("sos", notificationCheckbox.isChecked());
        editor.apply();
        ((MyApplication) getApplication()).createSOSNotification();
    }

    @OnClick(R.id.activity_hello_button_start)
    void onStartClicked() {
        if (TextUtils.isEmpty(activityHelloName.getText().toString()) ||
                TextUtils.isEmpty(activityHelloHeight.getText().toString()) ||
                TextUtils.isEmpty(activityHelloWeight.getText().toString()))
            displayErrorAllDetailsRequired();
        else {
            if (validateAge(activityHelloAge.getText().toString())) {
                MyApplication myApplication = (MyApplication) getApplication();
                DatabaseHandler databaseHandler = myApplication.getDBHandler();
                databaseHandler.addUser(new User(1, "name", "language", "country",
                        Integer.parseInt(activityHelloAge.getText().toString()),
                        genderSpinner.getSpinner().getSelectedItem().toString(), 1, "mg/dL",
                        "percentage", "kilograms", "ADA", 70, 180));
                morpheusUpdate();
                saveToDB();
            } else displayErrorWrongAge();
        }
    }

    private void morpheusUpdate() {
        UpdateQuery<UsersRecord> q = Hasura.db.update(USERS);
        final String morpheus = "{\n" +
                "\t\"type\":\"update\",\n" +
                "\t\"args\":{\n" +
                "\t\t\"table\":\"users\",\n" +
                "\t\t\"where\":{\n" +
                "\t\t\t\"sos_id\":\"" + Hasura.getCurrentUserId() + "\"\n" +
                "\t\t},\n" +
                "\t\t\"$set\":{\n" +
                "\t\t\t\"reports\":\"[\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\\\"doc_name\\\":\\\"Harish Chand\\\",\n" +
                "\t\t\t\t\t\\\"doc_address\\\":\\\"Chennai\\\",\n" +
                "\t\t\t\t\t\\\"patient_name\\\":\\\"" + activityHelloName.getText().toString() + "\\\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t[\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\\\"colour\\\":\\\"#EB5453\\\",\n" +
                "\t\t\t\t\\\"content\\\":\\\"For Headache\\\",\n" +
                "\t\t\t\t\\\"date_and_time\\\":\n" +
                "\t\t\t\t\\\"201612120700\\\",\n" +
                "\t\t\t\t\\\"days_of_week\\\":null,\n" +
                "\t\t\t\t\\\"dosage\\\":1.0,\n" +
                "\t\t\t\t\\\"forever_state\\\":\n" +
                "\t\t\t\t\\\"true\\\",\n" +
                "\t\t\t\t\\\"icon\\\":\\\"ic_store_white_24dp\\\",\n" +
                "\t\t\t\t\\\"id\\\":1000,\n" +
                "\t\t\t\t\\\"interval\\\":1,\n" +
                "\t\t\t\t\\\"number_shown\\\":0,\n" +
                "\t\t\t\t\\\"number_to_show\\\":1,\n" +
                "\t\t\t\t\\\"quantity\\\":1.0,\n" +
                "\t\t\t\t\\\"repeat_type\\\":0,\n" +
                "\t\t\t\t\\\"title\\\":\\\"Aspirin\\\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\\\"colour\\\":\\\"#418EC7\\\",\n" +
                "\t\t\t\t\\\"content\\\":\\\"Reduce Fever\\\",\n" +
                "\t\t\t\t\\\"date_and_time\\\":\n" +
                "\t\t\t\t\\\"201612120700\\\",\n" +
                "\t\t\t\t\\\"days_of_week\\\":null,\n" +
                "\t\t\t\t\\\"dosage\\\":1.0,\n" +
                "\t\t\t\t\\\"forever_state\\\":\n" +
                "\t\t\t\t\\\"true\\\",\n" +
                "\t\t\t\t\\\"icon\\\":\\\"ic_store_white_24dp\\\",\n" +
                "\t\t\t\t\\\"id\\\":2000,\n" +
                "\t\t\t\t\\\"interval\\\":1,\n" +
                "\t\t\t\t\\\"number_shown\\\":0,\n" +
                "\t\t\t\t\\\"number_to_show\\\":1,\n" +
                "\t\t\t\t\\\"quantity\\\":1.0,\n" +
                "\t\t\t\t\\\"repeat_type\\\":0,\n" +
                "\t\t\t\t\\\"title\\\":\\\"Paracetamol\\\"\n" +
                "\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t]\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        q.build(morpheus).enqueue(new Callback<UpdateResult<UsersRecord>, DBException>() {
            @Override
            public void onSuccess(UpdateResult<UsersRecord> response) {
                Log.e("MORPH", morpheus + "\n\n\n" + response.toString());
            }

            @Override
            public void onFailure(final DBException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String errMsg = e.getCode().toString() + " : " + e.getLocalizedMessage();
                        Toast.makeText(HelloActivity.this, errMsg, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private boolean validateAge(String age) {
        if (TextUtils.isEmpty(age)) {
            return false;
        } else if (!TextUtils.isDigitsOnly(age)) {
            return false;
        } else {
            int finalAge = Integer.parseInt(age);
            return finalAge > 0 && finalAge < 120;
        }
    }

    private void saveToDB() {
        startButton.setEnabled(false);

        UpdateQuery<UsersRecord> query
                = Hasura.db.update(USERS);
        String json = "{\n" +
                "\t\"type\":\"update\",\n" +
                "\t\"args\":{\n" +
                "\t\t\"table\":\"users\",\n" +
                "\t\t\"where\":{\n" +
                "\t\t\t\"sos_id\":\"" + Hasura.getCurrentUserId() + "\"\n" +
                "\t\t},\n" +
                "\t\t\"$set\":{\n" +
                "\t\t\t\"name\":\"" + activityHelloName.getText().toString() + "\",\n" +
                "\t\t\t\"dob\":\"" + activityHelloAge.getText().toString() + "\",\n" +
                "\t\t\t\"gender\":\"" + genderSpinner.getSpinner().getSelectedItem().toString() + "\",\n" +
                "\t\t\t\"bg\":\"" + bloodGroupSpinner.getSpinner().getSelectedItem().toString() + "\",\n" +
                "\t\t\t\"height\":\"" + activityHelloHeight.getText().toString() + "\",\n" +
                "\t\t\t\"contact_no\":\"" + activityHelloContactNo.getText().toString() + "\",\n" +
                "\t\t\t\"weight\":\"" + activityHelloWeight.getText().toString() + "\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        query.build(json).enqueue(new Callback<UpdateResult<UsersRecord>, DBException>() {
            @Override
            public void onSuccess(UpdateResult<UsersRecord> response) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        editor = sharedPreferences.edit();
                        editor.putBoolean("first", false);
                        editor.apply();
                        Intent intent = new Intent(HelloActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(final DBException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String errMsg = e.getCode().toString() + " : " + e.getLocalizedMessage();
                        Toast.makeText(HelloActivity.this, errMsg, Toast.LENGTH_LONG).show();
                        startButton.setEnabled(true);
                    }
                });
            }
        });

        String[] bg = getResources().getStringArray(R.array.blood_groups_alias);
        FirebaseMessaging.getInstance().subscribeToTopic(bg[bloodGroupSpinner.getSpinner()
                .getSelectedItemPosition()]);
        Log.e("BG", bg[bloodGroupSpinner.getSpinner().getSelectedItemPosition()]);
    }

    public void displayErrorWrongAge() {
        Toast.makeText(getApplicationContext(), getString(R.string.helloactivity_age_invalid), Toast.LENGTH_SHORT).show();
    }

    public void displayErrorAllDetailsRequired() {
        Toast.makeText(getApplicationContext(), getString(R.string.helloactivity_details_invalid), Toast.LENGTH_LONG).show();
    }
}
