package yin.style.notes.dao;


import io.realm.Realm;

/**
 * Created by ChneY on 2017/4/10.
 */

public class RealmHelper {
    public static final String DB_NAME = "notes.realm";
    public static RealmHelper realmHelper;
    private Realm mRealm;

    public static RealmHelper getInstance() {
        if (realmHelper == null || realmHelper.mRealm == null) {
            synchronized (RealmHelper.class) {
                if (realmHelper == null)
                    realmHelper = new RealmHelper();
                if (realmHelper.mRealm == null) {
                    realmHelper.mRealm = Realm.getDefaultInstance();
                }
            }
        }

        return realmHelper;
    }

//    /**
//     * 查询所有
//     */
//
//    public void findProjectsAll(final RequestDaoListener listener) {
//        final RealmResults<UserProject> userProjects;
//        try {
//            User user = SPCache.getInstance().getUser();
//            userProjects = mRealm.where(UserProject.class)
//                    .equalTo("userid", "" + user.getUserid())
//                    .findAllSortedAsync("id", Sort.DESCENDING);
//            userProjects.addChangeListener(new RealmChangeListener<RealmResults<UserProject>>() {
//                @Override
//                public void onChange(final RealmResults<UserProject> element) {
////                    List<UserProject> users = mRealm.copyFromRealm(element);
//
//                    List<UserProject> users = element.subList(0, element.size() > 10 ? 10 : element.size());
//                    listener.onChange(users);
//                    userProjects.removeAllChangeListeners();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            mRealm.removeAllChangeListeners();
//            listener.onChange(new ArrayList<UserProject>());
//        }
//    }
//
//    /**
//     * 通过 project  值，查询一个
//     */
//    public UserProject findProject(String projectName) {
//        if (TextUtils.isEmpty(projectName))
//            return null;
//        try {
//            UserProject temp = mRealm.where(UserProject.class)
//                    .equalTo("project", projectName)
//                    .findFirst();
//            return mRealm.copyFromRealm(temp);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new UserProject();
//        }
//    }
//
//    /**
//     * 保存 临时 定位
//     */
//    public void savaTempLocation(UserProject userProject) {
//        try {
//            mRealm.beginTransaction();
//            userProject.setId(PrimaryKeyUserProject());
//            mRealm.copyToRealm(userProject);
//            mRealm.commitTransaction();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 更新 一整套数据
//     *
//     * @param userProject
//     */
//    public void updateTempLocation(UserProject userProject) {
//        try {
//            mRealm.beginTransaction();
//            mRealm.copyToRealmOrUpdate(userProject);
//            mRealm.commitTransaction();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //UserProject 获取最大的PrimaryKey并加一
//    private long PrimaryKeyUserProject() {
//        long primaryKey = 0;
//        RealmResults<UserProject> results = mRealm.where(UserProject.class).findAll();
//        for (int i = 0; i < results.size(); i++) {
//            primaryKey = primaryKey > results.get(i).getId() + 1 ? primaryKey : results.get(i).getId() + 1;
//        }
////        if (results != null && results.size() > 0) {
////            UserProject last = results.last();
////            primaryKey = last.getId() + 1;
////        }
//        return primaryKey;
//    }
//
//    /**
//     * 删除一条记录
//     *
//     * @param userProject
//     */
//    public void delete(UserProject userProject) {
//        try {
////            Dog dog = mRealm.where(Dog.class).equalTo("id", id).findFirst();
////            mRealm.beginTransaction();
////            dog.deleteFromRealm();
////            mRealm.commitTransaction();
//
//            mRealm.beginTransaction();
//            userProject.deleteFromRealm();
//            mRealm.commitTransaction();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 去除 记录的 每个定位，减少数据量
//     * 先删除，在添加
//     */
//    public void relase(String projectName) {
//        //先查找到数据 删除
//        if (TextUtils.isEmpty(projectName))
//            return;
//
//        UserProject userProject = null;
//        try {
//            UserProject temp = mRealm.where(UserProject.class)
//                    .equalTo("project", projectName)
//                    .findFirst();
//            userProject = mRealm.copyFromRealm(temp);
//
//            mRealm.beginTransaction();
//            temp.deleteFromRealm();
//            mRealm.commitTransaction();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (userProject == null)
//            return;
//        userProject.setLngLatBeen(null);
//        //再添加
//        try {
//            mRealm.beginTransaction();
//            mRealm.copyToRealm(userProject);
//            mRealm.commitTransaction();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 改变 Flag
//     *
//     * @param u
//     */
//    public void chageUploadFlag(UserProject u) {
//        try {
//            mRealm.beginTransaction();
//            //如果操作的对象没有PrimaryKey, 会报错: java.lang.IllegalArgumentException: A RealmObject with no @PrimaryKey cannot be updated: class com.stone.hostproject.db.model.Movie
//            mRealm.copyToRealmOrUpdate(u);
//            mRealm.commitTransaction();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 关闭 mRealm
//     */
//    public static void close() {
//        // Close the Realm instance.
//        if (realmHelper != null && realmHelper.mRealm != null) {
//            realmHelper.mRealm.close();
//            realmHelper.mRealm = null;
//            realmHelper = null;
//        }
//    }
//
//    /**
//     * 更新 一条数据的 项目里程数
//     */
//    public void updataMileage(UserProject userProject, String projectMileage) {
//        if (userProject == null || TextUtils.isEmpty(projectMileage))
//            return;
//
//        try {
//            userProject.setProjectMileage(projectMileage);
//            mRealm.beginTransaction();
//            mRealm.copyToRealmOrUpdate(userProject);
//            mRealm.commitTransaction();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//}

}
