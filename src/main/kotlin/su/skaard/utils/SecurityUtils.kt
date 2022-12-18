package su.skaard.utils

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import su.skaard.model.discord.DiscordUser
import su.skaard.security.OAuth2DiscordUser
import java.security.Principal

/*
    Returns DiscordUser correspondent to the Principal if it is possible.
    Otherwise, returns null.
 */
// TODO: add custom UserDetailsService or ACL implementation to avoid casting
fun Principal.asDiscordUser(): DiscordUser? {
    return try {
        val principal = (this as OAuth2AuthenticationToken).principal as OAuth2DiscordUser
        principal.discordUser
    } catch (e: ClassCastException) {
        null
    }
}
