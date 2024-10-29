package vn.cmcati.eid.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import vn.cmcati.eid.enums.Role;
import vn.cmcati.eid.repository.TokenRepository;


import javax.crypto.spec.SecretKeySpec;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig {
    @Value("${signer.key}")

    private String SIGNER_KEY;
  
    @Value("${springdoc.swagger-ui.path}")

    private String SWAGGER_UI_PATH;
  
    @Value("${springdoc.api-docs.path}")
    private String SWAGGER_API_DOCS_PATH;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/auth/token",
            "/auth/introspect",
            "/api/users"
    };
  
    private static final String[] ADMIN_ENDPOINTS = {
            "/api/admin/statistics",
            "/api/admin/api_request",
            "/api/admin/api_request_by_type/{type}",
            "/api/admin/api_request_by_user/{userId}",
            "/api/admin/api_request_by_time",
            "/api/apitype/**"
    };
    private final TokenRepository tokenRepository;

    public SecurityConfig(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Bean
    public ApiKeyFilter apiKeyFilter() {
        return new ApiKeyFilter(tokenRepository);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(SWAGGER_API_DOCS_PATH,SWAGGER_UI_PATH,"/swagger-ui/**","/api-docs/**").permitAll()
                                .antMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                                .antMatchers(HttpMethod.GET, "/api/users").hasRole(Role.ADMIN.name())
                                .antMatchers(HttpMethod.POST,"/api/paymentMethod","/api/paymentPlan").hasRole(Role.ADMIN.name())
                                .antMatchers(HttpMethod.PUT,"/api/paymentMethod/**","/api/paymentPlan/**").hasRole(Role.ADMIN.name())
                                .antMatchers(HttpMethod.DELETE,"/api/paymentMethod/**","/api/paymentPlan/**").hasRole(Role.ADMIN.name())
                                .antMatchers(ADMIN_ENDPOINTS).hasRole(Role.ADMIN.name())
                                .antMatchers("/api/momo/**").hasRole(Role.USER.name())
                                .antMatchers("/api/eKYC/**").permitAll()
                                .anyRequest().authenticated()
                )
        .addFilterBefore(apiKeyFilter(), UsernamePasswordAuthenticationFilter.class);
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                        .decoder(jwtDecoder())));
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("scope");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec signingKey = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(signingKey)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);

    }
}
