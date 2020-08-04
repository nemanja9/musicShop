/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.config;

import java.util.Properties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 *
 * @author lj
 */

@Configuration
@Import(DatabaseConfiguration.class)
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan(basePackages = {
    "fon.silab.web.an.ainmusicshop.controller",
    "fon.silab.web.an.ainmusicshop.repository",
    "fon.silab.web.an.ainmusicshop.service",
    "fon.silab.web.an.ainmusicshop.validator"
})
public class ContextConfiguration{
    
    
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
        viewResolve.setPrefix("/WEB-INF/pages/");
        viewResolve.setSuffix(".jsp");

        return viewResolve;
    }
    
    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("validation");
        return messageSource;
    }
    
    @Bean(name="multipartResolver")
    public StandardServletMultipartResolver resolver(){
        return new StandardServletMultipartResolver();
    }
    
    @Bean
    public JavaMailSenderImpl mailSender(){
        
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
          
        mailSender.setUsername("musicshopan@gmail.com");
        mailSender.setPassword("andjela997");
          
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("spring.mail.properties.mail.smtp.ssl.enable", "true");
        props.put("spring.mail.properties.mail.smtp.starttls.enable", "true");
        props.put("spring.mail.properties.mail.smtp.starttls.required", "true");
        props.put("spring.mail.properties.mail.smtp.auth", "true");
        props.put("mail.smtp.ssl", "true");
          
        mailSender.setJavaMailProperties(props);
        return mailSender;
    }
}
