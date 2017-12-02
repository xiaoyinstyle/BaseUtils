package com.jskingen.baseutils.chat;

/**
 * Created by BangDu on 2017/11/23.
 */

public class ChatYEntry implements Comparable<ChatYEntry> {
    float value = 0;
    String text = "";
    int color = 0;

    public ChatYEntry(float value, String text, int color) {
        this.text = text;
        this.value = value;
        this.color = color;
    }

    public ChatYEntry() {
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int compareTo(ChatYEntry o) {
        int i = (int) (1000 * (this.getValue() - o.getValue()));//先按照年龄排序
        return i;
    }
}
