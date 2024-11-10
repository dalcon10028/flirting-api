package site.ymango.user.enums

enum class Gender {
    MALE, FEMALE
    ;

    fun opposite(): Gender = when (this) {
        MALE -> FEMALE
        FEMALE -> MALE
    }
}