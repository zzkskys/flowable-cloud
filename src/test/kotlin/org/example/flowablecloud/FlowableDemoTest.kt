package org.example.flowablecloud

import junit.framework.TestCase.assertEquals
import org.flowable.engine.RuntimeService
import org.flowable.engine.TaskService
import org.flowable.engine.test.Deployment
import org.flowable.spring.impl.test.FlowableSpringExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test


@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
class FlowableDemoTest {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Autowired
    lateinit var taskService: TaskService


    @Test
    @Deployment(resources = ["processes/junit-demo.bpmn20.xml"])
    fun demo() {
        val instance = runtimeService.startProcessInstanceByKey("junit-demo")

        val t1 = taskService.createTaskQuery().active().singleResult()
        assertEquals("apply", t1.taskDefinitionKey)
        taskService.complete(t1.id)

        val t2 = taskService.createTaskQuery().active().singleResult()
        assertEquals("audit", t2.taskDefinitionKey)
        taskService.complete(t2.id)

    }
}