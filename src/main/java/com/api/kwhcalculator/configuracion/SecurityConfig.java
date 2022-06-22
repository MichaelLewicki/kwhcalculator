package com.api.kwhcalculator.configuracion;

import com.api.kwhcalculator.seguridad.CustomUserDetailsService;
import com.api.kwhcalculator.seguridad.JwtAuthenticationEntryPoint;
import com.api.kwhcalculator.seguridad.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Inyectar dependencias

    @Autowired
    private CustomUserDetailsService userDetailsService;

    //esto se encarga de generar error de no autorizado
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        //esto retorna una nueva instancia del authenticationFilter
        return new JwtAuthenticationFilter();
    }

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    //donde se aplique esto se obliga a hashear las contraseñas, ya que retorna una contraseña hasheada
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //acá configuramos entre tantas cosas que todos los usuarios podrán realizar peticiones GET
    //si necesita realizar otro tipo de Request, deben estar registrados y autenticados
    //en el siguiente método se crearán un par de usuarios para realizar estas operaciones
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //csrf se desactiva porque spring boot ya tiene integrado un bloqueo de ataques con CSRF
        http.cors();
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)//se asigna porque quien será quien libere las excepciones será este componente
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//política de creación de sesión es Stateless porque es SIN ESTADO
                .and()
                .authorizeRequests()//desde aquí se indicarán quienes podrán realizar peticiones
                .antMatchers(HttpMethod.GET,"/api/**").permitAll()//todos pueden hacer peticiones GET
                .antMatchers("/api/auth/**").permitAll()//todos pueden autenticarse
                .antMatchers(AUTH_WHITELIST).permitAll()//para que funcione swagger
                .anyRequest().authenticated();//cualquier otra petición debe estar autenticada
                //esto se quitó para realizar peticiones en Postman con un Token
                //.and()
                //.httpBasic();
        //asignar el filtro del token
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    //Crear método de autenticación de usuarios
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /* esto era así antes de implementar roles en los usuarios
    //acá crearemos un par de usuarios que se guardarán en memoria
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails victor = User.builder().username("victor").password(passwordEncoder().encode("password"))
                .roles("USER").build();
        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("password"))
                .roles("ADMIN").build();
        //acá retornamos los usuarios creados en memoria
        return new InMemoryUserDetailsManager(victor, admin);
    }
    */
}
