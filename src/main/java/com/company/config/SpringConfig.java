package com.company.config;

import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Authentication
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authorization
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/profile", "/profile/adm/*").hasRole( "ADMIN")
                .antMatchers("/profile", "/profile/user/*").hasRole( "USER")
                .antMatchers("/category/adm/*").hasRole( "ADMIN")
                .antMatchers("/category/public/*").hasRole( "USER")
                .antMatchers("/video_like", "/video_like/*").permitAll()
                .antMatchers("/video/public/*","/video/public").permitAll()
                .antMatchers("/video/adm/*").hasRole( "ADMIN")
                .antMatchers("/video/adnOwn/*").hasAnyRole("ADMIN")
                .antMatchers("/video/publisher/*").hasRole( "PUBLISHER")
                .antMatchers("/video/mod/*").hasRole( "MODERATOR")
                .antMatchers("/videoTag/userOwn/*").hasAnyRole("USER")
                .antMatchers("/videoTag/public/*").permitAll()
                .antMatchers("/comment/adm/*").hasRole( "ADMIN")
                .antMatchers("/comment/user/**").hasRole( "USER")
                .antMatchers("/comment/public/*").permitAll()
                .antMatchers("/comment_like", "/video_like/*").permitAll()
                .antMatchers("/attach/all/**").permitAll()
                .antMatchers("/attach/admUser/*").hasAnyRole("USER", "ADMIN")
                .antMatchers("/sms/adm/*").hasRole( "ADMIN")
                .antMatchers("/admin", "/admin/*").hasRole("ADMIN")
                .antMatchers("/auth", "/auth/*").permitAll()
                .antMatchers("/init", "/init/*").permitAll()
                .antMatchers("/channel/public/**").permitAll()
                .antMatchers("/channel/user/*").hasRole("USER")
                .antMatchers("/channel/ownUser/**").hasAnyRole("USER")
                .antMatchers("/channel/AOU/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/channel/admin").hasRole("ADMIN")
                .antMatchers("/playlist/ownUser/*").hasAnyRole("USER")
                .antMatchers("/playlist/AOU/*").hasAnyRole("USER", "ADMIN")
                .antMatchers("/playlist/OU/*").hasAnyRole("USER")
                .antMatchers("/playlistVideo/user/*").hasAnyRole("USER")
                .antMatchers("/playlist/admin").hasRole("ADMIN")
                .antMatchers("/playlist/public/**").permitAll()
                .antMatchers("/video/user/*").hasRole("USER")
                .antMatchers("/video/ownUser/**").hasAnyRole("USER")
                .antMatchers("/subscription/adm").hasRole("ADMIN")
                .antMatchers("/subscription/user/*").hasAnyRole("USER")
                .antMatchers("/subscription/user/**").hasAnyRole("USER")
                .antMatchers("/subscription/public/*").permitAll()
                .antMatchers("/tag/list").permitAll()
                .antMatchers("/tag/adm/*").hasRole("ADMIN")
                .antMatchers("/video_watch/create").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors().disable();
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String md5 = MD5Util.getMd5(rawPassword.toString());
                return md5.equals(encodedPassword);
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
