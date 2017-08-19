package yin.style.notes.model;

/**
 * Created by Chne on 2017/8/18.
 * <p>
 * Details 的搜索规则
 */

public class RuleDetails {
    public static final int FLAG_TIME = 1;//按时间
    public static final int FLAG_MONEY = 2;//按金额

    private int flag;
    private boolean isUp;
    private boolean isReceipt;//收款
    private boolean isMaterial;//材料
    private boolean isWage;//工资
    private boolean isOther;//其他
    private String startTime;
    private String endTime;

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public boolean isReceipt() {
        return isReceipt;
    }

    public void setReceipt(boolean receipt) {
        isReceipt = receipt;
    }

    public boolean isMaterial() {
        return isMaterial;
    }

    public void setMaterial(boolean material) {
        isMaterial = material;
    }

    public boolean isWage() {
        return isWage;
    }

    public void setWage(boolean wage) {
        isWage = wage;
    }

    public boolean isOther() {
        return isOther;
    }

    public void setOther(boolean other) {
        isOther = other;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public static String getFlagText(int flag) {
        switch (flag) {
            case FLAG_TIME:
                return "时间";
            case FLAG_MONEY:
                return "金额";
        }
        return "";
    }
}
