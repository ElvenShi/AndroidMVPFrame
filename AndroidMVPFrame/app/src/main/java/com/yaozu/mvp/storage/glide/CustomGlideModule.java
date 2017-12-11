package com.yaozu.mvp.storage.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.yaozu.mvp.storage.localstorage.LocalFileDirManager;

import java.io.File;

/**
 * @author : shiyaozu
 * @version : V1.0
 * @date : 2017/5/4
 * @Desc: Glide 配置管理类。需要在AndroidManifest中配置<meta-data/>
 */

public class CustomGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        /**
         * 设置磁盘缓存
         * */
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                // Careful: the external cache directory doesn't enforce permissions
                File cacheLocation = new File(LocalFileDirManager.getInstance().getCacheImgFilePath());
                cacheLocation.mkdirs();

                int diskCacheSize = 200 * 1024 * 1024;//磁盘缓存200MB
                return DiskLruCacheWrapper.get(cacheLocation, diskCacheSize);
            }
        });

        /**
         * 内存缓存大小200MB
         */
        int memoryCacheSize = 100*1024*1024;
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));

        /**
         * 设置BitmapPool缓存内存大小
         */
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        builder.setBitmapPool(new LruBitmapPool((int)(1.2 * defaultBitmapPoolSize)));

        //设置图片解码格式，默认是RGB_565
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}