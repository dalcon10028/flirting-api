package site.ymango.send.entity

import jakarta.persistence.*
import site.ymango.common.AuditEntity
import site.ymango.send.enums.SendCategory
import java.time.LocalDateTime


@Entity
@Table(name = "send_template_sms", schema = "flirting")
class SendTemplateSms (

    @Id
    @Column(name = "template_code")
    val templateCode: String,

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    val category: SendCategory,

    @Column(name = "content")
    val content: String,

    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime? = null

) : AuditEntity()