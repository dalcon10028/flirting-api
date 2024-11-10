package site.ymango.config.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit


@Configuration
class LocalCacheConfig {
    @Bean
    fun cacheManager(): CacheManager {
        val caches = CacheType.entries.map { cacheType ->
            CaffeineCache(
                cacheType.cacheName,
                Caffeine.newBuilder()
                    .expireAfterWrite(cacheType.secsToExpireAfterWrite, TimeUnit.SECONDS)
                    .maximumSize(cacheType.entryLimit)
                    .build()
            )
        }.toMutableList()

        return SimpleCacheManager().apply {
            setCaches(caches.toList())
        }
    }
}