package site.ymango.report

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.report.entity.Report
import site.ymango.report.enums.ReportType
import site.ymango.report.repository.ReportRepository

@Service
class ReportService(
    private val reportRepository: ReportRepository,
) {

    @Transactional
    fun reportUserProfile(reporterId: Long, reportedId: Long, description: String) {
        reportRepository.existsByReporterIdAndReportedIdAndReportType(reporterId, reportedId, ReportType.USER_PROFILE)
            .takeIf { !it }
            ?: throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "이미 신고한 사용자입니다.")

        reportRepository.save(
            Report(
                reportType = ReportType.USER_PROFILE,
                reporterId = reporterId,
                reportedId = reportedId,
                description = description,
            )
        )
    }
}