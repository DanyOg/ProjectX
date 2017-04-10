package com.focus.projectx.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.focus.projectx.model.UserModel;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    private EditText regLastName;
    private EditText regLogin;
    private CircleImageView uploadImage;
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
        regLastName = (EditText) view.findViewById(R.id.registerLastName);
        regLogin = (EditText) view.findViewById(R.id.registerLogin);
        uploadImage = (CircleImageView) view.findViewById(R.id.profile_image);
        regBtn = (Button) view.findViewById(R.id.btnRegister);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectPicIntent = new Intent(Intent.ACTION_PICK);
                selectPicIntent.setType("image/*");
                startActivityForResult(selectPicIntent, 12345);
            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(view.getContext(), "", "loading...");
                registerUser(regName.getText().toString(),
                             regMail.getText().toString(),
                             regPass.getText().toString(),
                             regDesc.getText().toString(),
                             regLogin.getText().toString(),
                             regLastName.getText().toString());
            }
        });

        return view;
    }

    private void registerUser(final String userName, String mail, String pass, final String desc, String login, String lastName){
        File file = new File(uploadImage.getTag().toString());
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RequestBody fullName =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "Nick Patel");
        Call<UserModel> call = link.register(mail, pass, userName, login, lastName, desc, body);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                dialog.dismiss();
                UserModel registerRequestStatus = response.body();
                Log.d("RegToken", registerRequestStatus.getToken());

                try {
                }catch (Exception e){
                    Log.d("ex", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 12345 && data != null)
        {
            Uri pickedImage = data.getData();
            uploadImage.setImageURI(pickedImage);
            uploadImage.setTag(pickedImage.toString());
        }
    }

}
