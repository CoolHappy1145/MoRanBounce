package org.apache.log4j.net;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/net/SocketNode.class */
public class SocketNode implements Runnable {
    Socket socket;
    LoggerRepository hierarchy;
    ObjectInputStream ois;
    static Logger logger;
    static Class class$org$apache$log4j$net$SocketNode;

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$net$SocketNode == null) {
            clsClass$ = class$("org.apache.log4j.net.SocketNode");
            class$org$apache$log4j$net$SocketNode = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$net$SocketNode;
        }
        logger = Logger.getLogger(clsClass$);
    }

    public SocketNode(Socket socket, LoggerRepository loggerRepository) {
        this.socket = socket;
        this.hierarchy = loggerRepository;
        try {
            this.ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
            logger.error(new StringBuffer().append("Could not open ObjectInputStream to ").append(socket).toString(), e);
        } catch (IOException e2) {
            logger.error(new StringBuffer().append("Could not open ObjectInputStream to ").append(socket).toString(), e2);
        } catch (RuntimeException e3) {
            logger.error(new StringBuffer().append("Could not open ObjectInputStream to ").append(socket).toString(), e3);
        }
    }

    @Override // java.lang.Runnable
    public void run() throws IOException {
        try {
            try {
                try {
                    try {
                        try {
                            if (this.ois == null) {
                                if (this.ois != null) {
                                    try {
                                        this.ois.close();
                                    } catch (Exception e) {
                                        logger.info("Could not close connection.", e);
                                    }
                                }
                                if (this.socket != null) {
                                    try {
                                        this.socket.close();
                                        return;
                                    } catch (InterruptedIOException unused) {
                                        Thread.currentThread().interrupt();
                                        return;
                                    } catch (IOException unused2) {
                                        return;
                                    }
                                }
                                return;
                            }
                            while (true) {
                                LoggingEvent loggingEvent = (LoggingEvent) this.ois.readObject();
                                Logger logger2 = this.hierarchy.getLogger(loggingEvent.getLoggerName());
                                if (loggingEvent.getLevel().isGreaterOrEqual(logger2.getEffectiveLevel())) {
                                    logger2.callAppenders(loggingEvent);
                                }
                            }
                        } catch (SocketException unused3) {
                            logger.info("Caught java.net.SocketException closing conneciton.");
                            if (this.ois != null) {
                                try {
                                    this.ois.close();
                                } catch (Exception e2) {
                                    logger.info("Could not close connection.", e2);
                                }
                            }
                            if (this.socket != null) {
                                try {
                                    this.socket.close();
                                } catch (InterruptedIOException unused4) {
                                    Thread.currentThread().interrupt();
                                } catch (IOException unused5) {
                                }
                            }
                        }
                    } catch (IOException e3) {
                        logger.info(new StringBuffer().append("Caught java.io.IOException: ").append(e3).toString());
                        logger.info("Closing connection.");
                        if (this.ois != null) {
                            try {
                                this.ois.close();
                            } catch (Exception e4) {
                                logger.info("Could not close connection.", e4);
                            }
                        }
                        if (this.socket != null) {
                            try {
                                this.socket.close();
                            } catch (InterruptedIOException unused6) {
                                Thread.currentThread().interrupt();
                            } catch (IOException unused7) {
                            }
                        }
                    }
                } catch (Throwable th) {
                    if (this.ois != null) {
                        try {
                            this.ois.close();
                        } catch (Exception e5) {
                            logger.info("Could not close connection.", e5);
                        }
                    }
                    if (this.socket != null) {
                        try {
                            this.socket.close();
                        } catch (InterruptedIOException unused8) {
                            Thread.currentThread().interrupt();
                        } catch (IOException unused9) {
                        }
                    }
                    throw th;
                }
            } catch (EOFException unused10) {
                logger.info("Caught java.io.EOFException closing conneciton.");
                if (this.ois != null) {
                    try {
                        this.ois.close();
                    } catch (Exception e6) {
                        logger.info("Could not close connection.", e6);
                    }
                }
                if (this.socket != null) {
                    try {
                        this.socket.close();
                    } catch (InterruptedIOException unused11) {
                        Thread.currentThread().interrupt();
                    } catch (IOException unused12) {
                    }
                }
            }
        } catch (InterruptedIOException e7) {
            Thread.currentThread().interrupt();
            logger.info(new StringBuffer().append("Caught java.io.InterruptedIOException: ").append(e7).toString());
            logger.info("Closing connection.");
            if (this.ois != null) {
                try {
                    this.ois.close();
                } catch (Exception e8) {
                    logger.info("Could not close connection.", e8);
                }
            }
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (InterruptedIOException unused13) {
                    Thread.currentThread().interrupt();
                } catch (IOException unused14) {
                }
            }
        } catch (Exception e9) {
            logger.error("Unexpected exception. Closing conneciton.", e9);
            if (this.ois != null) {
                try {
                    this.ois.close();
                } catch (Exception e10) {
                    logger.info("Could not close connection.", e10);
                }
            }
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (InterruptedIOException unused15) {
                    Thread.currentThread().interrupt();
                } catch (IOException unused16) {
                }
            }
        }
    }
}
