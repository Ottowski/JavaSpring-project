package org.springframework.security.config.annotation.web.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public abstract class EnableWebSecurityConfigurerAdapter {

    protected abstract void authenticationManagerBean() throws Exception;

    protected abstract void configure(HttpSecurity http) throws Exception;
}
