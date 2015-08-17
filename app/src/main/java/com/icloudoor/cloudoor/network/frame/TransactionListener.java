package com.icloudoor.cloudoor.network.frame;

/**
 * Created by Derrick Guan on 7/21/15.
 */
public interface TransactionListener {

    /**
     * 事务成功回调接口
     *
     * @param type
     * @param tid
     * @param data
     */
    public void onTransactionSuccess(int type, int tid, Object data);

    /**
     * 事务失败回调接口
     *
     * @param errCode
     * @param type
     * @param tid
     * @param data
     */
    public void onTransactionError(int errCode, int type, int tid, Object data);

}
