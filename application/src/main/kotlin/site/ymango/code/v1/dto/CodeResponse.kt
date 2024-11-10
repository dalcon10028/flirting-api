package site.ymango.code.v1.dto

import site.ymango.code.model.CodeModel

data class CodeResponse(
    val mainCode: String,
    val parentCode: String,
    val code: String,
    val name: String,
    val sort: Int,
) {
    companion object {
        fun of(code: CodeModel): CodeResponse =
            CodeResponse(
                mainCode = code.mainCode,
                parentCode = code.parentCode,
                code = code.code,
                name = code.name,
                sort = code.sort,
            )
    }
}

