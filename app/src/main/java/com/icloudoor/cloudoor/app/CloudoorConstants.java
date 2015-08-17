package com.icloudoor.cloudoor.app;

/**
 * Created by Derrick Guan on 7/22/15.
 */
public class CloudoorConstants {

    // 是否debug环境
    public static final boolean DEBUG = true;


    /***** 后端定义常量 ******/

    /**
     * 性别
     */
    public interface Sex {
        // 男性
        public int Male = 1;
        // 女性
        public int Female = 2;
    }

    /**
     * 用户状态
     */
    public interface UserStatus {
        // 游客
        public int Guest = 1;
        // 认证用户
        public int AuthorizedUser = 2;
    }

    /**
     * 当前车状态
     */
    public interface CarPosStatus {
        // 无状态
        public int NoStatus = 0;
        // 车在小区内
        public int Inside = 1;
        // 车在小区外
        public int Outside = 2;
    }

    /**
     * 车状态
     */
    public interface CarStatus {
        // 自己的车
        public int Mine = 1;
        // 借用的车
        public int Borrow = 2;
        // 车已借出中
        public int Rent = 3;
    }

    /**
     * 门类型
     */
    public interface DoorType {
        // 人行门
        public int Pedestrian = 1;
        // 车门
        public int Car = 2;
        // 签到门
        public int Registration = 3;
    }

    /**
     * Banner类型
     */
    public interface BannerType {
        // 公告类banner
        public String Announcement = "1";
        // 链接类banner
        public String Link = "2";
    }

    /**
     * 签到类型
     */
    public interface SignType {
        // 上班
        public String OnDuty = "1";
        // 下班
        public String OffDuty = "2";
    }

    /**
     * 文件上传类型
     */
    public interface UploadFileType {
        // 物业报修
        public String PropertyRepair = "1";
        // 用户头像
        public String UserPortrait = "31";
        // 用户证件
        public String UserCredentials = "51";
    }

    /**
     * 文件上传后缀名
     */
    public interface FileExtension {
        // png
        public String PNG = "png";
        // jpg
        public String JPG = "jpg";
        // jpeg
        public String JPEG = "jpeg";
    }

    /**
     * 举报类型
     */
    public interface ComplainType {
        // 色情低俗
        public int Vulgar = 1;
        // 广告骚扰
        public int Advertisement = 2;
        // 政治敏感
        public int Politics = 3;
        // 谣言
        public int Rumor = 4;
        // 欺诈骗钱
        public int Fraudulence = 5;
        // 违法(暴力、恐怖、违禁品等)
        public int Illegal = 6;
    }

    /**
     * 钥匙使用状态
     */
    public interface UseStatus {
        // 使用中
        public int Using = 1;
        // 已完成
        public int Done = 2;
    }
}
