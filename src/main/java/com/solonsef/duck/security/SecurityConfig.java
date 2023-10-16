package com.solonsef.duck.security;


import com.solonsef.duck.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        jdbcUserDetailsManager.setUsersByUsernameQuery(
//                "select username, password, active from user where username=?");
//
//        //have to provide authorization query even though it's not being used
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
//                "select username, 'role_user' from user where username=?");
//
//        return jdbcUserDetailsManager;
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer.requestMatchers(HttpMethod.POST, "/fun-facts")
                        .authenticated()
                        .requestMatchers(HttpMethod.POST, "/images")
                        .authenticated()
                        .anyRequest().permitAll())
        ;

        // enable iframes
        http.headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin()));


//        http.authorizeHttpRequests(configurer ->
//                        configurer.requestMatchers(HttpMethod.POST, "/fun-facts").authenticated()
////                                .anyRequest().authenticated()
//        );
//                .formLogin(form ->
//                        form
//                                .loginPage("/showMyLoginPage")
//                                .loginProcessingUrl("/authenticateTheUser")
//                                .permitAll()
//                )
//                .logout(logout -> logout.permitAll()
//                )
//                .exceptionHandling(configurer ->
//                        configurer.accessDeniedPage("/access-denied")
//                );

        // use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());

        // disable Cross Site Request Forgery (CSRF)
        // in general, not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}

