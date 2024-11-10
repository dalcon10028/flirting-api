package site.ymango.chat_room

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.ymango.chat_room.entity.ChatRoom
import site.ymango.chat_room.model.ChatRoomModel
import site.ymango.chat_room.repository.ChatRoomRepository
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode
import site.ymango.send.push.PushSendService
import site.ymango.user.UserAdditionalInformationService
import site.ymango.user.UserProfileService
import site.ymango.user.UserService
import site.ymango.user.enums.PushNotification

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
    private val userAdditionalInformationService: UserAdditionalInformationService,
    private val userProfileService: UserProfileService,
    private val userService: UserService,
    private val pushSendService: PushSendService,
) {
    @Transactional(readOnly = true)
    fun getChatRooms(userId: Long): List<ChatRoomModel> =
        chatRoomRepository.findByUserId(userId).mapNotNull { chatRoom ->
            userService.findByUserIdOrNull(chatRoom.opponentUserId)?.let { user ->
                assert(user.userProfile != null)
                assert(user.userAdditionalInformation != null)

                val chatReceivedUser = user.userNotificationFilters.map { it.sendTemplate }.any { sendTemplate ->
                    sendTemplate == PushNotification.CHAT_MESSAGE
                }

                ChatRoomModel.of(
                    chatRoom,
                    user.userProfile!!,
                    when {
                        chatReceivedUser -> null
                        else -> user.userAdditionalInformation!!.fcmToken
                    }
                )
            }
        }


    @Transactional
    fun getChatRoom(userId: Long, chatRoomId: Long): ChatRoomModel  {
        val chatRoom1 = chatRoomRepository.findById(chatRoomId).orElseThrow { BaseException(ErrorCode.RESOURCE_NOT_FOUND, "채팅방을 찾을 수 없습니다.") }
        if (chatRoom1.openedAt == null) {
            val chatRoom2 = chatRoomRepository.findByUserIdAndOpponentUserId(chatRoom1.opponentUserId, userId)
                ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "채팅방을 찾을 수 없습니다.")
            chatRoomRepository.saveAll(listOf(chatRoom1.open(), chatRoom2.open()))
        }

        return userService.findByUserIdOrNull(chatRoom1.opponentUserId)?.let { user ->
            assert(user.userProfile != null)
            assert(user.userAdditionalInformation != null)
            val chatReceivedUser = user.userNotificationFilters.map { it.sendTemplate }.any { sendTemplate ->
                sendTemplate == PushNotification.CHAT_MESSAGE
            }

            ChatRoomModel.of(
                chatRoom1,
                user.userProfile!!,
                when {
                    chatReceivedUser -> null
                    else -> user.userAdditionalInformation!!.fcmToken
                }
            )
        } ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "채팅방을 찾을 수 없습니다.")
    }

    @Transactional
    fun createChatRoom(matchRequestId: Long, userId: Long, opponentUserId: Long) {
        if (chatRoomRepository.existsByUserIdAndOpponentUserId(userId, opponentUserId)) {
            throw BaseException(ErrorCode.DUPLICATE_RESOURCE, "이미 채팅방이 존재합니다.")
        }

        chatRoomRepository.saveAll(listOf( ChatRoom(
            matchRequestId = matchRequestId,
            userId = userId,
            opponentUserId = opponentUserId
        ), ChatRoom(
            matchRequestId = matchRequestId,
            userId = opponentUserId,
            opponentUserId = userId
        )))
    }

    @Transactional
    fun deleteChatRoom(chatRoomId: Long, userId: Long) {
        val chatRoom = chatRoomRepository.findByChatRoomIdAndUserId(chatRoomId, userId)
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "채팅방을 찾을 수 없습니다.")

        if (chatRoom.closedAt == null) {
            // 채팅방이 닫히지 않았다면 닫아준다.
            closeChatRoom(chatRoomId, userId)
        }

        chatRoomRepository.delete(chatRoom)
    }

    @Transactional
    fun closeChatRoom(chatRoomId: Long, userId: Long) {
        val chatRoom1 = chatRoomRepository.findByChatRoomIdAndUserId(chatRoomId, userId)
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "채팅방을 찾을 수 없습니다.")

        val chatRoom2 = (chatRoomRepository.findByUserIdAndOpponentUserId(chatRoom1.opponentUserId, userId)
            ?: throw BaseException(ErrorCode.RESOURCE_NOT_FOUND, "채팅방을 찾을 수 없습니다."))

        chatRoomRepository.saveAll(listOf(chatRoom1.close(), chatRoom2.close()))
        chatRoomRepository.delete(chatRoom1)
    }

    @Deprecated("token 내려주는 방식으로 변경")
    fun sendMessage(receiverProfileId: Long, message: String) {
        val userProfile = userProfileService.getUserProfileWithUser(receiverProfileId)
        val userId= userProfile.user?.userId
        assert(userId != null)

        userAdditionalInformationService.getUserAdditionalInformation(userId!!).let {
            pushSendService.send(it.fcmToken, null, message, null)
        }
    }
}