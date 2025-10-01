package org.apache.log4j.chainsaw;

import java.util.StringTokenizer;
import jdk.internal.dynalink.CallSiteDescriptor;
import org.apache.log4j.Level;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: L-out.jar:org/apache/log4j/chainsaw/XMLFileHandler.class */
class XMLFileHandler extends DefaultHandler {
    private static final String TAG_EVENT = "log4j:event";
    private static final String TAG_MESSAGE = "log4j:message";
    private static final String TAG_NDC = "log4j:NDC";
    private static final String TAG_THROWABLE = "log4j:throwable";
    private static final String TAG_LOCATION_INFO = "log4j:locationInfo";
    private final MyTableModel mModel;
    private int mNumEvents;
    private long mTimeStamp;
    private Level mLevel;
    private String mCategoryName;
    private String mNDC;
    private String mThreadName;
    private String mMessage;
    private String[] mThrowableStrRep;
    private String mLocationDetails;
    private final StringBuffer mBuf = new StringBuffer();

    XMLFileHandler(MyTableModel myTableModel) {
        this.mModel = myTableModel;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startDocument() {
        this.mNumEvents = 0;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) {
        this.mBuf.append(String.valueOf(cArr, i, i2));
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) {
        if (TAG_EVENT.equals(str3)) {
            addEvent();
            resetData();
            return;
        }
        if (TAG_NDC.equals(str3)) {
            this.mNDC = this.mBuf.toString();
            return;
        }
        if (TAG_MESSAGE.equals(str3)) {
            this.mMessage = this.mBuf.toString();
            return;
        }
        if (TAG_THROWABLE.equals(str3)) {
            StringTokenizer stringTokenizer = new StringTokenizer(this.mBuf.toString(), "\n\t");
            this.mThrowableStrRep = new String[stringTokenizer.countTokens()];
            if (this.mThrowableStrRep.length > 0) {
                this.mThrowableStrRep[0] = stringTokenizer.nextToken();
                for (int i = 1; i < this.mThrowableStrRep.length; i++) {
                    this.mThrowableStrRep[i] = new StringBuffer().append("\t").append(stringTokenizer.nextToken()).toString();
                }
            }
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) {
        this.mBuf.setLength(0);
        if (TAG_EVENT.equals(str3)) {
            this.mThreadName = attributes.getValue("thread");
            this.mTimeStamp = Long.parseLong(attributes.getValue("timestamp"));
            this.mCategoryName = attributes.getValue("logger");
            this.mLevel = Level.toLevel(attributes.getValue("level"));
            return;
        }
        if (TAG_LOCATION_INFO.equals(str3)) {
            this.mLocationDetails = new StringBuffer().append(attributes.getValue("class")).append(".").append(attributes.getValue("method")).append("(").append(attributes.getValue("file")).append(CallSiteDescriptor.TOKEN_DELIMITER).append(attributes.getValue("line")).append(")").toString();
        }
    }

    int getNumEvents() {
        return this.mNumEvents;
    }

    private void addEvent() {
        this.mModel.addEvent(new EventDetails(this.mTimeStamp, this.mLevel, this.mCategoryName, this.mNDC, this.mThreadName, this.mMessage, this.mThrowableStrRep, this.mLocationDetails));
        this.mNumEvents++;
    }

    private void resetData() {
        this.mTimeStamp = 0L;
        this.mLevel = null;
        this.mCategoryName = null;
        this.mNDC = null;
        this.mThreadName = null;
        this.mMessage = null;
        this.mThrowableStrRep = null;
        this.mLocationDetails = null;
    }
}
