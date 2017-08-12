package yin.style.notes;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import yin.style.notes.dao.DaoMigration;
import yin.style.notes.dao.RealmHelper;

/**
 * Created by Chne on 2017/8/10.
 */

public class MyApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm();
    }

    /**
     * 数据库 初始化
     */
    private void initRealm() {
        Realm.init(this.getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
//                .deleteRealmIfMigrationNeeded()//声明版本冲突时自动删除原数据库。
                .name(RealmHelper.DB_NAME) //文件名
                .schemaVersion(0)
                .migration(new DaoMigration())//数据库升级
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
