package ru.lenoblgis.introduse.sergey.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.services.EventService;
import ru.lenoblgis.introduse.sergey.services.OwnerService;
import ru.lenoblgis.introduse.sergey.services.PassportService;
import ru.lenoblgis.introduse.sergey.services.UserDetailsServiceImpl;
import ru.lenoblgis.introduse.sergey.services.UserService;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

@Configuration
@EnableWebMvc
@ComponentScan({"ru.lenoblgis.introduse.sergey.controllers", "ru.lenoblgis.introduse.sergey.data.dao"})
public class WebAppConfig extends WebMvcConfigurerAdapter {
 
    /**
     * ѕозвол€ет видеть все ресурсы в папке views, такие как картинки, стили и т.п.
     */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/views/**").addResourceLocations("/views/");
    }
 
	/**
	 * Ётот бин инициализирует View нашего проекта (альтернатива в mvc-dispatcher-servlet.xml)
	 * @return
	 */
    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
 
        return resolver;
    }
    
    
    /**
     * ѕолучение бина DAO
     * @return - DAO
     */
    @Bean
    DAO getDAO() {
        return new DAO();
    }
    
    /**
     * ѕолучение бина сервиса работы с организаци€ми
     * @return - сервис рабыты с организаци€ми
     */
    @Bean
    OwnerService getOwnerService() {
        return new OwnerService();
    }
    
    /**
     * ѕолучение бина сервиса работы с паспортами полей
     * @return - сервис работы с паспортами полей
     */
    @Bean
    PassportService getPassportService(){
    	return new PassportService();
    }
    
    /**
     * ѕолучение бина сервиса работы с журналом событий
     * @return - сервис работы с журналом событий
     */
    @Bean
    EventService getEventService(){
    	return new EventService();
    }
    
    /**
     * ѕолучение бина сервиса работы с пользовател€ми
     * @return - сервис работы с пользовател€ми
     */
    @Bean
    UserService getUserService(){
    	return new UserService();
    }
    
    /**
     * ѕолучение бина соединени€ с Ѕƒ
     * @return - соединение с Ѕƒ
     */
    @Bean
	public DataSource getDataSource() {
    	SQLServerDataSource ds = new SQLServerDataSource();
		ds.setPortNumber(1433);
		ds.setHostNameInCertificate("localhost");
		ds.setDatabaseName("passport_agricultural");
		ds.setUser("adminAgricultural");
		ds.setPassword("admin123");
		return ds;
    }
    
    /**
     * ѕолучение бина сервиса работы с пользовател€ми - дл€ аутентификации через spring security
     * @return - сервис работы с пользовател€ми - дл€ аутентификации через spring security
     */
    @Bean
    public UserDetailsService getUserDetailsService(){
        return new UserDetailsServiceImpl();
    }
 
}
