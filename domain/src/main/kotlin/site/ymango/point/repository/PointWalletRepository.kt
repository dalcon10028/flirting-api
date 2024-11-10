package site.ymango.point.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import site.ymango.point.entity.PointWallet

@Repository
interface PointWalletRepository : JpaRepository<PointWallet, Long> {
    fun findByUserId(userId: Long): PointWallet?

    fun existsByUserId(userId: Long): Boolean
}