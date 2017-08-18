package yin.style.notes.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jskingen.baselib.activity.base.RecyclerViewActivity;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yin.style.notes.MyApp;
import yin.style.notes.R;
import yin.style.notes.utils.ExcelUtil;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;

public class ExportHistoryActivity extends RecyclerViewActivity {
    private List<File> list = new ArrayList<>();

    private BaseQuickAdapter adapter;

    @Override
    protected void setItemDecoration() {
//        super.setItemDecoration();
    }

    @Override
    protected void initData() {
        QbSdk.initX5Environment(mContext, null);

        File fileParents = new File(MyApp.getExcelPath(mContext));
        if (fileParents.exists()) {
            File[] files = fileParents.listFiles();
            for (File file : files)
                if (file.getName().endsWith(ExcelUtil.SUFFIX))
                    list.add(file);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void setTitle() {
        title.setText("导出历史");
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new BaseQuickAdapter<File>(R.layout.item_export_history, list) {
            @Override
            protected void setViewHolder(BaseViewHolder baseViewHolder, final File bean, int position) {
                baseViewHolder.setText(R.id.tv_item_exp_file_name, bean.getName());
                baseViewHolder.getView(R.id.tv_item_exp_file_open).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                baseViewHolder.getView(R.id.tv_item_exp_file_preview).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFile(bean.getAbsolutePath());
                    }
                });
            }
        };

        return adapter;
    }

    private void openFile(String filePath) {
        String a = mContext.getCallingPackage();
        String b =  mContext.getCallingPackage() + "." + mContext.getLocalClassName();
//        String jsondata = "{pkgName:\"" + mContext.getCallingPackage() + "\", "
//                + "className:\"" + mContext.getCallingPackage() + "." + mContext.getLocalClassName() + "\","
//                + "thirdCtx: {pp:123},"
//                + "menuItems:"
//                + "["
//                + "{id:0,iconResId:" + R.drawable.ic_launcher + ",text:\"分享\"}"
//                + "]"
//                + " }";

        HashMap<String, String> params = new HashMap<>();
        params.put("style", "0");
        params.put("local", "true");
//        params.put("menuData", jsondata);

        QbSdk.openFileReader(mContext, filePath, params, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.e("", "" + s);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
