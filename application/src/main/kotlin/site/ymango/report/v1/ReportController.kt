package site.ymango.report.v1

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.ymango.auth.model.UserInfo
import site.ymango.common.annotation.Auth
import site.ymango.report.ReportService
import site.ymango.report.v1.dto.ReportRequest

@RestController
@RequestMapping("/v1/reports")
class ReportController(
    private val reportService: ReportService,
) {
    @PostMapping("/report-user-profile")
    fun reportUserProfile(
        @Auth user: UserInfo,
        @RequestBody request: ReportRequest,
    ) {
        reportService.reportUserProfile(user.userId, request.reportedId, request.description)
    }
}