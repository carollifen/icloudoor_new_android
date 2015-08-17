package com.icloudoor.cloudoor.network.frame;

/**
 * 网络层各类返回码
 * Created by Derrick Guan on 7/21/15.
 */
public interface TransTypeCode {


    // HTTP 返回成功: 200
    public static final int HTTP_SUCCESS = 200;

    // HTTP 错误
    public static final int ERR_HTTP = -0xE001;

    // HTTP 取消
    public static final int ERR_HTTP_CANCEL = -0xE002;

    // HTTP 异常
    public static final int ERR_NETWORK_IO_EXCEPTION = -0xE003; // 网络IO异常
    public static final int ERR_NETWORK_EXCEPTION = -0xE004; // 网络异常


    // HTTP 成功，但内部传递数据出错
    public static final int ERR_INTERNAL_DATA_UNKNOWN = -0xE100;

    // 数据解析异常
    public static final int ERR_DATA_PARSE_EXCEPTION = -0xE101;

}
