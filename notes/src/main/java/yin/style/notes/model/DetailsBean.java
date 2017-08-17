package yin.style.notes.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chne on 2017/8/15.
 * 项目明细
 */

public class DetailsBean extends RealmObject {
    public static final int DETAILS_FLAG_RECEIPT = 1;//收款
    public static final int DETAILS_FLAG_MATERIAL = 2;//材料
    public static final int DETAILS_FLAG_WAGE = 3;//工资
    public static final int DETAILS_FLAG_OTHER = 4;//其他

    @PrimaryKey
    private long id;
    private long projectId;//项目
    private String content;//明细内容
    private float money;//金额
    private int flag;// 0：收款 1：材料 2 人工 3其他支出
    private String worker;//工人姓名
    private Date time;//时间
    private String remarks;//备注

    @Ignore
    private boolean isUpdateData;//是否更新数据

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isUpdateData() {
        return isUpdateData;
    }

    public void setUpdateData(boolean updateData) {
        isUpdateData = updateData;
    }

    public static String getFlagText(int flag) {
        String text = "";
        if (flag == ProjectBean.PROJECTS_FLAG_START) {
            text = "预定";
        } else if (flag == ProjectBean.PROJECTS_FLAG_RUNNING) {
            text = "进行中";
        } else if (flag == ProjectBean.PROJECTS_FLAG_END) {
            text = "结束";
        }
        return text;
    }
}
