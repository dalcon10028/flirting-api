package site.ymango.company.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.*
import org.springframework.context.annotation.*

@Configuration
class QueryDslConfig(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    @Bean
    fun jpaQueryFactory(): JPAQueryFactory = JPAQueryFactory(entityManager)
}