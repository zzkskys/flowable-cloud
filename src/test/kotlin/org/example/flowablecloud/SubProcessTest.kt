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
import kotlin.test.assertNotNull

/**
 * 贷款子流程测试
 * @since : 2024/08/15
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class SubProcessTest {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Autowired
    lateinit var taskService: TaskService


    @Test
    fun 贷款流程申请() {

        val p1 = runtimeService.startProcessInstanceByKey("loan_process", mapOf("loan_limit" to 5_0000))

        val 贷款申请 = taskService
            .createTaskQuery()
            .taskDefinitionKey("apply")
            .active()
            .singleResult()

        taskService.complete(贷款申请.id)

        val 审批贷款额度 = taskService
            .createTaskQuery()
            .taskDefinitionKey("audit")
            .active()
            .singleResult()

        assertNotNull(审批贷款额度)
        assertEquals(p1.id,审批贷款额度.processInstanceId)

        taskService.complete(审批贷款额度.id)
    }

}