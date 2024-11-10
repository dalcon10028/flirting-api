package site.ymango.send.push

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PushSendServiceTest(
   private val pushSendService: PushSendService,
) : FunSpec({
 test("send") {
  pushSendService.send("dRjaXoCpT_yFpXg255Jeyl:APA91bGgamuynUidZ5i7R5fYp3dxN0A4qkiKbvXciFUdpmnr5CXVDEgPbqbC1HKTRCh5spWhS-BDkTFH5_h-K8APvu1QwvIzRPVyPlCMkD7BvUnGSpu86FaxeWHvKP62LGikNYrJIxZ5", "하얀이 사랑해", "하얀이 사랑해", null)
 }
})
