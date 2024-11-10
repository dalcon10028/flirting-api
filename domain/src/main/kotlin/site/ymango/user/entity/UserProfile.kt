package site.ymango.user.entity

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import site.ymango.user.enums.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import site.ymango.common.BaseEntity
import site.ymango.user.converter.LocationConverter
import site.ymango.user.model.Location
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "user_profile", catalog = "flirting", indexes = [Index(name = "user_profile_ix_user_id", columnList = "user_id")])
class UserProfile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_id")
    val userProfileId: Long? = null,

    @Column(name = "user_id", unique = true)
    var userId: Long? = null,

    @Column(name = "nickname", nullable = false)
    var nickname: String,

    @Column(name = "avatar_code", nullable = false)
    var avatarCode: String,

    @Column(name = "company_name", nullable = false)
    var companyName: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    var gender: Gender,

    @Enumerated(EnumType.STRING)
    @Column(name = "mbti", nullable = false)
    var mbti: MBTI,

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_mbti", nullable = false)
    var preferredMBTI: PreferredMBTI,

    @Column(name = "birthdate", nullable = false)
    var birthdate: LocalDate,

    @Column(name = "sido", nullable = false)
    var sido: String,

    @Column(name = "sigungu", nullable = false)
    var sigungu: String,

    @Column(name = "location", nullable = false)
    @Convert(converter = LocationConverter::class)
    var location: Location,

    @Column(name = "height", nullable = false)
    var height: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "body_type", nullable = false)
    var bodyType: BodyType,

    @Column(name = "job", nullable = false)
    var job: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "smoking", nullable = false)
    var smoking: Smoking,

    @Enumerated(EnumType.STRING)
    @Column(name = "drinking", nullable = false)
    var drinking: Drinking,

    @Enumerated(EnumType.STRING)
    @Column(name = "religion", nullable = false)
    var religion: Religion,

    @Column(name = "introduction")
    var introduction: String? = null,

    @Column(name = "dream")
    var dream: String? = null,

    @Column(name = "love_style")
    var loveStyle: String? = null,

    @Type(JsonType::class)
    @Column(name = "charm", columnDefinition = "json")
    var charm: List<String> = listOf(),

    @Type(JsonType::class)
    @Column(name = "interest", columnDefinition = "json")
    var interest: List<String> = listOf(),

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: UserProfileStatus = UserProfileStatus.ACTIVE,

    @CreatedDate
    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null,

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    var user: User? = null
): BaseEntity() {
    fun block() {
        status = UserProfileStatus.BLOCKED
    }

    fun unblock() {
        status = UserProfileStatus.ACTIVE
    }

    fun withdrawal(): UserProfile {
        status = UserProfileStatus.INACTIVE
        return this
    }

    fun deactivate(): UserProfile {
        status = UserProfileStatus.RECOMMEND_INACTIVE
        return this
    }

    fun activate(): UserProfile {
        status = UserProfileStatus.ACTIVE
        return this
    }
}