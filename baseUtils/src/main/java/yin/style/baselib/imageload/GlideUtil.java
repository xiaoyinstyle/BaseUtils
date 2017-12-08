package yin.style.baselib.imageload;

import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import yin.style.baselib.R;
import yin.style.baselib.imageload.transform.GlideCircleTransform;
import yin.style.baselib.imageload.transform.GlideRoundTransform;
import yin.style.baselib.utils.AppManager;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by pc on 2016/8/26.
 * <p>
 * Glide工具 二次封装 v1.0.2
 */
public class GlideUtil {
    private static final int error = R.mipmap.glide_error_loading;//error图片
    private static final int error_circle = R.mipmap.glide_error_loading_round;//error圆形图片

    private static final int defaultImage = R.mipmap.glide_loading;//默认显示 加载成功之前的图片
    private static final int defaultCircleImage = R.mipmap.glide_error_loading_round;//默认显示 加载成功之前的圆形图片

    private static final int borderWidth = 2;//边框宽度
    private static final int borderColor = Color.BLUE;//边框颜色

    private Context context;
    private boolean hasCache = true;//是否缓存

    private static GlideUtil instance;

    //有缓存
    public static GlideUtil getInstance() {
        return getInstance(null);
    }

    //无缓存
    public static GlideUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (GlideUtil.class) {
                if (instance == null)
                    instance = new GlideUtil();
            }
        }
        if (null == instance.context)
            instance.context = AppManager.getInstance().currentActivity().getApplicationContext();

        instance.hasCache = context == null ? true : false;
        return instance;
    }

    /**
     * @param res
     * @return
     */
    private DrawableRequestBuilder setGlide(Object res) {
        DrawableRequestBuilder drawableTypeRequest = Glide.with(context)
                .load(res).placeholder(defaultImage);
        if (hasCache)
            return drawableTypeRequest;
        else
            return drawableTypeRequest
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
                    .skipMemoryCache(true);//跳过内存缓存
    }

    /**
     * @param imageView
     * @param res       网络图片的url或者资源地址
     * @param error     错误图片
     */
    public void setView(ImageView imageView, Object res, int error) {
        setGlide(res)
                .error(error)
                .into(imageView);
    }

    public void setView(ImageView imageView, Object res) {
        setView(imageView, res, error);
    }

    /**
     * .显示gif动画,asGif()判断是否是gif动画
     */
    public void setGif(ImageView imageView, Object res) {
        Glide.with(context).load(res).asGif().into(imageView);
    }

    /**
     * 设置圆形图片
     *
     * @param imageView
     * @param res       网络图片的url或者资源地址
     * @param error     错误图片
     */
    public void setCircleView(ImageView imageView, Object res, int error) {
        setGlide(res)
                .error(error)
                .placeholder(defaultCircleImage)
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }

    public void setCircleView(ImageView imageView, Object url) {
        setCircleView(imageView, url, error_circle);
    }

    /**
     * 设置圆形图片+ 周边
     *
     * @param res
     * @param mborderWidth 边框宽度
     * @param mborderColor 边框颜色
     * @param error        错误图片
     */

    public void setCircleView2(ImageView imageView, Object res, int mborderWidth, @ColorInt int mborderColor, int error) {
        setGlide(res)
                .error(error)
                .placeholder(defaultCircleImage)
                .transform(new GlideCircleTransform(context, mborderWidth, mborderColor))
                .into(imageView);
    }

    public void setCircleView2(ImageView imageView, Object url, int mborderWidth, @ColorInt int mborderColor) {
        setCircleView2(imageView, url, mborderWidth, mborderColor, error_circle);
    }

    /*附加颜色颜色*/
    public void setCircleView2(ImageView imageView, Object url, @ColorInt int mborderColor) {
        setCircleView2(imageView, url, borderWidth, mborderColor, error_circle);
    }

    public void setCircleView2(ImageView imageView, Object url) {
        setCircleView2(imageView, url, borderWidth, borderColor, error_circle);
    }

    /**
     * 设置圆角图片
     *
     * @param imageView
     * @param res       网络图片的url或者资源地址
     * @param angle     圆角角度
     * @param error     错误图片
     */
    public void setRoundView(ImageView imageView, Object res, int angle, int error) {
        setGlide(res)
                .error(error)
                .transform(new GlideRoundTransform(context, angle))
                .into(imageView);
    }

    public void setRoundView(ImageView imageView, Object url) {
        setRoundView(imageView, url, 4, error);
    }

    public void setRoundView(ImageView imageView, Object url, int angle) {
        setRoundView(imageView, url, angle, error);
    }


    /**
     * Glide缓存工具类
     */
    public static class Cache {
        /**
         * 清除图片磁盘缓存
         */
        public static void clearImageDiskCache(final Context context) {
            try {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.get(context).clearDiskCache();
//                        BusUtil.getBus().post(new GlideCacheClearSuccessEvent());
                        }
                    }).start();
                } else {
                    Glide.get(context).clearDiskCache();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 清除图片内存缓存
         */
        public static void clearImageMemoryCache(Context context) {
            try {
                if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                    Glide.get(context).clearMemory();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 清除图片所有缓存
         */
        public static void clearImageAllCache(Context context) {
            clearImageDiskCache(context);
            clearImageMemoryCache(context);
            String ImageExternalCatchDir = context.getExternalCacheDir() + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
            deleteFolderFile(ImageExternalCatchDir, true);
        }

        /**
         * 获取Glide造成的缓存大小
         */
        public static String getCacheSize(Context context) {
            try {
                return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        /**
         * 获取指定文件夹内所有文件大小的和
         *
         * @param file file
         * @return size
         * @throws Exception
         */
        private static long getFolderSize(File file) throws Exception {
            long size = 0;
            try {
                File[] fileList = file.listFiles();
                for (File aFileList : fileList) {
                    if (aFileList.isDirectory()) {
                        size = size + getFolderSize(aFileList);
                    } else {
                        size = size + aFileList.length();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return size;
        }

        /**
         * 删除指定目录下的文件，这里用于缓存的删除
         *
         * @param filePath       filePath
         * @param deleteThisPath deleteThisPath
         */
        private static void deleteFolderFile(String filePath, boolean deleteThisPath) {
            if (!TextUtils.isEmpty(filePath)) {
                try {
                    File file = new File(filePath);
                    if (file.isDirectory()) {
                        File files[] = file.listFiles();
                        for (File file1 : files) {
                            deleteFolderFile(file1.getAbsolutePath(), true);
                        }
                    }
                    if (deleteThisPath) {
                        if (!file.isDirectory()) {
                            file.delete();
                        } else {
                            if (file.listFiles().length == 0) {
                                file.delete();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 格式化单位
         *
         * @param size size
         * @return size
         */
        private static String getFormatSize(double size) {
            double kiloByte = size / 1024;
            if (kiloByte < 1) {
                return size + "Byte";
            }

            double megaByte = kiloByte / 1024;
            if (megaByte < 1) {
                BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
                return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
            }

            double gigaByte = megaByte / 1024;
            if (gigaByte < 1) {
                BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
                return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
            }

            double teraBytes = gigaByte / 1024;
            if (teraBytes < 1) {
                BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
                return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
            }
            BigDecimal result4 = new BigDecimal(teraBytes);

            return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
        }
    }
}