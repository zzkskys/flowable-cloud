package org.example.flowablecloud

import org.flowable.engine.RuntimeService
import org.flowable.spring.impl.test.FlowableSpringExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

/**
 * 信号开始事件测试
 * @since : 2024/08/02
 * @author zzk
 */

@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class SignalDemoTest {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Test
    fun 信号开始事件测试() {
        runtimeService.signalEventReceived("信号开始")

        val instances = runtimeService
            .createProcessInstanceQuery()
            .list()

        assertEquals(1, instances.size)

    }
}