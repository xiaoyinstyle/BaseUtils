package yin.style.baselib.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import yin.style.baselib.utils.AppManager;


/**
 * Created by wangjitao on 2016/11/8 0008.
 * 基类Activity的封装
 * 一般使用mvp模式的话会在BaseActivity中进行P和V的初始化绑定
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewID() != 0) {
            setContentView(getContentViewID());
        }
        //管理activity，将activity添加到集合中
        AppManager.getInstance().addActivity(this);
        //注解
        ButterKnife.bind(this);

        findView();
        initView();
    }

    /**
     * 初始化控件
     */
    public void findView() {

    }

    /**
     * 给控件赋值
     */
    public void initView() {

    }

    /**
     * bind layout resource file
     *
     * @return
     */
    protected abstract int getContentViewID();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //将销毁的activity在集合中移除
    }
}