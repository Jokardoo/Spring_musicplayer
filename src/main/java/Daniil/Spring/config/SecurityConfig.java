package Daniil.Spring.config;

//import Daniil.Spring.Services.PersonDetailsService;
//import Daniil.Spring.security.AuthProviderImpl;
import Daniil.Spring.Services.PersonDetailsService;
import Daniil.Spring.security.AuthProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthProviderImpl authProvider;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider, PersonDetailsService personDetailsService) {
        this.authProvider = authProvider;
        this.personDetailsService = personDetailsService;
    }

    // настраивает аутентификацию
    // в более простом случае достаточно передать UserDetailsService для аутентификации.
    // Если у нас кастомная аутентификация, то можно использовать AuthProvider (т.к.
    // логика аутентификации достаточно простая, то использовать AuthProvider обычно не имеет смысла

    // НО при использовании UserDetailsService нам необходимо указать способ шифровки пароля
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authProvider);
        // Для перевода пароля в зашифрованную версию используем реализованный нами
        // метод passwordEncoder()
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());

    }

    @Bean   // Шифрование
    public PasswordEncoder getPasswordEncoder() {
        //в таком случае пароль никак не шифруется
        return new BCryptPasswordEncoder();
    }

    // конфигурируем авторизацию
    // метод hasRole() указывает, пользователь с какой ролью имеет доступ к данной странице
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/auth/login","/auth/registration",  "error").permitAll() // все перечисленные ссылки доступны всем
                .anyRequest().hasAnyRole("USER", "ADMIN")   // все остальные ссылки только для ролей USER и ADMIN
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/find-page", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login");
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//
//
//        return http.build();
//    }
}
