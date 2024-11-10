package site.ymango.point

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import site.ymango.exception.BaseException
import site.ymango.point.enums.PointType
import site.ymango.point.model.PointWalletHistoryModel
import site.ymango.point.repository.PointWalletRepository

@SpringBootTest
class PointServiceTest(
    private val pointService: PointService,
    private val pointWalletRepository: PointWalletRepository,

    ) : BehaviorSpec({
    beforeContainer {
        pointWalletRepository.deleteAll()
    }

    given("포인트 지갑이 없을 때") {
        `when`("포인트 지갑 이력을 조회하면") {
            then("에러 발생") {
                shouldThrow<BaseException> {
                    pointService.getPointHistory(1, 0)
                }.also {
                    it.message shouldBe "포인트 지갑이 존재하지 않습니다."
                }
            }
        }

        `when`("포인트 지갑을 조회하면") {
            then("에러 발생") {
                shouldThrow<BaseException> {
                    pointService.getPointWallet(1)
                }.also {
                    it.message shouldBe "포인트 지갑이 존재하지 않습니다."
                }
            }
        }

        `when`("포인트 사용하면") {
            then("에러 발생") {
                shouldThrow<BaseException> {
                    pointService.applyPoint(1, PointType.ADMIN)
                }.also {
                    it.message shouldBe "포인트 지갑이 존재하지 않습니다."
                }
            }
        }
    }

    given("포인트 지갑 생성 확인") {
        `when`("포인트 지갑을 생성하면") {
            then("포인트 지갑 생성") {
                pointService.createPointWallet(1)
                pointWalletRepository.count() shouldBe 1
            }
        }
    }

    given("20 포인트가 있을 때") {
        `when`("30 포인트를 사용한다면") {
            then("포인트 잔액이 부족합니다.") {
                pointService.createPointWallet(1)
                shouldThrow<BaseException> {
                    pointService.applyPoint(1, PointType.ADMIN, -10)
                }.also {
                    it.message shouldBe "포인트 잔액이 부족합니다."
                }
            }
        }

        `when`("10 포인트를 사용한다면") {
            pointService.createPointWallet(1)
            val pointWallet = pointService.getPointWallet(1)
            pointService.applyPoint(1, PointType.ADMIN, 20)
            pointService.applyPoint(1, PointType.ADMIN, -10)
            then("포인트 잔액이 10이다.") {
                pointService.getPointWallet(1).balance shouldBe 10
            }

            then("포인트 이력은 2개이다.") {
                pointService.getPointHistory(1, 0).content.size shouldBe 2
            }

            then("포인트 이력 내용 확인") {
                val pointHistories = pointService.getPointHistory(1, 1)

                val array: Array<PointWalletHistoryModel> = pointHistories.get().toList().toTypedArray()
                array[1].balance shouldBe 20
                array[1].amount shouldBe 20
                array[0].balance shouldBe 10
                array[0].amount shouldBe -10
            }
        }
    }
})
