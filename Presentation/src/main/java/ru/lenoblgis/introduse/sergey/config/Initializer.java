package ru.lenoblgis.introduse.sergey.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;

/**
 * Класс инициализирующий приложение
 * @author VILGODSKIY
 *
 */
public class Initializer implements WebApplicationInitializer{

	 // Указываем имя нашему Servlet Dispatcher для мапинга
    private static final String DISPATCHER_SERVLET_NAME = "dispatcherAgrocultural";
 

    /*
     * * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext)
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
 
        // Регистрируем в контексте конфигурационный класс, который мы создадим ниже
        ctx.register(WebAppConfig.class);
        ctx.register(SecurityConfig.class);
        servletContext.addListener(new ContextLoaderListener(ctx));
 
        ctx.setServletContext(servletContext);
 
        ServletRegistration.Dynamic servlet = servletContext.addServlet(DISPATCHER_SERVLET_NAME, 
                                                                        new DispatcherServlet(ctx));
        initLog4j(servletContext);
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
    }
    
    /**
     * Инициализация Log4J
     * @param servletContext - контекст сервлетов
     */
    private void initLog4j(ServletContext servletContext){
    	servletContext.setInitParameter( "log4jConfigLocation" , "/WEB-INF/log4j.properties" );
		servletContext.setInitParameter( "log4jRefreshInterval" , "10000" );
		servletContext.setInitParameter( "log4jExposeWebAppRoot", "false" );
		Log4jConfigListener log4jListener = new Log4jConfigListener();
		servletContext.addListener( log4jListener );
    }

}
