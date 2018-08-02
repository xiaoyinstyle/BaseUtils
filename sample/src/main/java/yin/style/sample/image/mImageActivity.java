package yin.style.sample.image;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cjt2325.cameralibrary.util.FileUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.imageload.GlideUtil;
import yin.style.baselib.utils.FileUtils;
import yin.style.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class mImageActivity extends TitleActivity {
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.image_round)
    ImageView imageRound;
    @BindView(R.id.image_round2)
    ImageView imageRound2;
    @BindView(R.id.image_gif)
    ImageView imageGif;
    @BindView(R.id.image_circle)
    ImageView imageCircle;
    @BindView(R.id.image_circle2)
    ImageView imageCircle2;
    @BindView(R.id.image_other)
    ImageView imageOther;
    @BindView(R.id.tv_size)
    TextView tvSize;

    @Override
    protected void setTitle(TitleLayout titleLayout) {
        title.setText("图片加载");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_image;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        GlideUtil.getInstance().setView(imageView, "http://attach.bbs.miui.com/forum/201209/11/222515j3sgvvjv36gg5n29.jpg");
        GlideUtil.getInstance().setRoundView(imageRound, "http://e.hiphotos.baidu.com/zhidao/pic/item/3812b31bb051f81962b410e8dbb44aed2f73e7fb.jpg", 10);
        GlideUtil.getInstance().setRoundView2(imageRound2, "http://e.hiphotos.baidu.com/zhidao/pic/item/3812b31bb051f81962b410e8dbb44aed2f73e7fb.jpg", 30, 10, Color.BLUE);
        GlideUtil.getInstance().setCircleView(imageCircle, "http://img5.imgtn.bdimg.com/it/u=1858405955,2052924480&fm=23&gp=0.jpg");
        GlideUtil.getInstance().setCircleView2(imageCircle2, "http://img5.imgtn.bdimg.com/it/u=1858405955,2052924480&fm=23&gp=0.jpg", 3, Color.BLUE);
        GlideUtil.getInstance().setGif(imageGif, "http://img1.imgtn.bdimg.com/it/u=3789472357,190916494&fm=214&gp=0.jpg");
    }

//    ExecutorService fixedThreadPool;
//    List<String> listUrl = Arrays.asList("https://up.enterdesk.com/edpic_source/5d/de/10/5dde105efcc0ab75fb6e5b634f166ce3.jpg",
//            "http://up.enterdesk.com/edpic_source/3a/12/32/3a1232e2965f596f1523acb2ea042405.jpg",
//            "http://up.enterdesk.com/edpic_source/2c/02/0e/2c020e7c31cf3f1a13787fc515f17e7d.jpg",
//            "https://up.enterdesk.com/edpic_source/56/ce/b5/56ceb531d46e4e5fb972e2dc8b352fda.jpg",
//            "http://up.enterdesk.com/edpic_source/42/a1/a6/42a1a667a872542cbc5e93987e2d18c8.jpg",
//            "https://up.enterdesk.com/edpic_source/76/1a/b9/761ab9cb7eef867eff72c3350cbacdd4.jpg",
//            "https://up.enterdesk.com/edpic_source/44/db/56/44db567910d83845c1d4110129f5cc74.jpg"
//    );

    @Override
    protected void initData() {
//        fixedThreadPool = Executors.newSingleThreadExecutor();
//        for (int i = 0; i < listUrl.size(); i++) {
//            fixedThreadPool.execute(new TempRunnable(i + "", listUrl.get(i)));
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (fixedThreadPool != null) {
//            fixedThreadPool.shutdownNow();
//            fixedThreadPool == null;
//        }
    }

    @OnClick({R.id.bt_clear, R.id.bt_read})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_clear:
                GlideUtil.Cache.clearImageAllCache(this);
                break;
            case R.id.bt_read:
                String size = GlideUtil.Cache.getCacheSize(this);
                tvSize.setText("" + size);
                break;
        }
    }

    class TempRunnable implements Runnable {
        String tempUrl;
        String tag;

        public TempRunnable(String tag, String tempUrl) {
            this.tempUrl = tempUrl;
            this.tag = tag;
        }

        @Override
        public void run() {
            boolean isSuccess = false;
//            if (!TextUtils.equals(tag, "2")) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Glide.with(getApplicationContext()).asBitmap()
//                                .load(tempUrl)
//                                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
////                            .into(500, 500)
//                                .listener(new RequestListener<Bitmap>() {
//                                    @Override
//                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
//                                        Log.e(TAG, tag + "__run: onLoadFailed:");
//                                        return false;
//                                    }
//
//                                    @Override
//                                    public boolean onResourceReady(Bitmap myBitmap, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
//                                        Log.e(TAG, tag + "__run: Width:" + myBitmap.getWidth() + "---Height:" + myBitmap.getHeight());
//                                        return false;
//                                    }
//                                })
//                                .submit();
//                    }
//                });
//            } else
            File apkFile = new File(FileUtils.getDownloadFile(mContext), UUID.randomUUID() + ".jpg");

            while (!isSuccess) {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(tempUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setRequestProperty("Accept-Encoding", "identity");
                    InputStream in = connection.getInputStream();
                    in.skip(178);
                    //下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    int length = connection.getContentLength();
                    Log.e(TAG, tag + "__run: length:" + length + "   apkFile：" + apkFile.length());
//                    showResponse(response.toString());
                    isSuccess = length != 178;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, tag + "__run: Exception:");
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean way1(String tempUrl, String tag) throws ExecutionException, InterruptedException {
        boolean isSuccess = false;
        final Bitmap myBitmap = Glide.with(getApplicationContext()).asBitmap()
                .load(tempUrl)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
//                            .into(500, 500)
                .submit()
                .get();
        Log.e(TAG, tag + "__run: Width:" + myBitmap.getWidth() + "---Height:" + myBitmap.getHeight());
        isSuccess = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageOther.setImageBitmap(myBitmap);
            }
        });
        return isSuccess;
    }


    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        System.out.println("info:" + url + " download success");
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


}
