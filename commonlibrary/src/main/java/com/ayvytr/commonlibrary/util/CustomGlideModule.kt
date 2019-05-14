package com.ayvytr.commonlibrary.util

import android.content.Context

import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class CustomGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //        设置缓存大小为20mb
        val memoryCacheSizeBytes = 1024 * 1024 * 30 // 20mb
        //        设置内存缓存大小
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
        //        根据SD卡是否可用选择是在内部缓存还是SD卡缓存
    }
}