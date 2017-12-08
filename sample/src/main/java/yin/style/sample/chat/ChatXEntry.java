package yin.style.sample.chat;

/**
 * Created by BangDu on 2017/11/23.
 */

public class ChatXEntry {
    float value = 0;
    String text = "";

    public ChatXEntry(float value, String text) {
        this.text = text;
        this.value = value;
    }

    public ChatXEntry() {
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
}
