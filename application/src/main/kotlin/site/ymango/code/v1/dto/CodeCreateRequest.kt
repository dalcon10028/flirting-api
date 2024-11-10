package site.ymango.code.v1.dto

import org.hibernate.validator.constraints.Length
import site.ymango.code.model.CodeModel

data class CodeCreateRequest(
    @field:Length(max = 30)
    @field:Length(min = 1)
    val mainCode: String,

    @field:Length(max = 30)
    @field:Length(min = 1)
    val parentCode: String,

    @field:Length(max = 30)
    @field:Length(min = 1)
    val code: String,

    @field:Length(max = 30)
    @field:Length(min = 1)
    val name: String,

    val sort: Int = 0,
) {
    fun toModel() = CodeModel(
        mainCode = mainCode,
        parentCode = parentCode,
        code = code,
        name = name,
        sort = sort,
    )
}