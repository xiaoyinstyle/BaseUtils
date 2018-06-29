package yin.style.sample.http;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.utils.FileUtils;
import yin.style.baselib.utils.ToastUtils;
import yin.style.sample.R;
import yin.style.sample.http.okgo.BaseResponse;
import yin.style.sample.http.okgo.BaseResult;

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on  2018/6/27.
 * <p>
 * https://github.com/jeasonlzy/okhttp-OkGo
 */
public class mOkgoActivity extends TitleActivity {


    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.text)
    TextView text;

    @Override
    protected void setTitle(TitleLayout titleLayout) {
        title.setText("Okgo");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_network;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        checkbox.setChecked(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @OnClick({R.id.bt2_cancel, R.id.bt2_get, R.id.bt2_post, R.id.bt2_upload, R.id.bt2_bitmap, R.id.bt2_download, R.id.bt2_rx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt2_cancel:
                OkGo.getInstance().cancelTag(this);
                break;
            case R.id.bt2_get:
                Map<String, String> maps = new HashMap();
                maps.put("mobile", "17625064050");
                maps.put("password", "123456");
                OkGo.<JSONObject>get("http://stage.chengpai.net.cn/doctor/login/login")//
                        .params(maps)
                        .tag(this)
                        .execute(new BaseResult<JSONObject>() {
                            @Override
                            public void onSuccess(Response<JSONObject> response) {
                                ToastUtils.show("网络请求成功");
                                try {
                                    text.setText(response.body().get("result") + "");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Response<JSONObject> response) {
                                super.onError(response);
                                ToastUtils.show("网络请求失败");
                            }

                        });
                break;
            case R.id.bt2_post:
                maps = new HashMap();
                maps.put("mobile", "17625064050");
                maps.put("password", "123456");
                OkGo.<BaseResponse<List<String>>>post("http://stage.chengpai.net.cn/doctor/login/login")//
                        .params(maps)
                        .tag(this)
                        .execute(new BaseResult<BaseResponse<List<String>>>() {
                            @Override
                            public void onSuccess(Response<BaseResponse<List<String>>> response) {
                                ToastUtils.show("网络请求成功");
                                text.setText(response.body().data.size() + "");
                            }

                            @Override
                            public void onError(Response<BaseResponse<List<String>>> response) {
                                super.onError(response);
                                ToastUtils.show("网络请求失败");
                            }
                        });
                break;
            case R.id.bt2_upload:
                break;
            case R.id.bt2_bitmap:
                OkGo.<Bitmap>post("https://img-blog.csdn.net/20170625011730956?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2VpeGluXzM2MjEwNjk4/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast")

                        .tag(this).execute(new BaseResult<Bitmap>() {
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        ToastUtils.show("网络请求成功");
                        ivPic.setImageBitmap(response.body());
                    }

                    @Override
                    public void onError(Response<Bitmap> response) {
                        super.onError(response);
                        ToastUtils.show("网络请求失败");
                    }
                });
                break;
            case R.id.bt2_download:
                OkGo.<File>get("http://ez.downxy.com/down1/pythoncks_downcc.zip")
                        .tag(this).execute(new BaseResult<File>() {
                    @Override
                    protected void FileCallback(String destFileDir, String destFileName) {
                        super.FileCallback(FileUtils.getDownloadFile(mContext).getPath(), "abc.zip");
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        ToastUtils.show("网络请求成功");
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        ToastUtils.show("网络请求失败");
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        Log.e(TAG, "downloadProgress: " + progress.fraction);
                        text.setText("totalSize:" + progress.totalSize + "下载进度:" + progress.fraction + "");
                    }
                });
                break;
            case R.id.bt2_rx:
                break;
        }
    }
}
