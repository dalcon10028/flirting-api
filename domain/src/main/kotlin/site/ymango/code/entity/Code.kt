package site.ymango.code.entity

import jakarta.persistence.*
import site.ymango.common.AuditEntity

@Entity
@Table(name = "code", catalog = "flirting", indexes = [
    Index(name = "idx_code_main_code", columnList = "main_code"),
], uniqueConstraints = [
    UniqueConstraint(name = "code_uq_main_code_code", columnNames = ["main_code", "code"])
])
class Code(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    val codeId: Int? = null,

    @Column(name = "main_code")
    var mainCode: String,

    @Column(name = "parent_code")
    var parentCode: String,

    @Column(name = "code")
    var code: String,

    @Column(name = "sort")
    var sort: Int,

    @Column(name = "name")
    var name: String,
): AuditEntity()