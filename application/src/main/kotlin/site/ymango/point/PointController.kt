package site.ymango.point

import jakarta.validation.constraints.Min
import org.springframework.web.bind.annotation.*
import site.ymango.auth.model.UserInfo
import site.ymango.common.annotation.Auth
import site.ymango.point.v1.dto.*

@RestController
@RequestMapping("/v1/points")
class PointController(
    private val pointWalletService: PointService,
) {
    @GetMapping
    fun getPointWallet(
        @Auth user: UserInfo,
    ) = pointWalletService.getPointWallet(user.userId)
    .let { PointWalletResponse.from(it) }

    @GetMapping("/histories")
    fun getPointHistory(
        @Auth user: UserInfo,
        @Min(0) @RequestParam cursor: Int,
    ) = pointWalletService.getPointHistory(user.userId, cursor)
        .map { PointHistoryResponse.from(it) }
}