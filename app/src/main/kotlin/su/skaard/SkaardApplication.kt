package su.skaard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SkaardApplication

fun main(args: Array<String>) {
    runApplication<SkaardApplication>(*args)
}
