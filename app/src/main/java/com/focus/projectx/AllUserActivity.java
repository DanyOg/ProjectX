package com.focus.projectx;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.focus.projectx.HttpLoader.Link;
import com.focus.projectx.HttpLoader.RetroClient;
import com.focus.projectx.helpers.UserAdapter;
import com.focus.projectx.model.AllUserModel;
import com.focus.projectx.model.UserModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.focus.projectx.fragments.SignInFragment.PUBLIC_TOKEN_KEY;
import static com.focus.projectx.helpers.Urls.APP_PREFERENCES;

public class AllUserActivity extends AppCompatActivity {
    private Link link;
    private RecyclerView recyclerView;
    private UserAdapter mAdapter;
    private AllUserModel userModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);
        link  = RetroClient.getApiService();

        getUsersList();

    }

    private void getUsersList(){
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + getSavedToken());

        Call<AllUserModel> call = link.getAllUser(map);
        call.enqueue(new Callback<AllUserModel>() {
            @Override
            public void onResponse(Call<AllUserModel> call, Response<AllUserModel> response) {
                AllUserModel userModel = response.body();

                createList(userModel);
                Log.d("Alluser",userModel.getUser().get(1).getPersonFirstName());
            }

            @Override
            public void onFailure(Call<AllUserModel> call, Throwable t) {

            }
        });
    }

    private String getSavedToken(){
        SharedPreferences sharedPref = getApplication().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getString(PUBLIC_TOKEN_KEY, "logout");
    }

    private void createList(AllUserModel model){
        recyclerView = (RecyclerView)findViewById(R.id.user_recycle_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        mAdapter = new UserAdapter(getApplicationContext(), model);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
