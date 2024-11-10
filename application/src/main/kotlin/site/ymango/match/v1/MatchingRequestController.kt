package site.ymango.match.v1

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import site.ymango.auth.model.UserInfo
import site.ymango.common.annotation.Auth
import site.ymango.match.MatchRequestService
import site.ymango.match.v1.dto.ConnectRequest
import site.ymango.match.v1.dto.MatchingRequestResponse

@RestController
@RequestMapping("/v1/matching-requests")
class MatchingRequestController (
    private val matchRequestService: MatchRequestService,
) {
    /**
     * 보낸 플러팅
     */
    @GetMapping("/sent")
    fun getMatchingRequestsByRequesterId(
        @Auth user: UserInfo
    ): List<MatchingRequestResponse> = matchRequestService.getMatchRequestsByRequesterId(user.userProfileId)
        .map { MatchingRequestResponse.of(it) }

    @GetMapping("/{matchRequestId}")
    fun getMatchingRequest(
        @Auth user: UserInfo,
        @PathVariable matchRequestId: Long
    ): MatchingRequestResponse = matchRequestService.getMatchRequest(matchRequestId, user.userProfileId)
        .let { MatchingRequestResponse.of(it) }


    /**
     * 받은 플러팅
     */
    @GetMapping("/received")
    fun getMatchingRequestsByRequesteeId(
        @Auth user: UserInfo
    ): List<MatchingRequestResponse> = matchRequestService.getMatchRequestsByRequesteeId(user.userProfileId)
        .map { MatchingRequestResponse.of(it) }

    /**
     * 플러팅 삭제
     */
    @DeleteMapping("/{matchRequestId}")
    fun deleteMatchingRequest(
        @Auth user: UserInfo,
        @PathVariable matchRequestId: Long
    ) {
        matchRequestService.deleteMatchRequest(matchRequestId, user.userProfileId)
    }

    /**
     * 연결하기
     */
    @PostMapping("/{matchRequestId}/connect")
    fun connectMatchingRequest(
        @Auth user: UserInfo,
        @PathVariable matchRequestId: Long,
        @Valid @RequestBody connectRequest: ConnectRequest
    ) {
        matchRequestService.connectMatchRequest(user.userId, connectRequest.requesterId, matchRequestId)
    }
}