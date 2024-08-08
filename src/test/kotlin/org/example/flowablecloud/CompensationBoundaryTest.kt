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
 * 边界补偿事件测试
 * @since : 2024/08/08
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class CompensationBoundaryTest {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Autowired
    lateinit var taskService: TaskService

    @Test
    fun 边界补偿事件测试() {
        val p1 = runtimeService.startProcessInstanceByKey("compensation_boundary")

        val 预报名 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("pre_registry")
            .singleResult()

        taskService.complete(预报名.id)

        val 正式报名 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("registry")
            .singleResult()

        taskService.complete(正式报名.id)

        val 报名审核 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("registry_audit")
            .singleResult()

        taskService.complete(报名审核.id)

        val 金额支付 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("pay")
            .singleResult()

        taskService.complete(金额支付.id)
    }
}