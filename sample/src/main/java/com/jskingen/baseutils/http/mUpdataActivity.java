package com.jskingen.baseutils.http;

import android.os.Bundle;
import android.widget.CheckBox;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.update.UpdateSoftUtils;
import com.jskingen.baseutils.R;

import butterknife.BindView;
import butterknife.OnClick;

public class mUpdataActivity extends TitleActivity {

    @BindView(R.id.checkbox)
    CheckBox checkbox;

    @Override
    protected void setTitle() {

    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_updata;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        checkUpload();
    }

    private void checkUpload() {
        String url = "http://p.gdown.baidu.com/f4b25a396162d5cde06b19b4d82f27af4c07423bc926de4431c9ef1d69b06f88b60e6d8dfd2dec478ebe974cf1625724814573cc476eb6df14434356fb562e40ddfd9ab07fbafbb0b0162960091221c864c30dd198705ea91e34a6297c0ee4baba01e5e76dceec4fb9e420d35757d98be3c16189119a8e6df60daa87706ede4411ab2480ab1cd515052821fefec27c74ff1324ca1f0cd484fae93ebcd71585600980f17f123aab8ffeff1ef38569e3c8ce7d2ba59e5795c32ca06a7f117365aec90a05396ddc0b7d52651f73c5d0e63480fe978631423a9c96ebea2adff05c94f1f538b2aaf7a98d53fdc27f43e4fc545696944a24ba03d0e7ad5a10f07d2797551f6e5c169b5e8114cb5297bf41777a55ad61b0162ba7f6";
        new UpdateSoftUtils.Builder(this)
                .downloadUrl(url)//下载的链接
                .showToast(checkbox.isChecked())//是否显示Toast
                .promptDialog(null)//自定义下载dialog
                .start();
    }
}
