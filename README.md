# Flirting API
> 플러팅 서비스 API

## ErrorResponse
```json
{
    "code": "INVALID_INPUT_VALUE", // 에러 코드
    "message": "입력값이 올바르지 않습니다.", // 에러 메시지
    "errors": [
        {
            "codes": null,
            "arguments": null,
            "defaultMessage": "길이가 0에서 30 사이여야 합니다", // 에러 메시지
            "objectName": "getCompanies.name", // 에러가 발생한 객체
            "field": "name", // 에러가 발생한 필드
            "rejectedValue": "asdadsfasfddsafasdfasdfasdfasdfasdfasdfsdf", // 거절된 값
            "bindingFailure": false,
            "code": null
        }
    ]
}
```
