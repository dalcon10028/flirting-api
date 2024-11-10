package site.ymango.code

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import site.ymango.code.model.CodeModel
import site.ymango.code.repository.CodeRepository
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode

@SpringBootTest
class CodeServiceTest(
    private val codeService: CodeService,
    private val codeRepository: CodeRepository,
) : BehaviorSpec({
  beforeContainer {
    codeRepository.deleteAll()
  }

  given("테스트 Code가 등록되어있을 때") {
    `when`("mainCode와 code가 같은 Code를 등록하면") {
      then("BaseException이 발생한다") {
          codeService.createCode(
              CodeModel(
                  mainCode = "TEST",
                  parentCode = "TEST",
                  code = "TEST",
                  name = "TEST",
              )
          )
        val shouldThrow = shouldThrow<BaseException> {
         codeService.createCode(
          CodeModel(
           mainCode = "TEST",
           parentCode = "TEST",
           code = "TEST",
           name = "TEST",
          )
         )
        }
        shouldThrow.code shouldBe ErrorCode.DUPLICATE_RESOURCE
        shouldThrow.message shouldBe "Code already exists"

      }
    }
  }
})
