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

package com.zduo.sos.hasura.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zduo.sos.hasura.core.Call;

import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AuthService {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    protected static final Gson gson = new GsonBuilder().create();
    private OkHttpClient httpClient;
    private String authUrl;

    public AuthService(String authUrl, OkHttpClient httpClient) {
        this.authUrl = authUrl;
        this.httpClient = httpClient;
    }

    public AuthService(String authUrl) {
        this.authUrl = authUrl;
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        this.httpClient
                = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
    }

    public OkHttpClient getClient() {
        return this.httpClient;
    }

    public String getUrl() {
        return this.authUrl;
    }

    private <T> Call<T, AuthException> mkPostCall(String url, String jsonBody, Type bodyType) {
        RequestBody reqBody = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .url(this.authUrl + url)
                .post(reqBody)
                .build();
        Call<T, AuthException> newCall
                = new Call<T, AuthException>(
                httpClient.newCall(request), new AuthResponseConverter<T>(bodyType));
        return newCall;
    }

    private <T> Call<T, AuthException> mkGetCall(String url, Type bodyType) {
        Request request = new Request.Builder()
                .url(this.authUrl + url)
                .build();
        Call<T, AuthException> newCall
                = new Call<T, AuthException>(
                httpClient.newCall(request), new AuthResponseConverter<T>(bodyType));
        return newCall;
    }

    /**
     * Signup or register a new user
     *
     * @param r a {@link RegisterRequest} type
     * @return the {@link RegisterResponse}
     * @throws AuthException
     */
    public Call<RegisterResponse, AuthException> register(RegisterRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<RegisterResponse>() {
        }.getType();
        return mkPostCall("/signup", jsonBody, respType);
    }

    /**
     * Login an existing user
     * <p>
     * Login an existing user by creating a {@link LoginRequest} class
     *
     * @param r {@link LoginRequest} type
     * @return the {@link LoginResponse}
     * @throws AuthException
     */
    public Call<LoginResponse, AuthException> login(LoginRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<LoginResponse>() {
        }.getType();
        return mkPostCall("/login", jsonBody, respType);
    }

    /**
     * Login an existing user
     * <p>
     * Login an existing user by passing username and password. This is a shortcut for the above
     * method when only username and password is used for login.
     *
     * @param userName the user name of the user
     * @param password password of the user (unencrypted)
     * @return the {@link LoginResponse}
     * @throws AuthException
     */
    public Call<LoginResponse, AuthException> login(
            String userName, String password) {
        return this.login(new LoginRequest(userName, password));
    }

    /**
     * Logout a logged-in user.
     *
     * @return a {@link LogoutResponse} type
     * @throws AuthException
     */
    public Call<LogoutResponse, AuthException> logout() {
        Type respType = new TypeToken<LogoutResponse>() {
        }.getType();
        return mkGetCall("/user/logout", respType);
    }

    /**
     * Returns credentials of the logged in user
     * <p>
     * This method can be used to retrieve Hasura credentials for the current logged in user.
     * Hasura credentials include "Hasura Id", "Hausura Role" and "Session Id". This method can
     * also be used to check if the user has an existing session (or logged in basically). If
     * not logged in, it will throw an {@link AuthException}.
     * </p>
     *
     * @return {@link GetCredentialsResponse}
     * @throws AuthException
     */
    public Call<GetCredentialsResponse, AuthException> getCredentials() {
        Type respType = new TypeToken<GetCredentialsResponse>() {
        }.getType();
        return mkGetCall("/user/account/info", respType);
    }

    /**
     * Confirm the email of an user - given an existing token.
     * <p>
     * Once the user retrieves the token that is sent to the user's email, this method can be
     * used to confirm the email of the user with Hasura Auth.
     * </p>
     *
     * @param r {@link ConfirmEmailRequest}
     * @return {@link ConfirmEmailResponse}
     * @throws AuthException
     */
    public Call<ConfirmEmailResponse, AuthException> confirmEmail(ConfirmEmailRequest r) {
        String token = r.token;
        Type respType = new TypeToken<ConfirmEmailResponse>() {
        }.getType();
        return mkGetCall("/email/confirm?token=" + token, respType);
    }

    /**
     * Resend the verification email of an user.
     * <p>
     * Initialize the {@link ResendEmailRequest} class with the email of the user, and pass the
     * object to this method.
     * </p>
     *
     * @param r {@link ResendEmailRequest}
     * @return {@link ResendEmailResponse}
     * @throws AuthException
     */
    public Call<ResendEmailResponse, AuthException> resendVerifyEmail(ResendEmailRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ResendEmailResponse>() {
        }.getType();
        return mkPostCall("/email/resend-verify", jsonBody, respType);
    }

    /**
     * Change user's email address.
     * <p>
     * Initialize {@link ChangeEmailRequest} with the new email address of the user. This method
     * will send a verification email to the new email address of the user.
     * </p>
     *
     * @param r {@link ChangeEmailRequest}
     * @return {@link ChangeEmailResponse}
     * @throws AuthException
     */
    public Call<ChangeEmailResponse, AuthException> changeEmail(ChangeEmailRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ChangeEmailResponse>() {
        }.getType();
        return mkPostCall("/user/email/change", jsonBody, respType);
    }

    /**
     * Change user's password
     * <p>
     * This method takes a {@link ChangePasswordRequest} object, which should contain the
     * current password and the new password.
     * </p>
     *
     * @param r {@link ChangePasswordRequest}
     * @return {@link ChangePasswordResponse}
     * @throws AuthException
     */
    public Call<ChangePasswordResponse, AuthException> changePassword(ChangePasswordRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ChangePasswordResponse>() {
        }.getType();
        return mkPostCall("/user/password/change", jsonBody, respType);
    }

    /**
     * Send an email to the user, containing the forgot password link.
     *
     * @param r {@link ForgotPasswordRequest}
     * @return {@link ForgotPasswordResponse}
     * @throws AuthException
     */
    public Call<ForgotPasswordResponse, AuthException> forgotPassword(ForgotPasswordRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ForgotPasswordResponse>() {
        }.getType();
        return mkPostCall("/password/forgot", jsonBody, respType);
    }

    /**
     * Reset the password of the user, given the password reset token and the new password.
     * <p>
     * Initialize the {@link ResetPasswordRequest} object with the password reset token (which
     * the user retrieves from the forgot password email, and the new password of the user.
     * </p>
     *
     * @param r {@link ResetPasswordRequest}
     * @return {@link ResetPasswordResponse}
     * @throws AuthException
     */
    public Call<ResetPasswordResponse, AuthException> resetPassword(ResetPasswordRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ResetPasswordResponse>() {
        }.getType();
        return mkPostCall("/password/reset", jsonBody, respType);
    }

    /**
     * Change user's username.
     *
     * @param r {@link ChangeUserNameRequest}
     * @return {@link ChangeUserNameResponse}
     * @throws AuthException
     */
    public Call<ChangeUserNameResponse, AuthException> changeUserName(ChangeUserNameRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ChangeUserNameResponse>() {
        }.getType();
        return mkPostCall("/user/account/change-username", jsonBody, respType);
    }

    /**
     * @param r
     * @return
     */
    public Call<CheckPasswordResponse, AuthException> checkPassword(CheckPasswordRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<CheckPasswordResponse>() {
        }.getType();
        return mkPostCall("/user/password/verify", jsonBody, respType);
    }

    /**
     * Confirm the mobile number of the user, by passing the OTP and the mobile number of the user.
     *
     * @param r {@link ConfirmMobileRequest}
     * @return {@link ConfirmMobileResponse}
     * @throws AuthException
     */
    public Call<ConfirmMobileResponse, AuthException> confirmMobile(ConfirmMobileRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ConfirmMobileResponse>() {
        }.getType();
        return mkPostCall("/mobile/confirm", jsonBody, respType);
    }

    /**
     * Change user's mobile number. This method will send an OTP to the new number of the user.
     *
     * @param r {@link ChangeMobileRequest}
     * @return {@link ChangeMobileResponse}
     * @throws AuthException
     */
    public Call<ChangeMobileResponse, AuthException> changeMobile(ChangeMobileRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ChangeMobileResponse>() {
        }.getType();
        return mkPostCall("/user/mobile/change", jsonBody, respType);
    }

    /**
     * Resend the OTP to a user's mobile number.
     *
     * @param r {@link ResendOTPRequest}
     * @return {@link ResendOTPResponse}
     * @throws AuthException
     */
    public Call<ResendOTPResponse, AuthException> resendOTP(ResendOTPRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ResendOTPResponse>() {
        }.getType();
        return mkPostCall("/mobile/resend-otp", jsonBody, respType);
    }

    /**
     * Delete account of the current user
     *
     * @param r {@link DeleteAccountRequest}
     * @return {@link DeleteAccountResponse}
     * @throws AuthException
     */
    public Call<DeleteAccountResponse, AuthException> deleteAccount(DeleteAccountRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<DeleteAccountResponse>() {
        }.getType();
        return mkPostCall("/user/account/delete", jsonBody, respType);
    }

    /**
     * Login or create a new user by authenticating with a third-party provider.
     * <p>
     * After you have obtained "access_token" or an "id_token" from your provider, you have to
     * pass the token and the provider name to {@link SocialLoginRequest}.
     * This method will then create the new user (if she is not an existing user), or else it
     * will login the user.
     * </p>
     *
     * @param r {@link SocialLoginRequest}
     * @return {@link SocialLoginResponse}
     * @throws AuthException
     */
    public Call<SocialLoginResponse, AuthException> socialAuth(SocialLoginRequest r) {
        // the URL is prepared inside the request class
        String url = r.prepareRequestURL();
        Type respType = new TypeToken<SocialLoginResponse>() {
        }.getType();
        return mkGetCall(url, respType);
    }
}
