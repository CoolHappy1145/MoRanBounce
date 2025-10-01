package org.apache.log4j.net;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/* loaded from: L-out.jar:org/apache/log4j/net/SimpleSocketServer.class */
public class SimpleSocketServer {
    static Logger cat;
    static int port;
    static Class class$org$apache$log4j$net$SimpleSocketServer;

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$net$SimpleSocketServer == null) {
            clsClass$ = class$("org.apache.log4j.net.SimpleSocketServer");
            class$org$apache$log4j$net$SimpleSocketServer = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$net$SimpleSocketServer;
        }
        cat = Logger.getLogger(clsClass$);
    }

    public static void main(String[] strArr) throws Throwable {
        if (strArr.length == 2) {
            init(strArr[0], strArr[1]);
        } else {
            usage("Wrong number of arguments.");
        }
        cat.info(new StringBuffer().append("Listening on port ").append(port).toString());
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            cat.info("Waiting to accept a new client.");
            Socket socketAccept = serverSocket.accept();
            cat.info(new StringBuffer().append("Connected to client at ").append(socketAccept.getInetAddress()).toString());
            cat.info("Starting new socket node.");
            new Thread(new SocketNode(socketAccept, LogManager.getLoggerRepository()), new StringBuffer().append("SimpleSocketServer-").append(port).toString()).start();
        }
    }

    static void usage(String str) throws Throwable {
        Class clsClass$;
        System.err.println(str);
        PrintStream printStream = System.err;
        StringBuffer stringBufferAppend = new StringBuffer().append("Usage: java ");
        if (class$org$apache$log4j$net$SimpleSocketServer == null) {
            clsClass$ = class$("org.apache.log4j.net.SimpleSocketServer");
            class$org$apache$log4j$net$SimpleSocketServer = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$net$SimpleSocketServer;
        }
        printStream.println(stringBufferAppend.append(clsClass$.getName()).append(" port configFile").toString());
        System.exit(1);
    }

    static void init(String str, String str2) throws Throwable {
        try {
            port = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            usage(new StringBuffer().append("Could not interpret port number [").append(str).append("].").toString());
        }
        if (str2.endsWith(".xml")) {
            DOMConfigurator.configure(str2);
        } else {
            PropertyConfigurator.configure(str2);
        }
    }
}
