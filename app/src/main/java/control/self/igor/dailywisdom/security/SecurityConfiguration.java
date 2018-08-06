package control.self.igor.dailywisdom.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import control.self.igor.dailywisdom.service.json.JsonService;
import control.self.igor.dailywisdom.service.stream.StreamService;
import control.self.igor.dailywisdom.service.user.TokenService;
import control.self.igor.dailywisdom.service.user.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JsonService jsonService;
    private TokenService tokenService;
    private StreamService streamService;

    public SecurityConfiguration(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder,
	    JsonService jsonService, TokenService tokenService, StreamService streamService) {
	this.userService = userService;
	this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	this.jsonService = jsonService;
	this.tokenService = tokenService;
	this.streamService = streamService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
	httpSecurity.cors().and().csrf().disable().authorizeRequests()
		.antMatchers(SecurityConstants.PUBLIC_ENDPOINTS_PATTERNS).permitAll().anyRequest().authenticated().and()
		.addFilter(new JwtAuthenticationFilter(authenticationManager(), userService, jsonService, tokenService,
			streamService))
		.addFilter(new JwtAuthorizationFilter(authenticationManager(), streamService)).sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
		.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
	builder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
