package com.focus.projectx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.focus.projectx.HttpLoader.Link;
import com.focus.projectx.HttpLoader.RetroClient;
import com.focus.projectx.model.RegisterRequestStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Link link = RetroClient.getApiService();
                Call<RegisterRequestStatus> call = link.registerNewUser("Svyatoslav", "mail2@mail.com", "123456", "desc");
                call.enqueue(new Callback<RegisterRequestStatus>() {
                    @Override
                    public void onResponse(Call<RegisterRequestStatus> call, Response<RegisterRequestStatus> response) {
                       RegisterRequestStatus registerRequestStatus = response.body();
                        Log.d("Log", registerRequestStatus.getStatusMessage());
                    }

                    @Override
                    public void onFailure(Call<RegisterRequestStatus> call, Throwable t) {

                    }
                });
             //   Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
              //  startActivity(intent);
            }
        });
    }
}
