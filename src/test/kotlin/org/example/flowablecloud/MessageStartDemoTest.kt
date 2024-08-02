package org.example.flowablecloud

import org.flowable.engine.RuntimeService
import org.flowable.spring.impl.test.FlowableSpringExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull

/**
 * 消息开始事件测试
 * @since : 2024/08/02
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class MessageStartDemoTest {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Test
    fun 测试消息开始事件() {
        val instance = runtimeService.startProcessInstanceByMessage("消息数据上报")

        assertNotNull(instance)
    }
}