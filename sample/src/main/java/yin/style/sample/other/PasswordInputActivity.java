package yin.style.sample.other;

import android.os.Bundle;
import android.widget.Toast;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.sample.R;
import yin.style.sample.other.weight.Keyboard;
import yin.style.sample.other.weight.PayEditText;

public class PasswordInputActivity extends TitleActivity {
    private static final String[] KEY = new String[] {
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9",
            "<<", "0", "完成"
    };

    private PayEditText payEditText;
    private Keyboard keyboard;

    @Override
    protected int getViewByXml() {
        return R.layout.activity_password_input;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        payEditText = (PayEditText) findViewById(R.id.PayEditText_pay);
        keyboard = (Keyboard) findViewById(R.id.KeyboardView_pay);

        setSubView();
        initEvent();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setTitle() {

    }

    private void setSubView() {
        //设置键盘
        keyboard.setKeyboardKeys(KEY);
    }

    private void initEvent() {
        keyboard.setOnClickKeyboardListener(new Keyboard.OnClickKeyboardListener() {
            @Override
            public void onKeyClick(int position, String value) {
                if (position < 11 && position != 9) {
                    payEditText.add(value);
                } else if (position == 9) {
                    payEditText.remove();
                }else if (position == 11) {
                    //当点击完成的时候，也可以通过payEditText.getText()获取密码，此时不应该注册OnInputFinishedListener接口
                    Toast.makeText(getApplication(), "您的密码是：" + payEditText.getText(), Toast.LENGTH_SHORT).show();
//                    finish();
                }
            }
        });

        /**
         * 当密码输入完成时的回调
         */
        payEditText.setOnInputFinishedListener(new PayEditText.OnInputFinishedListener() {
            @Override
            public void onInputFinished(String password) {
                Toast.makeText(getApplication(), "您的密码是：" + password, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
