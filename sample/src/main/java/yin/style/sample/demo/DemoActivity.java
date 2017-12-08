package yin.style.sample.demo;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import yin.style.baselib.activity.base.TitleActivity;
import com.jskingen.baseutils.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Chne
 */
public class DemoActivity extends TitleActivity {

    @BindView(R.id.grg)
    GridRadioGroup grg;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected int getViewByXml() {
        return R.layout.activity_demo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setTitle() {

    }

    @OnClick(R.id.tv)
    public void onViewClicked() {

        grg.removeAllViews();
        for (int i = 0; i < 5; i++) {
            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setText("文本—" + i);
            grg.addView(radioButton, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

    }
}
