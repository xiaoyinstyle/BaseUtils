package yin.style.notes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import yin.style.baselib.utils.GsonUtils;

import yin.style.notes.MyApp;
import yin.style.notes.model.RuleDetails;
import yin.style.notes.model.RuleProjects;

/**
 * Created by ChneY on 2017/4/7.
 */

public class SPCache {

    private final static String CACHENAME = "APP_CACHE";
    private static SPCache cache;
    private static Context mContext;

    private static SharedPreferences sharedPreferences;

    public static SPCache getInstance() {
        if (mContext == null)
            mContext = MyApp.getInstance().getApplicationContext();

        if (cache == null)
            cache = new SPCache();

        if (sharedPreferences == null) {
            sharedPreferences = mContext.getSharedPreferences(CACHENAME, Activity.MODE_PRIVATE);
        }
        return cache;
    }

    public void clearData() {
        sharedPreferences.edit().clear().commit();
    }

    /**
     * 设置九宫格密码
     */
    public String getLock() {
        return sharedPreferences.getString("password", "");
    }

    public void setLock(String password) {
        if (TextUtils.isEmpty(password))
            return;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", password);
        editor.commit();
    }

    /**
     * 获取 保存 的 Projects  搜索的规则
     *
     * @return
     */
    public RuleProjects getRuleProjects() {
        RuleProjects ruleProjects;
        String s = sharedPreferences.getString("ruleProjects", "");
        if (s.isEmpty()) {
            ruleProjects = new RuleProjects();
            ruleProjects.setFlag(RuleProjects.FLAG_TIME);
            ruleProjects.setUp(true);
            ruleProjects.setStart(true);
            ruleProjects.setRun(true);
            ruleProjects.setEnd(true);
        } else {
            ruleProjects = GsonUtils.getObject(s, RuleProjects.class);
        }
        return ruleProjects;
    }

    public void setRuleProjects(RuleProjects ruleProjects) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ruleProjects", GsonUtils.toJson(ruleProjects));
        editor.commit();
    }


    /**
     * 获取 保存 的 Details  搜索的规则
     *
     * @return
     */
    public RuleDetails getRuleDetails() {
        RuleDetails ruleDetails;
        String s = sharedPreferences.getString("ruleDetails", "");
        if (s.isEmpty()) {
            ruleDetails = new RuleDetails();
            ruleDetails.setFlag(RuleProjects.FLAG_TIME);
            ruleDetails.setUp(true);
            ruleDetails.setMaterial(true);
            ruleDetails.setReceipt(true);
            ruleDetails.setWage(true);
            ruleDetails.setOther(true);
        } else {
            ruleDetails = GsonUtils.getObject(s, RuleDetails.class);
        }
        return ruleDetails;
    }

    public void setRuleDetails(RuleDetails ruleDetails) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ruleDetails", GsonUtils.toJson(ruleDetails));
        editor.commit();
    }

}
