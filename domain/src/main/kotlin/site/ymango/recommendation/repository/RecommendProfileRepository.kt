package site.ymango.recommendation.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.NumberExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import site.ymango.match.entity.QMatchRequest.matchRequest
import site.ymango.recommendation.entity.QRecommendProfile.recommendProfile
import site.ymango.user.entity.QUserProfile.userProfile
import site.ymango.user.entity.UserProfile
import site.ymango.user.enums.Gender
import site.ymango.user.enums.MBTI
import site.ymango.user.model.Location
import java.time.LocalDateTime

@Repository
class RecommendProfileRepository (
    private val queryFactory: JPAQueryFactory,
    private val recommendProfileDelegatingRepository: RecommendProfileDelegatingRepository
) : RecommendProfileDelegatingRepository by recommendProfileDelegatingRepository {

    private fun inDistanceLessThanOne100Km(location: Location): BooleanExpression {
        return Expressions.booleanTemplate("ST_Distance_Sphere(location, POINT({0}, {1})) < {2}",
            location.longitude, location.latitude, 10000000)
    }
}