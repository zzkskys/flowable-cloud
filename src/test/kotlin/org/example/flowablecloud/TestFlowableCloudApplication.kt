package org.example.flowablecloud

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<FlowableCloudApplication>().with(TestcontainersConfiguration::class).run(*args)
}
