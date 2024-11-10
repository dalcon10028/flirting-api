package site.ymango.send.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import site.ymango.send.entity.QSendHistory.sendHistory
import site.ymango.send.enums.SendStatus

@Repository
class SendHistoryRepository(
    private val queryFactory: JPAQueryFactory,
    private val delegatingSendHistoryRepository: DelegatingSendHistoryRepository
) : DelegatingSendHistoryRepository by delegatingSendHistoryRepository {

}