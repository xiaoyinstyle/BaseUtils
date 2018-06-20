package yin.style.baselib.net.inter;

import java.io.File;

public abstract class OnFileResult extends OnBaseResult<File> {
    File downloadFile;

    public OnFileResult(File downloadFile) {
        super();
        this.downloadFile = downloadFile;
    }

    public OnFileResult(File downloadFile, boolean showDialog) {
        super(showDialog);
        this.downloadFile = downloadFile;
    }

    @Override
    public void onResponse(File response) {
        onSuccess(response, null);
    }


    public File getDownloadFile() {
        return downloadFile;
    }
}
