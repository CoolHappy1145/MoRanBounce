package org.apache.log4j.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.xml.DOMConfigurator;

/* loaded from: L-out.jar:org/apache/log4j/net/JMSSink.class */
public class JMSSink implements MessageListener {
    static Logger logger;
    static Class class$org$apache$log4j$net$JMSSink;

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$net$JMSSink == null) {
            clsClass$ = class$("org.apache.log4j.net.JMSSink");
            class$org$apache$log4j$net$JMSSink = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$net$JMSSink;
        }
        logger = Logger.getLogger(clsClass$);
    }

    public static void main(String[] strArr) throws Throwable {
        if (strArr.length != 5) {
            usage("Wrong number of arguments.");
        }
        String str = strArr[0];
        String str2 = strArr[1];
        String str3 = strArr[2];
        String str4 = strArr[3];
        String str5 = strArr[4];
        if (str5.endsWith(".xml")) {
            DOMConfigurator.configure(str5);
        } else {
            PropertyConfigurator.configure(str5);
        }
        new JMSSink(str, str2, str3, str4);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type \"exit\" to quit JMSSink.");
        while (!bufferedReader.readLine().equalsIgnoreCase("exit")) {
        }
        System.out.println("Exiting. Kill the application if it does not exit due to daemon threads.");
    }

    public JMSSink(String str, String str2, String str3, String str4) {
        try {
            InitialContext initialContext = new InitialContext();
            TopicConnection topicConnectionCreateTopicConnection = ((TopicConnectionFactory) lookup(initialContext, str)).createTopicConnection(str3, str4);
            topicConnectionCreateTopicConnection.start();
            topicConnectionCreateTopicConnection.createTopicSession(false, 1).createSubscriber((Topic) initialContext.lookup(str2)).setMessageListener(this);
        } catch (JMSException e) {
            logger.error("Could not read JMS message.", e);
        } catch (RuntimeException e2) {
            logger.error("Could not read JMS message.", e2);
        } catch (NamingException e3) {
            logger.error("Could not read JMS message.", e3);
        }
    }

    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                LoggingEvent loggingEvent = (LoggingEvent) ((ObjectMessage) message).getObject();
                Logger.getLogger(loggingEvent.getLoggerName()).callAppenders(loggingEvent);
            } else {
                logger.warn(new StringBuffer().append("Received message is of type ").append(message.getJMSType()).append(", was expecting ObjectMessage.").toString());
            }
        } catch (JMSException e) {
            logger.error("Exception thrown while processing incoming message.", e);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.naming.NameNotFoundException */
    protected static Object lookup(Context context, String str) throws NameNotFoundException {
        try {
            return context.lookup(str);
        } catch (NameNotFoundException e) {
            logger.error(new StringBuffer().append("Could not find name [").append(str).append("].").toString());
            throw e;
        }
    }

    static void usage(String str) throws Throwable {
        Class clsClass$;
        System.err.println(str);
        PrintStream printStream = System.err;
        StringBuffer stringBufferAppend = new StringBuffer().append("Usage: java ");
        if (class$org$apache$log4j$net$JMSSink == null) {
            clsClass$ = class$("org.apache.log4j.net.JMSSink");
            class$org$apache$log4j$net$JMSSink = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$net$JMSSink;
        }
        printStream.println(stringBufferAppend.append(clsClass$.getName()).append(" TopicConnectionFactoryBindingName TopicBindingName username password configFile").toString());
        System.exit(1);
    }
}
