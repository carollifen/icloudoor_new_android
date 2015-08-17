package com.icloudoor.cloudoor.network.protocol;

import com.icloudoor.cloudoor.app.CloudoorConstants;

/**
 * 云门后端接口
 * Created by Derrick Guan on 8/6/15.
 */
public interface CloudoorProtocol {

    // 正式环境下的host
    final String RELEASE_HOST = "http://zone.icloudoor.com/icloudoor-web";
    // 测试环境下的host
    final String DEBUG_HOST = "http://test.zone.icloudoor.com/icloudoor-web";
    // 根据是否DEBUG环境切换后端服务器地址
    final String SERVER_DOMAIN = CloudoorConstants.DEBUG ? DEBUG_HOST : RELEASE_HOST;

    /**
     * 用户管理模块接口
     */
    final String USER_MANAGEMENT_MODULE = "/user/manage/";

    // 发送验证码
    final String SEND_VERIFY_CODE = "sendVerifyCode.do";
    // 确认验证码
    final String CONFIRM_VERIFY_CODE = "confirmVerifyCode.do";
    // 确认验证码并验证用户是否注册
    final String CONFIRM_VERIFY_CODE_4_REG = "confirmVerifyCode4Reg.do";
    // 创建用户
    final String CREATE_USER = "createUser.do";
    // 设置用户资料
    final String UPDATE_PROFILE = "updateProfile.do";
    // 获取用户资料
    final String GET_PROFILE = "getProfile.do";
    // 修改密码
    final String CHANGE_PASSWORD = "changePassword.do";
    // 通过验证码修改密码
    final String CHANGE_PASSWORD_4_FORGET_PASSWORD = "changePassword2.do";
    // 登录
    final String LOGIN = "login.do";
    // 退出登录
    final String LOGOUT = "logout.do";
    // 验证密码
    final String VERIFY_PASSWORD = "verifyPassword.do";

    /**
     * 用户相关
     */
    final String USER_API_MODULE = "/user/api/";
    // 上传用户头像
    final String UPLOAD_PORTRAIT = "uploadPortrait.do";
    // 我的地址
    final String GET_MY_ADDRESS = "getMyAddress.do";
    // 家庭成功手机号
    final String GET_FAMILY_USER_AND_CARS = "getFamilyUserAndCars.do";
    // 授权临时车门权限
    final String AUTH_TEMP_CAR = "authTempCar.do";
    // 授权临时普通门权限
    final String AUTH_TEMP_NORMAL = "authTempNormal.do";
    // 归还车钥匙
    final String RETURN_TEMP_AUTH_CAR = "returnTempAuthCar.do";
    // 更新车状态
    final String UPDATE_CAR_POS_STATUS = "updateCarPosStatus.do";
    // 获取用户标签
    final String GET_TAGS = "getTags.do";
    // 签到
    final String SIGN = "sign.do";
    // 获取用户详细信息
    final String GET_USERS_DETAIL = "getUsersDetail.do";
    // 获取我的钥匙
    final String GET_MY_KEYS = "keys/my.do";
    // 获取借入的钥匙
    final String GET_MY_BORROW_KEYS = "keys/myBorrow.do";
    // 获取我授权的钥匙
    final String GET_MY_AUTH_KEYS = "keys/myAuth.do";
    // 是否家庭用户
    final String GET_FAMILY_ADDRESS = "getFamilyAddr.do";
    // 是否注册用户
    final String IS_USER_REG = "isUserReg.do";
    // 获取考勤记录
    final String GET_SIGNS = "office/getSigns.do";

    /**
     * 用户配置模块
     */
    final String USER_CONFIG_MODULE = "/user/config/";
    // 获取用户配置
    final String DEFAULT = "default.do";


    /**
     * 门锁信息模块
     */
    final String USER_DOOR_MODULE = "/user/door/";
    // 下载门锁
    final String DOWNLOAD = "download.do";

    /**
     * 物业服务模块
     */
    final String USER_PROP_ZONE_MODULE = "/user/prop/zone/";
    // 获取未读的公告数以及问卷调查数
    final String GET_GRID_COUNT = "getGridCount.do";
    // 获取banner
    final String GET_BANNERS = "getBannerRotate.do";

    /**
     * 其他接口模块
     */
    final String USER_DATA_MODULE = "/user/data/";
    // 获取黄历接口
    final String GET_ALMANAC = "laohuangli/get.do";

    /**
     * 文件上传
     */
    final String USER_FILE_MODULE = "/user/file/";
    // 获取文件上传策略和签名
    final String GET_SIGNATURE_AND_POLICY = "getSignatureAndPolicy.do";


    /**
     * 工具类接口
     */
    final String USER_UTILS_MODULE = "/user/utils/";
    // 获取服务器时间
    final String GET_SERVER_TIME = "time.do";

    /**
     * 红包接口
     */
    final String USER_ACTIVITY_RP = "/user/activity/rp/";
    // 开门抢红包
    final String GRAB = "grab.do";

    /**
     * 聊天相关接口
     */
    final String USER_IM_MODULE = "/user/im/";
    // 获取用户的IM登录账号
    final String GET_ACCOUNT = "getAccount.do";
    // 获取好友列表
    final String GET_FRIENDS = "getFriends.do";
    // 搜索好友
    final String SEARCH_USER = "searchUser.do";
    // 申请添加好友
    final String INVITE = "invite.do";
    // 同意添加好友申请
    final String ACCEPT_INVITATION = "acceptInvitation.do";
    // 删除好友
    final String REMOVE_FRIEND = "removeFriend.do";
    // 举报
    final String COMPLAIN = "complain.do";
}
