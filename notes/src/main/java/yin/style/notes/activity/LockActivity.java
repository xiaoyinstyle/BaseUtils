package yin.style.notes.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.utils.ToastUtils;

import yin.style.notes.MyApp;
import yin.style.notes.R;
import yin.style.notes.utils.MD5;
import yin.style.notes.utils.SPCache;
import yin.style.notes.view.NinePoint.PatternLockLayout;

public class LockActivity extends TitleActivity {
    private TextView tvLockInfo;
    private PatternLockLayout viewLock;

    private String rightLock = "";
    private String lockOne = "";
    private String lockTwo = "";

    @Override
    protected int getViewByXml() {
        return R.layout.activity_lock;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvLockInfo = (TextView) findViewById(R.id.tv_lock_info);
        viewLock = (PatternLockLayout) findViewById(R.id.view_lock);
        viewLock.setOnPatternStateListener(new PatternLockLayout.OnPatternStateListener() {
            @Override
            public void onFinish(String password, int sizeOfPoints) {
                if (sizeOfPoints < 3) {
                    tvLockInfo.setText("请连接至少3个点");
                    viewLock.setAllSelectedPointsError();
                } else if (rightLock.isEmpty()) {
                    //设置新图案
                    if (lockOne.isEmpty()) {
                        lockOne = MD5.getMD5(password);
                        tvLockInfo.setText("验证新图案");
                    } else if (MD5.getMD5(password).equals(lockOne)) {
                        tvLockInfo.setText("新图案设置成功");
                        ToastUtils.show("新图案设置成功");
                        SPCache.getInstance().setLock(MD5.getMD5(password));
                        finish();
                    } else {
                        tvLockInfo.setText("验证错误，重新绘制新图案");
                        lockOne = "";
                    }
                } else {
                    //检验 原密码 是否正确
                    if (MD5.getMD5(password).equals(rightLock)
                            || MD5.getMD5(password).equals(MyApp.getInstance().getSuperLock())) {
                        rightLock = "";
                        tvLockInfo.setText("设置新图案");
                    } else {
                        tvLockInfo.setText("图案验证错误");
                    }
                }
            }

            @Override
            public void onReset() {
//                tvLockInfo.setText("请绘制图案密码");
            }
        });
    }

    @Override
    protected void initData() {
        rightLock = SPCache.getInstance().getLock();

        tvLockInfo.setText("验证图案");
    }

    @Override
    protected void setTitle() {
        title.setText("修改图案密码");
    }
}
