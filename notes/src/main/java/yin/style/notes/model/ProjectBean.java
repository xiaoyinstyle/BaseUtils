package yin.style.notes.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Chne on 2017/8/15.
 * 项目列表
 */

public class ProjectBean extends RealmObject {
    public static final int PROJECTS_FLAG_START = 1;//预定
    public static final int PROJECTS_FLAG_RUNNING = 2;//进行中
    public static final int PROJECTS_FLAG_END = 3;//结束

    @PrimaryKey
    private long id;
    private String projects;//项目名称
    private int flag;//0:预定  1：进行中 2：结束
    private String firstParty;//甲方负责人
    private Date createTime;//创建时间/合同日期
    private float budget;//预算
    private Date startTime;//开工时间
    private Date endTime;//交工时间
    private String remarks;//备注
    private float inCome;//收入
    private float expend;//支出


    @Ignore
    private boolean isUpdateData;//是否更新数据

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getFirstParty() {
        return firstParty;
    }

    public void setFirstParty(String firstParty) {
        this.firstParty = firstParty;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public float getInCome() {
        return inCome;
    }

    public void setInCome(float inCome) {
        this.inCome = inCome;
    }

    public float getExpend() {
        return expend;
    }

    public void setExpend(float expend) {
        this.expend = expend;
    }


    public boolean isUpdateData() {
        return isUpdateData;
    }

    public void setUpdateData(boolean updateData) {
        isUpdateData = updateData;
    }

    public static String getFlagText(int flag) {
        String text = "";
        switch (flag) {
            case DetailsBean.DETAILS_FLAG_RECEIPT:
                text = "收款";
                break;
            case DetailsBean.DETAILS_FLAG_MATERIAL:
                text = "材料";
                break;
            case DetailsBean.DETAILS_FLAG_WAGE:
                text = "工资";
                break;
            case DetailsBean.DETAILS_FLAG_OTHER:
                text = "其他";
                break;
        }
        return text;
    }
}
