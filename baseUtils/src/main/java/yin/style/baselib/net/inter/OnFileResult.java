package yin.style.baselib.net.inter;

import java.io.File;

public abstract class OnFileResult extends ICallBack<File> {
    File downloadFile;
    public  OnFileResult( File downloadFile){
        this.downloadFile =downloadFile;
    }
    public File getDownloadFile() {
        return downloadFile;
    }
}
