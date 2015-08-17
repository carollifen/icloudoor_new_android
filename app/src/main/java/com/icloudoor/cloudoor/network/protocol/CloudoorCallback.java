package com.icloudoor.cloudoor.network.protocol;

import com.icloudoor.cloudoor.network.bean.DownloadDoorBean;
import com.icloudoor.cloudoor.network.bean.GetFamilyAndCarsBean;
import com.icloudoor.cloudoor.network.bean.GetMyAuthKeysBean;
import com.icloudoor.cloudoor.network.bean.GetMyBorrowKeysBean;
import com.icloudoor.cloudoor.network.bean.GetMyKeysBean;
import com.icloudoor.cloudoor.network.bean.GetOfficeSignsBean;
import com.icloudoor.cloudoor.network.bean.LoginBean;
import com.icloudoor.cloudoor.network.bean.SignBean;
import com.icloudoor.cloudoor.network.bean.UserDetailBean;
import com.icloudoor.cloudoor.network.bean.meta.AbstractBanner;
import com.icloudoor.cloudoor.network.bean.meta.Almanac;
import com.icloudoor.cloudoor.network.bean.meta.Config;
import com.icloudoor.cloudoor.network.bean.meta.Friend;
import com.icloudoor.cloudoor.network.bean.meta.HXAccount;
import com.icloudoor.cloudoor.network.bean.meta.MyAddress;
import com.icloudoor.cloudoor.network.bean.meta.SearchFriend;
import com.icloudoor.cloudoor.network.bean.meta.UserBasicInfo;
import com.icloudoor.cloudoor.network.transaction.CloudoorBaseTransaction;
import com.icloudoor.cloudoor.utils.CloudoorErrorUtil;

import java.util.List;

/**
 * Created by Derrick Guan on 7/21/15.
 * 云门各类网络请求的回调处理类
 */
public class CloudoorCallback {

    // 发送验证码成功
    public void onSendVerifyCode(int tid) {
    }

    // 确认验证码成功
    public void onConfirmVerifyCode(int tid) {
    }

    // 创建用户成功
    public void onCreateUser(int tid) {
    }

    // 登录成功
    public void onLogin(int tid, LoginBean bean) {
    }

    // 获取用户资料成功
    public void onGetProfile(int tid, UserBasicInfo userBasicInfo) {
    }

    // 修改密码成功
    public void onChangePassword(int tid) {
    }

    // 退出登录成功
    public void onLogout(int tid) {
    }

    // 修改用户资料成功
    public void onUpdateProfile(int tid) {
    }

    // 上传头像成功
    public void onUploadPortrait(int tid, String portraitUrl) {
    }

    // 验证密码成功
    public void onVerifyPassword(int tid, boolean isPswCorrect) {
    }

    // 下载门锁成功
    public void onDownloadDoor(int tid, DownloadDoorBean bean) {
    }

    // 获取我的地址成功
    public void onGetMyAddress(int tid, List<MyAddress> myAddressList) {
    }

    // 获取家庭成员手机号&车牌成功
    public void onGetFamilyAndCars(int tid, GetFamilyAndCarsBean bean) {
    }

    // 授权临时车门权限成功
    public void onAuthTempCar(int tid) {
    }

    // 授权临时普通门权限成功
    public void onAuthTempNormal(int tid) {
    }

    // 归还车钥匙成功
    public void onReturnTempAuthCar(int tid) {
    }

    // 更新车状态成功
    public void onUpdateCarPosStatus(int tid) {
    }

    // 获取用户标签成功
    public void onGetTags(int tid, List<String> tags) {
    }

    // 获取用户配置成功
    public void onGetDefaultConfig(int tid, Config config) {
    }

    /**
     * 获取未读的公告数以及问卷调查数成功
     *
     * @param tid          事务id
     * @param unReadCounts index=0 : 公告数; index=1 : 问卷调查数;
     */
    public void onGetPropNotifyCount(int tid, int[] unReadCounts) {
    }

    // 获取Banner成功
    public void onGetBanners(int tid, List<AbstractBanner> banners) {
    }

    // 上班签到成功
    public void onSignOnDuty(int tid, SignBean signBean) {
    }

    // 下班签到成功
    public void onSignOffDuty(int tid) {
    }

    // 获取黄历成功
    public void onGetAlmanac(int tid, List<Almanac> almanacList) {
    }

    // 获取服务器时间成功
    public void onGetServerTime(int tid, long serverTime) {
    }

    /**
     * 上传文件到又拍云成功
     *
     * @param tid tid
     * @param url 上传文件成功后，文件所在的HTTP地址
     */
    public void onUploadFile(int tid, String url) {
    }

    // 成功抢到红包
    public void onGrabRed(int tid) {
    }

    // 获取用户的IM登录账号成功
    public void onGetIMAccount(int tid, HXAccount account) {
    }

    // 获取好友列表成功
    public void onGetFriends(int tid, List<Friend> friendList) {
    }

    // 搜索好友成功
    public void onSearchUser(int tid, List<SearchFriend> searchList) {
    }

    // 发送申请添加好友
    public void onAddFriend(int tid) {
    }

    // 同意添加好友申请成功
    public void onAcceptInvitation(int tid) {
    }

    // 删除好友成功
    public void onRemoveFriend(int tid) {
    }

    // 获取用户详细信息成功
    public void onGetUsersDetail(int tid, List<UserDetailBean> beanList) {
    }

    // 举报成功
    public void onComplain(int tid) {
    }

    // 获取我的钥匙列表成功
    public void onGetMyKeys(int tid, List<GetMyKeysBean> myKeysList) {
    }

    // 获取我借入的钥匙列表成功
    public void onGetMyBorrowKeys(int tid, List<GetMyBorrowKeysBean> myBorrowKeysList) {
    }

    // 获取我授权的钥匙列表成功
    public void onGetMyAuthKeys(int tid, List<GetMyAuthKeysBean> myAuthKeysList) {
    }

    // 获取是否家庭用户成功
    public void onGetFamilyAddress(int tid, List<String> zoneUserIds) {
    }

    // 获取是否注册用户成功
    public void onGetIsUserReg(int tid, List<String> regMobiles) {
    }

    // 获取考勤记录成功
    public void onGetOfficeSigns(int tid, GetOfficeSignsBean bean) {
    }

    /**
     * 网络调用成功的回调入口
     *
     * @param type 事务类型
     * @param tid  事务id
     * @param data 返回的数据
     */
    public final void onSuccess(int type, int tid, Object data) {
        switch (type) {
            case CloudoorBaseTransaction.TRANSACTION_SEND_VERIFY_CODE:
                onSendVerifyCode(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_CONFIRM_VERIFY_CODE:
                onConfirmVerifyCode(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_CREATE_USER:
                onCreateUser(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_LOGIN:
                onLogin(tid, (LoginBean) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_PROFILE:
                onGetProfile(tid, (UserBasicInfo) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_CHANGE_PASSWORD:
                onChangePassword(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_LOGOUT:
                onLogout(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_UPDATE_PROFILE:
                onUpdateProfile(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_UPLOAD_PORTRAIT:
                onUploadPortrait(tid, (String) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_VERIFY_PASSWORD:
                onVerifyPassword(tid, (Boolean) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_DOWNLOAD_DOOR:
                onDownloadDoor(tid, (DownloadDoorBean) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_MY_ADDRESS:
                onGetMyAddress(tid, (List<MyAddress>) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_FAMILY_AND_CARS:
                onGetFamilyAndCars(tid, (GetFamilyAndCarsBean) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_AUTH_TEMP_CAR:
                onAuthTempCar(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_AUTH_TEMP_NORMAL:
                onAuthTempNormal(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_RETURN_TEMP_AUTH_CAR:
                onReturnTempAuthCar(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_UPDATE_CAR_POS_STATUS:
                onUpdateCarPosStatus(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_TAGS:
                onGetTags(tid, (List<String>) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_CONFIG_DEFAULT:
                onGetDefaultConfig(tid, (Config) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_GRID_COUNT:
                onGetPropNotifyCount(tid, (int[]) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_BANNERS:
                onGetBanners(tid, (List<AbstractBanner>) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_SIGN_ON:
                onSignOnDuty(tid, (SignBean) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_SIGN_OFF:
                onSignOffDuty(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_ALMANAC:
                onGetAlmanac(tid, (List<Almanac>) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_SERVER_TIME:
                onGetServerTime(tid, (long) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_FILE_UPLOAD:
                onUploadFile(tid, (String) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GRAB_RED:
                onGrabRed(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_IM_ACCOUNT:
                onGetIMAccount(tid, (HXAccount) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_FRIENDS:
                onGetFriends(tid, (List<Friend>) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_SEARCH_USER:
                onSearchUser(tid, (List<SearchFriend>) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_ADD_FRIEND:
                onAddFriend(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_ACCEPT_INVITATION:
                onAcceptInvitation(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_REMOVE_FRIEND:
                onRemoveFriend(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_USERS_DETAIL:
                onGetUsersDetail(tid, (List<UserDetailBean>) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_COMPLAIN:
                onComplain(tid);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_MY_KEYS:
                onGetMyKeys(tid, (List<GetMyKeysBean>) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_MY_BORROW_KEYS:
                onGetMyBorrowKeys(tid, (List<GetMyBorrowKeysBean>) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_MY_AUTH_KEYS:
                onGetMyAuthKeys(tid, (List<GetMyAuthKeysBean>) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_FAMILY_ADDRESS:
                onGetFamilyAddress(tid, (List<String>) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_IS_USER_REG:
                onGetIsUserReg(tid, (List<String>) data);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_OFFICE_SIGNS:
                onGetOfficeSigns(tid, (GetOfficeSignsBean) data);
                break;
        }
    }

    // sid过期，强制用户退出
    public void onSidInvalidError(int tid, String err) {
    }

    // 发送验证码失败
    public void onSendVerifyCodeError(int tid, String err) {
    }

    // 确认验证码失败
    public void onConfirmVerifyCodeError(int tid, String err) {
    }

    // 创建用户失败
    public void onCreateUserError(int tid, String err) {
    }

    // 登录失败
    public void onLoginError(int tid, String err) {
    }

    // 获取用户资料失败
    public void onGetProfileError(int tid, String err) {
    }

    // 修改密码失败
    public void onChangePasswordError(int tid, String err) {
    }

    // 退出登录失败
    public void onLogoutError(int tid, String err) {
    }

    // 修改用户资料失败
    public void onUpdateProfileError(int tid, String err) {
    }

    // 上传头像失败
    public void onUploadPortraitError(int tid, String err) {
    }

    // 验证密码失败
    public void onVerifyPasswordError(int tid, String err) {
    }

    // 下载门锁失败
    public void onDownloadDoorError(int tid, String err) {
    }

    // 获取我的地址失败
    public void onGetMyAddressError(int tid, String err) {
    }

    // 获取家庭成员手机号&车牌失败
    public void onGetFamilyAndCarsError(int tid, String err) {
    }

    // 授权临时车门权限失败
    public void onAuthTempCarError(int tid, String err) {
    }

    // 授权临时普通门权限失败
    public void onAuthTempNormalError(int tid, String err) {
    }

    // 归还车钥匙失败
    public void onReturnTempAuthCarError(int tid, String err) {
    }

    // 更新车状态失败
    public void onUpdateCarPosStatusError(int tid, String err) {
    }

    // 获取用户标签失败
    public void onGetTagsError(int tid, String err) {
    }

    // 获取用户配置失败
    public void onGetDefaultConfigError(int tid, String err) {
    }

    // 获取未读的公告数以及问卷调查数失败
    public void onGetPropNotifyCountError(int tid, String err) {
    }

    // 获取Banner成功
    public void onGetBannersError(int tid, String err) {
    }

    // 上班签到失败
    public void onSignOnDutyError(int tid, String err) {
    }

    // 下班签到失败
    public void onSignOffDutyError(int tid, String err) {
    }

    // 获取黄历失败
    public void onGetAlmanacError(int tid, String err) {
    }

    // 获取服务器时间失败
    public void onGetServerTimeError(int tid, String err) {
    }

    // 上传文件到又拍云失败
    public void onUploadFileError(int tid, String err) {
    }

    // 抢红包失败：可能是网络调用失败或者确实抢不到红包
    public void onGrabRedError(int tid, String err) {
    }

    //  获取用户的IM登录账号失败
    public void onGetIMAccountError(int tid, String err) {
    }

    // 获取好友列表失败
    public void onGetFriendsError(int tid, String err) {
    }

    // 搜索好友失败
    public void onSearchUserError(int tid, String err) {
    }

    // 发送申请添加好友失败
    public void onAddFriendError(int tid, String err) {
    }

    // 同意添加好友申请失败
    public void onAcceptInvitationError(int tid, String err) {
    }

    // 删除好友失败
    public void onRemoveFriendError(int tid, String err) {
    }

    // 获取用户详细信息失败
    public void onGetUsersDetailError(int tid, String err) {
    }

    // 举报失败
    public void onComplainError(int tid, String err) {
    }

    // 获取我的钥匙列表失败
    public void onGetMyKeysError(int tid, String err) {
    }

    // 获取我借入的钥匙列表失败
    public void onGetMyBorrowKeysError(int tid, String err) {
    }

    // 获取我授权的钥匙列表失败
    public void onGetMyAuthKeysError(int tid, String err) {
    }

    // 获取是否家庭用户失败
    public void onGetFamilyAddressError(int tid, String err) {
    }

    // 获取是否注册用户失败
    public void onGetIsUserRegError(int tid, String err) {
    }

    // 获取考勤记录失败
    public void onGetOfficeSignsError(int tid, String err) {
    }

    /**
     * 网络调用失败的回调入口
     *
     * @param type 事务类型
     * @param tid  事务id
     * @param err  错误信息
     */
    public final void onError(int type, int tid, String err) {
        // 先判定是否sid过期的错误, 如果是就不进行switch
        if (err.equalsIgnoreCase(CloudoorErrorUtil.getErrorMessage(CloudoorServiceCode.ERR_NOT_LOGGED_IN))) {
            onSidInvalidError(tid, err);
            return;
        }

        switch (type) {
            case CloudoorBaseTransaction.TRANSACTION_SEND_VERIFY_CODE:
                onSendVerifyCodeError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_CONFIRM_VERIFY_CODE:
                onConfirmVerifyCodeError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_CREATE_USER:
                onCreateUserError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_LOGIN:
                onLoginError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_PROFILE:
                onGetProfileError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_CHANGE_PASSWORD:
                onChangePasswordError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_LOGOUT:
                onLogoutError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_UPDATE_PROFILE:
                onUpdateProfileError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_UPLOAD_PORTRAIT:
                onUploadPortraitError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_VERIFY_PASSWORD:
                onVerifyPasswordError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_DOWNLOAD_DOOR:
                onDownloadDoorError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_MY_ADDRESS:
                onGetMyAddressError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_FAMILY_AND_CARS:
                onGetFamilyAndCarsError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_AUTH_TEMP_CAR:
                onAuthTempCarError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_AUTH_TEMP_NORMAL:
                onAuthTempNormalError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_RETURN_TEMP_AUTH_CAR:
                onReturnTempAuthCarError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_UPDATE_CAR_POS_STATUS:
                onUpdateCarPosStatusError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_TAGS:
                onGetTagsError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_CONFIG_DEFAULT:
                onGetDefaultConfigError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_GRID_COUNT:
                onGetPropNotifyCountError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_BANNERS:
                onGetBannersError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_SIGN_ON:
                onSignOnDutyError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_SIGN_OFF:
                onSignOffDutyError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_ALMANAC:
                onGetAlmanacError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_SERVER_TIME:
                onGetServerTimeError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_FILE_UPLOAD:
                onUploadFileError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GRAB_RED:
                onGrabRedError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_IM_ACCOUNT:
                onGetIMAccountError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_FRIENDS:
                onGetFriendsError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_SEARCH_USER:
                onSearchUserError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_ADD_FRIEND:
                onAddFriendError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_ACCEPT_INVITATION:
                onAcceptInvitationError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_REMOVE_FRIEND:
                onRemoveFriendError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_USERS_DETAIL:
                onGetUsersDetailError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_COMPLAIN:
                onComplainError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_MY_KEYS:
                onGetMyKeysError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_MY_BORROW_KEYS:
                onGetMyBorrowKeysError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_MY_AUTH_KEYS:
                onGetMyAuthKeysError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_FAMILY_ADDRESS:
                onGetFamilyAddressError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_IS_USER_REG:
                onGetIsUserRegError(tid, err);
                break;
            case CloudoorBaseTransaction.TRANSACTION_GET_OFFICE_SIGNS:
                onGetOfficeSignsError(tid, err);
                break;
        }
    }

}