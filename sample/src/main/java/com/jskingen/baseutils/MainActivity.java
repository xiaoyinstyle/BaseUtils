package com.jskingen.baseutils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baseutils.baseactivity.mLoadRefreshActivity;
import com.jskingen.baseutils.baseactivity.mRecyclerActivity;
import com.jskingen.baseutils.baseactivity.mTabActivity;
import com.jskingen.baseutils.baseactivity.mWebviewActivity;
import com.jskingen.baseutils.http.mDownloadActivity;
import com.jskingen.baseutils.http.mNetworkActivity;
import com.jskingen.baseutils.http.mUpdataActivity;
import com.jskingen.baseutils.image.mImageActivity;
import com.jskingen.baseutils.photo.TakePhotoActivity;
import com.jskingen.baseutils.utils.mPermissionsActivity;

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

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @OnClick({R.id.bt_main_tab, R.id.bt_main_recycler, R.id.bt_main_loadrefresh
            , R.id.bt_main_webwiew, R.id.bt_main_permisson, R.id.bt_main_image, R.id.bt_main_http
            , R.id.bt_main_download, R.id.bt_main_upload, R.id.bt_main_update
            , R.id.bt_main_dialog, R.id.bt_main_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_main_tab:
                startActivity(new Intent(this, mTabActivity.class));
                break;
            case R.id.bt_main_recycler:
                startActivity(new Intent(this, mRecyclerActivity.class));
                break;
            case R.id.bt_main_loadrefresh:
                startActivity(new Intent(this, mLoadRefreshActivity.class));
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
            case R.id.bt_main_download:
                startActivity(new Intent(this, mDownloadActivity.class));
                break;
            case R.id.bt_main_upload:
                startActivity(new Intent(this, mDownloadActivity.class));
                break;
            case R.id.bt_main_update:
                startActivity(new Intent(this, mUpdataActivity.class));
                break;
            case R.id.bt_main_dialog:
                break;
            case R.id.bt_main_photo:
                startActivity(new Intent(this, TakePhotoActivity.class));
                break;

        }
    }
}
