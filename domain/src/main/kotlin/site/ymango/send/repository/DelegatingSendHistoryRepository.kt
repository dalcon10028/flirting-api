package site.ymango.send.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import site.ymango.send.entity.SendHistory

interface DelegatingSendHistoryRepository : JpaRepository<SendHistory, Long> {
    @Modifying
    @Query("update SendHistory sh set sh.status = 'DELIVERED', sh.response = :response where sh.sendHistoryId = :sendHistoryId")
    fun delivered(sendHistoryId: Long, response: Map<String, Any>) : Int
}