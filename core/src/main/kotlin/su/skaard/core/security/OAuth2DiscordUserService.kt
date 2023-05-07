package su.skaard.core.security

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import su.skaard.core.repositories.discord.DiscordUserRepository

class OAuth2DiscordUserService(private val userRepository: DiscordUserRepository) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val user = super.loadUser(userRequest)
        val id = user.getAttribute<String>("id")?.toLong()
        requireNotNull(id)
        val discordUser =
            userRepository.searchById(id) ?: throw UsernameNotFoundException("User with id $id is not found")
        return OAuth2DiscordUser(
            discordUser,
            user.authorities,
            user.attributes,
            userRequest?.clientRegistration?.providerDetails?.userInfoEndpoint?.userNameAttributeName
        )
    }
}
