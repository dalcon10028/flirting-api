package site.ymango.point.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.ymango.point.entity.PointWalletHistory

interface DelegatingPointWalletHistoryRepository : JpaRepository<PointWalletHistory, Long>