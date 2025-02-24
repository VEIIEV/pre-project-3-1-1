package org.example.preproject231.config.security;

import org.example.preproject231.config.property.InMemoryUserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Autowired
    @Qualifier("dbUserDetailsService")
    private UserDetailsService userService;

    @Autowired
    private InMemoryUserProperties inMemoryUserProperties;


    @Bean
    public SecurityFilterChain configure(HttpSecurity http,
                                         AuthenticationManager authenticationManager,
                                         AuthenticationByRoleSuccessHandler authenticationByRoleSuccessHandler) throws Exception {
        return http.
                csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(auth -> auth.
                        requestMatchers(
                                new AntPathRequestMatcher("/api/admin/**")
                        ).hasAuthority("ADMIN").
                        anyRequest().authenticated()).
                anonymous(Customizer.withDefaults()).
                securityContext(securityContext -> securityContext.requireExplicitSave(false)).
                authenticationManager(authenticationManager).
                formLogin(login -> login.
                        successHandler(authenticationByRoleSuccessHandler)).
                logout(Customizer.withDefaults()).
                build();


    }


    @Bean
    public AuthenticationManager authenticationManager(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        return new ProviderManager(daoAuthenticationProvider(), inMemoryAuthenticationProvider(inMemoryUserDetailsManager));
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    //    дефолтный провайдер
    @Bean
    public DaoAuthenticationProvider inMemoryAuthenticationProvider(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        DaoAuthenticationProvider inMemoryAuthenticationProvider = new DaoAuthenticationProvider();
        inMemoryAuthenticationProvider.setUserDetailsService(inMemoryUserDetailsManager);
        return inMemoryAuthenticationProvider;
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails userDetails = User.withUsername(inMemoryUserProperties.getName())
                .password("{noop}" + inMemoryUserProperties.getPassword())
                .authorities(inMemoryUserProperties.getAuthorities().toArray(new String[0]))
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
