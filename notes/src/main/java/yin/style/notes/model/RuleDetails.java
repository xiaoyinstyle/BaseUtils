package yin.style.notes.model;

/**
 * Created by Chne on 2017/8/18.
 * <p>
 * Details 的搜索规则
 */

public class RuleDetails {
    public static final int FLAG_MONEY = 1;//按金额
    public static final int FLAG_TYPE = 2;//按类型
    public static final int FLAG_TIME = 3;//按时间

    private boolean isUp;
    private int Flag;
    private String startTime;
    private String endTime;

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public int getFlag() {
        return Flag;
    }

    public void setFlag(int flag) {
        Flag = flag;
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
}
