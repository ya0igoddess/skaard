package su.skaard.core.initializer

import dev.kord.core.Kord
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import su.skaard.core.commands.services.IKordCommandRegistry

@Component
@Order(1)
class CommandRegisterPreInit(
    private val commandRegistry: IKordCommandRegistry
) : IKordPreInitializer {
    override fun preInitialize(kord: Kord) {
        commandRegistry.registerCommands(kord)
    }
}