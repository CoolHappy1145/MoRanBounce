package org.spongepowered.asm.util;

import com.google.common.base.Strings;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.spi.Configurator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* loaded from: L-out.jar:org/spongepowered/asm/util/PrettyPrinter.class */
public class PrettyPrinter {
    private static final int MIN_WIDTH = 40;
    private final HorizontalRule horizontalRule;
    private final List lines;
    private Table table;
    private boolean recalcWidth;
    protected int width;
    protected int wrapWidth;
    protected int kvKeyWidth;
    protected String kvFormat;

    /* loaded from: L-out.jar:org/spongepowered/asm/util/PrettyPrinter$Alignment.class */
    public enum Alignment {
        LEFT,
        RIGHT
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/PrettyPrinter$IPrettyPrintable.class */
    public interface IPrettyPrintable {
        void print(PrettyPrinter prettyPrinter);
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/PrettyPrinter$ISpecialEntry.class */
    interface ISpecialEntry {
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/PrettyPrinter$IVariableWidthEntry.class */
    interface IVariableWidthEntry {
        int getWidth();
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/PrettyPrinter$KeyValue.class */
    class KeyValue implements IVariableWidthEntry {
        private final String key;
        private final Object value;
        final PrettyPrinter this$0;

        public KeyValue(PrettyPrinter prettyPrinter, String str, Object obj) {
            this.this$0 = prettyPrinter;
            this.key = str;
            this.value = obj;
        }

        public String toString() {
            return String.format(this.this$0.kvFormat, this.key, this.value);
        }

        @Override // org.spongepowered.asm.util.PrettyPrinter.IVariableWidthEntry
        public int getWidth() {
            return toString().length();
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/PrettyPrinter$HorizontalRule.class */
    class HorizontalRule implements ISpecialEntry {
        private final char[] hrChars;
        final PrettyPrinter this$0;

        public HorizontalRule(PrettyPrinter prettyPrinter, char[] cArr) {
            this.this$0 = prettyPrinter;
            this.hrChars = cArr;
        }

        public String toString() {
            return Strings.repeat(new String(this.hrChars), this.this$0.width + 2);
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/PrettyPrinter$CentredText.class */
    class CentredText {
        private final Object centred;
        final PrettyPrinter this$0;

        public CentredText(PrettyPrinter prettyPrinter, Object obj) {
            this.this$0 = prettyPrinter;
            this.centred = obj;
        }

        public String toString() {
            String string = this.centred.toString();
            return String.format("%" + (((this.this$0.width - string.length()) / 2) + string.length()) + "s", string);
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/PrettyPrinter$Table.class */
    static class Table implements IVariableWidthEntry {
        final List columns = new ArrayList();
        final List rows = new ArrayList();
        String format = "%s";
        int colSpacing = 2;
        boolean addHeader = true;

        Table() {
        }

        void headerAdded() {
            this.addHeader = false;
        }

        void setColSpacing(int i) {
            this.colSpacing = Math.max(0, i);
            updateFormat();
        }

        Table grow(int i) {
            while (this.columns.size() < i) {
                this.columns.add(new Column(this));
            }
            updateFormat();
            return this;
        }

        Column add(Column column) {
            this.columns.add(column);
            return column;
        }

        Row add(Row row) {
            this.rows.add(row);
            return row;
        }

        Column addColumn(String str) {
            return add(new Column(this, str));
        }

        Column addColumn(Alignment alignment, int i, String str) {
            return add(new Column(this, alignment, i, str));
        }

        Row addRow(Object[] objArr) {
            return add(new Row(this, objArr));
        }

        void updateFormat() {
            String strRepeat = Strings.repeat(" ", this.colSpacing);
            StringBuilder sb = new StringBuilder();
            boolean z = false;
            for (Column column : this.columns) {
                if (z) {
                    sb.append(strRepeat);
                }
                z = true;
                sb.append(column.getFormat());
            }
            this.format = sb.toString();
        }

        String getFormat() {
            return this.format;
        }

        Object[] getTitles() {
            ArrayList arrayList = new ArrayList();
            Iterator it = this.columns.iterator();
            while (it.hasNext()) {
                arrayList.add(((Column) it.next()).getTitle());
            }
            return arrayList.toArray();
        }

        public String toString() {
            boolean z = false;
            String[] strArr = new String[this.columns.size()];
            for (int i = 0; i < this.columns.size(); i++) {
                strArr[i] = ((Column) this.columns.get(i)).toString();
                z |= !strArr[i].isEmpty();
            }
            if (z) {
                return String.format(this.format, strArr);
            }
            return null;
        }

        @Override // org.spongepowered.asm.util.PrettyPrinter.IVariableWidthEntry
        public int getWidth() {
            String string = toString();
            if (string != null) {
                return string.length();
            }
            return 0;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/PrettyPrinter$Column.class */
    static class Column {
        private final Table table;
        private Alignment align;
        private int minWidth;
        private int maxWidth;
        private int size;
        private String title;
        private String format;

        Column(Table table) {
            this.align = Alignment.LEFT;
            this.minWidth = 1;
            this.maxWidth = Integer.MAX_VALUE;
            this.size = 0;
            this.title = "";
            this.format = "%s";
            this.table = table;
        }

        Column(Table table, String str) {
            this(table);
            this.title = str;
            this.minWidth = str.length();
            updateFormat();
        }

        Column(Table table, Alignment alignment, int i, String str) {
            this(table, str);
            this.align = alignment;
            this.size = i;
        }

        void setAlignment(Alignment alignment) {
            this.align = alignment;
            updateFormat();
        }

        void setWidth(int i) {
            if (i > this.size) {
                this.size = i;
                updateFormat();
            }
        }

        void setMinWidth(int i) {
            if (i > this.minWidth) {
                this.minWidth = i;
                updateFormat();
            }
        }

        void setMaxWidth(int i) {
            this.size = Math.min(this.size, this.maxWidth);
            this.maxWidth = Math.max(1, i);
            updateFormat();
        }

        void setTitle(String str) {
            this.title = str;
            setWidth(str.length());
        }

        private void updateFormat() {
            this.format = "%" + (this.align == Alignment.RIGHT ? "" : "-") + Math.min(this.maxWidth, this.size == 0 ? this.minWidth : this.size) + "s";
            this.table.updateFormat();
        }

        int getMaxWidth() {
            return this.maxWidth;
        }

        String getTitle() {
            return this.title;
        }

        String getFormat() {
            return this.format;
        }

        public String toString() {
            if (this.title.length() > this.maxWidth) {
                return this.title.substring(0, this.maxWidth);
            }
            return this.title;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/PrettyPrinter$Row.class */
    static class Row implements IVariableWidthEntry {
        final Table table;
        final String[] args;

        public Row(Table table, Object[] objArr) {
            this.table = table.grow(objArr.length);
            this.args = new String[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                this.args[i] = objArr[i].toString();
                ((Column) this.table.columns.get(i)).setMinWidth(this.args[i].length());
            }
        }

        public String toString() {
            Object[] objArr = new Object[this.table.columns.size()];
            for (int i = 0; i < objArr.length; i++) {
                Column column = (Column) this.table.columns.get(i);
                if (i >= this.args.length) {
                    objArr[i] = "";
                } else {
                    objArr[i] = this.args[i].length() > column.getMaxWidth() ? this.args[i].substring(0, column.getMaxWidth()) : this.args[i];
                }
            }
            return String.format(this.table.format, objArr);
        }

        @Override // org.spongepowered.asm.util.PrettyPrinter.IVariableWidthEntry
        public int getWidth() {
            return toString().length();
        }
    }

    public PrettyPrinter() {
        this(100);
    }

    public PrettyPrinter(int i) {
        this.horizontalRule = new HorizontalRule(this, new char[]{'*'});
        this.lines = new ArrayList();
        this.recalcWidth = false;
        this.width = 100;
        this.wrapWidth = 80;
        this.kvKeyWidth = 10;
        this.kvFormat = makeKvFormat(this.kvKeyWidth);
        this.width = Math.max(40, i);
        this.wrapWidth = this.width - 20;
    }

    public PrettyPrinter wrapTo(int i) {
        this.wrapWidth = i;
        return this;
    }

    public int wrapTo() {
        return this.wrapWidth;
    }

    public PrettyPrinter table() {
        this.table = new Table();
        return this;
    }

    public PrettyPrinter table(String[] strArr) {
        this.table = new Table();
        for (String str : strArr) {
            this.table.addColumn(str);
        }
        return this;
    }

    public PrettyPrinter table(Object[] objArr) {
        this.table = new Table();
        Column columnAddColumn = null;
        for (Object obj : objArr) {
            if (obj instanceof String) {
                columnAddColumn = this.table.addColumn((String) obj);
            } else if ((obj instanceof Integer) && columnAddColumn != null) {
                int iIntValue = ((Integer) obj).intValue();
                if (iIntValue > 0) {
                    columnAddColumn.setWidth(iIntValue);
                } else if (iIntValue < 0) {
                    columnAddColumn.setMaxWidth(-iIntValue);
                }
            } else if ((obj instanceof Alignment) && columnAddColumn != null) {
                columnAddColumn.setAlignment((Alignment) obj);
            } else if (obj != null) {
                columnAddColumn = this.table.addColumn(obj.toString());
            }
        }
        return this;
    }

    public PrettyPrinter spacing(int i) {
        if (this.table == null) {
            this.table = new Table();
        }
        this.table.setColSpacing(i);
        return this;
    }

    /* renamed from: th */
    public PrettyPrinter m71th() {
        return m72th(false);
    }

    /* renamed from: th */
    private PrettyPrinter m72th(boolean z) {
        if (this.table == null) {
            this.table = new Table();
        }
        if (!z || this.table.addHeader) {
            this.table.headerAdded();
            addLine(this.table);
        }
        return this;
    }

    /* renamed from: tr */
    public PrettyPrinter m73tr(Object[] objArr) {
        m72th(true);
        addLine(this.table.addRow(objArr));
        this.recalcWidth = true;
        return this;
    }

    public PrettyPrinter add() {
        addLine("");
        return this;
    }

    public PrettyPrinter add(String str) {
        addLine(str);
        this.width = Math.max(this.width, str.length());
        return this;
    }

    public PrettyPrinter add(String str, Object[] objArr) {
        String str2 = String.format(str, objArr);
        addLine(str2);
        this.width = Math.max(this.width, str2.length());
        return this;
    }

    public PrettyPrinter add(Object[] objArr) {
        return add(objArr, "%s");
    }

    public PrettyPrinter add(Object[] objArr, String str) {
        for (Object obj : objArr) {
            add(str, new Object[]{obj});
        }
        return this;
    }

    public PrettyPrinter addIndexed(Object[] objArr) {
        String str = "[%" + String.valueOf(objArr.length - 1).length() + "d] %s";
        for (int i = 0; i < objArr.length; i++) {
            add(str, new Object[]{Integer.valueOf(i), objArr[i]});
        }
        return this;
    }

    public PrettyPrinter addWithIndices(Collection collection) {
        return addIndexed(collection.toArray());
    }

    public PrettyPrinter add(IPrettyPrintable iPrettyPrintable) {
        if (iPrettyPrintable != null) {
            iPrettyPrintable.print(this);
        }
        return this;
    }

    public PrettyPrinter add(Throwable th) {
        return add(th, 4);
    }

    public PrettyPrinter add(Throwable th, int i) {
        while (th != null) {
            addWrapped("    %s: %s", new Object[]{th.getClass().getName(), th.getMessage()});
            add(th.getStackTrace(), i);
            th = th.getCause();
        }
        return this;
    }

    public PrettyPrinter add(StackTraceElement[] stackTraceElementArr, int i) {
        String strRepeat = Strings.repeat(" ", i);
        for (StackTraceElement stackTraceElement : stackTraceElementArr) {
            add("%s%s", new Object[]{strRepeat, stackTraceElement});
        }
        return this;
    }

    public PrettyPrinter add(Object obj) {
        return add(obj, 0);
    }

    public PrettyPrinter add(Object obj, int i) {
        return append(obj, i, Strings.repeat(" ", i));
    }

    private PrettyPrinter append(Object obj, int i, String str) {
        if (obj instanceof String) {
            return add("%s%s", new Object[]{str, obj});
        }
        if (obj instanceof Iterable) {
            Iterator it = ((Iterable) obj).iterator();
            while (it.hasNext()) {
                append(it.next(), i, str);
            }
            return this;
        }
        if (obj instanceof Map) {
            kvWidth(i);
            return add((Map) obj);
        }
        if (obj instanceof IPrettyPrintable) {
            return add((IPrettyPrintable) obj);
        }
        if (obj instanceof Throwable) {
            return add((Throwable) obj, i);
        }
        if (obj.getClass().isArray()) {
            return add((Object[]) obj, i + "%s");
        }
        return add("%s%s", new Object[]{str, obj});
    }

    public PrettyPrinter addWrapped(String str, Object[] objArr) {
        return addWrapped(this.wrapWidth, str, objArr);
    }

    public PrettyPrinter addWrapped(int i, String str, Object[] objArr) {
        String strGroup = "";
        String strReplace = String.format(str, objArr).replace("\t", "    ");
        Matcher matcher = Pattern.compile("^(\\s+)[^\\s]").matcher(strReplace);
        if (matcher.find()) {
            strGroup = matcher.group(1);
        }
        try {
            Iterator it = getWrapped(i, strReplace, strGroup).iterator();
            while (it.hasNext()) {
                add((String) it.next());
            }
        } catch (Exception unused) {
            add(strReplace);
        }
        return this;
    }

    private List getWrapped(int i, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        while (str.length() > i) {
            int iLastBreakIndex = lastBreakIndex(str, i);
            if (iLastBreakIndex < 10) {
                iLastBreakIndex = i;
            }
            arrayList.add(str.substring(0, iLastBreakIndex));
            str = str2 + str.substring(iLastBreakIndex + 1);
        }
        if (str.length() > 0) {
            arrayList.add(str);
        }
        return arrayList;
    }

    private static int lastBreakIndex(String str, int i) {
        int iLastIndexOf = str.lastIndexOf(10, i);
        return iLastIndexOf > -1 ? iLastIndexOf : Math.max(str.lastIndexOf(32, i), str.lastIndexOf(9, i));
    }

    /* renamed from: kv */
    public PrettyPrinter m74kv(String str, String str2, Object[] objArr) {
        return m75kv(str, String.format(str2, objArr));
    }

    /* renamed from: kv */
    public PrettyPrinter m75kv(String str, Object obj) {
        addLine(new KeyValue(this, str, obj));
        return kvWidth(str.length());
    }

    public PrettyPrinter kvWidth(int i) {
        if (i > this.kvKeyWidth) {
            this.kvKeyWidth = i;
            this.kvFormat = makeKvFormat(i);
        }
        this.recalcWidth = true;
        return this;
    }

    public PrettyPrinter add(Map map) {
        for (Map.Entry entry : map.entrySet()) {
            m75kv(entry.getKey() == null ? Configurator.NULL : entry.getKey().toString(), entry.getValue());
        }
        return this;
    }

    /* renamed from: hr */
    public PrettyPrinter m76hr() {
        return m77hr('*');
    }

    /* renamed from: hr */
    public PrettyPrinter m77hr(char c) {
        addLine(new HorizontalRule(this, new char[]{c}));
        return this;
    }

    public PrettyPrinter centre() {
        if (!this.lines.isEmpty() && (this.lines.get(this.lines.size() - 1) instanceof String)) {
            addLine(new CentredText(this, this.lines.remove(this.lines.size() - 1)));
        }
        return this;
    }

    private void addLine(Object obj) {
        if (obj == null) {
            return;
        }
        this.lines.add(obj);
        this.recalcWidth |= obj instanceof IVariableWidthEntry;
    }

    public PrettyPrinter trace() {
        return trace(getDefaultLoggerName());
    }

    public PrettyPrinter trace(Level level) {
        return trace(getDefaultLoggerName(), level);
    }

    public PrettyPrinter trace(String str) {
        return trace(System.err, LogManager.getLogger(str));
    }

    public PrettyPrinter trace(String str, Level level) {
        return trace(System.err, LogManager.getLogger(str), level);
    }

    public PrettyPrinter trace(Logger logger) {
        return trace(System.err, logger);
    }

    public PrettyPrinter trace(Logger logger, Level level) {
        return trace(System.err, logger, level);
    }

    public PrettyPrinter trace(PrintStream printStream) {
        return trace(printStream, getDefaultLoggerName());
    }

    public PrettyPrinter trace(PrintStream printStream, Level level) {
        return trace(printStream, getDefaultLoggerName(), level);
    }

    public PrettyPrinter trace(PrintStream printStream, String str) {
        return trace(printStream, LogManager.getLogger(str));
    }

    public PrettyPrinter trace(PrintStream printStream, String str, Level level) {
        return trace(printStream, LogManager.getLogger(str), level);
    }

    public PrettyPrinter trace(PrintStream printStream, Logger logger) {
        return trace(printStream, logger, Level.DEBUG);
    }

    public PrettyPrinter trace(PrintStream printStream, Logger logger, Level level) {
        log(logger, level);
        print(printStream);
        return this;
    }

    public PrettyPrinter print() {
        return print(System.err);
    }

    public PrettyPrinter print(PrintStream printStream) {
        updateWidth();
        printSpecial(printStream, this.horizontalRule);
        for (Object obj : this.lines) {
            if (obj instanceof ISpecialEntry) {
                printSpecial(printStream, (ISpecialEntry) obj);
            } else {
                printString(printStream, obj.toString());
            }
        }
        printSpecial(printStream, this.horizontalRule);
        return this;
    }

    private void printSpecial(PrintStream printStream, ISpecialEntry iSpecialEntry) {
        printStream.printf("/*%s*/\n", iSpecialEntry.toString());
    }

    private void printString(PrintStream printStream, String str) {
        if (str != null) {
            printStream.printf("/* %-" + this.width + "s */\n", str);
        }
    }

    public PrettyPrinter log(Logger logger) {
        return log(logger, Level.INFO);
    }

    public PrettyPrinter log(Level level) {
        return log(LogManager.getLogger(getDefaultLoggerName()), level);
    }

    public PrettyPrinter log(Logger logger, Level level) {
        updateWidth();
        logSpecial(logger, level, this.horizontalRule);
        for (Object obj : this.lines) {
            if (obj instanceof ISpecialEntry) {
                logSpecial(logger, level, (ISpecialEntry) obj);
            } else {
                logString(logger, level, obj.toString());
            }
        }
        logSpecial(logger, level, this.horizontalRule);
        return this;
    }

    private void logSpecial(Logger logger, Level level, ISpecialEntry iSpecialEntry) {
        logger.log(level, "/*{}*/", new Object[]{iSpecialEntry.toString()});
    }

    private void logString(Logger logger, Level level, String str) {
        if (str != null) {
            logger.log(level, String.format("/* %-" + this.width + "s */", str));
        }
    }

    private void updateWidth() {
        if (this.recalcWidth) {
            this.recalcWidth = false;
            for (Object obj : this.lines) {
                if (obj instanceof IVariableWidthEntry) {
                    this.width = Math.min(4096, Math.max(this.width, ((IVariableWidthEntry) obj).getWidth()));
                }
            }
        }
    }

    private static String makeKvFormat(int i) {
        return String.format("%%%ds : %%s", Integer.valueOf(i));
    }

    private static String getDefaultLoggerName() {
        String className = new Throwable().getStackTrace()[2].getClassName();
        int iLastIndexOf = className.lastIndexOf(46);
        return iLastIndexOf == -1 ? className : className.substring(iLastIndexOf + 1);
    }

    public static void dumpStack() {
        new PrettyPrinter().add((Throwable) new Exception("Stack trace")).print(System.err);
    }

    public static void print(Throwable th) {
        new PrettyPrinter().add(th).print(System.err);
    }
}
