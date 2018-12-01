package server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import server.jsersey.servlet.CourseServlet;
import server.jsersey.servlet.InstructorServlet;
import server.jsersey.servlet.SelectionServlet;
import server.jsersey.servlet.StudentServlet;

/**
 * JettyServer can run Jetty embedded web server to handle http request and mapping request to jersey servlet
 */
public class JettyServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8081);

        server.setHandler(getJerseyHandler());

        server.start();
        server.join();
    }

    /**
     * Generate ServletContextHandler to mapping routing path with Jersey servlet
     * @return Jersey handler
     */
    private static Handler getJerseyHandler() {
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        handler.setContextPath("/");

        ServletHolder studentHolder = handler.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/student/*");
        studentHolder.setInitOrder(0);
        studentHolder.setInitParameter("jersey.config.server.provider.classnames", StudentServlet.class.getCanonicalName());

        ServletHolder instructorHolder = handler.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/instructor/*");
        instructorHolder.setInitOrder(0);
        instructorHolder.setInitParameter("jersey.config.server.provider.classnames", InstructorServlet.class.getCanonicalName());

        ServletHolder courseHolder = handler.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/course/*");
        courseHolder.setInitOrder(0);
        courseHolder.setInitParameter("jersey.config.server.provider.classnames", CourseServlet.class.getCanonicalName());

        ServletHolder selectionHolder = handler.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/selection/*");
        selectionHolder.setInitOrder(0);
        selectionHolder.setInitParameter("jersey.config.server.provider.classnames", SelectionServlet.class.getCanonicalName());


        return handler;
    }

}
