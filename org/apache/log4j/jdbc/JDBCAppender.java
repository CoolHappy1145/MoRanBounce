package org.apache.log4j.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/jdbc/JDBCAppender.class */
public class JDBCAppender extends AppenderSkeleton implements Appender {
    protected String databaseURL = "jdbc:odbc:myDB";
    protected String databaseUser = "me";
    protected String databasePassword = "mypassword";
    protected Connection connection = null;
    protected String sqlStatement = "";
    protected int bufferSize = 1;
    private boolean locationInfo = false;
    protected ArrayList buffer = new ArrayList(this.bufferSize);
    protected ArrayList removes = new ArrayList(this.bufferSize);

    public boolean getLocationInfo() {
        return this.locationInfo;
    }

    public void setLocationInfo(boolean z) {
        this.locationInfo = z;
    }

    @Override // org.apache.log4j.AppenderSkeleton
    public void append(LoggingEvent loggingEvent) {
        loggingEvent.getNDC();
        loggingEvent.getThreadName();
        loggingEvent.getMDCCopy();
        if (this.locationInfo) {
            loggingEvent.getLocationInformation();
        }
        loggingEvent.getRenderedMessage();
        loggingEvent.getThrowableStrRep();
        this.buffer.add(loggingEvent);
        if (this.buffer.size() >= this.bufferSize) {
            flushBuffer();
        }
    }

    protected String getLogStatement(LoggingEvent loggingEvent) {
        return getLayout().format(loggingEvent);
    }

    protected void execute(String str) throws SQLException {
        Connection connection = null;
        Statement statementCreateStatement = null;
        try {
            connection = getConnection();
            statementCreateStatement = connection.createStatement();
            statementCreateStatement.executeUpdate(str);
            if (statementCreateStatement != null) {
                statementCreateStatement.close();
            }
        } catch (Throwable th) {
            if (statementCreateStatement != null) {
                statementCreateStatement.close();
            }
            throw th;
        }
    }

    protected Connection getConnection() throws ClassNotFoundException {
        if (!DriverManager.getDrivers().hasMoreElements()) {
            setDriver("sun.jdbc.odbc.JdbcOdbcDriver");
        }
        if (this.connection == null) {
            this.connection = DriverManager.getConnection(this.databaseURL, this.databaseUser, this.databasePassword);
        }
        return this.connection;
    }

    @Override // org.apache.log4j.Appender
    public void close() throws SQLException {
        flushBuffer();
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
            }
        } catch (SQLException e) {
            this.errorHandler.error("Error closing connection", e, 0);
        }
        this.closed = true;
    }

    public void flushBuffer() {
        this.removes.ensureCapacity(this.buffer.size());
        Iterator it = this.buffer.iterator();
        while (it.hasNext()) {
            LoggingEvent loggingEvent = (LoggingEvent) it.next();
            try {
                execute(getLogStatement(loggingEvent));
            } catch (SQLException e) {
                this.errorHandler.error("Failed to excute sql", e, 2);
            } finally {
                this.removes.add(loggingEvent);
            }
        }
        this.buffer.removeAll(this.removes);
        this.removes.clear();
    }

    @Override // org.apache.log4j.AppenderSkeleton
    public void finalize() throws SQLException {
        close();
    }

    public void setSql(String str) {
        this.sqlStatement = str;
        if (getLayout() == null) {
            setLayout(new PatternLayout(str));
        } else {
            ((PatternLayout) getLayout()).setConversionPattern(str);
        }
    }

    public String getSql() {
        return this.sqlStatement;
    }

    public void setUser(String str) {
        this.databaseUser = str;
    }

    public void setURL(String str) {
        this.databaseURL = str;
    }

    public void setPassword(String str) {
        this.databasePassword = str;
    }

    public void setBufferSize(int i) {
        this.bufferSize = i;
        this.buffer.ensureCapacity(this.bufferSize);
        this.removes.ensureCapacity(this.bufferSize);
    }

    public String getUser() {
        return this.databaseUser;
    }

    public String getURL() {
        return this.databaseURL;
    }

    public String getPassword() {
        return this.databasePassword;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setDriver(String str) throws ClassNotFoundException {
        try {
            Class.forName(str);
        } catch (Exception e) {
            this.errorHandler.error("Failed to load driver", e, 0);
        }
    }
}
