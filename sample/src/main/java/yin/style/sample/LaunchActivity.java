package yin.style.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on  2018/5/17.
 * 获取权限  进行初始化
 */
public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
