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
 *
 * @since : 2024/08/05
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class HealthyCodeTest {

    @Autowired
    lateinit var runtimeService: RuntimeService


    @Autowired
    lateinit var taskService: TaskService

    @Test
    fun 健康码测试() {
        val p1 = runtimeService.startProcessInstanceByKey("health_code")

        val t1 = taskService
            .createTaskQuery()
            .processInstanceId(p1.processInstanceId)
            .active()
            .singleResult()

        assertEquals("commit", t1.taskDefinitionKey)


        taskService.complete(t1.id, mapOf(HealthyCodeService.HEALTHY_CODE to "red"))

        val t2 = taskService
            .createTaskQuery()
            .processInstanceId(p1.processInstanceId)
            .active()
            .singleResult()
        assertEquals("person_audit", t2.taskDefinitionKey)

        taskService.complete(t2.id)
    }
}