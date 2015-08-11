package com.bjcre.server;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebStart {
    private final static Logger logger = Logger.getLogger(WebStart.class);

    private static String CONTEXT_PATH = "/test";
	private static int PORT = 8080;
	private static String WAR = "src/main/webapp";

    public static void main(String[] args) {

        logger.info("args:" + Arrays.toString(args));

        if (args.length > 0) {
            try {
                PORT = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                logger.error("PORT error!");
            }
        }
        if (args.length > 1) {
            CONTEXT_PATH = args[1];
        }
        if (args.length > 2) {
            WAR = args[2];
        }
        logger.info("CONTEXT_PATH:" + CONTEXT_PATH + " PORT:" + PORT + " WAR:"
                + WAR);

        start(PORT, CONTEXT_PATH, WAR);
    }

    public static Server start(int port, String contextPath, String war) {
        final Server server = new Server(port);

        WebAppContext bb = new WebAppContext();
        bb.setServer(server);
        bb.setContextPath(contextPath);
        bb.setWar(war);
        server.setHandler(bb);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    server.stop();
                    server.join();
                    logger.info(">>> server is stoped.");
                } catch (Exception e) {
                    logger.error("server.stop() join() error", e);
                    System.exit(100);
                }
            }
        });

        try {
            server.start();
        } catch (Exception e) {
            logger.error("server.start() error.", e);
        }

        return server;
    }
}
