package tiw1.maintenance.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractDispatcherServletInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationInitializer.class);


    @Override
    protected WebApplicationContext createServletApplicationContext() {
        // Load Spring web application configuration
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        ac.register(AppConfig.class);
        return ac;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return new AnnotationConfigWebApplicationContext();
    }
}