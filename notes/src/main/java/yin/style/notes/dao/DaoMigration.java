package yin.style.notes.dao;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by ChneY on 2017/4/13.
 * 数据的 升级程序
 */

public class DaoMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
//        RealmSchema schema = realm.getSchema();
//        if (oldVersion == 0 && newVersion == 1) {
//            RealmObjectSchema personSchema = schema.get("UserProject");
//            personSchema.addField("projectMileage", String.class);
//            oldVersion = 1;
//        }
    }
}
