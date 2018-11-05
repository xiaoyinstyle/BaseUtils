package yin.style.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import yin.style.baselib.utils.ToastUtils;
import yin.style.sample.utils.SignCheck;
import yin.style.sample.utilsUI.AutoSizeActivity;

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on  2018/5/17.
 * 获取权限  进行初始化
 */
public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        SignCheck signCheck = new SignCheck(this, getString(R.string.sign_value));
        if (!signCheck.check()) {
            ToastUtils.show("验证签名错误");
        }

        startActivity(new Intent(this,MainActivity.class));
//        startActivity(new Intent(this,AutoSizeActivity.class));
        finish();
    }
}
