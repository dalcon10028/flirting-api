package site.ymango.match

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.chat_room.ChatRoomService
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.match.entity.MatchRequest
import site.ymango.match.model.MatchRequestModel
import site.ymango.match.repository.MatchRequestRepository
import site.ymango.point.PointService
import site.ymango.point.enums.PointType
import site.ymango.send.enums.SendTemplate
import site.ymango.send.model.SendEvent
import site.ymango.user.UserProfileService
import site.ymango.user.UserService

@Service
class MatchRequestService(
    private val matchRequestRepository: MatchRequestRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val userService: UserService,
    private val userProfileService: UserProfileService,
    private val chatRoomService: ChatRoomService,
    private val pointService: PointService,
) {
    /**
     * 플러팅 한 목록을 가져온다.
     * 활성 프로필이 아닌경우 필터링 한다.
     */
    fun getMatchRequestsByRequesterId(userProfileId: Long): List<MatchRequestModel> =
        matchRequestRepository.findByRequesterId(userProfileId).mapNotNull { MatchRequestModel.ofNullable(it, userProfileService.getActiveOrRecommendInactiveProfileOrNull(it.requesteeId)) }


    /**
     * 플러팅 받은 목록을 가져온다.
     * 활성 프로필이 아닌경우 필터링 한다.
     */
    fun getMatchRequestsByRequesteeId(userProfileId: Long): List<MatchRequestModel> =
        matchRequestRepository.findByRequesteeId(userProfileId).mapNotNull { MatchRequestModel.ofNullable(it, userProfileService.getActiveOrRecommendInactiveProfileOrNull(it.requesterId)) }

    /**
     * 프로필 상세
     */
    fun getMatchRequest(matchRequestId: Long, userProfileId: Long): MatchRequestModel {
        val matchRequest = matchRequestRepository.findByMatchRequestIdAndUserProfileId(matchRequestId, userProfileId) ?: throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "플러팅 요청을 찾을 수 없습니다.")

        return when {
            matchRequest.requesterId == userProfileId -> {
                MatchRequestModel.of(matchRequest, userProfileService.getActiveOrRecommendInactiveProfile(matchRequest.requesteeId))
            }
            matchRequest.requesteeId == userProfileId -> {
                MatchRequestModel.of(matchRequest, userProfileService.getActiveOrRecommendInactiveProfile(matchRequest.requesterId))
            }
            else -> throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "플러팅 요청을 찾을 수 없습니다.")
        }
    }

    /**
     * 플러팅 요청을 생성한다.
     */
    @Transactional
    fun createMatchRequest(userId: Long, requesterId: Long, requesteeId: Long) {
        if (matchRequestRepository.existsByRequesterIdAndRequesteeId(requesterId, requesteeId)) {
            throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "이미 플러팅 요청을 보낸 상대입니다.")
        }

        val matchingRequest = matchRequestRepository.findByRequesterIdAndRequesteeId(requesteeId, requesterId)

        if (matchingRequest != null) {
            // 이미 받은 매칭 요청이 존재한다면 매칭 성공
            assert(matchingRequest.matchRequestId != null) { "matchingRequestId must not be null" }

            // 채팅방 생성
            val opponentUser = userProfileService.getUserProfileWithUser(matchingRequest.requesterId)
            chatRoomService.createChatRoom(
                matchRequestId = matchingRequest.matchRequestId!!,
                userId = userId,
                opponentUserId = opponentUser.userId!!,
            )

            matchRequestRepository.save(matchingRequest.match())

            // 매칭 완료 푸시 발송
            applicationEventPublisher.publishEvent(SendEvent(
                userId = userProfileService.getActiveOrRecommendInactiveProfile(requesteeId).userId,
                parameters = mapOf("nickname" to userProfileService.getActiveOrRecommendInactiveProfile(requesterId).nickname),
                sendTemplate = SendTemplate.MATCH_COMPLETE,
            ))
            return
        }

        matchRequestRepository.save(MatchRequest(
            requesterId = requesterId,
            requesteeId = requesteeId,
        ))

        // 플러팅 요청 푸시 발송
        applicationEventPublisher.publishEvent(SendEvent(
            userId = userProfileService.getActiveOrRecommendInactiveProfile(requesteeId).userId,
            parameters = mapOf("nickname" to userProfileService.getActiveOrRecommendInactiveProfile(requesterId).nickname),
            sendTemplate = SendTemplate.FLIRTING_RECEIVED,
        ))
    }

    /**
     * 연결하기
     */
    @Transactional
    fun connectMatchRequest(userId: Long, requesterId: Long, matchRequestId: Long) {
        val matchRequest = matchRequestRepository.findByMatchRequestIdAndRequesterId(matchRequestId, requesterId)
            ?: throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "플러팅 요청 정보가 존재하지 않습니다.")

        // 포인트 소진
        pointService.applyPoint(
            userId = userId,
            pointType = PointType.MATCH_COMPLETE,
        )

        // 채팅방 생성
        val opponentUser = userProfileService.getUserProfileWithUser(matchRequest.requesterId)
        chatRoomService.createChatRoom(
            matchRequestId = matchRequestId,
            userId = userId,
            opponentUserId = opponentUser.userId!!,
        )

        matchRequestRepository.save(matchRequest.match())

        // 매칭 완료 푸시 발송
        applicationEventPublisher.publishEvent(SendEvent(
            userId = userProfileService.getActiveOrRecommendInactiveProfile(matchRequest.requesterId).userId,
            parameters = mapOf("nickname" to userProfileService.getActiveOrRecommendInactiveProfile(matchRequest.requesteeId).nickname),
            sendTemplate = SendTemplate.MATCH_COMPLETE,
        ))
    }

    /**
     * 플러팅 요청을 삭제한다.
     */
    @Transactional
    fun deleteMatchRequest(matchRequestId: Long, userProfileId: Long) {
        val matchRequest = matchRequestRepository.findByMatchRequestIdAndUserProfileId(matchRequestId, userProfileId) ?: throw BaseException(ErrorCode.INVALID_INPUT_VALUE, "플러팅 요청을 찾을 수 없습니다.")

        matchRequestRepository.delete(matchRequest)
    }
}