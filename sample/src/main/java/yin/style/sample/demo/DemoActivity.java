package yin.style.sample.demo;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.view.scaleView.ScaleLinearLayout;
import yin.style.sample.R;

/**
 * @author Chne
 */
public class DemoActivity extends TitleActivity {

    @BindView(R.id.grg)
    GridRadioGroup grg;
    @BindView(R.id.tv)
    TextView tv;

    @BindView(R.id.scaleLinearLayout)
    ScaleLinearLayout scaleLinearLayout;

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

    @OnClick(R.id.scaleLinearLayout)
    public void onClick() {
        scaleLinearLayout.getHelper().setWeight(1, 1);
        scaleLinearLayout.getHelper().measure();
    }
}
