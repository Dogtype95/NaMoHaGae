package kr.kro.namohagae.global;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        return http.build();
    }

}
