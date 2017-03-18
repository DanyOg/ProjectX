package com.focus.projectx.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.focus.projectx.HttpLoader.Link;
import com.focus.projectx.HttpLoader.RetroClient;
import com.focus.projectx.MainActivity;
import com.focus.projectx.R;
import com.focus.projectx.UserInfoActivity;
import com.focus.projectx.model.RegisterRequestStatus;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    private Link link;
    private ProgressDialog dialog;

    private EditText mail;
    private EditText password;
    private Button signInBtn;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signin, container, false);
        unbinder = ButterKnife.bind(this, view);

        link = RetroClient.getApiService();

        mail = (EditText) view.findViewById(R.id.user_mail);
        password = (EditText) view.findViewById(R.id.user_password);
        signInBtn = (Button) view.findViewById(R.id.button_SignIn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(view.getContext(), "", "loading...");
                Login(mail.getText().toString(), password.getText().toString());
            }
        });

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void Login(String mail, String password){
        Call<RegisterRequestStatus> call = link.login(mail, password);
        call.enqueue(new Callback<RegisterRequestStatus>() {
            @Override
            public void onResponse(Call<RegisterRequestStatus> call, Response<RegisterRequestStatus> response) {
                dialog.dismiss();
                RegisterRequestStatus registerRequestStatus = response.body();
                Log.d("Log", registerRequestStatus.getToken());
                Log.d("Log", response.toString());
              if(registerRequestStatus.getStatusMessage().equals("invalid_credentials")){
                    Intent intent = new Intent(getContext(), UserInfoActivity.class);
                    intent.putExtra("name", "Svyatoslav");
                    intent.putExtra("desc", "dsdsd");
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<RegisterRequestStatus> call, Throwable t) {
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
}
