package yin.style.notes.dao;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import yin.style.notes.model.DetailsBean;
import yin.style.notes.model.ProjectBean;

/**
 * Created by ChneY on 2017/4/10.
 */

public class RealmHelper {
    public static final String DB_NAME = "notes.realm";
    public static final int pageSize = 15;//分页

    public static RealmHelper realmHelper;

    private Realm mRealm;

    public static RealmHelper getInstance() {
        if (realmHelper == null || realmHelper.mRealm == null) {
            synchronized (RealmHelper.class) {
                if (realmHelper == null)
                    realmHelper = new RealmHelper();
                if (realmHelper.mRealm == null) {
                    try {
                        realmHelper.mRealm = Realm.getDefaultInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return realmHelper;
    }

    /**
     * 查询所有 ProjectBean
     */
    public List<ProjectBean> findProjectsList(int page) {
        RealmResults<ProjectBean> projectBeen = mRealm.where(ProjectBean.class)
//                    .equalTo("userid", "" + user.getUserid())
                .findAllSorted("id", Sort.DESCENDING);

        int start = page * pageSize;
        int end = projectBeen.size() > start + pageSize ? start + pageSize : projectBeen.size();
        List<ProjectBean> beanList = projectBeen.subList(start, end);
        return beanList;
    }

    /**
     * 通过 id 值，查询一个 项目详情
     */
    public ProjectBean findProject(long id) {
        ProjectBean temp = mRealm.where(ProjectBean.class)
                .equalTo("id", id)
                .findFirst();
        return mRealm.copyFromRealm(temp);
    }

    /**
     * 添加 项目记录
     */
    public void addProjects(ProjectBean projectBean) {
        try {
            mRealm.beginTransaction();
            projectBean.setId(PrimaryKeyProjectBean());
            mRealm.copyToRealm(projectBean);
            mRealm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新 ProjectBean 数据
     *
     * @param projectBean
     */
    public void updateProjects(ProjectBean projectBean) {
        try {
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(projectBean);
            mRealm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ProjectBean 获取最大的PrimaryKey并加一
    private long PrimaryKeyProjectBean() {
        long primaryKey = 0;
        RealmResults<ProjectBean> results = mRealm.where(ProjectBean.class).findAllSorted("id", Sort.DESCENDING);
        if (results != null && results.size() > 0) {
            ProjectBean last = results.first();
            primaryKey = last.getId() + 1;
        }
        return primaryKey;
    }

    /**
     * 删除一条记录
     *
     * @param projectBean
     */
    public void deleteProject(ProjectBean projectBean) {
        try {
            mRealm.beginTransaction();
            projectBean.deleteFromRealm();
            mRealm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据 projects 的 id 查询所有 DetailsBean
     */
    public List<DetailsBean> findDetailsList(int page, long projectId) {
        RealmResults<DetailsBean> projectBeen = mRealm.where(DetailsBean.class)
                .equalTo("projectId", projectId)
                .findAllSorted("id", Sort.DESCENDING);

        int start = page * pageSize;
        int end = projectBeen.size() > start + pageSize ? start + pageSize : projectBeen.size();
        List<DetailsBean> beanList = projectBeen.subList(start, end);
        return beanList;
    }

    /**
     * 查询所有 DetailsBean
     */
    public List<DetailsBean> findDetailsList(int page) {
        RealmResults<DetailsBean> projectBeen = mRealm.where(DetailsBean.class)
                .findAllSorted("id", Sort.DESCENDING);

        int start = page * pageSize;
        int end = projectBeen.size() > start + pageSize ? start + pageSize : projectBeen.size();
        List<DetailsBean> beanList = projectBeen.subList(start, end);
        return beanList;
    }

    /**
     * 通过 id 值，查询一个 项目明细详情
     */
    public DetailsBean finddDetails(long id) {
        try {
            DetailsBean temp = mRealm.where(DetailsBean.class)
                    .equalTo("id", id)
                    .findFirst();
            return mRealm.copyFromRealm(temp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加 项目明细记录
     */
    public void addDetails(DetailsBean detailsBean) {
        try {
            mRealm.beginTransaction();
            detailsBean.setId(PrimaryKeyDetailsBean());
            mRealm.copyToRealm(detailsBean);
            mRealm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新 DetailsBean 数据
     *
     * @param detailsBean
     */
    public void updateDetails(DetailsBean detailsBean) {
        try {
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(detailsBean);
            mRealm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ProjectBean 获取最大的PrimaryKey并加一
    private long PrimaryKeyDetailsBean() {
        long primaryKey = 0;
        RealmResults<DetailsBean> results = mRealm.where(DetailsBean.class).findAllSorted("id", Sort.DESCENDING);
        if (results != null && results.size() > 0) {
            DetailsBean last = results.first();
            primaryKey = last.getId() + 1;
        }
        return primaryKey;
    }

    /**
     * 删除一条记录
     *
     * @param detailsBean
     */
    public void deleteDetails(DetailsBean detailsBean) {
        try {
            mRealm.beginTransaction();
            detailsBean.deleteFromRealm();
            mRealm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    /**
//     *  查询总年龄
//     */
//    private void getSumAge() {
//        Number sum=  mRealm.where(Dog.class).findAll().sum("age");
//        int sumAge=sum.intValue();
//    }
}
