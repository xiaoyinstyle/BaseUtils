package yin.style.baselib.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import yin.style.baselib.utils.AppManager;


public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity{
    public P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    /**
     * 初始化Prensenter
     */
    public abstract P initPresenter();

    @Override
    protected int getContentViewID() {
        return 0;
    }

    /**
     * 避免内存泄露，activity销毁，取消网络请求
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onUnsubscribe();
        }
    }
}