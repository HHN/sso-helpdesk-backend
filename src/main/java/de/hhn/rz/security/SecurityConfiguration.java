/*
 * Copyright © 2023 Hochschule Heilbronn (ticket@hs-heilbronn.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.hhn.rz.security;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(buildSessionRegistry());
    }

    @Bean
    protected SessionRegistry buildSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapperForKeycloak() {
        return authorities -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            var authority = authorities.iterator().next();

            if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                var userInfo = oidcUserAuthority.getUserInfo();

                if (userInfo.hasClaim("realm_access")) {
                    var realmAccess = userInfo.getClaimAsMap("realm_access");
                    var roles = (Collection<String>) realmAccess.get("roles");
                    mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
                } else {
                    logger.warn("User '{}' does not have a claim 'realm_access'", oidcUserAuthority.getUserInfo().getNickName());
                }
            } else if (authority instanceof OAuth2UserAuthority oAuth2UserAuthority) {
                Map<String, Object> userAttributes = oAuth2UserAuthority.getAttributes();

                if (userAttributes.containsKey("realm_access")) {
                    var realmAccess = (Map<String, Object>) userAttributes.get("realm_access");
                    var roles = (Collection<String>) realmAccess.get("roles");
                    mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
                } else {
                    logger.warn("Given user does not have a claim 'realm_access'");
                }
            } else {
                final String msg = String.format("Unknown authority of type %s", authority.getClass().getName());
                logger.warn(msg);
                throw new RuntimeException(msg);
            }

            return mappedAuthorities;
        };
    }

    private Collection<GrantedAuthority> generateAuthoritiesFromClaim(Collection<String> roles) {
        if (logger.isDebugEnabled()) {
            logger.debug("Assigned roles: {}", roles);
        }
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(req -> req.requestMatchers("/admin/**").hasRole(Role.HHN_HELPDESK_ADMIN.toString()).anyRequest().permitAll());
        http.oauth2Login(Customizer.withDefaults());
        http.exceptionHandling(exhan -> exhan.authenticationEntryPoint(new Http403ForbiddenEntryPoint()));

        //Needed since Spring 6
        CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        http.csrf((csrf) -> csrf.csrfTokenRepository(tokenRepository).csrfTokenRequestHandler(requestHandler))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);

        http.getSharedObject(AuthenticationManagerBuilder.class).authenticationEventPublisher(new LoggableAuthenticationEventPublisher());
        http.addFilterAfter(new HelpdeskAuthorizationFilter(), CsrfCookieFilter.class);
        return http.build();
    }

    @Bean
    @ConditionalOnMissingFilterBean(ForwardedHeaderFilter.class)
    @ConditionalOnProperty(value = "server.forward-headers-strategy", havingValue = "framework")
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        ForwardedHeaderFilter filter = new ForwardedHeaderFilter();
        FilterRegistrationBean<ForwardedHeaderFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC, DispatcherType.ERROR);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    private static final class CsrfCookieFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            // Render the token value to a cookie by causing the deferred token to be loaded
            csrfToken.getToken();

            filterChain.doFilter(request, response);
        }

    }

    private static final class HelpdeskAuthorizationFilter extends OncePerRequestFilter {

        private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

            final Authentication authentication = this.securityContextHolderStrategy.getContext().getAuthentication();

            //user did successfully authenticate with Keycloak, let's check for required helpdesk role.
            if (authentication != null) {
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                if (authorities != null) {
                    final GrantedAuthority ga = Role.HHN_HELPDESK_ADMIN.asGrantedAuthority();
                    if (!authorities.contains(ga)) {
                        response.addHeader("helpdesk-missing-role", "The user doesn't has the required role to access helpdesk.");
                    }
                }
            }

            chain.doFilter(request, response);
        }
    }

    /**
     * The default {@link DefaultAuthenticationEventPublisher} does not log an {@link AuthenticationException} if no
     * {@link AbstractAuthenticationEvent} exists for the given exception type.
     * However, in certain circumstances it is required to see some more log output. For example, the system running this
     * application has a time difference to the system hosting keycloak.
     */
    private static final class LoggableAuthenticationEventPublisher extends DefaultAuthenticationEventPublisher {

        public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
            if (logger.isDebugEnabled()) {
                logger.debug("Authentication failed. See stacktrace for more information.");
                logger.debug(exception.getLocalizedMessage(), exception);
            }
            super.publishAuthenticationFailure(exception, authentication);
        }
    }
}
