package com.focus.projectx.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signin, container, false);

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

    private void Login(String mail, String password){
        Call<RegisterRequestStatus> call = link.login(mail, password);
        call.enqueue(new Callback<RegisterRequestStatus>() {
            @Override
            public void onResponse(Call<RegisterRequestStatus> call, Response<RegisterRequestStatus> response) {
                dialog.dismiss();
                RegisterRequestStatus registerRequestStatus = response.body();
                Log.d("Log", registerRequestStatus.getStatusMessage());
                if(registerRequestStatus.getStatusMessage().equals("Logined")){
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
}
