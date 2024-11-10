package site.ymango.config.cache

enum class CacheType(
    val cacheName: String,
    val secsToExpireAfterWrite: Long,
    val entryLimit: Long
) {
    EMAIL_VERIFICATION("emailVerification", 60 * 30, 1000), // 30분 후 만료
    PHONE_VERIFICATION("phoneVerification", 60 * 30, 1000) // 30분 후 만료
    ;
}