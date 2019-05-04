package usemam.clc.dataaccess;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.rules.ExternalResource;

import java.io.IOException;
import java.net.ServerSocket;

public class LocalDynamoCreationRule extends ExternalResource {

    private DynamoDBProxyServer server;

    public static String port;

    public LocalDynamoCreationRule() {
        System.setProperty("sqlite4java.library.path", "native-libs");
    }

    @Override
    protected void before() throws Exception {
        port = getAvailablePort();
        server = ServerRunner.createServerFromCommandLineArgs(new String[] {"-inMemory", "-port", port});
        server.start();
    }

    @Override
    protected void after() {
        try {
            server.stop();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected String getAvailablePort() throws IOException {
        ServerSocket serverSocket = new ServerSocket(0);
        return String.valueOf(serverSocket.getLocalPort());
    }
}
