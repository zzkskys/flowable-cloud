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
 * 中间补偿抛出事件测试
 * @since : 2024/08/13
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class CompensateTest {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Autowired
    lateinit var taskService: TaskService


    @Test
    fun 中间补偿抛出事件测试() {
        val p1 = runtimeService.startProcessInstanceByKey("compensate_process", mapOf("pass" to false))

        val 会议申请 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("apply")
            .singleResult()

        taskService.complete(会议申请.id)
    }
}