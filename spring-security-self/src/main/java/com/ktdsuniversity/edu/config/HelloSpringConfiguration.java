package com.ktdsuniversity.edu.config;

import com.ktdsuniversity.edu.security.authenticate.handler.LoginFailureHandler;
import com.ktdsuniversity.edu.security.authenticate.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HelloSpringConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.formLogin(formLogin ->
                formLogin.loginPage("/login")
                        .usernameParameter("email")
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
        );

        http.cors(cors -> {
            CorsConfigurationSource source = (httpServletRequest) -> {
                // 허용할 타 사이트의 도메인을 작성
                CorsConfiguration config = new CorsConfiguration();

                // 허용할 타 사이트의 URL
//                config.addAllowedOrigin("");

                // 허용할 타 사이트의 POST와 GET으로 요청하는 접근만 허용하고 이외의 요청은 허용하지 않음
                config.addAllowedMethod("POST");
                config.addAllowedMethod("GET");

                // 허용할 타 사이트의 요청 HttpHeader
                config.addAllowedHeader("*");

                return config;
            };

            cors.configurationSource(source);
        });

        http.csrf(csrf -> csrf.disable());

        return http.build();


    }


}
