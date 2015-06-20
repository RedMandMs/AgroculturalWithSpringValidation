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
import ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries;
import ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLServerQueries;
import ru.lenoblgis.introduse.sergey.domen.mappers.EventRowMapper;
import ru.lenoblgis.introduse.sergey.domen.mappers.OrganizationRowMapper;
import ru.lenoblgis.introduse.sergey.domen.mappers.PassportRowMapper;
import ru.lenoblgis.introduse.sergey.domen.mappers.UserRowMapper;
import ru.lenoblgis.introduse.sergey.services.EventService;
import ru.lenoblgis.introduse.sergey.services.OwnerService;
import ru.lenoblgis.introduse.sergey.services.PassportService;
import ru.lenoblgis.introduse.sergey.services.UserDetailsServiceImpl;
import ru.lenoblgis.introduse.sergey.services.UserService;
import ru.lenoblgis.introduse.sergey.validation.PassportValidator;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

@Configuration
@EnableWebMvc
@ComponentScan({"ru.lenoblgis.introduse.sergey.controllers",
					"ru.lenoblgis.introduse.sergey.data.dao",
					"ru.lenoblgis.introduse.sergey.domen.mappers"})
public class WebAppConfig extends WebMvcConfigurerAdapter {
 
    /**
     * Позволяет видеть все ресурсы в папке views, такие как картинки, стили и т.п.
     */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/views/**").addResourceLocations("/views/");
    }
 
	/**
	 * Этот бин инициализирует View нашего проекта (альтернатива в mvc-dispatcher-servlet.xml)
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
     * Получение бина DAO
     * @return - DAO
     */
    @Bean
    public DAO getDAO() {
        return new DAO();
    }
    
    /**
     * Получение бина предоставляющего sql-запросы для DAO
     * @return - бин предоставляющий sql-запросы для DAO
     */
    @Bean
    public SQLQueries getSQLQueries(){
    	return new SQLServerQueries();
    }
    
    /**
     * Объект для отображения пасспорта из БД в программное представляение (используется DAO)
     * @return - маппер для паспорта
     */
    @Bean
    public PassportRowMapper getPassportRowMapper(){
    	return new PassportRowMapper();
    }
    
    /**
     * Объект для отображения событий из БД в программное представляение (используется DAO)
     * @return - маппер для событий
     */
    @Bean
    public EventRowMapper getEventRowMapper(){
    	return new EventRowMapper();
    }
    
    /**
     * Объект для отображения организаций из БД в программное представляение (используется DAO)
     * @return - маппер для организаций
     */
    @Bean
    public OrganizationRowMapper getOrganizationRowMapper(){
    	return new OrganizationRowMapper();
    }
    
    /**
     * Объект для отображения пользователей из БД в программное представляение (используется DAO)
     * @return - маппер для пользователей
     */
    @Bean
    public UserRowMapper getUserRowMapper(){
    	return new UserRowMapper();
    }
    
    /**
     * Получение бина сервиса работы с организациями
     * @return - сервис рабыты с организациями
     */
    @Bean
    public OwnerService getOwnerService() {
        return new OwnerService();
    }
    
    /**
     * Получение бина сервиса работы с паспортами полей
     * @return - сервис работы с паспортами полей
     */
    @Bean
    public PassportService getPassportService(){
    	return new PassportService();
    }
    
    /**
     * Получение бина сервиса работы с журналом событий
     * @return - сервис работы с журналом событий
     */
    @Bean
    public EventService getEventService(){
    	return new EventService();
    }
    
    /**
     * Получение бина сервиса работы с пользователями
     * @return - сервис работы с пользователями
     */
    @Bean
    public UserService getUserService(){
    	return new UserService();
    }
    
    /**
     * Получение бина соединения с БД
     * @return - соединение с БД
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
     * Получение бина сервиса работы с пользователями - для аутентификации через spring security
     * @return - сервис работы с пользователями - для аутентификации через spring security
     */
    @Bean
    public UserDetailsService getUserDetailsService(){
        return new UserDetailsServiceImpl();
    }
    
    /**
     * Получение бина валидатора введённых данных о пасспорте, при его создании или регистрации
     * @return
     */
    @Bean
    public PassportValidator getPassportValidator(){
    	return new PassportValidator();
    }

}
