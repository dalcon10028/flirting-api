package site.ymango.point

import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.point.repository.*
import site.ymango.point.entity.*
import site.ymango.point.model.*
import site.ymango.point.enums.*

@Service
class PointService(
    private val pointHistoryRepository: PointWalletHistoryRepository,
    private val pointWalletRepository: PointWalletRepository,
    private val pointRepository: PointRepository,
) {

    /**
     * 포인트 정책 목록
     */
    @Transactional(readOnly = true)
    fun getPointPolicies(): List<PointPolicyModel> = pointRepository.findAll()
        .map { PointPolicyModel.from(it) }

    @Transactional(readOnly = true)
    fun getPointPolicy(pointType: PointType): PointPolicyModel = pointRepository.findByPointType(pointType)
        ?.let { PointPolicyModel.from(it) } ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "포인트 정책이 존재하지 않습니다.")

    /**
     * 포인트 지갑 생성
     */
    @Transactional
    fun createPointWallet(userId: Long) {
        pointWalletRepository.existsByUserId(userId)
            .takeIf { it }
            ?.let { throw BaseException(ErrorCode.DUPLICATE_RESOURCE, "포인트 지갑이 이미 존재합니다.") }
        pointWalletRepository.save(PointWallet(userId = userId))
    }

    /**
     * 포인트 지갑 조회
     */
    @Transactional(readOnly = true)
    fun getPointWallet(userId: Long): PointWalletModel = pointWalletRepository.findByUserId(userId)
        ?.let { PointWalletModel.from(it) } ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "포인트 지갑이 존재하지 않습니다.")


    /**
     * 포인트 처리(적립, 사용)
     */
    @Transactional
    fun applyPoint(userId: Long, pointType: PointType, amount: Int? = null): PointWalletModel {
        // 포인트 정책 조회
        val pointPolicy = pointRepository.findByPointType(pointType)
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "포인트 정책이 존재하지 않습니다.")

        // 포인트 지갑 조회
        val pointWallet = pointWalletRepository.findByUserId(userId)
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "포인트 지갑이 존재하지 않습니다.")

        // 포인트 사용
        val balance = pointWallet.applyPoint(amount ?: pointPolicy.defaultAmount)

        assert(pointPolicy.pointId != null) { "포인트 정책의 포인트 아이디가 존재하지 않습니다." }
        assert(pointWallet.pointWalletId != null) { "포인트 지갑의 포인트 지갑 아이디가 존재하지 않습니다." }

        // 포인트 이력 생성
        pointHistoryRepository.save(
            PointWalletHistory(
                userId = userId,
                pointWalletId = pointWallet.pointWalletId!!,
                amount = amount ?: pointPolicy.defaultAmount,
                balance = balance,
                pointId = pointPolicy.pointId!!,
                summary = pointPolicy.summary,
            )
        )

        return pointWalletRepository.save(pointWallet).let { PointWalletModel.from(it) }
    }

    /**
     * 포인트 이력 조회 페이징
     */
    @Transactional(readOnly = true)
    fun getPointHistory(userId: Long, cursor: Int): Slice<PointWalletHistoryModel> =
        getPointWallet(userId).let { pointWalletModel ->
            pointHistoryRepository.findByUserIdAndPointWalletId(userId, pointWalletModel.pointWalletId, cursor)
                .map { PointWalletHistoryModel.from(it) }
        }
}