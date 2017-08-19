package yin.style.notes.model;

/**
 * Created by Chne on 2017/8/18.
 * <p>
 * Projects 的搜索规则
 */

public class RuleProjects {
    public static final int FLAG_TIME = 1;//按时间
    public static final int FLAG_MONEY = 2;//按金额
    private int flag;
    private boolean isUp;
    private boolean isStart;
    private boolean isRun;
    private boolean isEnd;
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

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
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
                return "预算";
        }
        return "";
    }
}
