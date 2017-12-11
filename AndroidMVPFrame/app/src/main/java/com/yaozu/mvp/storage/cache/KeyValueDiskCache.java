package com.yaozu.mvp.storage.cache;


import com.jakewharton.disklrucache.DiskLruCache;
import com.yaozu.mvp.storage.localstorage.LocalFileDirManager;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供针对key-value的磁盘存储
 *
 * @author shiyaozu
 */
public class KeyValueDiskCache {
    private static final String TAG = KeyValueDiskCache.class.getSimpleName();
    private static final long MAX_SIZE = 1024 * 1024 * 50; // 50MB
    private static final Map<CacheType, DiskLruCache> caches = new ConcurrentHashMap<>();

    /**
     * 文件管理类
     */
    private static LocalFileDirManager fileStorageManager = LocalFileDirManager.getInstance();


    private KeyValueDiskCache() {

    }

    /**
     * 重置缓存
     */
    public synchronized static void clear() {
        for (CacheType type : CacheType.values()) {
            get(type, "0");
        }

        Set<CacheType> clearTypes = new HashSet<>();

        for (Map.Entry<CacheType, DiskLruCache> entry : caches.entrySet()) {
            if (entry.getKey().isClearable()) {
                try {
                    entry.getValue().delete();
                    clearTypes.add(entry.getKey());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        for (CacheType type : clearTypes) {
            caches.remove(type);
        }
    }

    public synchronized static void set(CacheType type, String key, String value) {
        DiskLruCache cache = null;
        DiskLruCache.Editor editor = null;

        try {
            if (!caches.containsKey(type)) {
                caches.put(type, DiskLruCache.open(getCacheDir(type), 1, 1, MAX_SIZE));
            }

            cache = caches.get(type);
            editor = cache.edit(key);

            if (null != editor) {
                cache.remove(key);
                editor.set(0, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != editor) {
                try {
                    editor.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != cache) {
                try {
                    cache.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized static String get(CacheType type, String key) {
        String value = null;

        DiskLruCache cache = null;

        try {
            if (!caches.containsKey(type)) {
                caches.put(type, DiskLruCache.open(getCacheDir(type), 1, 1, MAX_SIZE));
            }

            cache = caches.get(type);
            DiskLruCache.Snapshot snapshot = cache.get(key);

            if (null != snapshot) {
                value = snapshot.getString(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cache) {
                try {
                    cache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return value;
    }


    public synchronized static File getCacheDir(CacheType type) {
        File base;
        if (type.isClearable())
            base = new File(fileStorageManager.getInterDiskCachePath() + type.name().toLowerCase() + File.separator);
        else
            base = new File(fileStorageManager.getExtraDiskCachePath() + type.name().toLowerCase() + File.separator);

        return base;
    }


    public enum CacheType {
        /**
         * 图片
         */
        IMAGES(true),
        /**
         * 通知
         */
        NOTICES(true),
        /**
         * 音频
         */
        AUDIO(true),
        /**
         * 拍照
         */
        CAMERA(true),
        /**
         * 登录信息和引导页
         */
        LOGIN_INFO(false),
        /**
         * crash上报信息
         */
        CRASH(false),
        LOGS(false),
        ZIPS(false),

        /**
         * h5页面草稿
         */
        HTML_DRAFT(true);


        private boolean value;

        CacheType(boolean value) {
            this.value = value;
        }

        public boolean isClearable() {
            return value;
        }
    }
}
