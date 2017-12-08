package yin.style.notes.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import yin.style.baselib.activity.base.RecyclerViewActivity;
import yin.style.baselib.utils.FileUtils;
import yin.style.baselib.utils.ToastUtils;
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
        adapter = new BaseQuickAdapter<File>(mContext, list) {
            @Override
            protected int getLayoutResId() {
                return R.layout.item_export_history;
            }

            @Override
            protected void setViewHolder(BaseViewHolder baseViewHolder, final File bean, final int position) {
                baseViewHolder.setText(R.id.tv_item_exp_file_name, bean.getName());
                baseViewHolder.getView(R.id.tv_item_exp_file_open).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareFile(list.get(position));
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

    /**
     * 打开 Excel 文件
     * @param filePath
     */
    private void openFile(String filePath) {
        HashMap<String, String> params = new HashMap<>();
        params.put("style", "0");
        params.put("local", "true");

        QbSdk.openFileReader(mContext, filePath, params, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.e("", "" + s);
            }
        });
    }

    /**
     * 分享 Excel 文件
     *
     * @param file
     */
    private void shareFile(File file) {
        Intent imageIntent = new Intent(Intent.ACTION_SEND);
        imageIntent.setType("application/vnd.ms-excel");
        imageIntent.putExtra(Intent.EXTRA_STREAM, FileUtils.getUri2File(mContext, file));
        startActivity(Intent.createChooser(imageIntent, "分享"));
    }

    /**
     * 删除 Excel 文件
     *
     * @param file
     */
    private void deleteFile(File file) {
        if (file.delete()) {
            list.remove(file);
            adapter.notifyDataSetChanged();
            ToastUtils.show("删除成功");
        } else {
            ToastUtils.show("删除失败");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
