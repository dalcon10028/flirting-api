package site.ymango.code

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.code.entity.Code
import site.ymango.code.model.CodeModel
import site.ymango.code.repository.CodeRepository
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode

@Service
class CodeService(
    private val codeRepository: CodeRepository
) {
    @Transactional(readOnly = true)
    fun getCodes(): List<CodeModel> = codeRepository.findAll().map { CodeModel.of(it) }

    @Transactional(readOnly = true)
    fun getCodes(mainCode: String): List<CodeModel> = codeRepository.findByMainCode(mainCode).map { CodeModel.of(it) }

    @Transactional
    fun createCode(code: CodeModel): CodeModel {
        if (codeRepository.existsByMainCodeAndCode(code.mainCode, code.code)) {
            throw BaseException(ErrorCode.DUPLICATE_RESOURCE, "Code already exists")
        }
        return CodeModel.of(codeRepository.save(code.toEntity()))
    }
}