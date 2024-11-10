package site.ymango.code.model

import site.ymango.code.entity.Code

data class CodeModel(
    val mainCode: String,
    val parentCode: String,
    val code: String,
    val name: String,
    val sort: Int = 0,
) {
    fun toEntity(): Code = Code(
        mainCode = mainCode,
        parentCode = parentCode,
        code = code,
        name = name,
        sort = sort,
    )

    companion object {
        fun of(code: Code): CodeModel = CodeModel(
            mainCode = code.mainCode,
            parentCode = code.parentCode,
            code = code.code,
            name = code.name,
            sort = code.sort,
        )
    }
}
