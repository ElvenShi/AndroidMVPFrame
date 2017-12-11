package com.yaozu.mvp.storage.localstorage;


/**
 * <文件类型后缀及文件存储路径>
 *
 * @author shiyaozu
 * @version [版本号, 2016/6/22]
 * @see [相关类/方法]
 * @since [V1]
 */

public enum FileProperty {
    //安装文件
    APK(".apk") {
        @Override
        public String getPath() {
            return LocalFileDirManager.getInstance().getVersionUpdatePath();
        }
    },
    //图片
    JPG(".jpg") {
        @Override
        public String getPath() {
            return LocalFileDirManager.getInstance().getCacheImgFilePath();
        }
    },


    //图片
    PNG(".png") {
        @Override
        public String getPath() {
            return LocalFileDirManager.getInstance().getCacheImgFilePath();
        }
    },
    //视频
    VEDIO(".mp4") {
        @Override
        public String getPath() {
            return LocalFileDirManager.getInstance().getVedioPath();
        }
    },
    //语音
    VOICE(".amr") {
        @Override
        public String getPath() {
            return LocalFileDirManager.getInstance().getVoicePath();
        }
    };

    /**
     * 文件后缀名
     */
    String suffix;

    FileProperty(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 文件后缀名
     *
     * @return 文件后缀名
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 获取文件目录地址
     *
     * @return 目录地址
     */
    public String getPath() {
        return "";
    }
}
