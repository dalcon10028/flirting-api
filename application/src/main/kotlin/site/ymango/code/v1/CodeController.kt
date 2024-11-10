package site.ymango.code.v1

import org.springframework.web.bind.annotation.*
import site.ymango.code.CodeService
import site.ymango.code.v1.dto.CodeCreateRequest
import site.ymango.code.v1.dto.CodeResponse

@RestController
@RequestMapping("/v1/codes")
class CodeController(
    private val codeService: CodeService
) {
    @GetMapping
    fun getCodes(): List<CodeResponse> = codeService.getCodes().map { CodeResponse.of(it) }

    @PostMapping
    fun createCode(@RequestBody code: CodeCreateRequest): CodeResponse = CodeResponse.of(codeService.createCode(code.toModel()))
}