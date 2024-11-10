package site.ymango.common

import jakarta.persistence.*
import org.springframework.data.annotation.*
import java.time.LocalDateTime


@MappedSuperclass
abstract class AuditEntity(
    @CreatedDate
    @LastModifiedDate
    @Column(name = "updated_at", nullable = true, updatable = true)
    var updatedAt: LocalDateTime? = null,

    @CreatedBy
    @LastModifiedBy
    @Column(name = "updated_by", nullable = true, updatable = true)
    var updatedBy: String? = null
): BaseEntity()