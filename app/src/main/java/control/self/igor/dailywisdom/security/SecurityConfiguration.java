package control.self.igor.dailywisdom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import control.self.igor.dailywisdom.service.abstraction.JsonService;
import control.self.igor.dailywisdom.service.abstraction.StreamService;
import control.self.igor.dailywisdom.service.abstraction.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JsonService jsonService;
    private StreamService streamService;

    @Autowired
    public SecurityConfiguration(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder,
	    JsonService jsonService, StreamService streamService) {
	this.userService = userService;
	this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	this.jsonService = jsonService;
	this.streamService = streamService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
	httpSecurity.cors().and().csrf().disable().authorizeRequests()
		.antMatchers("/data/**", SecurityConstants.SIGN_UP_URL, SecurityConstants.LOGIN_URL).permitAll()
		.anyRequest().authenticated().and()
		.addFilter(
			new JwtAuthenticationFilter(authenticationManager(), userService, jsonService, streamService))
		.addFilter(new JwtAuthorizationFilter(authenticationManager(), streamService)).sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
	builder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    // @Bean
    // CorsConfigurationSource corsConfigurationSource() {
    // CorsConfiguration configuration = new CorsConfiguration();
    // configuration.setAllowedOrigins(Arrays.asList("*"));
    // configuration.setAllowedHeaders(Arrays.asList("*"));
    // configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();
    // source.registerCorsConfiguration("/**", configuration);
    // return source;
    // }
}
