package com.jskingen.baseutils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.log.Logger;
import com.jskingen.baseutils.baseactivity.mExpandViewActivity;
import com.jskingen.baseutils.baseactivity.mRecyclerActivity;
import com.jskingen.baseutils.baseactivity.mTabActivity;
import com.jskingen.baseutils.baseactivity.mWebviewActivity;
import com.jskingen.baseutils.common.PhoneInfo;
import com.jskingen.baseutils.demo.DemoActivity;
import com.jskingen.baseutils.flowLayout.FlowLayoutActivity;
import com.jskingen.baseutils.http.mDownloadActivity;
import com.jskingen.baseutils.http.mNetworkActivity;
import com.jskingen.baseutils.http.mUpdateActivity;
import com.jskingen.baseutils.image.mImageActivity;
import com.jskingen.baseutils.photo.TakePhotoActivity;
import com.jskingen.baseutils.utils.mButtonActivity;
import com.jskingen.baseutils.utils.mDialogActivity;
import com.jskingen.baseutils.utils.mPermissionsActivity;
import com.jskingen.baseutils.utils.mPopWindowActivity;
import com.jskingen.baseutils.utils.mRadioButtonActivity;
import com.jskingen.baseutils.utils.mRecyclerVActivity;
import com.jskingen.baseutils.utils.mRefreshviewActivity;

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
//                if (b) {
//                    hideStatusView();
//                } else {
//                    showStatusView();
//                }
//                b = !b;
                break;

        }
    }

    boolean b;
}
