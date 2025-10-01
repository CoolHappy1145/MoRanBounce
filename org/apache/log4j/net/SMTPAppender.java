package org.apache.log4j.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.CyclicBuffer;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.OptionHandler;
import org.apache.log4j.spi.TriggeringEventEvaluator;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.xml.UnrecognizedElementHandler;
import org.w3c.dom.Element;

/* loaded from: L-out.jar:org/apache/log4j/net/SMTPAppender.class */
public class SMTPAppender extends AppenderSkeleton implements UnrecognizedElementHandler {

    /* renamed from: to */
    private String f196to;

    /* renamed from: cc */
    private String f197cc;
    private String bcc;
    private String from;
    private String replyTo;
    private String subject;
    private String smtpHost;
    private String smtpUsername;
    private String smtpPassword;
    private String smtpProtocol;
    private int smtpPort;
    private boolean smtpDebug;
    private int bufferSize;
    private boolean locationInfo;
    private boolean sendOnClose;

    /* renamed from: cb */
    protected CyclicBuffer f198cb;
    protected Message msg;
    protected TriggeringEventEvaluator evaluator;
    static Class class$org$apache$log4j$spi$TriggeringEventEvaluator;

    public SMTPAppender() {
        this(new DefaultEvaluator());
    }

    public SMTPAppender(TriggeringEventEvaluator triggeringEventEvaluator) {
        this.smtpPort = -1;
        this.smtpDebug = false;
        this.bufferSize = 512;
        this.locationInfo = false;
        this.sendOnClose = false;
        this.f198cb = new CyclicBuffer(this.bufferSize);
        this.evaluator = triggeringEventEvaluator;
    }

    @Override // org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
        this.msg = new MimeMessage(createSession());
        try {
            addressMessage(this.msg);
            if (this.subject != null) {
                try {
                    this.msg.setSubject(MimeUtility.encodeText(this.subject, "UTF-8", (String) null));
                } catch (UnsupportedEncodingException e) {
                    LogLog.error("Unable to encode SMTP subject", e);
                }
            }
        } catch (MessagingException e2) {
            LogLog.error("Could not activate SMTPAppender options.", e2);
        }
        if (this.evaluator instanceof OptionHandler) {
            ((OptionHandler) this.evaluator).activateOptions();
        }
    }

    protected void addressMessage(Message message) {
        if (this.from != null) {
            message.setFrom(getAddress(this.from));
        } else {
            message.setFrom();
        }
        if (this.replyTo != null && this.replyTo.length() > 0) {
            message.setReplyTo(parseAddress(this.replyTo));
        }
        if (this.f196to != null && this.f196to.length() > 0) {
            message.setRecipients(Message.RecipientType.TO, parseAddress(this.f196to));
        }
        if (this.f197cc != null && this.f197cc.length() > 0) {
            message.setRecipients(Message.RecipientType.CC, parseAddress(this.f197cc));
        }
        if (this.bcc != null && this.bcc.length() > 0) {
            message.setRecipients(Message.RecipientType.BCC, parseAddress(this.bcc));
        }
    }

    protected Session createSession() {
        Properties properties;
        try {
            properties = new Properties(System.getProperties());
        } catch (SecurityException unused) {
            properties = new Properties();
        }
        String string = "mail.smtp";
        if (this.smtpProtocol != null) {
            properties.put("mail.transport.protocol", this.smtpProtocol);
            string = new StringBuffer().append("mail.").append(this.smtpProtocol).toString();
        }
        if (this.smtpHost != null) {
            properties.put(new StringBuffer().append(string).append(".host").toString(), this.smtpHost);
        }
        if (this.smtpPort > 0) {
            properties.put(new StringBuffer().append(string).append(".port").toString(), String.valueOf(this.smtpPort));
        }
        Authenticator authenticator = null;
        if (this.smtpPassword != null && this.smtpUsername != null) {
            properties.put(new StringBuffer().append(string).append(".auth").toString(), "true");
            authenticator = new Authenticator(this) { // from class: org.apache.log4j.net.SMTPAppender.1
                private final SMTPAppender this$0;

                {
                    this.this$0 = this;
                }

                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(this.this$0.smtpUsername, this.this$0.smtpPassword);
                }
            };
        }
        Session session = Session.getInstance(properties, authenticator);
        if (this.smtpProtocol != null) {
            session.setProtocolForAddress("rfc822", this.smtpProtocol);
        }
        if (this.smtpDebug) {
            session.setDebug(this.smtpDebug);
        }
        return session;
    }

    @Override // org.apache.log4j.AppenderSkeleton
    public void append(LoggingEvent loggingEvent) throws IOException {
        if (!checkEntryConditions()) {
            return;
        }
        loggingEvent.getThreadName();
        loggingEvent.getNDC();
        loggingEvent.getMDCCopy();
        if (this.locationInfo) {
            loggingEvent.getLocationInformation();
        }
        loggingEvent.getRenderedMessage();
        loggingEvent.getThrowableStrRep();
        this.f198cb.add(loggingEvent);
        if (this.evaluator.isTriggeringEvent(loggingEvent)) {
            sendBuffer();
        }
    }

    protected boolean checkEntryConditions() {
        if (this.msg == null) {
            this.errorHandler.error("Message object not configured.");
            return false;
        }
        if (this.evaluator == null) {
            this.errorHandler.error(new StringBuffer().append("No TriggeringEventEvaluator is set for appender [").append(this.name).append("].").toString());
            return false;
        }
        if (this.layout == null) {
            this.errorHandler.error(new StringBuffer().append("No layout set for appender named [").append(this.name).append("].").toString());
            return false;
        }
        return true;
    }

    @Override // org.apache.log4j.Appender
    public void close() throws IOException {
        this.closed = true;
        if (this.sendOnClose && this.f198cb.length() > 0) {
            sendBuffer();
        }
    }

    InternetAddress getAddress(String str) {
        try {
            return new InternetAddress(str);
        } catch (AddressException e) {
            this.errorHandler.error(new StringBuffer().append("Could not parse address [").append(str).append("].").toString(), e, 6);
            return null;
        }
    }

    InternetAddress[] parseAddress(String str) {
        try {
            return InternetAddress.parse(str, true);
        } catch (AddressException e) {
            this.errorHandler.error(new StringBuffer().append("Could not parse address [").append(str).append("].").toString(), e, 6);
            return null;
        }
    }

    public String getTo() {
        return this.f196to;
    }

    protected String formatBody() {
        String[] throwableStrRep;
        StringBuffer stringBuffer = new StringBuffer();
        Layout layout = this.layout;
        if (0 != 0) {
            stringBuffer.append((String) null);
        }
        int length = this.f198cb.length();
        for (int i = 0; i < length; i++) {
            LoggingEvent loggingEvent = this.f198cb.get();
            stringBuffer.append(this.layout.format(loggingEvent));
            if (this.layout.ignoresThrowable() && (throwableStrRep = loggingEvent.getThrowableStrRep()) != null) {
                for (String str : throwableStrRep) {
                    stringBuffer.append(str);
                    stringBuffer.append(Layout.LINE_SEP);
                }
            }
        }
        Layout layout2 = this.layout;
        if (0 != 0) {
            stringBuffer.append((String) null);
        }
        return stringBuffer.toString();
    }

    /* JADX WARN: Type inference failed for: r2v6, types: [java.lang.String, java.lang.StringBuffer] */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.lang.StringBuffer, org.apache.log4j.Layout] */
    protected void sendBuffer() throws IOException {
        MimeBodyPart mimeBodyPart;
        try {
            String body = formatBody();
            boolean z = true;
            for (int i = 0; i < body.length() && z; i++) {
                z = body.charAt(i) <= '\u007f';
            }
            if (z) {
                mimeBodyPart = new MimeBodyPart();
                body.setContent(this.layout, "text/plain");
            } else {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MimeUtility.encode(byteArrayOutputStream, "quoted-printable"), "UTF-8");
                    outputStreamWriter.write(body);
                    outputStreamWriter.close();
                    InternetHeaders internetHeaders = new InternetHeaders();
                    "Content-Type".setHeader((String) new StringBuffer(), this.layout.append("text/plain").append("; charset=UTF-8").toString());
                    internetHeaders.setHeader("Content-Transfer-Encoding", "quoted-printable");
                    mimeBodyPart = new MimeBodyPart(internetHeaders, byteArrayOutputStream.toByteArray());
                } catch (Exception unused) {
                    StringBuffer stringBuffer = new StringBuffer(body);
                    for (int i2 = 0; i2 < stringBuffer.length(); i2++) {
                        if (stringBuffer.charAt(i2) >= '\u0080') {
                            stringBuffer.setCharAt(i2, '?');
                        }
                    }
                    mimeBodyPart = new MimeBodyPart();
                    stringBuffer.toString().setContent(this.layout, "text/plain");
                }
            }
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(mimeBodyPart);
            this.msg.setContent(mimeMultipart);
            this.msg.setSentDate(new Date());
            Transport.send(this.msg);
        } catch (MessagingException e) {
            LogLog.error("Error occured while sending e-mail notification.", e);
        } catch (RuntimeException e2) {
            LogLog.error("Error occured while sending e-mail notification.", e2);
        }
    }

    public String getEvaluatorClass() {
        if (this.evaluator == null) {
            return null;
        }
        return this.evaluator.getClass().getName();
    }

    public String getFrom() {
        return this.from;
    }

    public String getReplyTo() {
        return this.replyTo;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setFrom(String str) {
        this.from = str;
    }

    public void setReplyTo(String str) {
        this.replyTo = str;
    }

    public void setSubject(String str) {
        this.subject = str;
    }

    public void setBufferSize(int i) {
        this.bufferSize = i;
        this.f198cb.resize(i);
    }

    public void setSMTPHost(String str) {
        this.smtpHost = str;
    }

    public String getSMTPHost() {
        return this.smtpHost;
    }

    public void setTo(String str) {
        this.f196to = str;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setEvaluatorClass(String str) throws Throwable {
        Class clsClass$;
        if (class$org$apache$log4j$spi$TriggeringEventEvaluator == null) {
            clsClass$ = class$("org.apache.log4j.spi.TriggeringEventEvaluator");
            class$org$apache$log4j$spi$TriggeringEventEvaluator = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$spi$TriggeringEventEvaluator;
        }
        this.evaluator = (TriggeringEventEvaluator) OptionConverter.instantiateByClassName(str, clsClass$, this.evaluator);
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public void setLocationInfo(boolean z) {
        this.locationInfo = z;
    }

    public boolean getLocationInfo() {
        return this.locationInfo;
    }

    public void setCc(String str) {
        this.f197cc = str;
    }

    public String getCc() {
        return this.f197cc;
    }

    public void setBcc(String str) {
        this.bcc = str;
    }

    public String getBcc() {
        return this.bcc;
    }

    public void setSMTPPassword(String str) {
        this.smtpPassword = str;
    }

    public void setSMTPUsername(String str) {
        this.smtpUsername = str;
    }

    public void setSMTPDebug(boolean z) {
        this.smtpDebug = z;
    }

    public String getSMTPPassword() {
        return this.smtpPassword;
    }

    public String getSMTPUsername() {
        return this.smtpUsername;
    }

    public boolean getSMTPDebug() {
        return this.smtpDebug;
    }

    public final void setEvaluator(TriggeringEventEvaluator triggeringEventEvaluator) {
        if (triggeringEventEvaluator == null) {
            throw new NullPointerException("trigger");
        }
        this.evaluator = triggeringEventEvaluator;
    }

    public final TriggeringEventEvaluator getEvaluator() {
        return this.evaluator;
    }

    @Override // org.apache.log4j.xml.UnrecognizedElementHandler
    public boolean parseUnrecognizedElement(Element element, Properties properties) throws Throwable {
        Class clsClass$;
        if ("triggeringPolicy".equals(element.getNodeName())) {
            if (class$org$apache$log4j$spi$TriggeringEventEvaluator == null) {
                clsClass$ = class$("org.apache.log4j.spi.TriggeringEventEvaluator");
                class$org$apache$log4j$spi$TriggeringEventEvaluator = clsClass$;
            } else {
                clsClass$ = class$org$apache$log4j$spi$TriggeringEventEvaluator;
            }
            Object element2 = DOMConfigurator.parseElement(element, properties, clsClass$);
            if (element2 instanceof TriggeringEventEvaluator) {
                setEvaluator((TriggeringEventEvaluator) element2);
                return true;
            }
            return true;
        }
        return false;
    }

    public final String getSMTPProtocol() {
        return this.smtpProtocol;
    }

    public final void setSMTPProtocol(String str) {
        this.smtpProtocol = str;
    }

    public final int getSMTPPort() {
        return this.smtpPort;
    }

    public final void setSMTPPort(int i) {
        this.smtpPort = i;
    }

    public final boolean getSendOnClose() {
        return this.sendOnClose;
    }

    public final void setSendOnClose(boolean z) {
        this.sendOnClose = z;
    }
}
