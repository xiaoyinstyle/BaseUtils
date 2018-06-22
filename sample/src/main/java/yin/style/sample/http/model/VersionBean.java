package yin.style.sample.http.model;

import com.cretin.www.cretinautoupdatelibrary.model.UpdateInterface;

/**
 * Created by User on 2018/5/17.
 */

public class VersionBean implements UpdateInterface {

    /**
     * id : 1
     * version : 1.0
     * url : http://chengpai.net.cn
     * version_desc : <p></p><p>工惠驿家，新版本添加</p>
     * type : 1
     * code : 1
     * is_lines : 0
     * is_update : 1   //1否2是
     * time : 1526541201
     */

    private int id;
    private String version;
    private String url;
    private String version_desc;
    private int type;
    private int code;
    private int is_lines;
    private int is_update;
    private int time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion_desc() {
        return version_desc;
    }

    public void setVersion_desc(String version_desc) {
        this.version_desc = version_desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getIs_lines() {
        return is_lines;
    }

    public void setIs_lines(int is_lines) {
        this.is_lines = is_lines;
    }

    public int getIs_update() {
        return is_update;
    }

    public void setIs_update(int is_update) {
        this.is_update = is_update;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public int getVersionCodes() {
        return getCode();
    }

    @Override
    public boolean getIsForceUpdates() {
        return getIs_update() == 2;
    }

    @Override
    public String getVersionNames() {
        return getVersion();
    }

    @Override
    public String getDownUrls() {
        return getUrl();
    }

    @Override
    public String getUpdateLogs() {
        return getVersion_desc();
    }

    @Override
    public long getApkSizes() {
        return -1;
    }
}
