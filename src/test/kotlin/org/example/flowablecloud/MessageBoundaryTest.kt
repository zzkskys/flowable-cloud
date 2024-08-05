package org.example.flowablecloud

import org.flowable.engine.RuntimeService
import org.flowable.engine.TaskService
import org.flowable.spring.impl.test.FlowableSpringExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

/**
 * 消息边界事件测试
 *
 * @since : 2024/08/05
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class MessageBoundaryTest {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Autowired
    lateinit var taskService: TaskService


    @Test
    fun 消息边界事件测试() {
        val p1 = runtimeService.startProcessInstanceByKey("message_boundary_demo")

        val t1 = taskService
            .createTaskQuery()
            .processInstanceId(p1.processInstanceId)
            .active()
            .singleResult()

        assertEquals("message_boundary_touch", t1.taskDefinitionKey)

        val e1 = runtimeService
            .createExecutionQuery()
            .processInstanceId(p1.processInstanceId)
            .messageEventSubscriptionName("触发消息边界事件")
            .singleResult()

        runtimeService.messageEventReceived("触发消息边界事件", e1.id)

        val t2 = taskService
            .createTaskQuery()
            .processInstanceId(p1.processInstanceId)
            .active()
            .singleResult()

        assertEquals("apply", t2.taskDefinitionKey)
        taskService.complete(t2.id)
    }
}