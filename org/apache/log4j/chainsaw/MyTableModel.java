package org.apache.log4j.chainsaw;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/* loaded from: L-out.jar:org/apache/log4j/chainsaw/MyTableModel.class */
class MyTableModel extends AbstractTableModel {
    private static final Logger LOG;
    private static final Comparator MY_COMP;
    private static final String[] COL_NAMES;
    private static final EventDetails[] EMPTY_LIST;
    private static final DateFormat DATE_FORMATTER;
    private final Object mLock = new Object();
    private final SortedSet mAllEvents = new TreeSet(MY_COMP);
    private EventDetails[] mFilteredEvents = EMPTY_LIST;
    private final List mPendingEvents = new ArrayList();
    private boolean mPaused = false;
    private String mThreadFilter = "";
    private String mMessageFilter = "";
    private String mNDCFilter = "";
    private String mCategoryFilter = "";
    private Priority mPriorityFilter = Priority.DEBUG;
    static Class class$org$apache$log4j$chainsaw$MyTableModel;
    static Class class$java$lang$Boolean;
    static Class class$java$lang$Object;

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$chainsaw$MyTableModel == null) {
            clsClass$ = class$("org.apache.log4j.chainsaw.MyTableModel");
            class$org$apache$log4j$chainsaw$MyTableModel = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$chainsaw$MyTableModel;
        }
        LOG = Logger.getLogger(clsClass$);
        MY_COMP = new Comparator() { // from class: org.apache.log4j.chainsaw.MyTableModel.1
            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                if (obj == null && obj2 == null) {
                    return 0;
                }
                if (obj == null) {
                    return -1;
                }
                if (obj2 == null || ((EventDetails) obj).getTimeStamp() < ((EventDetails) obj2).getTimeStamp()) {
                    return 1;
                }
                return -1;
            }
        };
        COL_NAMES = new String[]{"Time", "Priority", "Trace", "Category", "NDC", "Message"};
        EMPTY_LIST = new EventDetails[0];
        DATE_FORMATTER = DateFormat.getDateTimeInstance(3, 2);
    }

    /* loaded from: L-out.jar:org/apache/log4j/chainsaw/MyTableModel$Processor.class */
    private class Processor implements Runnable {
        private final MyTableModel this$0;

        private Processor(MyTableModel myTableModel) {
            this.this$0 = myTableModel;
        }

        Processor(MyTableModel myTableModel, C04851 c04851) {
            this(myTableModel);
        }

        @Override // java.lang.Runnable
        public void run() throws InterruptedException {
            while (true) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException unused) {
                }
                synchronized (this.this$0.mLock) {
                    if (!this.this$0.mPaused) {
                        boolean z = true;
                        boolean z2 = false;
                        for (EventDetails eventDetails : this.this$0.mPendingEvents) {
                            this.this$0.mAllEvents.add(eventDetails);
                            z = z && eventDetails == this.this$0.mAllEvents.first();
                            z2 = z2 || this.this$0.matchFilter(eventDetails);
                        }
                        this.this$0.mPendingEvents.clear();
                        if (z2) {
                            this.this$0.updateFilteredEvents(z);
                        }
                    }
                }
            }
        }
    }

    MyTableModel() {
        Thread thread = new Thread(new Processor(this, null));
        thread.setDaemon(true);
        thread.start();
    }

    public int getRowCount() {
        int length;
        synchronized (this.mLock) {
            length = this.mFilteredEvents.length;
        }
        return length;
    }

    public int getColumnCount() {
        return COL_NAMES.length;
    }

    public String getColumnName(int i) {
        return COL_NAMES[i];
    }

    public Class getColumnClass(int i) throws Throwable {
        if (i == 2) {
            if (class$java$lang$Boolean != null) {
                return class$java$lang$Boolean;
            }
            Class clsClass$ = class$("java.lang.Boolean");
            class$java$lang$Boolean = clsClass$;
            return clsClass$;
        }
        if (class$java$lang$Object != null) {
            return class$java$lang$Object;
        }
        Class clsClass$2 = class$("java.lang.Object");
        class$java$lang$Object = clsClass$2;
        return clsClass$2;
    }

    public Object getValueAt(int i, int i2) {
        synchronized (this.mLock) {
            EventDetails eventDetails = this.mFilteredEvents[i];
            if (i2 == 0) {
                return DATE_FORMATTER.format(new Date(eventDetails.getTimeStamp()));
            }
            if (i2 == 1) {
                return eventDetails.getPriority();
            }
            if (i2 == 2) {
                return eventDetails.getThrowableStrRep() == null ? Boolean.FALSE : Boolean.TRUE;
            }
            if (i2 == 3) {
                return eventDetails.getCategoryName();
            }
            if (i2 == 4) {
                return eventDetails.getNDC();
            }
            return eventDetails.getMessage();
        }
    }

    public void setPriorityFilter(Priority priority) {
        synchronized (this.mLock) {
            this.mPriorityFilter = priority;
            updateFilteredEvents(false);
        }
    }

    public void setThreadFilter(String str) {
        synchronized (this.mLock) {
            this.mThreadFilter = str.trim();
            updateFilteredEvents(false);
        }
    }

    public void setMessageFilter(String str) {
        synchronized (this.mLock) {
            this.mMessageFilter = str.trim();
            updateFilteredEvents(false);
        }
    }

    public void setNDCFilter(String str) {
        synchronized (this.mLock) {
            this.mNDCFilter = str.trim();
            updateFilteredEvents(false);
        }
    }

    public void setCategoryFilter(String str) {
        synchronized (this.mLock) {
            this.mCategoryFilter = str.trim();
            updateFilteredEvents(false);
        }
    }

    public void addEvent(EventDetails eventDetails) {
        synchronized (this.mLock) {
            this.mPendingEvents.add(eventDetails);
        }
    }

    public void clear() {
        synchronized (this.mLock) {
            this.mAllEvents.clear();
            this.mFilteredEvents = new EventDetails[0];
            this.mPendingEvents.clear();
            fireTableDataChanged();
        }
    }

    public void toggle() {
        synchronized (this.mLock) {
            this.mPaused = !this.mPaused;
        }
    }

    public boolean isPaused() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mPaused;
        }
        return z;
    }

    public EventDetails getEventDetails(int i) {
        EventDetails eventDetails;
        synchronized (this.mLock) {
            eventDetails = this.mFilteredEvents[i];
        }
        return eventDetails;
    }

    private void updateFilteredEvents(boolean z) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        int size = this.mAllEvents.size();
        for (EventDetails eventDetails : this.mAllEvents) {
            if (matchFilter(eventDetails)) {
                arrayList.add(eventDetails);
            }
        }
        EventDetails eventDetails2 = this.mFilteredEvents.length == 0 ? null : this.mFilteredEvents[0];
        this.mFilteredEvents = (EventDetails[]) arrayList.toArray(EMPTY_LIST);
        if (z && eventDetails2 != null) {
            int iIndexOf = arrayList.indexOf(eventDetails2);
            if (iIndexOf < 1) {
                LOG.warn("In strange state");
                fireTableDataChanged();
            } else {
                fireTableRowsInserted(0, iIndexOf - 1);
            }
        } else {
            fireTableDataChanged();
        }
        LOG.debug(new StringBuffer().append("Total time [ms]: ").append(System.currentTimeMillis() - jCurrentTimeMillis).append(" in update, size: ").append(size).toString());
    }

    private boolean matchFilter(EventDetails eventDetails) {
        if (!eventDetails.getPriority().isGreaterOrEqual(this.mPriorityFilter) || eventDetails.getThreadName().indexOf(this.mThreadFilter) < 0 || eventDetails.getCategoryName().indexOf(this.mCategoryFilter) < 0) {
            return false;
        }
        if (this.mNDCFilter.length() == 0 || (eventDetails.getNDC() != null && eventDetails.getNDC().indexOf(this.mNDCFilter) >= 0)) {
            String message = eventDetails.getMessage();
            return message == null ? this.mMessageFilter.length() == 0 : message.indexOf(this.mMessageFilter) >= 0;
        }
        return false;
    }
}
