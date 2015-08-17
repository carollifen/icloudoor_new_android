package com.icloudoor.cloudoor.network.transaction;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.icloudoor.cloudoor.PreferMgr.CloudoorPreHelper;
import com.icloudoor.cloudoor.network.bean.BaseData;
import com.icloudoor.cloudoor.network.frame.AsyncTransaction;
import com.icloudoor.cloudoor.network.frame.NotifyTransaction;
import com.icloudoor.cloudoor.network.frame.TransTypeCode;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorProtocol;
import com.icloudoor.cloudoor.network.protocol.CloudoorServiceCode;

import java.lang.reflect.Type;

/**
 * 云门业务逻辑事务基础类
 * Created by Derrick Guan on 7/22/15.
 */
public abstract class CloudoorBaseTransaction extends AsyncTransaction
        implements CloudoorProtocol {

    /**
     * 用于UI间传递数据
     */
    public static final int TRANSACTION_UI_BROADCAST = 1;

    /**
     * 图片加载Transaction使用的TYPE, ImageTransaction使用
     */
    public static final int TRANSACTION_IMAGE = 2;

    /**
     * Derrick Guan协议段
     * 其余开发者在Derrick协议段后增加256个协议预留字段
     */
    private static final int TRANSACTION_TYPE_DERRICK_BASE = 0x100;

    /**
     * lifen协议段
     */
    private static final int TRANSACTION_TYPE_LIFEN_BASE = TRANSACTION_TYPE_DERRICK_BASE + 0x100;

    /**
     * fengxixi协议段
     */
    private static final int TRANSACTION_TYPE_FXX_BASE = TRANSACTION_TYPE_DERRICK_BASE + 0x200;

    // <---------- 各类网络请求协议段 ---------->

    /**
     * v1.0接口
     */
    // 发送验证码
    public static final int TRANSACTION_SEND_VERIFY_CODE = TRANSACTION_TYPE_DERRICK_BASE + 1;
    // 确认验证码
    public static final int TRANSACTION_CONFIRM_VERIFY_CODE = TRANSACTION_TYPE_DERRICK_BASE + 2;
    // 确认验证码并验证用户是否注册
    public static final int TRANSACTION_CONFIRM_VC_4_REG = TRANSACTION_TYPE_DERRICK_BASE + 3;
    // 创建用户
    public static final int TRANSACTION_CREATE_USER = TRANSACTION_TYPE_DERRICK_BASE + 4;
    // 登录
    public static final int TRANSACTION_LOGIN = TRANSACTION_TYPE_DERRICK_BASE + 5;
    // 获取用户资料
    public static final int TRANSACTION_GET_PROFILE = TRANSACTION_TYPE_DERRICK_BASE + 6;
    // 修改密码，需要登录
    public static final int TRANSACTION_CHANGE_PASSWORD = TRANSACTION_TYPE_DERRICK_BASE + 7;
    // 退出登录
    public static final int TRANSACTION_LOGOUT = TRANSACTION_TYPE_DERRICK_BASE + 8;
    // 修改用户资料
    public static final int TRANSACTION_UPDATE_PROFILE = TRANSACTION_TYPE_DERRICK_BASE + 9;
    // 上传用户头像
    public static final int TRANSACTION_UPLOAD_PORTRAIT = TRANSACTION_TYPE_DERRICK_BASE + 10;
    // 验证密码
    public static final int TRANSACTION_VERIFY_PASSWORD = TRANSACTION_TYPE_DERRICK_BASE + 11;
    // 下载门锁
    public static final int TRANSACTION_DOWNLOAD_DOOR = TRANSACTION_TYPE_DERRICK_BASE + 12;
    // 获取我的地址
    public static final int TRANSACTION_GET_MY_ADDRESS = TRANSACTION_TYPE_DERRICK_BASE + 13;
    // 获取家庭成员手机号
    public static final int TRANSACTION_GET_FAMILY_AND_CARS = TRANSACTION_TYPE_DERRICK_BASE + 14;
    // 授权临时车门权限
    public static final int TRANSACTION_AUTH_TEMP_CAR = TRANSACTION_TYPE_DERRICK_BASE + 15;
    // 授权临时普通门权限
    public static final int TRANSACTION_AUTH_TEMP_NORMAL = TRANSACTION_TYPE_DERRICK_BASE + 16;
    // 归还车钥匙
    public static final int TRANSACTION_RETURN_TEMP_AUTH_CAR = TRANSACTION_TYPE_DERRICK_BASE + 17;
    // 更新车状态
    public static final int TRANSACTION_UPDATE_CAR_POS_STATUS = TRANSACTION_TYPE_DERRICK_BASE + 18;
    // 获取用户标签
    public static final int TRANSACTION_GET_TAGS = TRANSACTION_TYPE_DERRICK_BASE + 19;
    // 获取用户配置
    public static final int TRANSACTION_CONFIG_DEFAULT = TRANSACTION_TYPE_DERRICK_BASE + 20;
    // 获取未读的公告数以及问卷调查数
    public static final int TRANSACTION_GET_GRID_COUNT = TRANSACTION_TYPE_DERRICK_BASE + 21;
    // 获取Banner
    public static final int TRANSACTION_GET_BANNERS = TRANSACTION_TYPE_DERRICK_BASE + 22;
    // 上班签到
    public static final int TRANSACTION_SIGN_ON = TRANSACTION_TYPE_DERRICK_BASE + 23;
    // 下班签到
    public static final int TRANSACTION_SIGN_OFF = TRANSACTION_TYPE_DERRICK_BASE + 24;
    // 获取老黄历
    public static final int TRANSACTION_GET_ALMANAC = TRANSACTION_TYPE_DERRICK_BASE + 25;
    // 获取服务器时间
    public static final int TRANSACTION_GET_SERVER_TIME = TRANSACTION_TYPE_DERRICK_BASE + 26;
    // 文件上传
    public static final int TRANSACTION_FILE_UPLOAD = TRANSACTION_TYPE_DERRICK_BASE + 27;

    /**
     * v1.2接口
     */
    // 开门抢红包
    public static final int TRANSACTION_GRAB_RED = TRANSACTION_TYPE_DERRICK_BASE + 28;

    /**
     * v1.3接口
     */
    // 获取用户的IM登录账号
    public static final int TRANSACTION_GET_IM_ACCOUNT = TRANSACTION_TYPE_DERRICK_BASE + 29;
    // 获取好友列表
    public static final int TRANSACTION_GET_FRIENDS = TRANSACTION_TYPE_DERRICK_BASE + 30;
    // 搜索好友
    public static final int TRANSACTION_SEARCH_USER = TRANSACTION_TYPE_DERRICK_BASE + 31;
    // 申请添加好友
    public static final int TRANSACTION_ADD_FRIEND = TRANSACTION_TYPE_DERRICK_BASE + 32;
    // 同意添加好友申请
    public static final int TRANSACTION_ACCEPT_INVITATION = TRANSACTION_TYPE_DERRICK_BASE + 33;
    // 删除好友
    public static final int TRANSACTION_REMOVE_FRIEND = TRANSACTION_TYPE_DERRICK_BASE + 34;
    // 获取用户详细信息
    public static final int TRANSACTION_GET_USERS_DETAIL = TRANSACTION_TYPE_DERRICK_BASE + 35;
    // 举报
    public static final int TRANSACTION_COMPLAIN = TRANSACTION_TYPE_DERRICK_BASE + 36;
    // 获取我的钥匙列表
    public static final int TRANSACTION_GET_MY_KEYS = TRANSACTION_TYPE_DERRICK_BASE + 37;
    // 获取我借入的钥匙列表
    public static final int TRANSACTION_GET_MY_BORROW_KEYS = TRANSACTION_TYPE_DERRICK_BASE + 38;
    // 获取我的授权记录列表
    public static final int TRANSACTION_GET_MY_AUTH_KEYS = TRANSACTION_TYPE_DERRICK_BASE + 39;
    // 获取是否家庭用户
    public static final int TRANSACTION_GET_FAMILY_ADDRESS = TRANSACTION_TYPE_DERRICK_BASE + 40;
    // 获取是否注册用户
    public static final int TRANSACTION_GET_IS_USER_REG = TRANSACTION_TYPE_DERRICK_BASE + 41;
    // 获取考勤记录
    public static final int TRANSACTION_GET_OFFICE_SIGNS = TRANSACTION_TYPE_DERRICK_BASE + 42;


    // <---------- 各类网络请求协议段 ---------->


    private final static Gson gson = new GsonBuilder().registerTypeAdapter(
            JsonElement.class, new JsonElementJsonDeserializer()).create();

    protected CloudoorBaseTransaction(int type) {
        super(type);
    }

    @Override
    protected final void onTransactionSuccess(Object data) {
        onCloudoorTransactionSuccess(data);
    }

    @Override
    protected final void onTransactionError(int errCode, Object data) {
        notifyError(errCode, data);
    }

    // 所有子类必须重载此函数
    protected abstract void onCloudoorTransactionSuccess(Object data);

    // 数据解析错误
    protected void notifyDataParseError() {
        notifyError(TransTypeCode.ERR_DATA_PARSE_EXCEPTION,
                TransTypeCode.ERR_DATA_PARSE_EXCEPTION);
    }

    @Override
    public NotifyTransaction createNotifyTransaction(int notifyType,
                                                     int code, Object data) {
        return new CloudoorNotifyTransaction(this, notifyType, code, data);
    }

    private static class JsonElementJsonDeserializer implements
            JsonDeserializer<JsonElement> {
        @Override
        public JsonElement deserialize(JsonElement json, Type typeOfT,
                                       JsonDeserializationContext context) throws JsonParseException {
            return json;
        }
    }

    protected class CloudoorNotifyTransaction extends NotifyTransaction {

        public CloudoorNotifyTransaction(AsyncTransaction trans, int type, int code, Object data) {
            super(trans, type, code, data);
        }

        @Override
        public void doBeforeTransact() {
            if (isSuccessNotify()) {
                Object data = getData();
                if (data != null) {
                    try {
                        do {
                            BaseData base;

                            Type type = new TypeToken<BaseData>() {
                            }.getType();

                            if (data instanceof String) {
                                base = gson.fromJson((String) data, type);
                            } else {
                                setNotifyTypeAndCode(NOTIFY_TYPE_ERROR,
                                        TransTypeCode.ERR_INTERNAL_DATA_UNKNOWN);
                                break;
                            }

                            int code = base.getCode();
                            if (code == CloudoorServiceCode.SUCCESS) {
                                // 后端返回码为: 1
                                resetData(base.getData());
                                // 如果返回的sid不为空，则保存
                                if (!TextUtils.isEmpty(base.getSid())) {
                                    CloudoorPreHelper.putUserSid(base.getSid());
                                }
                            } else {
                                // 后端返回码不为1
                                resetData(null);
                                setNotifyTypeAndCode(NOTIFY_TYPE_ERROR,
                                        base.getCode());
                            }

                        } while (false);
                    } catch (Exception e) {
                        setNotifyTypeAndCode(NOTIFY_TYPE_ERROR,
                                TransTypeCode.ERR_DATA_PARSE_EXCEPTION);
                    }
                } else {
                    setNotifyTypeAndCode(NOTIFY_TYPE_ERROR,
                            TransTypeCode.ERR_INTERNAL_DATA_UNKNOWN);
                }
            }
        }
    }

    /**
     * 通过模块名 & 具体请求名获取HTTP请求的url
     *
     * @param module   模块名
     * @param specific 接口名
     * @return url
     */
    protected String getRequestUrl(String module, String specific) {
        return SERVER_DOMAIN + module + specific;
    }

    @Override
    public void onTransact() {
        THttpRequest request = createRequest();
        sendRequest(request);
    }

    /**
     * 创建HTTP请求
     * @return THttpRequest
     */
    public abstract THttpRequest createRequest();
}