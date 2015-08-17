package com.icloudoor.cloudoor.network.protocol;

import com.icloudoor.cloudoor.app.CloudoorConstants;
import com.icloudoor.cloudoor.network.frame.BaseService;
import com.icloudoor.cloudoor.network.frame.GroupTransactionListener;
import com.icloudoor.cloudoor.network.frame.Transaction;
import com.icloudoor.cloudoor.network.frame.TransactionEngine;
import com.icloudoor.cloudoor.network.http.HttpDataChannel;
import com.icloudoor.cloudoor.network.http.HttpEngine;
import com.icloudoor.cloudoor.network.transaction.AcceptInvitationTransaction;
import com.icloudoor.cloudoor.network.transaction.AddFriendTransaction;
import com.icloudoor.cloudoor.network.transaction.AuthBaseTransaction;
import com.icloudoor.cloudoor.network.transaction.AuthTempCarTransaction;
import com.icloudoor.cloudoor.network.transaction.AuthTempNormalTransaction;
import com.icloudoor.cloudoor.network.transaction.ChangePasswordTransaction;
import com.icloudoor.cloudoor.network.transaction.CloudoorBaseTransaction;
import com.icloudoor.cloudoor.network.transaction.ComplainTransaction;
import com.icloudoor.cloudoor.network.transaction.ConfigDefaultTransaction;
import com.icloudoor.cloudoor.network.transaction.ConfirmVerifyCodeTransaction;
import com.icloudoor.cloudoor.network.transaction.CreateUserTransaction;
import com.icloudoor.cloudoor.network.transaction.DownloadDoorTransaction;
import com.icloudoor.cloudoor.network.transaction.FileUploadTransaction;
import com.icloudoor.cloudoor.network.transaction.GetAlmanacTransaction;
import com.icloudoor.cloudoor.network.transaction.GetBannersTransaction;
import com.icloudoor.cloudoor.network.transaction.GetFamilyAddressTransaction;
import com.icloudoor.cloudoor.network.transaction.GetFamilyAndCarsTransaction;
import com.icloudoor.cloudoor.network.transaction.GetFriendsTransaction;
import com.icloudoor.cloudoor.network.transaction.GetGridCountTransaction;
import com.icloudoor.cloudoor.network.transaction.GetIMAccountTransaction;
import com.icloudoor.cloudoor.network.transaction.GetIsUserRegTransaction;
import com.icloudoor.cloudoor.network.transaction.GetMyAddressTransaction;
import com.icloudoor.cloudoor.network.transaction.GetOfficeSignsTransaction;
import com.icloudoor.cloudoor.network.transaction.GetProfileTransaction;
import com.icloudoor.cloudoor.network.transaction.GetServerTimeTransaction;
import com.icloudoor.cloudoor.network.transaction.GetTagsTransaction;
import com.icloudoor.cloudoor.network.transaction.GetUsersDetailTransaction;
import com.icloudoor.cloudoor.network.transaction.GrabRedTransaction;
import com.icloudoor.cloudoor.network.transaction.KeyManagementTransaction;
import com.icloudoor.cloudoor.network.transaction.LoginTransaction;
import com.icloudoor.cloudoor.network.transaction.LogoutTransaction;
import com.icloudoor.cloudoor.network.transaction.RemoveFriendTransaction;
import com.icloudoor.cloudoor.network.transaction.ReturnTempAuthCarTransaction;
import com.icloudoor.cloudoor.network.transaction.SearchUserTransaction;
import com.icloudoor.cloudoor.network.transaction.SendVerifyCodeTransaction;
import com.icloudoor.cloudoor.network.transaction.SignTransaction;
import com.icloudoor.cloudoor.network.transaction.UpdateCarPosStatusTransaction;
import com.icloudoor.cloudoor.network.transaction.UpdateProfileTransaction;
import com.icloudoor.cloudoor.network.transaction.UploadPortraitTransaction;
import com.icloudoor.cloudoor.network.transaction.VerifyPasswordTransaction;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Derrick Guan on 7/22/15.
 */
public class CloudoorService extends BaseService {

    /**
     * TransactionEngine核心线程个数
     */
    public static int coreThreadCount = 3;
    private static CloudoorService instance = null;
    private GroupTransactionListener mGroupListener;

    private CloudoorService() {
        super(new HttpDataChannel(new TransactionEngine(
                TransactionEngine.Priority, coreThreadCount), new HttpEngine(3,
                Thread.NORM_PRIORITY - 1)));

        mGroupListener = new GroupTransactionListener();
    }

    public static CloudoorService getInstance() {
        if (instance == null) {
            instance = new CloudoorService();
        }
        return instance;
    }

    public void addListener(CloudoorCallback callback) {
        mGroupListener.addListener(callback);
    }

    public void removeListener(CloudoorCallback callback) {
        mGroupListener.removeListener(callback);
    }

    @Override
    protected int beginTransaction(Transaction trans) {
        trans.setListener(mGroupListener);
        return super.beginTransaction(trans);
    }

    /********** 各类网络请求 **********/

    /**
     * 发送验证码
     *
     * @param mobile 手机号码
     * @return tid
     */
    public int doSendVerifyCode(String mobile) {
        SendVerifyCodeTransaction trans = new SendVerifyCodeTransaction(mobile);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 确认验证码
     *
     * @param verifyCode 验证码
     * @return tid
     */
    public int doConfirmVerifyCode(String verifyCode) {
        ConfirmVerifyCodeTransaction trans = new ConfirmVerifyCodeTransaction(verifyCode);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 创建用户
     *
     * @param password 用户密码
     * @return tid
     */
    public int doCreateUser(String password) {
        CreateUserTransaction trans = new CreateUserTransaction(password);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 登录
     *
     * @param mobile   手机号码
     * @param password 用户密码
     * @return tid
     */
    public int doLogin(String mobile, String password) {
        LoginTransaction trans = new LoginTransaction(mobile, password);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取用户资料
     *
     * @return tid
     */
    public int doGetProfile() {
        GetProfileTransaction trans = new GetProfileTransaction();
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 修改用户密码，需要登录
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return tid
     */
    public int doChangePassword(String oldPassword, String newPassword) {
        ChangePasswordTransaction trans = new ChangePasswordTransaction(oldPassword, newPassword);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 退出登录
     *
     * @return tid
     */
    public int doLogout() {
        LogoutTransaction trans = new LogoutTransaction();
        beginTransaction(trans);
        return trans.getId();
    }


    /**
     * 更新用户资料
     *
     * @param profileMap 需要修改的用户资料
     * @return tid
     */
    public int doUpdateProfile(Map<String, String> profileMap) {
        UpdateProfileTransaction trans = new UpdateProfileTransaction(profileMap);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 上传用户头像
     *
     * @param file 待上传的头像文件
     * @return tid
     */
    public int doUploadPortrait(File file) {
        UploadPortraitTransaction trans = new UploadPortraitTransaction(file);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 验证密码
     *
     * @param mobile   手机号码
     * @param password 用户密码
     * @return tid
     */
    public int doVerifyPassword(String mobile, String password) {
        VerifyPasswordTransaction trans = new VerifyPasswordTransaction(mobile, password);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 下载门锁
     *
     * @return tid
     */
    public int doDownloadDoor() {
        DownloadDoorTransaction trans = new DownloadDoorTransaction();
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取我的地址
     *
     * @return tid
     */
    public int doGetMyAddress() {
        GetMyAddressTransaction trans = new GetMyAddressTransaction();
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取家庭成员手机号&车牌
     *
     * @param zoneUserId 住户用户id
     * @return tid
     */
    public int doGetFamilyAndCars(String zoneUserId) {
        GetFamilyAndCarsTransaction trans = new GetFamilyAndCarsTransaction(zoneUserId);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 授权临时车门权限
     *
     * @param zoneUserId   住址id
     * @param plateNum     车牌号
     * @param toTarget     授权目标
     * @param toUser       手机号或用户id
     * @param carPosStatus 当前车状态
     * @param authFrom     授权时间  yyyy-MM-dd HH:mm:ss
     * @return tid
     * @see com.icloudoor.cloudoor.network.transaction.AuthBaseTransaction.AuthToTarget
     */
    public int doAuthTempCar(String zoneUserId, String plateNum,
                             AuthBaseTransaction.AuthToTarget toTarget,
                             String toUser, String carPosStatus, String authFrom) {

        AuthTempCarTransaction trans = new AuthTempCarTransaction(
                zoneUserId, plateNum, toTarget, toUser, carPosStatus, authFrom);
        beginTransaction(trans);
        return trans.getId();
    }


    /**
     * 授权临时普通门权限
     *
     * @param zoneUserId 住址id
     * @param toTarget   授权目标
     * @param toUser     手机号或用户id
     * @param authDate   授权日期 yyyy-MM-dd格式
     * @return tid
     * @see com.icloudoor.cloudoor.network.transaction.AuthBaseTransaction.AuthToTarget
     */
    public int doAuthTempNormal(String zoneUserId,
                                AuthBaseTransaction.AuthToTarget toTarget,
                                String toUser, String authDate) {
        AuthTempNormalTransaction trans = new AuthTempNormalTransaction(zoneUserId, toTarget, toUser, authDate);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 授权临时普通门权限
     *
     * @param zoneUserId 住址id
     * @param toTarget   授权目标
     * @param toUser     手机号或用户id
     * @param authFrom   授权开始时间  yyyy-MM-dd HH:mm:ss
     * @param authTo     授权结束时间  yyyy-MM-dd HH:mm:ss
     * @return tid
     * @see com.icloudoor.cloudoor.network.transaction.AuthBaseTransaction.AuthToTarget
     */
    public int doAuthTempNormal(String zoneUserId,
                                AuthBaseTransaction.AuthToTarget toTarget,
                                String toUser, String authFrom, String authTo) {
        AuthTempNormalTransaction trans = new AuthTempNormalTransaction(zoneUserId, toTarget, toUser, authFrom, authTo);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 归还车钥匙
     *
     * @param l1ZoneId     小区id
     * @param plateNum     车牌号
     * @param carPosStatus 车状态
     * @return tid
     */
    public int doReturnTempAuthCar(String l1ZoneId, String plateNum, String carPosStatus) {
        ReturnTempAuthCarTransaction trans = new ReturnTempAuthCarTransaction(l1ZoneId, plateNum, carPosStatus);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 更新车状态
     *
     * @param l1ZoneId     小区id
     * @param plateNum     车牌号
     * @param carPosStatus 车状态
     * @return tid
     */
    public int doUpdateCarPosStatus(String l1ZoneId, String plateNum, String carPosStatus) {
        UpdateCarPosStatusTransaction trans = new UpdateCarPosStatusTransaction(l1ZoneId, plateNum, carPosStatus);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取用户标签
     *
     * @return tid
     */
    public int doGetTags() {
        GetTagsTransaction trans = new GetTagsTransaction();
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取用户配置
     *
     * @return tid
     */
    public int doGetDefaultConfig() {
        ConfigDefaultTransaction trans = new ConfigDefaultTransaction();
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取未读的公告数以及问卷调查数
     *
     * @return tid
     */
    public int doGetPropNotifyCounts() {
        GetGridCountTransaction trans = new GetGridCountTransaction();
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取Banner
     *
     * @return tid
     */
    public int doGetBanners() {
        GetBannersTransaction trans = new GetBannersTransaction();
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 签到
     *
     * @param doorId 门id
     * @param type   签到类型
     * @return tid
     */
    public int doSign(String doorId, String type) {
        int transType = 0;
        if (type.equalsIgnoreCase(CloudoorConstants.SignType.OnDuty)) {
            transType = CloudoorBaseTransaction.TRANSACTION_SIGN_ON;
        } else if (type.equalsIgnoreCase(CloudoorConstants.SignType.OffDuty)) {
            transType = CloudoorBaseTransaction.TRANSACTION_SIGN_OFF;
        }
        SignTransaction trans = new SignTransaction(transType, doorId, type);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取黄历
     *
     * @param date 开始日期 yyyy-MM-dd 默认获取3天
     * @return tid
     */
    public int doGetAlmanac(String date) {
        return doGetAlmanac(date, "3");
    }

    /**
     * 获取黄历
     *
     * @param date 开始日期 yyyy-MM-dd
     * @param days 返回的天数
     * @return tid
     */
    public int doGetAlmanac(String date, String days) {
        GetAlmanacTransaction trans = new GetAlmanacTransaction(date, days);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取服务器时间
     *
     * @return tid
     */
    public int doGetServerTime() {
        GetServerTimeTransaction trans = new GetServerTimeTransaction();
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 上传文件到又拍云
     *
     * @param type 上传类型
     * @param ext  上传文件扩展名
     * @param file 待上传文件
     * @return tid
     * @see com.icloudoor.cloudoor.app.CloudoorConstants.UploadFileType
     * @see com.icloudoor.cloudoor.app.CloudoorConstants.FileExtension
     */
    public int doUploadFile(String type, String ext, File file) {
        FileUploadTransaction trans = new FileUploadTransaction(type, ext, file);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 开门抢红包
     *
     * @param doorId 门id
     * @return tid
     */
    public int doGrabRed(String doorId) {
        GrabRedTransaction trans = new GrabRedTransaction(doorId);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取用户的IM登录账号
     *
     * @return tid
     */
    public int doGetIMAccount() {
        GetIMAccountTransaction trans = new GetIMAccountTransaction();
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取好友列表
     *
     * @return tid
     */
    public int doGetFriends() {
        GetFriendsTransaction trans = new GetFriendsTransaction();
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 搜索好友
     *
     * @param searchValue 搜索字段,不能为空
     * @return tid
     */
    public int doSearchUser(String searchValue) {
        SearchUserTransaction trans = new SearchUserTransaction(searchValue);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 申请添加好友
     *
     * @param targetUserId 目标用户ID
     * @param targetMobile 目标用户手机号码
     * @param comment      注释
     * @return tid
     */
    public int doAddFriend(String targetUserId, String targetMobile, String comment) {
        AddFriendTransaction trans = new AddFriendTransaction(targetUserId, targetMobile, comment);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 同意添加好友申请
     *
     * @param invitationId 好友申请ID
     * @return tid
     */
    public int doAcceptInvitation(String invitationId) {
        AcceptInvitationTransaction trans = new AcceptInvitationTransaction(invitationId);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 删除好友
     *
     * @param friendUserId 好友userId
     * @return tid
     */
    public int doRemoveFriend(String friendUserId) {
        RemoveFriendTransaction trans = new RemoveFriendTransaction(friendUserId);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取用户详细信息
     *
     * @param userIds 用户id列表
     * @return tid
     */
    public int doGetUsersDetail(List<String> userIds) {
        GetUsersDetailTransaction trans = new GetUsersDetailTransaction(userIds);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 举报
     *
     * @param trgUserId 投诉目标用户id
     * @param type      举报类型
     * @return tid
     * @see com.icloudoor.cloudoor.app.CloudoorConstants.ComplainType
     */
    public int doComplain(String trgUserId, int type) {
        ComplainTransaction trans = new ComplainTransaction(trgUserId, type);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取我的钥匙
     *
     * @return tid
     */
    public int doGetMyKeys() {
        KeyManagementTransaction trans = new KeyManagementTransaction(CloudoorBaseTransaction.TRANSACTION_GET_MY_KEYS);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取我借入的钥匙
     *
     * @return tid
     */
    public int doGetMyBorrowKeys() {
        KeyManagementTransaction trans = new KeyManagementTransaction(CloudoorBaseTransaction.TRANSACTION_GET_MY_BORROW_KEYS);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取我授权的钥匙
     *
     * @return tid
     */
    public int doGetMyAuthKeys() {
        KeyManagementTransaction trans = new KeyManagementTransaction(CloudoorBaseTransaction.TRANSACTION_GET_MY_AUTH_KEYS);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 是否家庭用户
     *
     * @param targetUserId 目标用户id
     * @return tid
     */
    public int doGetFamilyAddress(String targetUserId) {
        GetFamilyAddressTransaction trans = new GetFamilyAddressTransaction(targetUserId);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 是否注册用户
     *
     * @param mobiles 手机列表
     * @return tid
     */
    public int doGetIsUserReg(List<String> mobiles) {
        GetIsUserRegTransaction trans = new GetIsUserRegTransaction(mobiles);
        beginTransaction(trans);
        return trans.getId();
    }

    /**
     * 获取考勤记录
     *
     * @param from 开始时间
     * @param to   结束时间
     * @return tid
     */
    public int doGetOfficeSigns(String from, String to) {
        GetOfficeSignsTransaction trans = new GetOfficeSignsTransaction(from, to);
        beginTransaction(trans);
        return trans.getId();
    }

}
