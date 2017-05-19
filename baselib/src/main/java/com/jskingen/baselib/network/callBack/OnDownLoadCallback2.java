package com.jskingen.baselib.network.callBack;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.jskingen.baselib.network.inter.IFileCallBack;
import com.jskingen.baselib.utils.AppManager;
import com.jskingen.baselib.utils.LogUtils;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baselib.view.NumberProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ChneY on 2017/4/6.
 */

public abstract class OnDownLoadCallback2 implements Callback<ResponseBody>, IFileCallBack {
    private boolean showDialog;
    private Context mContext;
    private AlertDialog mDialog;
    private NumberProgressBar numberProgressBar;
    private Call<ResponseBody> call;
    private String filePath;

    public OnDownLoadCallback2(String filePath) {
        this.filePath = filePath;
    }

    public OnDownLoadCallback2(String filePath, AlertDialog mDialog) {
        this.filePath = filePath;
        if (mContext == null) {
            mContext = AppManager.getInstance().currentActivity();
            this.showDialog = true;
            this.mDialog = mDialog;
        }
    }

    public OnDownLoadCallback2(String filePath, boolean showDialog) {
        this.filePath = filePath;
        if (mContext == null) {
            mContext = AppManager.getInstance().currentActivity();
            this.showDialog = showDialog;
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
        this.call = call;
        final ResponseBody body = response.body();
        if (response.isSuccessful()) {
            onStart();
            new AsyncTask<Void, Integer, Boolean>() {
                @Override
                protected void onPreExecute() {
                    onStart();
                }

                @Override
                protected Boolean doInBackground(Void... params) {
                    try {
                        // todo change the file location/name according to your needs
                        File futureStudioIconFile = new File(filePath);
                        if (!futureStudioIconFile.getParentFile().exists())
                            futureStudioIconFile.getParentFile().mkdirs();

                        InputStream inputStream = null;
                        OutputStream outputStream = null;

                        try {
                            byte[] fileReader = new byte[4096];

                            final long fileSize = body.contentLength();
                            long fileSizeDownloaded = 0;

                            inputStream = body.byteStream();
                            outputStream = new FileOutputStream(futureStudioIconFile);

                            while (true) {
                                int read = inputStream.read(fileReader);
                                if (read == -1) {
                                    break;
                                }
                                outputStream.write(fileReader, 0, read);
                                fileSizeDownloaded += read;

                                final int per = (int) (((float) fileSizeDownloaded / (float) fileSize) * 100);
                                publishProgress(per, (int) fileSizeDownloaded, (int) fileSize);
                            }

                            outputStream.flush();

                            return true;
                        } catch (IOException e) {
                            return false;
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }

                            if (outputStream != null) {
                                outputStream.close();
                            }
                        }
                    } catch (IOException e) {
                        return false;
                    }
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                    onLoading(values[0], values[1], values[2]);
                    if (numberProgressBar != null)
                        numberProgressBar.setProgress(values[0]);
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                    if (mDialog != null)
                        mDialog.dismiss();
                    onFinish(aBoolean);
                }

            }.execute();
        } else {
            onError(new Exception("server contact failed"));
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        if (t instanceof ConnectException) {
            ToastUtils.show("网络未连接");
        } else if (t instanceof SocketTimeoutException) {
            ToastUtils.show("连接超时");
        } else
            this.onError(new Exception(t.getMessage()));
        t.printStackTrace();
    }

    @Override
    public void onStart() {
        if (showDialog) {
            showDialog();
        }
    }

    public void cancel() {

    }

    /**
     *
     */
    private void showDialog() {
        if (mDialog == null) {
            numberProgressBar = new NumberProgressBar(mContext);

            mDialog = new AlertDialog.Builder(mContext)
                    .setTitle("正在下载")
                    .setView(numberProgressBar, 20, 50, 20, 50)
                    .setCancelable(false)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDialog.dismiss();
                            if (call != null)
                                call.cancel();

                        }
                    }).create();
        }
        mDialog.show();
    }


    public boolean writeResponseBodyToDisk(ResponseBody body, String filePath) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(filePath);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                    LogUtils.i("AAAA", (float) fileSizeDownloaded / (float) fileSize + "");
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
