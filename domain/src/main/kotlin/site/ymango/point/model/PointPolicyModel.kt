package site.ymango.point.model

import site.ymango.point.entity.Point
import site.ymango.point.enums.PointType
import java.time.LocalDateTime

data class PointPolicyModel(
    val pointId: Int? = null,
    val pointType: PointType,
    val summary: String,
    val defaultAmount: Int,
    val createdAt: LocalDateTime? = null,
) {
    companion object {
        fun from(pointPolicy: Point): PointPolicyModel {
            assert(pointPolicy.pointId != null) { "pointId must not be null" }
            assert(pointPolicy.createdAt != null) { "createdAt must not be null" }
            return PointPolicyModel(
                pointId = pointPolicy.pointId!!,
                pointType = pointPolicy.pointType,
                summary = pointPolicy.summary,
                defaultAmount = pointPolicy.defaultAmount,
                createdAt = pointPolicy.createdAt,
            )
        }
    }
}
