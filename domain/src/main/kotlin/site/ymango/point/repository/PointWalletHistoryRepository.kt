package site.ymango.point.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository
import site.ymango.point.entity.PointWalletHistory
import site.ymango.point.entity.QPointWalletHistory.pointWalletHistory

@Repository
class PointWalletHistoryRepository(
    private val queryFactory: JPAQueryFactory,
    @Qualifier("delegatingPointWalletHistoryRepository") private val repository: DelegatingPointWalletHistoryRepository
) : DelegatingPointWalletHistoryRepository by repository {
    fun findByUserIdAndPointWalletId(userId: Long, walletId: Long, cursor: Int): Slice<PointWalletHistory> {
        queryFactory.selectFrom(pointWalletHistory)
            .where(pointWalletHistory.userId.eq(userId).and(pointWalletHistory.pointWalletId.eq(walletId)))
            .orderBy(pointWalletHistory.pointWalletHistoryId.desc())
            .limit(21)
            .fetch()
            .let {
                val content = it.take(20)
                val hasNext = it.size > 20
                return SliceImpl(content, Pageable.ofSize(20), hasNext)
            }
    }
}