package com.icloudoor.cloudoor.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.icloudoor.cloudoor.database.table.AccountTable;

import java.util.LinkedList;
import java.util.List;

/**
 * 云门数据库管理类，负责创建、删除、更新各类用户表
 * Created by Derrick Guan on 8/16/15.
 */
public class CloudoorDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cloudoor.db";

    private static final String CREATE_TABLE = "CREATE TABLE";
    // int key auto inc
    private static final String INTEGER_KEY_AUTO_INC = "INTEGER PRIMARY KEY AUTOINCREMENT";
    // text
    private static final String TEXT = "TEXT";
    // text not null
    private static final String TEXT_NOT_NULL = "TEXT NOT NULL";
    // REAL
    private static final String REAL = "REAL";
    // integer default 0
    private static final String INT = "INTEGER";
    // long default 0
    private static final String LONG = "INTEGER";


    public CloudoorDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /** 创建AccountTable */
        db.execSQL(getCreateAccountTableSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == newVersion) {
            return;
        }
    }

    /**
     * 创建用户基础资料表SQL
     *
     * @return SQL
     */
    private String getCreateAccountTableSQL() {
        List<KeyValuePair> columns = null;
        columns = new LinkedList<KeyValuePair>();

        columns.add(new KeyValuePair(AccountTable._ID, INTEGER_KEY_AUTO_INC));
        columns.add(new KeyValuePair(AccountTable.C_USER_ID, TEXT_NOT_NULL));
        columns.add(new KeyValuePair(AccountTable.C_USERNAME, TEXT));
        columns.add(new KeyValuePair(AccountTable.C_NICKNAME, TEXT));
        columns.add(new KeyValuePair(AccountTable.C_MOBILE, TEXT));
        columns.add(new KeyValuePair(AccountTable.C_GENDER, INT));
        columns.add(new KeyValuePair(AccountTable.C_ID_CARD, TEXT));
        columns.add(new KeyValuePair(AccountTable.C_BIRTHDAY, TEXT));
        columns.add(new KeyValuePair(AccountTable.C_PROVINCE_ID, INT));
        columns.add(new KeyValuePair(AccountTable.C_CITY_ID, INT));
        columns.add(new KeyValuePair(AccountTable.C_DISTRICT_ID, INT));
        columns.add(new KeyValuePair(AccountTable.C_PORTRAIT_URL, TEXT));
        columns.add(new KeyValuePair(AccountTable.C_USER_STATUS, INT));
        columns.add(new KeyValuePair(AccountTable.C_HAS_PRO_SERV, INT));
        columns.add(new KeyValuePair(AccountTable.C_ROLE, INT));
        columns.add(new KeyValuePair(AccountTable.C_L1ZONES_JSON, TEXT));
        columns.add(new KeyValuePair(AccountTable.C_CHAT_USERNAME, TEXT));
        columns.add(new KeyValuePair(AccountTable.C_CHAT_PASSWORD, TEXT));
        columns.add(new KeyValuePair(AccountTable.C_LAST_LOGIN, INT));

        return createTable(AccountTable.TABLE_NAME,
                columns, "UNIQUE (" + AccountTable.C_USER_ID + ") ON CONFLICT REPLACE");

    }

    /**
     * 通用创建表方法
     *
     * @param tableName 表名
     * @param columns   列名
     * @param desc      description
     * @return SQL
     */
    private String createTable(String tableName, List<KeyValuePair> columns, String desc) {

        StringBuilder builder = new StringBuilder();
        builder.append(CREATE_TABLE).append(' ').append(tableName).append(' ').append('(');

        for (KeyValuePair pair : columns) {
            builder.append(pair.getKey()).append(' ').append(pair.getValue()).append(',');
        }

        if (!TextUtils.isEmpty(desc)) {
            // unique or index
            builder.append(desc);
        } else {
            // delete last char ','
            builder.deleteCharAt(builder.length() - 1);
        }

        builder.append(')');

        return builder.toString();
    }
}
