package com.focus.projectx.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.focus.projectx.HttpLoader.Link;
import com.focus.projectx.HttpLoader.RetroClient;
import com.focus.projectx.MainActivity;
import com.focus.projectx.R;
import com.focus.projectx.UserInfoActivity;
import com.focus.projectx.model.RegisterRequestStatus;
import com.focus.projectx.model.UserModel;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.focus.projectx.helpers.Urls.APP_PREFERENCES;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    public static final String PUBLIC_TOKEN_KEY = "user_token_key";
    private static final String EMAIL_PATTERN   = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";

    private FragmentTransaction mFragmentTransaction;
    private Button              signInBtn;
    private FragmentManager     mFragmentManager;
    private Unbinder            unbinder;
    private Pattern             pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher             matcher;
    private ProgressDialog      dialog;
    private Link                link;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signin, container, false);

        unbinder = ButterKnife.bind(this, view);
        Log.d("sharedToken", getSavedToken());

        if(!getSavedToken().equals("logout")){
            Intent intent = new Intent(getContext(), UserInfoActivity.class);
            startActivity(intent);
        }
        final TextInputLayout usernameWrapper = (TextInputLayout) view.findViewById(R.id.logMail);
        final TextInputLayout passwordWrapper = (TextInputLayout) view.findViewById(R.id.logPass);

        signInBtn = (Button) view.findViewById(R.id.button_SignIn);
        link      = RetroClient.getApiService();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail(usernameWrapper.getEditText().getText().toString())) {
                    usernameWrapper.setError("Not a valid email address!");
                } else if (!validatePassword(passwordWrapper.getEditText().getText().toString())) {
                    passwordWrapper.setError("Not a valid password!");
                } else {
                    dialog = ProgressDialog.show(view.getContext(), "", "loading...");
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    Login(usernameWrapper.getEditText().getText().toString(), passwordWrapper.getEditText().getText().toString());
                }
            }
        });

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void Login(String mail, String password){
        Call<UserModel> call = link.login(mail, password);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                dialog.dismiss();
                UserModel userModel = response.body();
              try {
                  if (userModel.getToken() != null) {
                      saveToken(userModel.getToken());
                      String json = new Gson().toJson(userModel);
                      Log.d("jsoon",json);
                      Log.d("user", userModel.getUser().getPersonAvatar() + userModel.getUser().getPersonFirstName());
                      Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                      intent.putExtra("Edit", userModel);
                      startActivity(intent);
                  }
              } catch (Exception ex){
                  Toast.makeText(getContext(),"Error sign in please check your mail and password",Toast.LENGTH_SHORT).show();
              }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.button_newUser)
    public void regNewUser(){
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new SignUpFragment())
                .addToBackStack(null)
                .commit();
    }

    private boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        return password.length() > 5;
    }

    private void saveToken(String token){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(PUBLIC_TOKEN_KEY, token);
        editor.apply();
    }

    private String getSavedToken(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        return sharedPref.getString(PUBLIC_TOKEN_KEY, "logout");
    }
}
