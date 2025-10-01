package org.spongepowered.asm.mixin.transformer;

import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ActivityStack.class */
public class ActivityStack {
    public static final String GLUE_STRING = " -> ";
    private final Activity head;
    private Activity tail;
    private String glue;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ActivityStack$Activity.class */
    public class Activity {
        public String description;
        Activity last;
        Activity next;
        final ActivityStack this$0;

        Activity(ActivityStack activityStack, Activity activity, String str) {
            this.this$0 = activityStack;
            if (activity != null) {
                activity.next = this;
            }
            this.last = activity;
            this.description = str;
        }

        public void append(String str) {
            this.description = this.description != null ? this.description + str : str;
        }

        public void append(String str, Object[] objArr) {
            append(String.format(str, objArr));
        }

        public void end() {
            if (this.last != null) {
                this.this$0.end(this);
                this.last = null;
            }
        }

        public void next(String str) {
            if (this.next != null) {
                this.next.end();
            }
            this.description = str;
        }

        public void next(String str, Object[] objArr) {
            if (str == null) {
                str = Configurator.NULL;
            }
            next(String.format(str, objArr));
        }
    }

    public ActivityStack() {
        this(null, GLUE_STRING);
    }

    public ActivityStack(String str) {
        this(str, GLUE_STRING);
    }

    public ActivityStack(String str, String str2) {
        Activity activity = new Activity(this, null, str);
        this.tail = activity;
        this.head = activity;
        this.glue = str2;
    }

    public void clear() {
        this.tail = this.head;
        this.head.next = null;
    }

    public Activity begin(String str) {
        Activity activity = new Activity(this, this.tail, str != null ? str : Configurator.NULL);
        this.tail = activity;
        return activity;
    }

    public Activity begin(String str, Object[] objArr) {
        if (str == null) {
            str = Configurator.NULL;
        }
        Activity activity = new Activity(this, this.tail, String.format(str, objArr));
        this.tail = activity;
        return activity;
    }

    void end(Activity activity) {
        this.tail = activity.last;
        this.tail.next = null;
    }

    public String toString() {
        return toString(this.glue);
    }

    public String toString(String str) {
        if (this.head.description == null && this.head.next == null) {
            return "Unknown";
        }
        StringBuilder sb = new StringBuilder();
        Activity activity = this.head;
        while (true) {
            Activity activity2 = activity;
            if (activity2 != null) {
                if (activity2.description != null) {
                    sb.append(activity2.description);
                    if (activity2.next != null) {
                        sb.append(str);
                    }
                }
                activity = activity2.next;
            } else {
                return sb.toString();
            }
        }
    }
}
