package server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import server.jsersey.servlet.MyEndPoint;
import server.jsersey.servlet.WelcomeServlet;

/**
 * Hello world!
 */
public class JettyServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(WelcomeServlet.class, "/welcome");

        //server.setHandler(handler);
        server.setHandler(getJerseyHandler());

        server.start();
        server.join();
    }

    private static Handler getJerseyHandler() {
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        handler.setContextPath("/");

        ServletHolder holder = handler.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/api/*");
        holder.setInitOrder(0);
        holder.setInitParameter("jersey.config.server.provider.classnames", MyEndPoint.class.getCanonicalName());


        return handler;
    }

}
