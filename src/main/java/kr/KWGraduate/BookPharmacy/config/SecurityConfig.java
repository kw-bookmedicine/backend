package kr.KWGraduate.BookPharmacy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.KWGraduate.BookPharmacy.jwt.CookieType;
import kr.KWGraduate.BookPharmacy.jwt.JWTUtil;
import kr.KWGraduate.BookPharmacy.jwt.filter.JWTFilter;
import kr.KWGraduate.BookPharmacy.jwt.filter.LoginFilter;
import kr.KWGraduate.BookPharmacy.jwt.oauth2.Oauth2SuccessHandler;
import kr.KWGraduate.BookPharmacy.service.ClientDetailsService;
import kr.KWGraduate.BookPharmacy.service.Oauth2ClientService;
import kr.KWGraduate.BookPharmacy.service.redis.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

import static kr.KWGraduate.BookPharmacy.config.Domain.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final ObjectMapper objectMapper;
    private final JWTUtil jwtUtil;
    private final ClientDetailsService clientDetailsService;

    private final AuthenticationEntryPoint entryPoint;
    private final AccessDeniedHandler deniedHandler;
    private final RefreshTokenService refreshTokenService;
    private final Oauth2ClientService oauth2ClientService;
    private final Oauth2SuccessHandler oauth2SuccessHandler;

    private final LogoutHandler logoutHandler;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.
                csrf(AbstractHttpConfigurer::disable);
        http.
                formLogin(AbstractHttpConfigurer::disable);
        http.
                httpBasic(AbstractHttpConfigurer::disable);

        http.
                logout(logout ->{
                    logout.logoutUrl("/logout");
                    logout.addLogoutHandler(logoutHandler);

                    logout.logoutSuccessHandler(((request, response, authentication) -> {
                        CookieType.deleteCookie(request,response);
                        SecurityContextHolder.clearContext();
                        response.getWriter().write("success");
                    }));

                });

        http.
                oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(oauth2ClientService))
                        .successHandler(oauth2SuccessHandler)
                );

        //나중에 배포할 때 수정(모든 곳에 혀용되지 않도록)
        http.
                authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/signup","/swagger-ui/**" ,"/v3/api-docs/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/hello").hasRole("USER")
                        .anyRequest().authenticated()
                        //.anyRequest().permitAll()
                );

        http.
                addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),objectMapper,jwtUtil,refreshTokenService), UsernamePasswordAuthenticationFilter.class);

//        http
//                .addFilterAfter(new JWTFilter(jwtUtil,clientDetailsService,refreshTokenService), OAuth2LoginAuthenticationFilter.class);
        http
                .addFilterBefore(new JWTFilter(jwtUtil,clientDetailsService,refreshTokenService), UsernamePasswordAuthenticationFilter.class);


        http.
                sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.exceptionHandling(handler -> handler
                .accessDeniedHandler(deniedHandler)
                .authenticationEntryPoint(entryPoint)
        );

        http
                .cors((corsCustomizer -> corsCustomizer.configurationSource(request -> {

                    CorsConfiguration configuration = new CorsConfiguration();

                    configuration.setAllowedOrigins(Collections.singletonList(FrontServer.getPresentAddress()));
                    configuration.setAllowedMethods(Collections.singletonList("*"));
                    configuration.setAllowCredentials(true);
                    configuration.setAllowedHeaders(Collections.singletonList("*"));
                    configuration.setMaxAge(3600L);
                    configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                    configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                    return configuration;
                })));

        return http.build();
    }
}
