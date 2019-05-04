package usemam.clc.parser;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.rules.ExternalResource;

public class HttpServer extends ExternalResource {

    private final int port;

    private Server server;

    public HttpServer(int port) {
        this.port = port;
    }

    @Override
    public void before() throws Throwable {
        server = new Server(port);
        WebAppContext webApp = new WebAppContext();
        webApp.setContextPath("/");
        webApp.setResourceBase("src/test/java/usemam/clc/parser");
        webApp.setParentLoaderPriority(true);

        server.setHandler(webApp);
        server.start();
    }

    @Override
    public void after() {
        try {
            server.stop();
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
