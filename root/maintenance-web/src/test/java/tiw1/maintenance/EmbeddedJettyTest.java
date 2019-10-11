package tiw1.maintenance;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;
import tiw1.maintenance.spring.ApplicationInitializer;

import java.io.InputStream;
import java.net.URI;

public class EmbeddedJettyTest {

    private static final Logger LOG = LoggerFactory.getLogger(EmbeddedJettyTest.class);

    /**
     * This test can be used as basis for integration testing with the maintenance Web server
     */
    @Test
    public void startStopServerTest() throws Exception {
        Server server = new Server(0);

        // Configure Spring using the spring dispatcher servlet
        ApplicationInitializer initializer = new ApplicationInitializer();
        DispatcherServlet springServlet = new DispatcherServlet();
        springServlet.setApplicationContext(initializer.createServletApplicationContext());

        // Enable servlet in Jetty
        ServletContextHandler contextHandler = new ServletContextHandler();
        server.setHandler(contextHandler);
        contextHandler.addServlet(new ServletHolder("springDispatcher", springServlet), "/");
        LOG.info("ContextPath: {}", contextHandler.getServletContext().getContextPath());

        server.start();
        ServerConnector connector = (ServerConnector) server.getConnectors()[0];
        String host = "localhost";
        int port = connector.getLocalPort();
        URI uri = new URI("http://" + host + ":" + port + "/trottinette");
        LOG.info("URI: {}", uri);
        InputStream s = uri.toURL().openStream();
        s.close();
        server.stop();
    }
}
