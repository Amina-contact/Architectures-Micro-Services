<h1>Microservices Security with Keycloak</h1>
<h1>Part 1 : </h1>
<li>Download Keycloak 19</strong>:</li><br>
<img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k1.JPG">

<li>Start Keycloak with </strong>: <code> kc.bat start-dev</code></li><br>
<img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k2.JPG">

<li>Create an Admin account</strong>:</li><br>
<img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k3.JPG">
<p align="center">
  <img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k4.JPG" class="center">
</p>
<p align="center">
  <img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k5.JPG" class="center">
</p>
<li>Create a Realm </strong>:</li><br>
<img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k6.JPG">
<img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k7.JPG">
<li>Create a client to secure </strong>:</li><br>
<img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k8.JPG">
<li>Create users </strong>:</li><br>
<img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k9.JPG">
<p align="center">
  <img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k10.JPG">
</p>
<li>Create roles </strong>:</li><br>
<p align="center">
  <img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/K11.JPG">
</p>
<p align="center">
  <img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k12.JPG">
</p>
<li>Assign roles to users </strong>:</li><br>
<img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k13.JPG">
<img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k14.JPG">
<li>With PostMan </strong>:</li><br>
<pre class="notranslate">
- Test authentication with password
</code></pre><br>
<img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/kp1.JPG">
<img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k16.JPG">
<pre class="notranslate">
- Analyze the contents of the two JWT Access Token and Refresh Token
</code></pre><br>
<p align="center">
  <img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k17.JPG">
</p>
<p align="center">
  <img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k18.JPG">
</p>
<pre class="notranslate">
- Test authentication with the Refresh Token
</code></pre><br>
<p align="center">
  <img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k19.JPG">
</p>
<pre class="notranslate">
- Test authentication with Client ID and Client Secret
</code></pre><br>
<p align="center">
  <img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/k20.JPG">
</p>
<h1>Part 2 : </h1>
<h3>Secure the Micro services architecture of the Customer-service, Inventory-service and Order-service project in the project <a href="https://github.com/Amina-contact/e-commerce-Micro-services-Spring-Angular"> E-commerce Micro-Services Spring|Angular</a></h3>
<li>Add dependencies at pom.xml</strong>:</li>
<pre class="notranslate"><code>
 -spring-boot-starter-security
 -keycloak-spring-boot-starter   
</code></pre>
<li>Create keycloakAdapterConfig</strong>:</li>
<pre class="notranslate"><code>
@Configuration
public class keycloakAdapterConfig {
    @Bean
    KeycloakSpringBootConfigResolver springBootConfigResolver(){
        return new KeycloakSpringBootConfigResolver();
    }
}  
</code></pre>
<li>Create Security Config</strong>:</li>
<pre class="notranslate"><code>
@KeycloakConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        super.configure(http);
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/h2-consol/**").permitAll();
        http.headers().frameOptions().disable();
        http.authorizeRequests().anyRequest().authenticated();
    }
} 
</code></pre>
<li>Configure authorization</strong>: <code>@PreAuthorize("hasAuthority('ADMIN')")</code> in the Customer Controller</li><br>
<li>Testing with ADMIN_Role</strong>:</li><br>
<p align="center">
  <img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/kp1.JPG">
  <img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/kp2.JPG">
</p>
<li>Testing with USER_Role</strong>:</li><br>
<p align="center">
  <img src="https://github.com/Amina-contact/Microservices-Security-with-Keycloak/blob/master/pictures/kp3.JPG">
</p>
