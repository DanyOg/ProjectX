package com.focus.projectx.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.focus.projectx.HttpLoader.Link;
import com.focus.projectx.HttpLoader.RetroClient;
import com.focus.projectx.R;
import com.focus.projectx.UserInfoActivity;
import com.focus.projectx.model.RegisterRequestStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private Link link;
    private ProgressDialog dialog;

    private EditText regName;
    private EditText regMail;
    private EditText regPass;
    private EditText regDesc;

    private Button regBtn;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signup, container, false);

        link = RetroClient.getApiService();
        LinearLayout layout =(LinearLayout)view.findViewById(R.id.back_color);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            layout.setBackgroundDrawable( getResources().getDrawable(R.drawable.reg_background) );
        } else {
            layout.setBackground( getResources().getDrawable(R.drawable.reg_background));
        }
        regName = (EditText) view.findViewById(R.id.registerName);
        regMail = (EditText) view.findViewById(R.id.registerMail);
        regPass = (EditText) view.findViewById(R.id.registerPassword);
        regDesc = (EditText) view.findViewById(R.id.registerDesc);

        regBtn = (Button) view.findViewById(R.id.btnRegister);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(view.getContext(), "", "loading...");
                registerUser(regName.getText().toString(),
                             regMail.getText().toString(),
                             regPass.getText().toString(),
                             regDesc.getText().toString());
            }
        });

        return view;
    }

    private void registerUser(final String userName, String mail, String pass, final String desc){

        Call<RegisterRequestStatus> call = link.registerNewUser(userName, mail, pass, desc);
        call.enqueue(new Callback<RegisterRequestStatus>() {
            @Override
            public void onResponse(Call<RegisterRequestStatus> call, Response<RegisterRequestStatus> response) {
                dialog.dismiss();
                RegisterRequestStatus registerRequestStatus = response.body();
              /*  Log.d("Log", registerRequestStatus.getStatusMessage());
                if(registerRequestStatus.getStatusMessage().equals("User created")){
                    UserInfoActivity userInfoActivity = new UserInfoActivity();

                    Intent intent = new Intent(getContext(), UserInfoActivity.class);
                    intent.putExtra("name", userName);
                    intent.putExtra("desc", desc);

                    startActivity(intent);
                }*/
            }

            @Override
            public void onFailure(Call<RegisterRequestStatus> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

}
