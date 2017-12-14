package yin.style.sample.chat;

import android.graphics.Color;
import android.os.Bundle;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.sample.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Chne
 *
 * 折线图 与 柱形图
 */
public class ChatsActivity extends TitleActivity {
    @BindView(R.id.chatView)
    LineChartView chatView;

    private int[] progress = {2000, 5000, 6000, 8000, 500, 6000, 9000, 1900};
    String[] xWeeks = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日", "周周"};


    @Override
    protected int getViewByXml() {
        return R.layout.activity_chats;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        float maxValue = 10000;

        List<ChatXEntry> chatXEntries = new ArrayList<>();
        for (int i = 0; i < progress.length; i++) {
            chatXEntries.add(new ChatXEntry(progress[i], xWeeks[i]));
        }

        List<ChatYEntry> chatYEntries = new ArrayList<>();

        chatYEntries.add(new ChatYEntry(0.2f, "" + 2, Color.RED));
        chatYEntries.add(new ChatYEntry(0.8f, "" + 8, Color.YELLOW));
        chatYEntries.add(new ChatYEntry(1f, "" + 10, Color.BLUE));
        chatView.start(chatXEntries, chatYEntries);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setTitle() {

    }
}
