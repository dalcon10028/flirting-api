package site.ymango.verification.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.ymango.verification.entity.Verification

interface VerificationDelegatingRepository : JpaRepository<Verification, Long>