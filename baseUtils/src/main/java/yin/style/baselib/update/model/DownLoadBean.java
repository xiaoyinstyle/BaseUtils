package yin.style.baselib.update.model;

/**
 * Created by ChneY on 2017/9/5.
 */

public class DownLoadBean {
    public long id;
    public String path;

    public DownLoadBean(long downloadUpdateApkId, String filePath) {
        this.id = downloadUpdateApkId;
        this.path = filePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
