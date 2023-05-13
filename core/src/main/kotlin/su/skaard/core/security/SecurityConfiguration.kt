//package su.skaard.core.security
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
//import org.springframework.security.web.SecurityFilterChain
//import su.skaard.core.repositories.discord.DiscordUserRepository
//
//@Configuration
//@EnableWebSecurity
//class SecurityConfiguration @Autowired constructor(private val discordUserRepository: DiscordUserRepository) {
//
//    @Bean
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http
//            .authorizeHttpRequests()
//            .anyRequest().authenticated()
//            .and()
//            .oauth2Login()
//        return http.build()
//    }
//
//    @Bean
//    fun oauth2UserService(): DefaultOAuth2UserService = OAuth2DiscordUserService(discordUserRepository)
//}
