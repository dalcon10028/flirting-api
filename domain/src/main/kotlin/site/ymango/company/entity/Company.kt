package site.ymango.company.entity

import jakarta.persistence.*
import site.ymango.common.AuditEntity
import java.time.LocalDateTime

@Entity
@Table(name = "company", catalog = "flirting", indexes = [
    Index(name = "idx_company_name", columnList = "name")
])
class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    val companyId: Int? = null,

    @Column(name = "name")
    var name: String,

    @Column(name = "domain", unique = true)
    var domain: String,

    @Column(name = "icon_url")
    var iconUrl: String? = null,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,
): AuditEntity()