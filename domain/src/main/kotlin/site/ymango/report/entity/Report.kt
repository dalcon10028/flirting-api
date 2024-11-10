package site.ymango.report.entity

import jakarta.persistence.*
import site.ymango.common.BaseEntity
import site.ymango.report.enums.ReportType
import java.time.LocalDateTime


@Entity
@Table(name = "report", schema = "flirting",
    indexes = [Index(name = "report_reporter_id_reported_id_idx", columnList = "reporter_id, reported_id")],
    uniqueConstraints = [UniqueConstraint(columnNames = ["reporter_id", "reported_id", "report_type"])]
)
class Report (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    val reportId: Long? = null,

    @Column(name = "report_type")
    @Enumerated(EnumType.STRING)
    val reportType: ReportType,

    @Column(name = "reporter_id")
    val reporterId: Long,

    @Column(name = "reported_id")
    val reportedId: Long,

    @Column(name = "description")
    val description: String,

    @Column(name = "resolution")
    val resolution: String? = null,

    @Column(name = "resolved_at")
    val resolvedAt: LocalDateTime? = null,
) : BaseEntity()