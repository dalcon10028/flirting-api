package site.ymango.report.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import site.ymango.report.entity.Report
import site.ymango.report.enums.ReportType

@Repository
interface ReportRepository : JpaRepository<Report, Long> {
    fun existsByReporterIdAndReportedIdAndReportType(reporterId: Long, reportedId: Long, reportType: ReportType): Boolean
}