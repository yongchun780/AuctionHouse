package com.mercury.AuctionHouse.security;

import com.mercury.AuctionHouse.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableMethodSecurity
@Configuration // in IoC
public class SecurityConfig {
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPointImpl;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandlerImpl;

    @Autowired
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;

    @Autowired
    private AuthenticationFailureHandlerImpl authenticationFailureHandlerImpl;

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandlerImpl;

    @Autowired
    private UserDetailsService userDetailsServiceImpl; // by type then by name
    @Bean // 加在function顶上，return的object会被当成一个Spring Bean, bean的名字就是function name
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> {
            c.disable();
        });
        http.cors(c -> {
            c.configurationSource(corsConfigurationSource());
        });
        http.authorizeHttpRequests(request -> {
            request
                    .anyRequest()
                    .permitAll();
        });
        http.formLogin(r -> {
            r.usernameParameter("username");
            r.passwordParameter("password");
            r.successHandler(authenticationSuccessHandlerImpl);
            r.failureHandler(authenticationFailureHandlerImpl);
        });


        http.exceptionHandling(c -> {
            c.accessDeniedHandler(accessDeniedHandlerImpl);
            c.authenticationEntryPoint(authenticationEntryPointImpl);
        });


        http.logout(c -> {
            c.permitAll()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(logoutSuccessHandlerImpl);
        });
        return http.build();
    }

    @Bean // put the return object into spring container, as a bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
    @Autowired // @Autowired on function will autowired the parameters
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(new BCryptPasswordEncoder(11))
        ;
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // You should only set trusted site here. e.g. http://localhost:4200 means only this site can access.
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","HEAD","OPTIONS"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
