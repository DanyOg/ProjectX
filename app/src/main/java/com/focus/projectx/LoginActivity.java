package com.focus.projectx;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.focus.projectx.fragments.SignInFragment;
import com.focus.projectx.fragments.SignUpDetalFragment;
import com.focus.projectx.fragments.SignUpFragment;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class LoginActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.sign_in, SignInFragment.class)
                .add(R.string.sign_up, SignUpFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.ChangeTab);
        viewPager.setAdapter(adapter);

        TabLayout viewPagerTab = (TabLayout) findViewById(R.id.AuthTab);
        viewPagerTab.setupWithViewPager(viewPager);

    }

    public void Registration(View v){
        /*HttpClient httpclient = new DefaultHttpClient();
        // указываем адрес вашего сервера и путь принимающего скрипта
        HttpPost httppost = new HttpPost(http://yoursite/test.php);
        try {
            List nameValuePairs = new ArrayList(2);

            // отправляемое сообщение
            nameValuePairs.add(new BasicNameValuePair("value", "12345"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost); // отправка на сервер
        } catch (ClientProtocolException e) {
            // ...
        } catch (IOException e) {
            // ...
        }*/
    }

    public void DetalAuth(View v) {
        SignUpDetalFragment detal = new SignUpDetalFragment();
        getSupportFragmentManager()
                .beginTransaction()
                //.add(R.id., detal)
                .replace(R.id.ChangeTab, detal)
                .commit();
    }

    public void Auth(View v) {
        Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
        startActivity(intent);
    }
}
