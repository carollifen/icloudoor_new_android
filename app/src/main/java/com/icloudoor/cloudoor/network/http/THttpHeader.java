package com.icloudoor.cloudoor.network.http;

import org.apache.http.NameValuePair;

/**
 * Created by Derrick Guan on 7/22/15.
 */

public class THttpHeader implements NameValuePair, Comparable<THttpHeader> {

    private String mKey;
    private String mValue;


    public THttpHeader(String key) {
        mKey = key;
    }

    public THttpHeader(String key, String value) {
        mKey = key;
        mValue = value;
    }

    public String getKey() {
        return mKey;
    }

    @Override
    public String getName() {
        return mKey;
    }

    @Override
    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    @Override
    public int compareTo(THttpHeader another) {
        int ret = 0;
        ret = mKey.compareTo(another.mKey);
        return ret;
    }

}
