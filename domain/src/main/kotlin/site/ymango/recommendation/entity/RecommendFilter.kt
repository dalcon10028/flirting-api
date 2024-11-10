package site.ymango.recommendation.entity

import jakarta.persistence.*
import site.ymango.common.BaseEntity

@Entity
@Table(name = "recommend_filter", schema = "flirting", indexes = [
    Index(name = "idx_user_profile_id", columnList = "user_profile_id"),
])
class RecommendFilter (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_filter_id")
    val recommendFilterId: Long? = null,

    @Column(name = "user_profile_id")
    val userProfileId: Long,

    @Column(name = "phone_number")
    val phoneNumber: String,
) : BaseEntity()