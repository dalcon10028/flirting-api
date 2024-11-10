package site.ymango.verification.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import site.ymango.verification.entity.QVerification.verification
import site.ymango.verification.entity.Verification
import site.ymango.verification.enums.ReceptionType
import java.time.LocalDateTime

@Repository
class VerificationRepository (
    private val jpaQueryFactory: JPAQueryFactory,
    private val verificationDelegatingRepository: VerificationDelegatingRepository
) : VerificationDelegatingRepository by verificationDelegatingRepository {

    fun findVerification(receiver: String, deviceId: String, receptionType: ReceptionType): Verification? =
        jpaQueryFactory.selectFrom(verification)
            .where(verification.receiver.eq(receiver))
            .where(verification.deviceId.eq(deviceId))
            .where(verification.receptionType.eq(receptionType))
            .where(verification.verified.eq(false))
            .where(verification.expiredAt.after(LocalDateTime.now()))
            .orderBy(verification.verificationId.desc())
            .fetchFirst()

    fun existsVerified(receiver: String, deviceId: String, receptionType: ReceptionType, minute: Long): Boolean =
        jpaQueryFactory.selectFrom(verification)
            .where(verification.receiver.eq(receiver))
            .where(verification.deviceId.eq(deviceId))
            .where(verification.receptionType.eq(receptionType))
            .where(verification.verified.eq(true))
            .where(verification.createdAt.after(LocalDateTime.now().minusMinutes(minute)))
            .fetchFirst() != null
}