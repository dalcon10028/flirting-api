package site.ymango.chat_room.v1

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import site.ymango.auth.model.UserInfo
import site.ymango.chat_room.ChatRoomService
import site.ymango.chat_room.v1.dto.ChatRoomResponse
import site.ymango.chat_room.v1.dto.SendMessageRequest
import site.ymango.common.annotation.Auth

@RestController
@RequestMapping("/v1/chat-rooms")
class ChatRoomController(
    private val chatRoomService: ChatRoomService,
) {

    @GetMapping
    fun getChatRooms(
        @Auth user: UserInfo,
    ): List<ChatRoomResponse> =
        chatRoomService.getChatRooms(user.userProfileId)
            .map { ChatRoomResponse.of(it) }

    @GetMapping("/{chatRoomId}")
    fun getChatRoom(
        @Auth user: UserInfo,
        @PathVariable chatRoomId: Long,
    ): ChatRoomResponse =
        chatRoomService.getChatRoom(user.userProfileId, chatRoomId)
            .let { ChatRoomResponse.of(it) }

    @PostMapping("/{chatRoomId}/close")
    fun closeChatRoom(
        @Auth user: UserInfo,
        @PathVariable chatRoomId: Long,
    ) {
        chatRoomService.closeChatRoom(chatRoomId, user.userId)
    }

    @DeleteMapping("/{chatRoomId}")
    fun deleteChatRoom(
        @Auth user: UserInfo,
        @PathVariable chatRoomId: Long,
    ) {
        chatRoomService.deleteChatRoom(chatRoomId, user.userId)
    }

//    @PostMapping("/send-message")
//    fun sendMessage(
//        @Auth user: UserInfo,
//        @Valid @RequestBody request: SendMessageRequest,
//    ): Unit = chatRoomService.sendMessage(request.receiverProfileId!!, request.message)
}