package yin.style.notes;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import yin.style.notes.dao.DaoMigration;
import yin.style.notes.dao.RealmHelper;
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

    public String getSuperLock() {
        return MD5.getMD5(superLock);
    }
}
