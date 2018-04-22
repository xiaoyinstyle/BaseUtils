package yin.style.sample.other;

import android.content.Context;
import android.view.View;

public class PayInputPop {
    Context mContent;
    View view;

    public PayInputPop(View view) {
        this.view = view;
        this.mContent = view.getContext();
    }

    private void show() {
        init();
    }

    private void init() {

    }
}
