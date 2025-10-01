package jdk.nashorn.internal.p001ir.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Label;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import jdk.nashorn.internal.p001ir.debug.NashornTextifier;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.tools.Shell;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlin.text.Typography;
import org.apache.log4j.net.SyslogAppender;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/NashornClassReader.class */
public class NashornClassReader extends ClassReader {
    private final Map labelMap;
    private static String[] type;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NashornClassReader.class.desiredAssertionStatus();
        type = new String[]{"<error>", "UTF8", "<error>", "Integer", "Float", "Long", "Double", "Class", "String", "Fieldref", "Methodref", "InterfaceMethodRef", "NameAndType", "<error>", "<error>", "MethodHandle", "MethodType", "<error>", "Invokedynamic"};
    }

    public NashornClassReader(byte[] bArr) {
        super(bArr);
        this.labelMap = new HashMap();
        parse(bArr);
    }

    List getExtraLabels(String str, String str2, String str3) {
        return (List) this.labelMap.get(fullyQualifiedName(str, str2, str3));
    }

    private static long readLong(byte[] bArr, int i) {
        int i2 = i + 4;
        return ((((((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16)) | ((bArr[i + 2] & 255) << 8)) | (bArr[i + 3] & 255)) << 32) | ((bArr[i2] & 255) << 24) | ((bArr[i2 + 1] & 255) << 16) | ((bArr[i2 + 2] & 255) << 8) | (bArr[i2 + 3] & 255);
    }

    private static String readUTF(int i, int i2, byte[] bArr) {
        int i3 = i + i2;
        char[] cArr = new char[i2 * 2];
        int i4 = 0;
        boolean z = false;
        char c = 0;
        int i5 = i;
        while (i5 < i3) {
            int i6 = i5;
            i5++;
            byte b = bArr[i6];
            switch (z) {
                case false:
                    int i7 = b & 255;
                    if (i7 < 128) {
                        int i8 = i4;
                        i4++;
                        cArr[i8] = (char) i7;
                        break;
                    } else if (i7 < 224 && i7 > 191) {
                        c = (char) (i7 & 31);
                        z = true;
                        break;
                    } else {
                        c = (char) (i7 & 15);
                        z = 2;
                        break;
                    }
                case true:
                    int i9 = i4;
                    i4++;
                    cArr[i9] = (char) ((c << 6) | (b & 63));
                    z = false;
                    break;
                case true:
                    c = (char) ((c << 6) | (b & 63));
                    z = true;
                    break;
            }
        }
        return new String(cArr, 0, i4);
    }

    private String parse(byte[] bArr) {
        int i = ((bArr[0] & 255) << 24) | ((bArr[0 + 1] & 255) << 16) | ((bArr[0 + 2] & 255) << 8) | (bArr[0 + 3] & 255);
        int i2 = 0 + 4;
        if (!$assertionsDisabled && i != -889275714) {
            throw new AssertionError(Integer.toHexString(i));
        }
        int i3 = ((short) ((bArr[i2] & 255) << 8)) | (bArr[i2 + 1] & 255);
        int i4 = i2 + 2;
        int i5 = ((short) ((bArr[i4] & 255) << 8)) | (bArr[i4 + 1] & 255);
        int i6 = i4 + 2;
        int i7 = ((short) ((bArr[i6] & 255) << 8)) | (bArr[i6 + 1] & 255);
        int i8 = i6 + 2;
        ArrayList arrayList = new ArrayList(i7);
        arrayList.add(null);
        int i9 = 1;
        while (i9 < i7) {
            byte b = (byte) (bArr[i8] & 255);
            i8++;
            switch (b) {
                case 1:
                    int i10 = ((short) ((bArr[i8] & 255) << 8)) | (bArr[i8 + 1] & 255);
                    int i11 = i8 + 2;
                    arrayList.add(new DirectInfo(arrayList, b, readUTF(i11, i10, bArr)));
                    i8 = i11 + i10;
                    break;
                case 2:
                case CharacterType.ALNUM /* 13 */:
                case 14:
                case OPCode.CCLASS_MB /* 17 */:
                default:
                    if (!$assertionsDisabled) {
                        throw new AssertionError((int) b);
                    }
                    break;
                case 3:
                    arrayList.add(new DirectInfo(arrayList, b, Integer.valueOf(((bArr[i8] & 255) << 24) | ((bArr[i8 + 1] & 255) << 16) | ((bArr[i8 + 2] & 255) << 8) | (bArr[i8 + 3] & 255))));
                    i8 += 4;
                    break;
                case 4:
                    arrayList.add(new DirectInfo(arrayList, b, Float.valueOf(Float.intBitsToFloat(((bArr[i8] & 255) << 24) | ((bArr[i8 + 1] & 255) << 16) | ((bArr[i8 + 2] & 255) << 8) | (bArr[i8 + 3] & 255)))));
                    i8 += 4;
                    break;
                case 5:
                    arrayList.add(new DirectInfo(arrayList, b, Long.valueOf(readLong(bArr, i8))));
                    arrayList.add(null);
                    i9++;
                    i8 += 8;
                    break;
                case 6:
                    arrayList.add(new DirectInfo(arrayList, b, Double.valueOf(Double.longBitsToDouble(readLong(bArr, i8)))));
                    arrayList.add(null);
                    i9++;
                    i8 += 8;
                    break;
                case 7:
                    arrayList.add(new IndexInfo(arrayList, b, ((short) ((bArr[i8] & 255) << 8)) | (bArr[i8 + 1] & 255)));
                    i8 += 2;
                    break;
                case 8:
                    arrayList.add(new IndexInfo(arrayList, b, ((short) ((bArr[i8] & 255) << 8)) | (bArr[i8 + 1] & 255)));
                    i8 += 2;
                    break;
                case 9:
                case 10:
                case 11:
                    int i12 = i8 + 2;
                    arrayList.add(new IndexInfo2(arrayList, b, ((short) ((bArr[i8] & 255) << 8)) | (bArr[i8 + 1] & 255), ((short) ((bArr[i12] & 255) << 8)) | (bArr[i12 + 1] & 255)));
                    i8 += 4;
                    break;
                case 12:
                    int i13 = i8 + 2;
                    arrayList.add(new IndexInfo2(arrayList, b, ((short) ((bArr[i8] & 255) << 8)) | (bArr[i8 + 1] & 255), ((short) ((bArr[i13] & 255) << 8)) | (bArr[i13 + 1] & 255)));
                    i8 += 4;
                    break;
                case OPCode.EXACTN_IC /* 15 */:
                    byte b2 = (byte) (bArr[i8] & 255);
                    if (!$assertionsDisabled && (b2 < 1 || b2 > 9)) {
                        throw new AssertionError((int) b2);
                    }
                    int i14 = i8 + 1;
                    arrayList.add(new IndexInfo2(this, arrayList, b, b2, ((short) ((bArr[i14] & 255) << 8)) | (bArr[i14 + 1] & 255)) { // from class: jdk.nashorn.internal.ir.debug.NashornClassReader.2
                        final NashornClassReader this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // jdk.nashorn.internal.ir.debug.NashornClassReader.IndexInfo2, jdk.nashorn.internal.ir.debug.NashornClassReader.IndexInfo
                        public String toString() {
                            return "#" + this.index + ' ' + ((Constant) this.f27cp.get(this.index2)).toString();
                        }
                    });
                    i8 += 3;
                    break;
                case 16:
                    arrayList.add(new IndexInfo(arrayList, b, ((short) ((bArr[i8] & 255) << 8)) | (bArr[i8 + 1] & 255)));
                    i8 += 2;
                    break;
                case OPCode.CCLASS_MIX /* 18 */:
                    int i15 = i8 + 2;
                    arrayList.add(new IndexInfo2(this, arrayList, b, ((short) ((bArr[i8] & 255) << 8)) | (bArr[i8 + 1] & 255), ((short) ((bArr[i15] & 255) << 8)) | (bArr[i15 + 1] & 255)) { // from class: jdk.nashorn.internal.ir.debug.NashornClassReader.1
                        final NashornClassReader this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // jdk.nashorn.internal.ir.debug.NashornClassReader.IndexInfo2, jdk.nashorn.internal.ir.debug.NashornClassReader.IndexInfo
                        public String toString() {
                            return "#" + this.index + ' ' + ((Constant) this.f27cp.get(this.index2)).toString();
                        }
                    });
                    i8 += 4;
                    break;
            }
            i9++;
        }
        int i16 = i8;
        int i17 = ((short) ((bArr[i16] & 255) << 8)) | (bArr[i16 + 1] & 255);
        int i18 = i8 + 2;
        String string = ((Constant) arrayList.get(((short) ((bArr[i18] & 255) << 8)) | (bArr[i18 + 1] & 255))).toString();
        int i19 = i18 + 2 + 2;
        int i20 = i19 + 2 + ((((short) ((bArr[i19] & 255) << 8)) | (bArr[i19 + 1] & 255)) * 2);
        int i21 = ((short) ((bArr[i20] & 255) << 8)) | (bArr[i20 + 1] & 255);
        int i22 = i20 + 2;
        for (int i23 = 0; i23 < i21; i23++) {
            int i24 = i22 + 2;
            int i25 = ((short) ((bArr[i24] & 255) << 8)) | (bArr[i24 + 1] & 255);
            int i26 = i24 + 2 + 2;
            int i27 = ((short) ((bArr[i26] & 255) << 8)) | (bArr[i26 + 1] & 255);
            i22 = i26 + 2;
            for (int i28 = 0; i28 < i27; i28++) {
                int i29 = i22 + 2;
                i22 = i29 + 4 + (((bArr[i29] & 255) << 24) | ((bArr[i29 + 1] & 255) << 16) | ((bArr[i29 + 2] & 255) << 8) | (bArr[i29 + 3] & 255));
            }
        }
        int i30 = i22;
        int i31 = ((short) ((bArr[i30] & 255) << 8)) | (bArr[i30 + 1] & 255);
        int i32 = i22 + 2;
        for (int i33 = 0; i33 < i31; i33++) {
            int i34 = i32;
            int i35 = ((short) ((bArr[i34] & 255) << 8)) | (bArr[i34 + 1] & 255);
            int i36 = i32 + 2;
            int i37 = ((short) ((bArr[i36] & 255) << 8)) | (bArr[i36 + 1] & 255);
            int i38 = i36 + 2;
            String string2 = ((Constant) arrayList.get(i37)).toString();
            int i39 = ((short) ((bArr[i38] & 255) << 8)) | (bArr[i38 + 1] & 255);
            int i40 = i38 + 2;
            String string3 = ((Constant) arrayList.get(i39)).toString();
            int i41 = ((short) ((bArr[i40] & 255) << 8)) | (bArr[i40 + 1] & 255);
            i32 = i40 + 2;
            for (int i42 = 0; i42 < i41; i42++) {
                int i43 = i32;
                int i44 = i32 + 2;
                String string4 = ((Constant) arrayList.get(((short) ((bArr[i43] & 255) << 8)) | (bArr[i43 + 1] & 255))).toString();
                int i45 = ((bArr[i44] & 255) << 24) | ((bArr[i44 + 1] & 255) << 16) | ((bArr[i44 + 2] & 255) << 8) | (bArr[i44 + 3] & 255);
                int i46 = i44 + 4;
                if ("Code".equals(string4)) {
                    int i47 = ((short) ((bArr[i46] & 255) << 8)) | (bArr[i46 + 1] & 255);
                    int i48 = i46 + 2;
                    int i49 = ((short) ((bArr[i48] & 255) << 8)) | (bArr[i48 + 1] & 255);
                    int i50 = i48 + 2;
                    int i51 = ((bArr[i50] & 255) << 24) | ((bArr[i50 + 1] & 255) << 16) | ((bArr[i50 + 2] & 255) << 8) | (bArr[i50 + 3] & 255);
                    int i52 = i50 + 4;
                    parseCode(bArr, i52, i51, fullyQualifiedName(string, string2, string3));
                    int i53 = i52 + i51;
                    int i54 = i53 + 2 + ((((short) ((bArr[i53] & 255) << 8)) | (bArr[i53 + 1] & 255)) * 8);
                    int i55 = ((short) ((bArr[i54] & 255) << 8)) | (bArr[i54 + 1] & 255);
                    i32 = i54 + 2;
                    for (int i56 = 0; i56 < i55; i56++) {
                        int i57 = i32 + 2;
                        i32 = i57 + 4 + (((bArr[i57] & 255) << 24) | ((bArr[i57 + 1] & 255) << 16) | ((bArr[i57 + 2] & 255) << 8) | (bArr[i57 + 3] & 255));
                    }
                } else {
                    i32 = i46 + i45;
                }
            }
        }
        int i58 = i32;
        int i59 = ((short) ((bArr[i58] & 255) << 8)) | (bArr[i58 + 1] & 255);
        int i60 = i32 + 2;
        for (int i61 = 0; i61 < i59; i61++) {
            int i62 = i60;
            int i63 = ((short) ((bArr[i62] & 255) << 8)) | (bArr[i62 + 1] & 255);
            int i64 = i60 + 2;
            i60 = i64 + 4 + (((bArr[i64] & 255) << 24) | ((bArr[i64 + 1] & 255) << 16) | ((bArr[i64 + 2] & 255) << 8) | (bArr[i64 + 3] & 255));
        }
        return string;
    }

    private static String fullyQualifiedName(String str, String str2, String str3) {
        return str + '.' + str2 + str3;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void parseCode(byte[] bArr, int i, int i2, String str) {
        ArrayList arrayList = new ArrayList();
        this.labelMap.put(str, arrayList);
        boolean z = false;
        int i3 = i;
        while (i3 < i + i2) {
            byte b = bArr[i3];
            arrayList.add(new NashornTextifier.NashornLabel(b, i3 - i));
            switch (b & 255) {
                case 16:
                case OPCode.CCLASS_MIX /* 18 */:
                case 188:
                    i3 += 2;
                    break;
                case OPCode.CCLASS_MB /* 17 */:
                case OPCode.CCLASS_NOT /* 19 */:
                case 20:
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                case 159:
                case 160:
                case 161:
                case Typography.cent /* 162 */:
                case Typography.pound /* 163 */:
                case 164:
                case 165:
                case 166:
                case Typography.section /* 167 */:
                case SyslogAppender.LOG_LOCAL5 /* 168 */:
                case 178:
                case 179:
                case 180:
                case 181:
                case Typography.paragraph /* 182 */:
                case Typography.middleDot /* 183 */:
                case SyslogAppender.LOG_LOCAL7 /* 184 */:
                case Typography.rightGuillemete /* 187 */:
                case Typography.half /* 189 */:
                case 192:
                case 193:
                case 198:
                case 199:
                    i3 += 3;
                    break;
                case OPCode.CCLASS_MIX_NOT /* 21 */:
                case OPCode.CCLASS_NODE /* 22 */:
                case OPCode.ANYCHAR /* 23 */:
                case 24:
                case OPCode.ANYCHAR_STAR /* 25 */:
                case OPCode.FAIL /* 54 */:
                case OPCode.JUMP /* 55 */:
                case 56:
                case OPCode.POP /* 57 */:
                case OPCode.PUSH_OR_JUMP_EXACT1 /* 58 */:
                    i3 += z ? 3 : 2;
                    break;
                case OPCode.ANYCHAR_ML_STAR /* 26 */:
                case OPCode.ANYCHAR_STAR_PEEK_NEXT /* 27 */:
                case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
                case OPCode.WORD /* 29 */:
                case 30:
                case 31:
                case 32:
                case OPCode.WORD_BEGIN /* 33 */:
                case 34:
                case OPCode.BEGIN_BUF /* 35 */:
                case 36:
                case OPCode.BEGIN_LINE /* 37 */:
                case 38:
                case OPCode.SEMI_END_BUF /* 39 */:
                case 40:
                case OPCode.BACKREF1 /* 41 */:
                case OPCode.BACKREF2 /* 42 */:
                case OPCode.BACKREFN /* 43 */:
                case OPCode.BACKREFN_IC /* 44 */:
                case OPCode.BACKREF_MULTI /* 45 */:
                case OPCode.BACKREF_MULTI_IC /* 46 */:
                case OPCode.BACKREF_WITH_LEVEL /* 47 */:
                case 48:
                case OPCode.MEMORY_START_PUSH /* 49 */:
                case OPCode.MEMORY_END_PUSH /* 50 */:
                case OPCode.MEMORY_END_PUSH_REC /* 51 */:
                case OPCode.MEMORY_END /* 52 */:
                case OPCode.MEMORY_END_REC /* 53 */:
                case OPCode.PUSH_IF_PEEK_NEXT /* 59 */:
                case 60:
                case OPCode.REPEAT_NG /* 61 */:
                case 62:
                case OPCode.REPEAT_INC_NG /* 63 */:
                case 64:
                case OPCode.REPEAT_INC_NG_SG /* 65 */:
                case OPCode.NULL_CHECK_START /* 66 */:
                case OPCode.NULL_CHECK_END /* 67 */:
                case OPCode.NULL_CHECK_END_MEMST /* 68 */:
                case OPCode.NULL_CHECK_END_MEMST_PUSH /* 69 */:
                case OPCode.PUSH_POS /* 70 */:
                case OPCode.POP_POS /* 71 */:
                case 72:
                case OPCode.FAIL_POS /* 73 */:
                case OPCode.PUSH_STOP_BT /* 74 */:
                case OPCode.POP_STOP_BT /* 75 */:
                case OPCode.LOOK_BEHIND /* 76 */:
                case OPCode.PUSH_LOOK_BEHIND_NOT /* 77 */:
                case OPCode.FAIL_LOOK_BEHIND_NOT /* 78 */:
                case OPCode.CALL /* 79 */:
                case 80:
                case OPCode.STATE_CHECK_PUSH /* 81 */:
                case OPCode.STATE_CHECK_PUSH_OR_JUMP /* 82 */:
                case OPCode.STATE_CHECK /* 83 */:
                case OPCode.STATE_CHECK_ANYCHAR_STAR /* 84 */:
                case OPCode.STATE_CHECK_ANYCHAR_ML_STAR /* 85 */:
                case OPCode.SET_OPTION_PUSH /* 86 */:
                case OPCode.SET_OPTION /* 87 */:
                case SyslogAppender.LOG_FTP /* 88 */:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                case Shell.COMMANDLINE_ERROR /* 100 */:
                case Shell.COMPILATION_ERROR /* 101 */:
                case Shell.RUNTIME_ERROR /* 102 */:
                case Shell.IO_ERROR /* 103 */:
                case Shell.INTERNAL_ERROR /* 104 */:
                case 105:
                case 106:
                case 107:
                case 108:
                case 109:
                case 110:
                case 111:
                case 112:
                case 113:
                case 114:
                case 115:
                case 116:
                case 117:
                case 118:
                case 119:
                case 120:
                case 121:
                case 122:
                case 123:
                case 124:
                case 125:
                case 126:
                case ByteCompanionObject.MAX_VALUE /* 127 */:
                case 128:
                case 129:
                case 130:
                case 131:
                case 133:
                case 134:
                case 135:
                case SyslogAppender.LOG_LOCAL1 /* 136 */:
                case 137:
                case 138:
                case 139:
                case 140:
                case 141:
                case 142:
                case 143:
                case SyslogAppender.LOG_LOCAL2 /* 144 */:
                case 145:
                case 146:
                case 147:
                case 148:
                case 149:
                case 150:
                case 151:
                case SyslogAppender.LOG_LOCAL3 /* 152 */:
                case 172:
                case 173:
                case Typography.registered /* 174 */:
                case 175:
                case 176:
                case Typography.plusMinus /* 177 */:
                case 190:
                case 191:
                case 194:
                case 195:
                default:
                    i3++;
                    break;
                case 132:
                    i3 += z ? 5 : 3;
                    break;
                case Typography.copyright /* 169 */:
                    i3 += z ? 4 : 2;
                    break;
                case 170:
                    do {
                        i3++;
                    } while (((i3 - i) & 3) != 0);
                    int i4 = ((bArr[i3] & 255) << 24) | ((bArr[i3 + 1] & 255) << 16) | ((bArr[i3 + 2] & 255) << 8) | (bArr[i3 + 3] & 255);
                    int i5 = i3 + 4;
                    int i6 = ((bArr[i5] & 255) << 24) | ((bArr[i5 + 1] & 255) << 16) | ((bArr[i5 + 2] & 255) << 8) | (bArr[i5 + 3] & 255);
                    int i7 = i5 + 4;
                    i3 = i7 + 4 + (4 * (((((((bArr[i7] & 255) << 24) | ((bArr[i7 + 1] & 255) << 16)) | ((bArr[i7 + 2] & 255) << 8)) | (bArr[i7 + 3] & 255)) - i6) + 1));
                    break;
                case Typography.leftGuillemete /* 171 */:
                    do {
                        i3++;
                    } while (((i3 - i) & 3) != 0);
                    int i8 = ((bArr[i3] & 255) << 24) | ((bArr[i3 + 1] & 255) << 16) | ((bArr[i3 + 2] & 255) << 8) | (bArr[i3 + 3] & 255);
                    int i9 = i3 + 4;
                    i3 = i9 + 4 + (8 * (((bArr[i9] & 255) << 24) | ((bArr[i9 + 1] & 255) << 16) | ((bArr[i9 + 2] & 255) << 8) | (bArr[i9 + 3] & 255)));
                    break;
                case 185:
                case 186:
                case SharedScopeCall.FAST_SCOPE_GET_THRESHOLD /* 200 */:
                case 201:
                    i3 += 5;
                    break;
                case 196:
                    z = true;
                    i3++;
                    break;
                case 197:
                    i3 += 4;
                    break;
            }
            if (z) {
                z = false;
            }
        }
    }

    public void accept(ClassVisitor classVisitor, Attribute[] attributeArr, int i) {
        super.accept(classVisitor, attributeArr, i);
    }

    protected Label readLabel(int i, Label[] labelArr) {
        Label label = super.readLabel(i, labelArr);
        label.info = Integer.valueOf(i);
        return label;
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/NashornClassReader$Constant.class */
    private static abstract class Constant {

        /* renamed from: cp */
        protected ArrayList f27cp;
        protected int tag;

        protected Constant(ArrayList arrayList, int i) {
            this.f27cp = arrayList;
            this.tag = i;
        }

        final String getType() {
            String str = NashornClassReader.type[this.tag];
            while (true) {
                String str2 = str;
                if (str2.length() < 16) {
                    str = str2 + " ";
                } else {
                    return str2;
                }
            }
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/NashornClassReader$IndexInfo.class */
    private static class IndexInfo extends Constant {
        protected final int index;

        IndexInfo(ArrayList arrayList, int i, int i2) {
            super(arrayList, i);
            this.index = i2;
        }

        public String toString() {
            return ((Constant) this.f27cp.get(this.index)).toString();
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/NashornClassReader$IndexInfo2.class */
    private static class IndexInfo2 extends IndexInfo {
        protected final int index2;

        IndexInfo2(ArrayList arrayList, int i, int i2, int i3) {
            super(arrayList, i, i2);
            this.index2 = i3;
        }

        @Override // jdk.nashorn.internal.ir.debug.NashornClassReader.IndexInfo
        public String toString() {
            return super.toString() + ' ' + ((Constant) this.f27cp.get(this.index2)).toString();
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/NashornClassReader$DirectInfo.class */
    private static class DirectInfo extends Constant {
        protected final Object info;

        DirectInfo(ArrayList arrayList, int i, Object obj) {
            super(arrayList, i);
            this.info = obj;
        }

        public String toString() {
            return this.info.toString();
        }
    }
}
