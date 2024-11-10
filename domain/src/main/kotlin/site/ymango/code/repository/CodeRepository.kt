package site.ymango.code.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.ymango.code.entity.Code

interface CodeRepository : JpaRepository<Code, Int> {
    fun existsByMainCodeAndCode(mainCode: String, code: String): Boolean

    fun findByMainCode(mainCode: String): List<Code>
}