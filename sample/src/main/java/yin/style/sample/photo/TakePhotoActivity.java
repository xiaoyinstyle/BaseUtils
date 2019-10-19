package yin.style.sample.photo;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.sample.R;
import yin.style.sample.photo.fragment.ChooseTypeFragment;
import yin.style.sample.photo.fragment.ChooseTypeFragment2;

public class TakePhotoActivity extends TitleActivity {
    @BindView(R.id.tv_photo)
    TextView tvPhoto;
    @BindView(R.id.fl_photo)
    FrameLayout flPhoto;


    ChooseTypeFragment fragment1;
    ChooseTypeFragment2 fragment2;
    @BindView(R.id.bt_type_1)
    RadioButton btType1;

    @Override
    protected void setTitle(TitleLayout titleLayout) {
        title.setText("图片选择");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_take_photo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        btType1.performClick();
    }

    @OnClick({R.id.bt_type_1, R.id.bt_type_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_type_1:
                if (fragment1 == null) {
                    fragment1 = new ChooseTypeFragment();
                }
                showFragment(fragment1, getSupportFragmentManager(), R.id.fl_photo);
                break;
            case R.id.bt_type_2:
                if (fragment2 == null) {
                    fragment2 = new ChooseTypeFragment2();
                }
                showFragment(fragment2, getSupportFragmentManager(), R.id.fl_photo);
                break;
        }
    }

    private void showFragment(Fragment fragment, FragmentManager manager, int idRes) {
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        boolean b = false;
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).equals(fragment)) {
                b = true;
                transaction.show(fragment);
            } else {
                transaction.hide(fragments.get(i));
            }
        }
        if (!b) {
            transaction.add(idRes, fragment)
                    .show(fragment);
        }
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fragment1 != null) {
            fragment1.onActivityResult(requestCode, resultCode, data);
        }
        if (fragment2 != null) {
            fragment2.onActivityResult(requestCode, resultCode, data);
        }
    }
}
