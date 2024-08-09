package org.example.flowablecloud

import org.flowable.engine.RuntimeService
import org.flowable.engine.TaskService
import org.flowable.spring.impl.test.FlowableSpringExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

/**
 * 取消边界事件测试
 * @since : 2024/08/09
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class CancelBoundaryTest {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Autowired
    lateinit var taskService: TaskService

    @Test
    fun 用户取消支付导致流程取消() {
        val p1 = runtimeService.startProcessInstanceByKey("user_order")

        val 用户提交订单 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("apply")
            .singleResult()

        taskService.complete(用户提交订单.id)

        val 用户取消订单 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("cancel_order")
            .singleResult()

        taskService.complete(用户取消订单.id)




    }

    @Test
    fun 扣减库存失败导致流程取消() {
        val p1 = runtimeService.startProcessInstanceByKey("user_order")

        val 用户提交订单 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("apply")
            .singleResult()

        taskService.complete(用户提交订单.id)

        val 用户支付订单 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("pay")
            .singleResult()
        taskService.complete(用户支付订单.id)
    }
}