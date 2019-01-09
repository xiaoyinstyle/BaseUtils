package yin.style.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.log.Logger;
import yin.style.baselib.permission.OnPermissionsListener;
import yin.style.baselib.permission.XPermission;
import yin.style.baselib.utils.AppManager;
import yin.style.baselib.utils.CheckCurrentAppTools;
import yin.style.baselib.utils.ScreenUtil;
import yin.style.baselib.utils.ToastUtils;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.sample.baseActivity.mExpandViewActivity;
import yin.style.sample.baseActivity.mRecyclerActivity;
import yin.style.sample.baseActivity.mTabActivity;
import yin.style.sample.baseActivity.mViewPagerActivity;
import yin.style.sample.baseActivity.mWebviewActivity;
import yin.style.sample.common.PhoneInfo;
import yin.style.sample.demo.DemoActivity;
import yin.style.sample.flowLayout.FlowLayoutActivity;
import yin.style.sample.http.mOkgoActivity;
import yin.style.sample.http.mNetworkActivity;
import yin.style.sample.http.mUpdateActivity;
import yin.style.sample.image.mImageActivity;
import yin.style.sample.other.AutoTextViewActivity;
import yin.style.sample.photo.TakePhotoActivity;
import yin.style.sample.utilsUI.mButtonActivity;
import yin.style.sample.utilsUI.mDialog2Activity;
import yin.style.sample.utilsUI.mDialogActivity;
import yin.style.sample.utilsUI.mPermissionsActivity;
import yin.style.sample.utilsUI.mPopWindowActivity;
import yin.style.sample.utilsUI.mRadioButtonActivity;
import yin.style.sample.utilsUI.mRecyclerVActivity;
import yin.style.sample.utilsUI.mRefreshviewActivity;

public class MainActivity extends TitleActivity {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private List<String> list = Arrays.asList(new String[]{"TabActivity", "RecyclerActivity", "ExpandView"
            , "mViewPagerActivity"
            , "WebviewActivity", "6.0权限管理", "图片加载"
            , "网络请求", "okgo", "软件更新"
            , "Dialog", "仿IOS Dialog", "获取图片"
            , "流式布局", "通用PopWindow", "refreshView"
            , "RadioButton", "自定义样式Button", "点击放大特效"
            , "demo", "缩放TextView", "支付宝输入框"});

    @Override
    protected void setTitle(TitleLayout titleLayout) {
        title.setText(getString(R.string.app_name));
        hiddenBackButton();

//        title.setIconLeft(0, null);
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_main_;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        recycleView.setLayoutFrozen();
        recycleView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recycleView.setAdapter(new BaseQuickAdapter<String>(mContext, list) {
            @Override
            protected int getLayoutResId() {
                return R.layout.recycler_main;
            }

            @Override
            protected void setViewHolder(BaseViewHolder baseViewHolder, final String s, int position) {
                baseViewHolder.setText(R.id.tv_item_1, s);
                baseViewHolder.setOnClickListener(R.id.tv_item_1, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onViewClicked(s);
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {

        Log.e(TAG, "initData:checkDeviceHasNavigationBar: " + ScreenUtil.checkDeviceHasNavigationBar(mContext));
        Log.e(TAG, "initData:getNavigationBarHeight: " + ScreenUtil.getNavigationBarHeight(mContext));

        try {
            PhoneInfo info = new PhoneInfo(this);
            Logger.e(info.getProvidersName());
            Logger.e(info.getNativePhoneNumber());
            Logger.e(info.getPhoneInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void onViewClicked(String view) {
        switch (view) {
            case "TabActivity":
//                startActivity(new Intent(this, mTabActivity.class));

                CheckCurrentAppTools.runThread(mContext, "", new CheckCurrentAppTools.OnCheckListener() {
                    @Override
                    public boolean result(Context context, final String flag) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.show("flag:" + flag);
                            }
                        });
                        return super.result(context, flag);

                    }

                    @Override
                    public boolean closeApp(Context context) {
                        return false;
                    }

                    @Override
                    public boolean deleteUser(Context context) {
                        return false;
                    }

                    @Override
                    public void exception() {

                    }
                });
                break;
            case "RecyclerActivity":
                startActivity(new Intent(this, mRecyclerActivity.class));
                break;
            case "ExpandView":
                startActivity(new Intent(this, mExpandViewActivity.class));
                break;
            case "mViewPagerActivity":
                startActivity(new Intent(this, mViewPagerActivity.class));
                break;
            case "WebviewActivity":
                startActivity(new Intent(this, mWebviewActivity.class));
                break;
            case "6.0权限管理":
                startActivity(new Intent(this, mPermissionsActivity.class));
                break;
            case "图片加载":
                startActivity(new Intent(this, mImageActivity.class));
                break;
            case "网络请求":
                startActivity(new Intent(this, mNetworkActivity.class));
                break;
            case "okgo":
                startActivity(new Intent(this, mOkgoActivity.class));
                break;
            case "软件更新":
                XPermission.init(mContext)
                        .setPermissions(XPermission.STORAGE_WRITE)
                        .showDialog(true, false)
                        .get(new OnPermissionsListener() {
                            @Override
                            public void missPermission(String[] permissions) {
                                if (permissions.length == 0) {
                                    startActivity(new Intent(mContext, mUpdateActivity.class));
                                } else {
                                    ToastUtils.show("缺少权限");
                                }
                            }
                        });

                break;
            case "Dialog":
                startActivity(new Intent(this, mDialogActivity.class));
                break;
            case "仿IOS Dialog":
                startActivity(new Intent(this, mDialog2Activity.class));
                break;
            case "获取图片":
                startActivity(new Intent(this, TakePhotoActivity.class));
                break;
            case "流式布局":
                startActivity(new Intent(this, FlowLayoutActivity.class));
                break;
            case "通用PopWindow":
                startActivity(new Intent(this, mPopWindowActivity.class));
                break;
            case "refreshView":
                startActivity(new Intent(this, mRefreshviewActivity.class));
                break;
            case "RadioButton":
                startActivity(new Intent(this, mRadioButtonActivity.class));
                break;
            case "自定义样式Button":
                startActivity(new Intent(this, mButtonActivity.class));
                break;
            case "点击放大特效":
                startActivity(new Intent(this, mRecyclerVActivity.class));
                break;
            case "demo":
                startActivity(new Intent(this, DemoActivity.class));
//                hideStatusView();
//                LogUtils.e("titleHeight:" + ScreenUtil.getTitleHeight(mContext));

//                setStatusBarView(mContext, b, Color.WHITE, false);
                b = !b;
                break;
            case "缩放TextView":
                startActivity(new Intent(this, AutoTextViewActivity.class));
                break;
            case "支付宝输入框":
//                startActivity(new Intent(this, PasswordInputActivity.class));
//                PasswordManagerUtils.showInputDialog(mContext, new PasswordManagerUtils.DialogInputListener() {
//                    @Override
//                    public void submit(Dialog dialog, String input) {
//                        if (PasswordManagerUtils.checkPassword(input)) {
//                            dialog.dismiss();
//                        }
//                    }
//                });

                break;

        }
    }

    boolean b;
}
