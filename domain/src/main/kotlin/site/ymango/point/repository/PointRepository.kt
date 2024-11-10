package site.ymango.point.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import site.ymango.point.entity.Point
import site.ymango.point.enums.PointType

@Repository
interface PointRepository : JpaRepository<Point, Int> {
    fun findByPointType(pointType: PointType): Point?
}