package com.app.videopreloading

import android.app.Application
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache

class MyApp : Application() {

    companion object {
        lateinit var simpleCache: SimpleCache
        lateinit var leastRecentlyUsedCacheEvictor: LeastRecentlyUsedCacheEvictor
        lateinit var exoDatabaseProvider: ExoDatabaseProvider
        lateinit var cacheDataSourceFactory: CacheDataSource.Factory
        const val exoPlayerCacheSize: Long = 100 * 1024 * 1024 // cache 100 MB
    }

    override fun onCreate() {
        super.onCreate()
        leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(exoPlayerCacheSize)
        exoDatabaseProvider = ExoDatabaseProvider(this)
        simpleCache = SimpleCache(cacheDir, leastRecentlyUsedCacheEvictor, exoDatabaseProvider)

        cacheDataSourceFactory = CacheDataSource.Factory()
        val dataSource = DefaultHttpDataSource.Factory()
        dataSource.setUserAgent("ExoPlayer")
        cacheDataSourceFactory.setUpstreamDataSourceFactory(dataSource)
        cacheDataSourceFactory.setCache(simpleCache)
        cacheDataSourceFactory.setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }
}