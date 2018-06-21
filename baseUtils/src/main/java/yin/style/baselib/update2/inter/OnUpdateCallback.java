package yin.style.baselib.update2.inter;

public interface OnUpdateCallback {

    void exit();

    void cancelDownload();

    void onProgress(float progress);
}
