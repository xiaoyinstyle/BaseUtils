package yin.style.sample.photo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import yin.style.baselib.activity.base.TitleActivity;
import yin.style.sample.R;
import yin.style.sample.baseActivity.fragment.HeadViewFragment;
import yin.style.sample.photo.fragment.ChooseTypeFragment;
import yin.style.sample.photo.fragment.ChooseTypeFragment2;

public class TakePhotoActivity extends TitleActivity {
    @BindView(R.id.tv_photo)
    TextView tvPhoto;
    @BindView(R.id.fl_photo)
    FrameLayout flPhoto;

    FragmentManager manager;

    ChooseTypeFragment fragment1;
    ChooseTypeFragment2 fragment2;

    @Override
    protected void setTitle() {
        title.setText("图片选择");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_take_photo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        fragment1 = new ChooseTypeFragment();
        fragment2 = new ChooseTypeFragment2();
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace( R.id.fl_photo, new ChooseTypeFragment())
//                .add(R.id.fl_photo, fragment2)
//                .show( fragment1)
               .commit();
    }


    @Override
    protected void initData() {
    }

    @OnClick({R.id.bt_type_1, R.id.bt_type_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_type_1:
                showFragment(fragment1);
                break;
            case R.id.bt_type_2:
                showFragment(fragment2);
                break;
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment2 != null) {
            transaction.hide(fragment2);
        }
        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        transaction.show(fragment)
                .commit();
    }
}
