package ru.poplaukhin.springcourse.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() { // для конфигурации "корневого" контекста приложения (не веб-инфраструктуры).
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() { // для DispatcherServlet конфигурации контекста приложения (инфраструктура Spring MVC).
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() { // Указываем отображение(я) сервлета для DispatcherServlet — например "/", "/app", и т. д.
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null, true, "/*");
    }
}
