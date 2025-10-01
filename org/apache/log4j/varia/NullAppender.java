package org.apache.log4j.varia;

import org.apache.log4j.AppenderSkeleton;

/* loaded from: L-out.jar:org/apache/log4j/varia/NullAppender.class */
public class NullAppender extends AppenderSkeleton {
    private static NullAppender instance = new NullAppender();

    public NullAppender getInstance() {
        return instance;
    }

    public static NullAppender getNullAppender() {
        return instance;
    }
}
