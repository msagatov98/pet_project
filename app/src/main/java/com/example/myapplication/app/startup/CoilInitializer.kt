package com.example.myapplication.app.startup

import android.content.Context
import androidx.startup.Initializer
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache

private const val MEMORY_CACHE_MAX_SIZE_PERCENT = 0.25
private const val DISK_CACHE_MAX_SIZE_PERCENT = 0.02
private const val DISK_CACHE_DIRECTORY = "image_cache"

@Suppress("unused")
class CoilInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val imageLoader = ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(MEMORY_CACHE_MAX_SIZE_PERCENT)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve(DISK_CACHE_DIRECTORY))
                    .maxSizePercent(DISK_CACHE_MAX_SIZE_PERCENT)
                    .build()
            }
            .build()

        Coil.setImageLoader(imageLoader)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}