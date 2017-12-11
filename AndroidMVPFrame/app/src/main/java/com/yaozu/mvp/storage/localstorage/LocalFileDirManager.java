package com.yaozu.mvp.storage.localstorage;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.yaozu.mvp.storage.sharePref.FileUtil;

import java.io.File;

/**
 * <管理本地文件目录>
 * 对本地存储路径做管理，本地存储分为外部存储（SD卡）和内部存储，
 * 项目以外部存储优先，如果没有外部存储，则使用内部存储\
 *
 * @author shiyaozu
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]
 * @since [V1]
 */
public class LocalFileDirManager {

    /**
     * app的工作目录
     */
    private static String FILE_APP_NAME = "inner";

    /**
     * 列表页面图片缓存目录
     */
    private static String FOLDER_NAME_IMAGE = "image";

    /**
     * 版本更新的目录
     */
    private static String FOLDER_NAME_VERSION_UPDATE = "update";

    /**
     * 日志目录
     */
    private static String FOLDER_NAME_LOG = "log";
    /**
     * 视频
     */
    private static String FOLDER_NAME_VIDEO = "video";
    /**
     * 语音
     */
    private static String FOLDER_NAME_VOICE = "voice";
    /**
     * diskLruCache 目录
     */
    private static String FOLDER_DISK_LRU_CACHE = "lrucache";
    /**
     * 系统文件夹dcim
     */
    public static String SYS_DIR_DCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/";
    /**
     * 系统文件夹pictures
     */
    public static String SYS_DIR_PIC = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/";
    /**
     * 系统文件夹downloads
     */
    public static String SYS_DIR_DOWNLOADS = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/";
    /**
     * 系统文件夹documents
     */
    public static String SYS_DIR_DOCUMENTS = null;

    private Context mContext;

    private static LocalFileDirManager instance;

    /**
     * 单例，供非主进程使用
     *
     * @return
     */
    public static LocalFileDirManager getInstance() {
        if (null == instance) {
            instance = new LocalFileDirManager();
        }
        return instance;
    }

    public void initOnApplicationCreate(Context context) {
        mContext = context;
        createFilePaths();
    }

    /**
     * 新建文件目录
     */
    private void createFilePaths() {
        //创建图片目录
        getCacheImgFilePath();
        //版本更新目录
        getVersionUpdatePath();
        //日志目录
        getVersionLogPath();
        //语音目录
        getVoicePath();
        //diskLrucache 内部目录
        getInterDiskCachePath();
        //diskLrucache sd卡目录
        getExtraDiskCachePath();
        //系统文件夹Documents
        getSystemDirDocuments();
    }

    /**
     * <根目录缓存目录>
     * <功能详细描述>
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String getAppWorkFilePath() {
        return FileUtil.getAppWorkPath(mContext) + File.separator + FILE_APP_NAME + File.separator;
    }


    /**
     * <图片缓存目录>
     * <功能详细描述>
     *
     * @return
     */
    public String getCacheImgFilePath() {
        return FileUtil.createNewFile(getAppWorkFilePath() + FOLDER_NAME_IMAGE + File.separator);
    }


    /**
     * <用户头像目录>
     * <功能详细描述>
     *
     * @param userId
     * @return
     */
    public String getUserHeadPath(String userId) {
        return FileUtil.createNewFile(getAppWorkFilePath() + FOLDER_NAME_IMAGE + File.separator + userId + File.separator);
    }


    /**
     * <版本更新目录>
     * <功能详细描述>
     *
     * @return
     */
    public String getVersionUpdatePath() {
        return FileUtil.createNewFile(getAppWorkFilePath() + FOLDER_NAME_VERSION_UPDATE + File.separator);
    }

    /**
     * <日志目录>
     * <功能详细描述>
     *
     * @return
     */
    public String getVersionLogPath() {
        return FileUtil.createNewFile(getAppWorkFilePath() + FOLDER_NAME_LOG + File.separator);
    }

    /**
     * <视频目录>
     * <功能详细描述>
     *
     * @return
     */
    public String getVedioPath() {
        return FileUtil.createNewFile(getAppWorkFilePath() + FOLDER_NAME_VIDEO + File.separator);
    }

    /**
     * <语音目录>
     * <功能详细描述>
     *
     * @return
     */
    public String getVoicePath() {
        return FileUtil.createNewFile(getAppWorkFilePath() + FOLDER_NAME_VOICE + File.separator);
    }


    /**
     * <内部DiskLrucache目录>
     *
     * @return
     */
    public String getInterDiskCachePath() {
        return FileUtil.createNewFile(FileUtil.getAppInterPath(mContext) + File.separator + FOLDER_DISK_LRU_CACHE + File.separator);
    }

    /**
     * <SD DiskLrucache目录>
     *
     * @return
     */
    public String getExtraDiskCachePath() {
        return FileUtil.createNewFile(getAppWorkFilePath() + FOLDER_DISK_LRU_CACHE + File.separator);
    }

    public void clearOnApplicationQuit() {

    }

    /**
     * 获得系统文献文件夹
     */
    public void getSystemDirDocuments() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SYS_DIR_DOCUMENTS = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/";
        } else {
            SYS_DIR_DOCUMENTS = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/";
        }

    }
}