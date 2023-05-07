package su.skaard.core.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import su.skaard.core.entities.discord.DiscordUser

class OAuth2DiscordUser(
    val discordUser: DiscordUser,
    authorities: MutableCollection<out GrantedAuthority>?,
    attributes: MutableMap<String, Any>?,
    nameAttributeKey: String?
) : DefaultOAuth2User(authorities, attributes, nameAttributeKey)
