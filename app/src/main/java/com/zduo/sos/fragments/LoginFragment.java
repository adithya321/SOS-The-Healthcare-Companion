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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zduo.sos.Hasura;
import com.zduo.sos.R;
import com.zduo.sos.activities.HelloActivity;
import com.zduo.sos.db.tables.records.UsersRecord;
import com.zduo.sos.hasura.auth.AuthException;
import com.zduo.sos.hasura.auth.LoginResponse;
import com.zduo.sos.hasura.auth.RegisterRequest;
import com.zduo.sos.hasura.auth.RegisterResponse;
import com.zduo.sos.hasura.core.Call;
import com.zduo.sos.hasura.core.Callback;
import com.zduo.sos.hasura.db.DBException;
import com.zduo.sos.hasura.db.InsertQuery;
import com.zduo.sos.hasura.db.UpdateQuery;
import com.zduo.sos.hasura.db.UpdateResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;
import static com.zduo.sos.db.Tables.USERS;

public class LoginFragment extends Fragment {

    @BindView(R.id.input_username)
    EditText mUsernameField;
    @BindView(R.id.input_password)
    EditText mPasswordField;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.DarkTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View view = localInflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        sharedPreferences = getActivity().getSharedPreferences("Profile", MODE_PRIVATE);
        return view;
    }

    @OnClick(R.id.btn_login)
    public void signIn(final View v) {
        if (!validate()) {
            v.setEnabled(true);
            return;
        }

        v.setEnabled(false);
        String userName = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();
        Call<LoginResponse, AuthException> loginCall = Hasura.auth.login(userName, password);

        loginCall.enqueue(new Callback<LoginResponse, AuthException>() {
            @Override
            public void onSuccess(final LoginResponse response) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Hasura.setUserId(response.getHasuraId() + 172538);
                        editor = sharedPreferences.edit();
                        editor.putInt("id", response.getHasuraId() + 172538);
                        editor.apply();

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
                                "\t\t\t\"firebase_token\":\"" + sharedPreferences.getString("token", "-1") + "\"\n" +
                                "\t\t}\n" +
                                "\t}\n" +
                                "}";
                        query.build(json).enqueue(new Callback<UpdateResult<UsersRecord>, DBException>() {
                            @Override
                            public void onSuccess(UpdateResult<UsersRecord> response) {
                            }

                            @Override
                            public void onFailure(final DBException e) {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        String errMsg = e.getCode().toString() + " : " + e.getLocalizedMessage();
                                        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });

                        Intent intent = new Intent(getActivity(), HelloActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }

            @Override
            public void onFailure(final AuthException e) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        String errMsg = e.getCode().toString() + " : " + e.getLocalizedMessage();
                        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_LONG).show();
                        v.setEnabled(true);
                    }
                });
            }
        });
    }

    @OnClick(R.id.btn_register)
    public void register(final View v) {
        if (!validate()) {
            v.setEnabled(true);
            return;
        }

        v.setEnabled(false);

        final String userName = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();

        RegisterRequest rr = new RegisterRequest();
        rr.setUsername(userName);
        rr.setPassword(password);

        Hasura.auth.register(rr).enqueue(new Callback<RegisterResponse, AuthException>() {
            @Override
            public void onSuccess(final RegisterResponse registerResponse) {
                Hasura.setUserId(registerResponse.getHasuraId() + 172538);

                editor = sharedPreferences.edit();
                editor.putInt("id", registerResponse.getHasuraId() + 172538);
                editor.apply();

                Log.e("TOKEN", sharedPreferences.getString("token", "-1"));

                InsertQuery<UsersRecord> query = Hasura.db.insert(USERS);
                String json = "{\n" +
                        "\t\"type\":\"insert\",\n" +
                        "\t\"args\":{\n" +
                        "\t\t\"table\":\"users\",\n" +
                        "\t\t\"objects\":[\n" +
                        "\t\t\t{\n" +
                        "\t\t\t\"sos_id\":\"" + Hasura.getCurrentUserId() + "\",\n" +
                        "\t\t\t\"dob\":\"-1\",\n" +
                        "\t\t\t\"contact_no\":\"-1\",\n" +
                        "\t\t\t\"name\":\"-1\", \n" +
                        "\t\t\t\"doc_id\":\"1\", \n" +
                        "\t\t\t\"bg\":\"-1\",\n" +
                        "\t\t\t\"username\":\"" + userName + "\",\n" +
                        "\t\t\t\"gender\":\"-1\",\n" +
                        "\t\t\t\"height\":\"-1\",\n" +
                        "\t\t\t\"weight\":\"-1\",\n" +
                        "\t\t\t\"firebase_token\":\"" + sharedPreferences.getString("token", "-1") + "\"\n" +
                        "\t\t\t}\n" +
                        "\t\t]\n" +
                        "\t}\n" +
                        "}";
                try {
                    query.build(json).execute();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(getActivity(), HelloActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    });
                } catch (final DBException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            String errMsg = e.getCode().toString() + " : " + e.getLocalizedMessage();
                            Toast.makeText(getActivity(), errMsg, Toast.LENGTH_LONG).show();
                            v.setEnabled(true);
                        }
                    });
                }
            }

            @Override
            public void onFailure(final AuthException e) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        String errMsg = "AUTH" + e.getCode().toString() + " : " + e.getLocalizedMessage();
                        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_LONG).show();
                        v.setEnabled(true);
                    }
                });
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String username = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();

        if (username.isEmpty()) {
            mUsernameField.setError("enter a valid username");
            valid = false;
        } else mUsernameField.setError(null);

        if (password.isEmpty() || password.length() < 8) {
            mPasswordField.setError("minimum password length is 8");
            valid = false;
        } else mPasswordField.setError(null);

        return valid;
    }
}
