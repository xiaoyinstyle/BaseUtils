package yin.style.notes;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.jskingen.baselib.BaseHelp;
import com.jskingen.baselib.Configuration;
import com.jskingen.baselib.utils.FileUtils;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import yin.style.notes.dao.DaoMigration;
import yin.style.notes.utils.MD5;

/**
 * Created by Chne on 2017/8/10.
 */

public class MyApp extends MultiDexApplication {
    private String superLock = "13974265";//超级密码
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRealm();
        init();
    }

    private void init() {

        Configuration configuration = new Configuration.Builder(this)
                .baseUrl("http;//baidu.com")
                .fileName("Notes")
                .debug(BuildConfig.DEBUG)
                .build();
        BaseHelp.getInstance().init(configuration);
    }

    /**
     * 数据库 初始化
     */
    private void initRealm() {
        Realm.init(this.getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
//                .deleteRealmIfMigrationNeeded()//声明版本冲突时自动删除原数据库。
//                .name(RealmHelper.DB_NAME) //文件名
                .schemaVersion(0)
                .migration(new DaoMigration())//数据库升级
                .build();
        Realm.setDefaultConfiguration(config);

        Stetho.initialize(//Stetho初始化
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build()
        );
    }

    /**
     * 图案的超级密码
     *
     * @return
     */
    public String getSuperLock() {
        return MD5.getMD5(superLock);
    }

    /**
     * Excel 保存路径
     *
     * @param context
     * @return
     */
    public static String getExcelPath(Context context) {
        File dirFile = new File(FileUtils.getRootPath(context), "Excel");
        if (dirFile != null && !dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile.getPath();
    }

}
