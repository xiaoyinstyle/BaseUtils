//package yin.style.baselib.net.inter;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.graphics.Color;
//import android.text.TextUtils;
//
//import com.google.gson.Gson;
//
//import java.io.File;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.Map;
//
//import okhttp3.Call;
//import yin.style.baselib.net.HttpHelper;
//import yin.style.baselib.net.processor.BInterceptor;
//import yin.style.baselib.utils.AppManager;
//import yin.style.baselib.view.LoadingDialog;
//
//public abstract class OnBaseResult<T> extends ICallBack<T> implements BInterceptor {
//    protected final String TAG = "OnBaseResult";
//
//    protected String loadText;
//    protected boolean showDialog;
//    protected Context mContext;
//    protected LoadingDialog mDialog;
//
//    public OnBaseResult() {
//        mContext = AppManager.getInstance().currentActivity();
//    }
//
//    /**
//     * @param showDialog 是否显示加载框
//     */
//    public OnBaseResult(boolean showDialog) {
//        this.showDialog = showDialog;
//        mContext = AppManager.getInstance().currentActivity();
//        if (showDialog) {
//            mDialog = new LoadingDialog.Builder(mContext)
//                    .setBackground(yin.style.baselib.R.drawable.progress_custom_bg_black)
//                    .setTextColor(Color.WHITE)
//                    .create();
//        }
//    }
//
//    /**
//     * @param showDialog 是否显示加载框
//     * @param loadText   加载框提示文字
//     */
//    public OnBaseResult(boolean showDialog, String loadText) {
//        this.showDialog = showDialog;
//        this.loadText = loadText;
//        if (showDialog) {
//            mContext = AppManager.getInstance().currentActivity().getApplicationContext();
//            mDialog = new LoadingDialog.Builder(mContext)
//                    .setBackground(yin.style.baselib.R.drawable.progress_custom_bg_black)
//                    .setTextColor(Color.WHITE)
//                    .create();
//        }
//    }
//
//    @Override
//    public void onStart(Call call) {
//
//        if (showDialog && mDialog != null) {
//            initDialog(call.request().tag());
//
//            if (!TextUtils.isEmpty(loadText))
//                mDialog.setMessage(loadText);
//            mDialog.show();
//        }
//    }
//
//    @Override
//    public void onResponse(T response) {
//        String data = response.toString();
//        Class<?> clz = analysisClassInfo(this);
//        T t;
//        if (clz == null || clz.equals(String.class)) {
//            t = (T) data;
//        } else {
//            t = (T) new Gson().fromJson(data, clz);
//        }
//
//        onSuccess(t, null);
//    }
//
//    @Override
//    public void onFinish() {
//        super.onFinish();
//        try {
//            if (mDialog != null) {
//                mDialog.dismiss();
//            }
//        } catch (Exception e) {
//
//        }
//    }
//
//    protected void setLoadText(String loadText) {
//        this.loadText = loadText;
//        if (mDialog != null)
//            mDialog.setMessage(loadText);
//    }
//
//
//    private void initDialog(final Object tag) {
//        mDialog.setMessage("加载中");
//        mDialog.setCancelable(true);
//        mDialog.setCanceledOnTouchOutside(false);
//        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                if (tag != null) {
//                    HttpHelper.init(null).cancel(tag);
//                }
//            }
//        });
//    }
//
//    protected final Class<T> analysisClassInfo(Object object) {
//        try {
//            Type genType = object.getClass().getGenericSuperclass();
//            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
//
//            return (Class<T>) params[0];
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    @Override
//    public Map<String, String> header(Map<String, String> headerMap) {
//        return headerMap;
//    }
//
//    @Override
//    public Map<String, String> post(Map<String, String> postMap) {
//        return postMap;
//    }
//
//    @Override
//    public Map<String, String> get(Map<String, String> getMap) {
//        return getMap;
//    }
//
//    @Override
//    public Map<String, File> upload(Map<String, File> uploadMap) {
//        return uploadMap;
//    }
//}
