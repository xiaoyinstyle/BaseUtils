package yin.style.sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import yin.style.baselib.utils.AppManager;
import yin.style.sample.baseActivity.mExpandViewActivity;
import yin.style.sample.common.PhoneInfo;
import yin.style.sample.demo.DemoActivity;
import yin.style.sample.flowLayout.FlowLayoutActivity;
import yin.style.sample.http.mNetworkActivity;
import yin.style.sample.photo.TakePhotoActivity;
import yin.style.sample.utils.mPopWindowActivity;
import yin.style.sample.utils.mRefreshviewActivity;
import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.log.Logger;
import yin.style.baselib.utils.LogUtils;
import yin.style.baselib.utils.ScreenUtil;

import yin.style.sample.baseActivity.mRecyclerActivity;
import yin.style.sample.baseActivity.mTabActivity;
import yin.style.sample.baseActivity.mWebviewActivity;
import yin.style.sample.http.mDownloadActivity;
import yin.style.sample.http.mUpdateActivity;
import yin.style.sample.image.mImageActivity;
import yin.style.sample.utils.mButtonActivity;
import yin.style.sample.utils.mDialogActivity;
import yin.style.sample.utils.mPermissionsActivity;
import yin.style.sample.utils.mRadioButtonActivity;
import yin.style.sample.utils.mRecyclerVActivity;

import butterknife.OnClick;

public class MainActivity extends TitleActivity {

    @Override
    protected void setTitle() {
        hiddenBackButton();
        title.setText(getString(R.string.app_name));
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        try {
            PhoneInfo info = new PhoneInfo(this);
            Logger.e(info.getProvidersName());
            Logger.e(info.getNativePhoneNumber());
            Logger.e(info.getPhoneInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    private long exitTime = 0;//点击2次返回，退出程序

    //点击两次退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {//两秒内再次点击返回则退出
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                AppManager.getInstance().finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.bt_main_tab, R.id.bt_main_recycler, R.id.bt_main_loadrefresh
            , R.id.bt_main_webwiew, R.id.bt_main_permisson, R.id.bt_main_image
            , R.id.bt_main_http, R.id.bt_main_download_upload, R.id.bt_main_update
            , R.id.bt_main_dialog, R.id.bt_main_photo, R.id.bt_main_flowlayout
            , R.id.bt_main_popWindow, R.id.bt_main_refresh, R.id.bt_main_radio
            , R.id.bt_main_button, R.id.bt_main_rec, R.id.bt_main_demo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_main_tab:
                startActivity(new Intent(this, mTabActivity.class));
                break;
            case R.id.bt_main_recycler:
                startActivity(new Intent(this, mRecyclerActivity.class));
                break;
            case R.id.bt_main_loadrefresh:
                startActivity(new Intent(this, mExpandViewActivity.class));
                break;
            case R.id.bt_main_webwiew:
                startActivity(new Intent(this, mWebviewActivity.class));
                break;
            case R.id.bt_main_permisson:
                startActivity(new Intent(this, mPermissionsActivity.class));
                break;
            case R.id.bt_main_image:
                startActivity(new Intent(this, mImageActivity.class));
                break;
            case R.id.bt_main_http:
                startActivity(new Intent(this, mNetworkActivity.class));
                break;
            case R.id.bt_main_download_upload:
                startActivity(new Intent(this, mDownloadActivity.class));
                break;
            case R.id.bt_main_update:
                startActivity(new Intent(this, mUpdateActivity.class));
                break;
            case R.id.bt_main_dialog:
                startActivity(new Intent(this, mDialogActivity.class));
                break;
            case R.id.bt_main_photo:
                startActivity(new Intent(this, TakePhotoActivity.class));
                break;
            case R.id.bt_main_flowlayout:
                startActivity(new Intent(this, FlowLayoutActivity.class));
                break;
            case R.id.bt_main_popWindow:
                startActivity(new Intent(this, mPopWindowActivity.class));
                break;
            case R.id.bt_main_refresh:
                startActivity(new Intent(this, mRefreshviewActivity.class));
                break;
            case R.id.bt_main_radio:
                startActivity(new Intent(this, mRadioButtonActivity.class));
                break;
            case R.id.bt_main_rec:
                startActivity(new Intent(this, mRecyclerVActivity.class));
                break;
            case R.id.bt_main_button:
                startActivity(new Intent(this, mButtonActivity.class));
                break;
            case R.id.bt_main_demo:
                startActivity(new Intent(this, DemoActivity.class));
//                hideStatusView();
//                LogUtils.e("titleHeight:" + ScreenUtil.getTitleHeight(mContext));

//                setStatusBarView(mContext, b, Color.WHITE, false);
                b = !b;
                break;

        }
    }

    boolean b;
}
