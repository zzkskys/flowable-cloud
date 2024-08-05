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
import kotlin.test.assertNull

/**
 * 报销流程
 * 以报销流程为例学习错误开始事件、错误结束事件
 * @since : 2024/08/02
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class ReimbursementTest {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Autowired
    lateinit var taskService: TaskService

    @Test
    fun 错误开始事件学习() {
        val instance = runtimeService.startProcessInstanceByKey("reimbursement")

        val t1 = taskService.createTaskQuery().active().singleResult()
        assertEquals("报销申请", t1.name)

        taskService.complete(t1.id, "u1", mapOf("pass" to false))

        val t2 = taskService.createTaskQuery().active().singleResult()
        assertEquals("重新制定报销预算", t2.name)

        assertEquals(instance.rootProcessInstanceId, t2.processInstanceId)
        assertEquals(instance.processInstanceId, t2.processInstanceId)
        taskService.complete(t2.id)


        val without = taskService.createTaskQuery().active().singleResult()
        assertNull(without)
    }
}