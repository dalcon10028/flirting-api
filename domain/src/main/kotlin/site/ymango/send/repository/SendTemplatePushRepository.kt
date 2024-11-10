package site.ymango.send.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import site.ymango.send.entity.SendTemplatePush

@Repository
interface SendTemplatePushRepository : JpaRepository<SendTemplatePush, String>