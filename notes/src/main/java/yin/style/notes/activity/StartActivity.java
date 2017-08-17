package yin.style.notes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jskingen.baselib.activity.base.NormalAcitivity;
import com.jskingen.baselib.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import yin.style.notes.MyApp;
import yin.style.notes.R;
import yin.style.notes.utils.MD5;
import yin.style.notes.utils.SPCache;
import yin.style.notes.view.NinePoint.PatternLockLayout;

public class StartActivity extends NormalAcitivity {
    @BindView(R.id.view_lock)
    PatternLockLayout viewLock;

    private String rightLock = "";

    @Override
    protected int getViewByXml() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }

        viewLock = (PatternLockLayout) findViewById(R.id.view_lock);
        viewLock.setOnPatternStateListener(new PatternLockLayout.OnPatternStateListener() {
            @Override
            public void onFinish(String password, int sizeOfPoints) {
                if (MD5.getMD5(password).equals(rightLock)
                        || MD5.getMD5(password).equals(MyApp.getInstance().getSuperLock())) {
                    startActivity(new Intent(mContext, MainActivity.class));
                    finish();
                    ToastUtils.show("图案密码正确");
                } else {
                    ToastUtils.show("图案密码错误");
                }
            }

            @Override
            public void onReset() {
            }
        });
    }

    @Override
    protected void initData() {
        rightLock = SPCache.getInstance().getLock();

        if (rightLock.isEmpty()) {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        } else
            viewLock.setVisibility(View.VISIBLE);
    }
}
