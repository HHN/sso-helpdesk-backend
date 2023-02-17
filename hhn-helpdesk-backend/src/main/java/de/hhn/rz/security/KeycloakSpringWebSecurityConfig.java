package de.hhn.rz.security;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@KeycloakConfiguration
public class KeycloakSpringWebSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(buildSessionRegistry());
    }

    @Bean
    protected SessionRegistry buildSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("HHN_HELPDESK_ADMIN")
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }

}