package site.ymango.purchase.enums

enum class PurchaseState(
    val androidValue: Int?
) {
    ACKNOWLEDGED(0),
    CANCELLED(1),
    PENDING(2)
    ;

    companion object {
        fun fromAndroidValue(value: Int): PurchaseState {
            return when (value) {
                0 -> ACKNOWLEDGED
                1 -> CANCELLED
                2 -> PENDING
                else -> throw IllegalArgumentException("Invalid value $value for PurchaseState")
            }
        }
    }
}