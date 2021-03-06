package yin.style.sample.utils;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.utils.ScreenUtil;
import yin.style.sample.R;
import yin.style.baselib.view.TestChoiceDrawable;

import butterknife.BindView;

public class mRadioButtonActivity extends TitleActivity {

    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.cb_1)
    CheckBox cb1;
    @BindView(R.id.cb_2)
    CheckBox cb2;
    @BindView(R.id.cb_3)
    CheckBox cb3;

    @Override
    protected void setTitle(TitleLayout titleLayout) {
        title.setText("自定义Radio");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_radio_button;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        int width = (int) ScreenUtil.sp2px(this, 17);
        rb1.setButtonDrawable(TestChoiceDrawable.getCheckDrawable("A", width, Color.WHITE, Color.BLUE, Color.GRAY));
        rb2.setButtonDrawable(TestChoiceDrawable.getCheckDrawable("B", width, Color.WHITE, Color.BLUE, Color.GRAY));

        cb1.setButtonDrawable(TestChoiceDrawable.getCheckDrawable("A", width, Color.WHITE, Color.BLUE, Color.GRAY));
        cb2.setButtonDrawable(TestChoiceDrawable.getCheckDrawable("B", width, Color.WHITE, Color.BLUE, Color.GRAY));
        cb3.setButtonDrawable(TestChoiceDrawable.getCheckDrawable("C", width, Color.WHITE, Color.BLUE, Color.GRAY));
    }

    @Override
    protected void initData() {

    }


}
