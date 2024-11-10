package site.ymango.user.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.locationtech.jts.geom.*
import site.ymango.user.model.Location

@Converter
class LocationConverter: AttributeConverter<Location, Point> {
    override fun convertToDatabaseColumn(location: Location): Point {
        val geometryFactory = GeometryFactory()
        return geometryFactory.createPoint(Coordinate(location.longitude, location.latitude))
    }

    override fun convertToEntityAttribute(point: Point): Location {
        return Location(point.x, point.y)
    }
}