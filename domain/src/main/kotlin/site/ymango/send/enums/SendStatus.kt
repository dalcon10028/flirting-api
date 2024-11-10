package site.ymango.send.enums

enum class SendStatus {
    PROCESSING, // 전송 처리중
    TEMPLATE_RENDERED, // 템플릿 렌더링 완료
    DELIVERED, // 전송 요청 완료
    FILTERED,  // 전송 필터링 됨
    SUCCESS, // 전송 성공
    FAILED // 전송 실패
}