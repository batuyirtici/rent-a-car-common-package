package rent.a.car.microservice.commonpackage.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import rent.a.car.microservice.commonpackage.utils.security.KeycloakJwtRoleConverter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        var converter = new JwtAuthenticationConverter();
                converter.setJwtGrantedAuthoritiesConverter(new KeycloakJwtRoleConverter());

        http.cors().and().authorizeHttpRequests()
                .requestMatchers("/api/filters",
                                          "/api/cars/check-car-available/**",
                                          "/api/cars/**",
                                          "api/payments/payment-validation")
                .permitAll()
                .requestMatchers("/api/**")
                .hasAnyRole("user")
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(converter);

        return http.build();
    }
}
