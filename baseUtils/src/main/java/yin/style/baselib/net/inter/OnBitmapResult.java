package yin.style.baselib.net.inter;

import android.graphics.Bitmap;

public abstract class OnBitmapResult extends OnBaseResult<Bitmap> {
    public OnBitmapResult() {
        super();
    }

    public OnBitmapResult(boolean showDialog) {
        super(showDialog);
    }

    @Override
    public void onResponse(Bitmap response) {
        onSuccess(response, null);
    }
}
