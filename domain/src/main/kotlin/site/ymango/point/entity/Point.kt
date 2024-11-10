package site.ymango.point.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction
import site.ymango.common.BaseEntity
import site.ymango.point.enums.PointType
import java.time.LocalDateTime

@Entity
@Table(name = "point", schema = "flirting")
@SQLRestriction("deleted_at IS NULL")
class Point(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    val pointId: Int? = null,

    @Column(name = "point_type")
    @Enumerated(EnumType.STRING)
    val pointType: PointType,

    @Column(name = "summary")
    var summary: String,

    @Column(name = "default_amount")
    var defaultAmount: Int,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,
) : BaseEntity()