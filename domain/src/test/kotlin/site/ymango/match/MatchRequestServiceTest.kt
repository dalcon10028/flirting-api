package site.ymango.match

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.justRun
import org.springframework.boot.test.context.SpringBootTest
import site.ymango.chat_room.ChatRoomService
import site.ymango.exception.BaseException
import site.ymango.match.entity.MatchRequest
import site.ymango.match.repository.MatchRequestRepository
import site.ymango.send.SendEventListener

@SpringBootTest
class MatchRequestServiceTest(
    private val matchRequestService: MatchRequestService,
    private val matchRequestRepository: MatchRequestRepository,
    @MockkBean private val chatRoomService: ChatRoomService,
    @MockkBean private val eventListener: SendEventListener,
) : BehaviorSpec({

    beforeContainer {
        matchRequestRepository.deleteAll()
        justRun { eventListener.send(any()) }
        justRun { chatRoomService.createChatRoom(any(), any(), any()) }
    }

    given("플러팅 요청을 생성할 때") {
        `when`("플러팅 요청을 받을 상대가 이미 플러팅 요청을 받은 상대인 경우") {

            then("이미 플러팅 요청을 보낸 상대입니다. 에러가 발생한다.") {
                matchRequestRepository.save(
                    MatchRequest(
                        requesterId = 1,
                        requesteeId = 2,
                    )
                )
                shouldThrow<BaseException> {
                    matchRequestService.createMatchRequest(1, 2)
                }.message shouldBe "이미 플러팅 요청을 보낸 상대입니다."
            }
        }

        `when`("매칭에 성공할 경우") {
            then("채팅방이 생성된다.") {

            }

            then("플러팅 요청 만료된다.") {
            }
        }

        `when`("요청에 성공할 경우") {
            then("요청은 7일 뒤에 만료된다.") {

            }
        }
    }

    given("연결하기를 할 때") {
        `when`("플러팅 요청 정보가 없는 경우") {
            then("플러팅 요청 정보가 존재하지 않습니다. 에러가 발생한다.") {

            }
        }

        `when`("포인트가 부족한 경우") {
            then("포인트가 부족합니다. 에러가 발생한다.") {

            }
        }

        `when`("이미 생성된 채팅방이 있는 경우") {
            then("이미 채팅방이 존재합니다. 에러가 발생한다.") {

            }
        }
    }
})
