package com.icloudoor.cloudoor.network.bean;

import com.icloudoor.cloudoor.network.bean.meta.AuthRecordKey;

import java.util.List;

/**
 * 获取我授权的钥匙数据结构
 * Created by Derrick Guan on 8/3/15.
 */
public class GetMyAuthKeysBean {

    private String date;

    private List<Record> records;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    private class Record {

        private String address;
        private List<AuthRecordKey> keys;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<AuthRecordKey> getKeys() {
            return keys;
        }

        public void setKeys(List<AuthRecordKey> keys) {
            this.keys = keys;
        }
    }

}
