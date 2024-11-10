package site.ymango.user.enums

enum class PreferredMBTI {
    ESTJ, ESTP, ESTX, ESFJ, ESFP, ESFX, ESXJ, ESXP, ESXX,
    ENTJ, ENTP, ENTX, ENFJ, ENFP, ENFX, ENXJ, ENXP, ENXX,
    EXTJ, EXTP, EXTX, EXFJ, EXFP, EXFX, EXXJ, EXXP, EXXX,
    ISTJ, ISTP, ISTX, ISFJ, ISFP, ISFX, ISXJ, ISXP, ISXX,
    INTJ, INTP, INTX, INFJ, INFP, INFX, INXJ, INXP, INXX,
    IXTJ, IXTP, IXTX, IXFJ, IXFP, IXFX, IXXJ, IXXP, IXXX,
    XSTJ, XSTP, XSTX, XSFJ, XSFP, XSFX, XSXJ, XSXP, XSXX,
    XNTJ, XNTP, XNTX, XNFJ, XNFP, XNFX, XNXJ, XNXP, XNXX,
    XXTJ, XXTP, XXTX, XXFJ, XXFP, XXFX, XXXJ, XXXP, XXXX
    ;

    fun getMBTIList(): List<MBTI> {
        return when (this) {
            ESTJ -> listOf(MBTI.ESTJ)
            ESTP -> listOf(MBTI.ESTP)
            ESTX -> listOf(MBTI.ESTJ, MBTI.ESTP)
            ESFJ -> listOf(MBTI.ESFJ)
            ESFP -> listOf(MBTI.ESFP)
            ESFX -> listOf(MBTI.ESFJ, MBTI.ESFP)
            ESXJ -> listOf(MBTI.ESFJ, MBTI.ESTJ)
            ESXP -> listOf(MBTI.ESFP, MBTI.ESTP)
            ESXX -> listOf(MBTI.ESFJ, MBTI.ESFP, MBTI.ESTJ, MBTI.ESTP)
            ENTJ -> listOf(MBTI.ENTJ)
            ENTP -> listOf(MBTI.ENTP)
            ENTX -> listOf(MBTI.ENTJ, MBTI.ENTP)
            ENFJ -> listOf(MBTI.ENFJ)
            ENFP -> listOf(MBTI.ENFP)
            ENFX -> listOf(MBTI.ENFJ, MBTI.ENFP)
            ENXJ -> listOf(MBTI.ENFJ, MBTI.ENTJ)
            ENXP -> listOf(MBTI.ENFP, MBTI.ENTP)
            ENXX -> listOf(MBTI.ENFJ, MBTI.ENFP, MBTI.ENTJ, MBTI.ENTP)
            EXTJ -> listOf(MBTI.ENTJ, MBTI.ESTJ)
            EXTP -> listOf(MBTI.ENTP, MBTI.ESTP)
            EXTX -> listOf(MBTI.ENTJ, MBTI.ENTP, MBTI.ESTJ, MBTI.ESTP)
            EXFJ -> listOf(MBTI.ENFJ, MBTI.ESFJ)
            EXFP -> listOf(MBTI.ENFP, MBTI.ESFP)
            EXFX -> listOf(MBTI.ENFJ, MBTI.ENFP, MBTI.ESFJ, MBTI.ESFP)
            EXXJ -> listOf(MBTI.ENFJ, MBTI.ENFP, MBTI.ESFJ, MBTI.ESFP, MBTI.ENTJ, MBTI.ESTJ)
            EXXP -> listOf(MBTI.ENFP, MBTI.ENTP, MBTI.ESFP, MBTI.ESTP)
            EXXX -> listOf(MBTI.ENFJ, MBTI.ENFP, MBTI.ESFJ, MBTI.ESFP, MBTI.ENTJ, MBTI.ENTP, MBTI.ESTJ, MBTI.ESTP)
            ISTJ -> listOf(MBTI.ISTJ)
            ISTP -> listOf(MBTI.ISTP)
            ISTX -> listOf(MBTI.ISTJ, MBTI.ISTP)
            ISFJ -> listOf(MBTI.ISFJ)
            ISFP -> listOf(MBTI.ISFP)
            ISFX -> listOf(MBTI.ISFJ, MBTI.ISFP)
            ISXJ -> listOf(MBTI.ISFJ, MBTI.ISTJ)
            ISXP -> listOf(MBTI.ISFP, MBTI.ISTP)
            ISXX -> listOf(MBTI.ISFJ, MBTI.ISFP, MBTI.ISTJ, MBTI.ISTP)
            INTJ -> listOf(MBTI.INTJ)
            INTP -> listOf(MBTI.INTP)
            INTX -> listOf(MBTI.INTJ, MBTI.INTP)
            INFJ -> listOf(MBTI.INFJ)
            INFP -> listOf(MBTI.INFP)
            INFX -> listOf(MBTI.INFJ, MBTI.INFP)
            INXJ -> listOf(MBTI.INFJ, MBTI.INTJ)
            INXP -> listOf(MBTI.INFP, MBTI.INTP)
            INXX -> listOf(MBTI.INFJ, MBTI.INFP, MBTI.INTJ, MBTI.INTP)
            IXTJ -> listOf(MBTI.INTJ, MBTI.ISTJ)
            IXTP -> listOf(MBTI.INTP, MBTI.ISTP)
            IXTX -> listOf(MBTI.INTJ, MBTI.INTP, MBTI.ISTJ, MBTI.ISTP)
            IXFJ -> listOf(MBTI.INFJ, MBTI.ISFJ)
            IXFP -> listOf(MBTI.INFP, MBTI.ISFP)
            IXFX -> listOf(MBTI.INFJ, MBTI.INFP, MBTI.ISFJ, MBTI.ISFP)
            IXXJ -> listOf(MBTI.INFJ, MBTI.INFP, MBTI.ISFJ, MBTI.ISFP, MBTI.INTJ, MBTI.ISTJ)
            IXXP -> listOf(MBTI.INFP, MBTI.INTP, MBTI.ISFP, MBTI.ISTP)
            IXXX -> listOf(MBTI.INFJ, MBTI.INFP, MBTI.ISFJ, MBTI.ISFP, MBTI.INTJ, MBTI.INTP, MBTI.ISTJ, MBTI.ISTP)
            XSTJ -> listOf(MBTI.ISTJ, MBTI.ESTJ)
            XSTP -> listOf(MBTI.ISTP, MBTI.ESTP)
            XSTX -> listOf(MBTI.ISTJ, MBTI.ISTP, MBTI.ESTJ, MBTI.ESTP)
            XSFJ -> listOf(MBTI.ISFJ, MBTI.ESFJ)
            XSFP -> listOf(MBTI.ISFP, MBTI.ESFP)
            XSFX -> listOf(MBTI.ISFJ, MBTI.ISFP, MBTI.ESFJ, MBTI.ESFP)
            XSXJ -> listOf(MBTI.ISFJ, MBTI.ISFP, MBTI.ISTJ, MBTI.ESTJ)
            XSXP -> listOf(MBTI.ISFP, MBTI.ISTP, MBTI.ESFP, MBTI.ESTP)
            XSXX -> listOf(MBTI.ISFJ, MBTI.ISFP, MBTI.ISTJ, MBTI.ISTP, MBTI.ESFJ, MBTI.ESFP, MBTI.ESTJ, MBTI.ESTP)
            XNTJ -> listOf(MBTI.INTJ, MBTI.ENTJ)
            XNTP -> listOf(MBTI.INTP, MBTI.ENTP)
            XNTX -> listOf(MBTI.INTJ, MBTI.INTP, MBTI.ENTJ, MBTI.ENTP)
            XNFJ -> listOf(MBTI.INFJ, MBTI.ENFJ)
            XNFP -> listOf(MBTI.INFP, MBTI.ENFP)
            XNFX -> listOf(MBTI.INFJ, MBTI.INFP, MBTI.ENFJ, MBTI.ENFP)
            XNXJ -> listOf(MBTI.INFJ, MBTI.INFP, MBTI.ENFJ, MBTI.ENFP, MBTI.INTJ, MBTI.ENTJ)
            XNXP -> listOf(MBTI.INFP, MBTI.ENFP, MBTI.INTP, MBTI.ENTP)
            XNXX -> listOf(MBTI.INFJ, MBTI.INFP, MBTI.ENFJ, MBTI.ENFP, MBTI.INTJ, MBTI.INTP, MBTI.ENTJ, MBTI.ENTP)
            XXTJ -> listOf(MBTI.INTJ, MBTI.ENTJ, MBTI.ISTJ, MBTI.ESTJ)
            XXTP -> listOf(MBTI.INTP, MBTI.ENTP, MBTI.ISTP, MBTI.ESTP)
            XXTX -> listOf(MBTI.INTJ, MBTI.INTP, MBTI.ENTJ, MBTI.ENTP, MBTI.ISTJ, MBTI.ISTP, MBTI.ESTJ, MBTI.ESTP)
            XXFJ -> listOf(MBTI.INFJ, MBTI.ENFJ, MBTI.ISFJ, MBTI.ESFJ)
            XXFP -> listOf(MBTI.INFP, MBTI.ENFP, MBTI.ISFP, MBTI.ESFP)
            XXFX -> listOf(MBTI.INFJ, MBTI.INFP, MBTI.ENFJ, MBTI.ENFP, MBTI.ISFJ, MBTI.ISFP, MBTI.ESFJ, MBTI.ESFP)
            XXXJ -> listOf(
                MBTI.INFJ,
                MBTI.INFP,
                MBTI.ENFJ,
                MBTI.ENFP,
                MBTI.ISFJ,
                MBTI.ISFP,
                MBTI.ESFJ,
                MBTI.ESFP,
                MBTI.INTJ,
                MBTI.ENTJ,
                MBTI.ISTJ,
                MBTI.ESTJ
            )

            XXXP -> listOf(MBTI.INFP, MBTI.ENFP, MBTI.INTP, MBTI.ENTP, MBTI.ISFP, MBTI.ESFP, MBTI.ISTP, MBTI.ESTP)
            XXXX -> listOf(
                MBTI.INFJ,
                MBTI.INFP,
                MBTI.ENFJ,
                MBTI.ENFP,
                MBTI.ISFJ,
                MBTI.ISFP,
                MBTI.ESFJ,
                MBTI.ESFP,
                MBTI.INTJ,
                MBTI.INTP,
                MBTI.ENTJ,
                MBTI.ENTP,
                MBTI.ISTJ,
                MBTI.ISTP,
                MBTI.ESTJ,
                MBTI.ESTP
            )
        }
    }
}