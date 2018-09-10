package com.personal.noncommercial.significantproject.greendao;

import android.content.Context;

import com.personal.noncommercial.significantproject.greendao.db.DaoMaster;
import com.personal.noncommercial.significantproject.greendao.db.DaoSession;
import com.personal.noncommercial.significantproject.greendao.db.UserBeanDao;
import com.personal.noncommercial.significantproject.moudle.bean.UserBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @author :lizhengcao
 * @date :2018/5/31
 * E-mail:lizc@bsoft.com.cn
 * @类说明 用户表
 */

public class UserDao {

    private DBManager dbManager;

    public UserDao(Context context) {
        dbManager = DBManager.getInstance(context);
    }

    /**
     * 插入一条数据
     */
    public void insertUser(UserBean user) {
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserBeanDao userBeanDao = daoSession.getUserBeanDao();
        userBeanDao.insert(user);
    }

    /**
     * 插入用户集合
     */
    public void insertUserList(List<UserBean> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserBeanDao userBeanDao = daoSession.getUserBeanDao();
        userBeanDao.insertOrReplaceInTx(users);
    }

    /**
     * 删除一个用户
     */

    public void deleteUser(UserBean user) {
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserBeanDao userBeanDao = daoSession.getUserBeanDao();
        userBeanDao.delete(user);

    }

    /**
     * 删除所有记录
     */

    public void deleteAll() {
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserBeanDao userBeanDao = daoSession.getUserBeanDao();
        userBeanDao.deleteAll();
    }

    /**
     * 更新一条数据
     */
    public void update(UserBean user) {
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserBeanDao userBeanDao = daoSession.getUserBeanDao();
        userBeanDao.update(user);
    }

    /**
     * 更新多条记录
     *
     * @param user
     */
    public void updateAllFunctionBean(List<UserBean> user) {
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserBeanDao userDao = daoSession.getUserBeanDao();
        userDao.updateInTx(user);
    }

    /**
     * 查询所有用户
     */
    public List<UserBean> queryAllUserList() {
        DaoMaster daoMaster = new DaoMaster(dbManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserBeanDao userBeanDao = daoSession.getUserBeanDao();
        QueryBuilder<UserBean> builder = userBeanDao.queryBuilder();
        List<UserBean> list = builder.orderAsc(UserBeanDao.Properties.Id).list();
        return list;
    }

}
