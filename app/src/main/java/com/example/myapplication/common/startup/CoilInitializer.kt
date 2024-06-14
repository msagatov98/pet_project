package com.example.myapplication.common.startup

import android.content.Context
import androidx.startup.Initializer
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache

@Suppress("unused")
class CoilInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val imageLoader = ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()

        Coil.setImageLoader(imageLoader)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}