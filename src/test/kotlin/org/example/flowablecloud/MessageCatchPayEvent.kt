package org.example.flowablecloud

import org.flowable.engine.RuntimeService
import org.flowable.engine.TaskService
import org.flowable.spring.impl.test.FlowableSpringExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull

/**
 * 基于消息捕获的付款申请流程
 * @since : 2024/08/12
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class MessageCatchPayEvent {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Autowired
    lateinit var taskService: TaskService


    @Test
    fun 付款申请() {
        val p1 = runtimeService.startProcessInstanceByKey("message_catch_pay_process")

        val 付款申请 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("pay_apply")
            .singleResult()


        assertNotNull(付款申请)

        taskService.complete(付款申请.id)

        val 业务主管审批 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("executive_audit")
            .singleResult()


        assertNotNull(业务主管审批)


        val e1 = runtimeService
            .createExecutionQuery()
            .processInstanceId(p1.processInstanceId)
            .activityId("treasurer_message")
            .singleResult()

        val e2 = runtimeService
            .createExecutionQuery()
            .processInstanceId(p1.processInstanceId)
            .activityId("dept_manager_message")
            .singleResult()
        runtimeService.messageEventReceived("业务主管审批通过", e1.id)
        runtimeService.messageEventReceived("业务主管审批通过", e2.id)


        val tasks = taskService
            .createTaskQuery()
            .active()
            .list()

        val t1 = tasks.find { it.taskDefinitionKey == "treasurer_audit" }
        val t2 = tasks.find { it.taskDefinitionKey == "dept_manager_audit" }

        assertNotNull(t1)
        assertNotNull(t2)

    }
}