package jdk.nashorn.internal.runtime;

import java.math.BigInteger;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/NumberToString.class */
public final class NumberToString {
    private final boolean isNaN;
    private boolean isNegative;
    private int decimalExponent;
    private char[] digits;
    private int nDigits;
    private static final int expMask = 2047;
    private static final long fractMask = 4503599627370495L;
    private static final int expShift = 52;
    private static final int expBias = 1023;
    private static final long fractHOB = 4503599627370496L;
    private static final long expOne = 4607182418800017408L;
    private static final int maxSmallBinExp = 62;
    private static final int minSmallBinExp = -21;
    private static final long[] powersOf5 = {1, 5, 25, 125, 625, 3125, 15625, 78125, 390625, 1953125, 9765625, 48828125, 244140625, 1220703125, 6103515625L, 30517578125L, 152587890625L, 762939453125L, 3814697265625L, 19073486328125L, 95367431640625L, 476837158203125L, 2384185791015625L, 11920928955078125L, 59604644775390625L, 298023223876953125L, 1490116119384765625L};
    private static final int[] nBitsPowerOf5 = {0, 3, 5, 7, 10, 12, 14, 17, 19, 21, 24, 26, 28, 31, 33, 35, 38, 40, 42, 45, 47, 49, 52, 54, 56, 59, 61};
    private static final char[] infinityDigits = {'I', 'n', 'f', 'i', 'n', 'i', 't', 'y'};
    private static final char[] nanDigits = {'N', 'a', 'N'};
    private static final char[] zeroes = {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};
    private static BigInteger[] powerOf5Cache;

    public static String stringFor(double d) {
        return new NumberToString(d).toString();
    }

    private NumberToString(double d) {
        int i;
        int i2;
        boolean z;
        boolean z2;
        long jCompareTo;
        long j;
        long j2;
        int i3;
        long jDoubleToLongBits = Double.doubleToLongBits(d);
        int i4 = (int) (jDoubleToLongBits >> 32);
        this.isNegative = i4 < 0;
        int i5 = (i4 >> 20) & 2047;
        long j3 = jDoubleToLongBits & fractMask;
        if (i5 == 2047) {
            this.isNaN = true;
            if (j3 == 0) {
                this.digits = infinityDigits;
            } else {
                this.digits = nanDigits;
                this.isNegative = false;
            }
            this.nDigits = this.digits.length;
            return;
        }
        this.isNaN = false;
        if (i5 != 0) {
            j3 |= fractHOB;
            i = 53;
        } else if (j3 == 0) {
            this.decimalExponent = 0;
            this.digits = zeroes;
            this.nDigits = 1;
            return;
        } else {
            while ((j3 & fractHOB) == 0) {
                j3 <<= 1;
                i5--;
            }
            i = 52 + i5 + 1;
            i5++;
        }
        int i6 = i5 - 1023;
        int iCountSignificantBits = countSignificantBits(j3);
        int iMax = Math.max(0, (iCountSignificantBits - i6) - 1);
        if (i6 <= 62 && i6 >= minSmallBinExp && iMax < powersOf5.length && iCountSignificantBits + nBitsPowerOf5[iMax] < 64 && iMax == 0) {
            if (i6 > i) {
                j = 1 << ((i6 - i) - 1);
            } else {
                j = 0;
            }
            if (i6 >= 52) {
                j2 = j3 << (i6 - 52);
            } else {
                j2 = j3 >>> (52 - i6);
            }
            int i7 = 0;
            while (j >= 10) {
                j /= 10;
                i7++;
            }
            int i8 = 0;
            if (i7 != 0) {
                long j4 = powersOf5[i7] << i7;
                long j5 = j2 % j4;
                j2 /= j4;
                i8 = 0 + i7;
                if (j5 >= (j4 >> 1)) {
                    j2++;
                }
            }
            char[] cArr = new char[26];
            int i9 = 19;
            while (true) {
                i3 = (int) (j2 % 10);
                j2 /= 10;
                if (i3 != 0) {
                    break;
                } else {
                    i8++;
                }
            }
            while (j2 != 0) {
                int i10 = i9;
                i9--;
                cArr[i10] = (char) (i3 + 48);
                i8++;
                i3 = (int) (j2 % 10);
                j2 /= 10;
            }
            cArr[i9] = (char) (i3 + 48);
            int i11 = 20 - i9;
            char[] cArr2 = new char[i11];
            System.arraycopy(cArr, i9, cArr2, 0, i11);
            this.digits = cArr2;
            this.decimalExponent = i8 + 1;
            this.nDigits = i11;
            return;
        }
        int iFloor = (int) Math.floor(((Double.longBitsToDouble(expOne | (j3 & (-4503599627370497L))) - 1.5d) * 0.289529654d) + 0.176091259d + (i6 * 0.301029995663981d));
        int iMax2 = Math.max(0, -iFloor);
        int i12 = iMax2 + iMax + i6;
        int iMax3 = Math.max(0, iFloor);
        int i13 = iMax3 + iMax;
        int i14 = i12 - i;
        long j6 = j3 >>> (53 - iCountSignificantBits);
        int i15 = i12 - (iCountSignificantBits - 1);
        int iMin = Math.min(i15, i13);
        int i16 = i15 - iMin;
        int i17 = i13 - iMin;
        int i18 = i14 - iMin;
        i18 = iCountSignificantBits == 1 ? i18 - 1 : i18;
        if (i18 < 0) {
            i16 -= i18;
            i17 -= i18;
            i18 = 0;
        }
        char[] cArr3 = new char[32];
        this.digits = cArr3;
        int i19 = iCountSignificantBits + i16 + (iMax2 < nBitsPowerOf5.length ? nBitsPowerOf5[iMax2] : iMax2 * 3);
        int i20 = i17 + 1 + (iMax3 + 1 < nBitsPowerOf5.length ? nBitsPowerOf5[iMax3 + 1] : (iMax3 + 1) * 3);
        if (i19 < 64 && i20 < 64) {
            long j7 = (j6 * powersOf5[iMax2]) << i16;
            long j8 = powersOf5[iMax3] << i17;
            long j9 = powersOf5[iMax2] << i18;
            long j10 = j8 * 10;
            i2 = 0;
            int i21 = (int) (j7 / j8);
            long j11 = 10 * (j7 % j8);
            long j12 = j9 * 10;
            z = j11 < j12;
            z2 = j11 + j12 > j10;
            if (i21 != 0 || z2) {
                i2 = 0 + 1;
                cArr3[0] = (char) (48 + i21);
            } else {
                iFloor--;
            }
            if (iFloor < -3 || iFloor >= 8) {
                z = false;
                z2 = false;
            }
            while (!z && !z2) {
                int i22 = (int) (j11 / j8);
                j11 = 10 * (j11 % j8);
                j12 *= 10;
                if (j12 > 0) {
                    z = j11 < j12;
                    z2 = j11 + j12 > j10;
                } else {
                    z = true;
                    z2 = true;
                }
                if (z && i22 == 0) {
                    break;
                }
                int i23 = i2;
                i2++;
                cArr3[i23] = (char) (48 + i22);
            }
            jCompareTo = (j11 << 1) - j10;
        } else {
            BigInteger bigIntegerMultiplyPowerOf5And2 = multiplyPowerOf5And2(BigInteger.valueOf(j6), iMax2, i16);
            BigInteger bigIntegerConstructPowerOf5And2 = constructPowerOf5And2(iMax3, i17);
            BigInteger bigIntegerConstructPowerOf5And22 = constructPowerOf5And2(iMax2, i18);
            int iNumberOfLeadingZeros = Long.numberOfLeadingZeros(j6) - 4;
            BigInteger bigIntegerShiftLeft = bigIntegerMultiplyPowerOf5And2.shiftLeft(iNumberOfLeadingZeros);
            BigInteger bigIntegerShiftLeft2 = bigIntegerConstructPowerOf5And22.shiftLeft(iNumberOfLeadingZeros);
            BigInteger bigIntegerShiftLeft3 = bigIntegerConstructPowerOf5And2.shiftLeft(iNumberOfLeadingZeros);
            BigInteger bigIntegerMultiply = bigIntegerShiftLeft3.multiply(BigInteger.TEN);
            i2 = 0;
            BigInteger[] bigIntegerArrDivideAndRemainder = bigIntegerShiftLeft.divideAndRemainder(bigIntegerShiftLeft3);
            int iIntValue = bigIntegerArrDivideAndRemainder[0].intValue();
            BigInteger bigIntegerMultiply2 = bigIntegerArrDivideAndRemainder[1].multiply(BigInteger.TEN);
            BigInteger bigIntegerMultiply3 = bigIntegerShiftLeft2.multiply(BigInteger.TEN);
            z = bigIntegerMultiply2.compareTo(bigIntegerMultiply3) < 0;
            z2 = bigIntegerMultiply2.add(bigIntegerMultiply3).compareTo(bigIntegerMultiply) > 0;
            if (iIntValue != 0 || z2) {
                i2 = 0 + 1;
                cArr3[0] = (char) (48 + iIntValue);
            } else {
                iFloor--;
            }
            if (iFloor < -3 || iFloor >= 8) {
                z = false;
                z2 = false;
            }
            while (!z && !z2) {
                BigInteger[] bigIntegerArrDivideAndRemainder2 = bigIntegerMultiply2.divideAndRemainder(bigIntegerShiftLeft3);
                int iIntValue2 = bigIntegerArrDivideAndRemainder2[0].intValue();
                bigIntegerMultiply2 = bigIntegerArrDivideAndRemainder2[1].multiply(BigInteger.TEN);
                bigIntegerMultiply3 = bigIntegerMultiply3.multiply(BigInteger.TEN);
                z = bigIntegerMultiply2.compareTo(bigIntegerMultiply3) < 0;
                z2 = bigIntegerMultiply2.add(bigIntegerMultiply3).compareTo(bigIntegerMultiply) > 0;
                if (z && iIntValue2 == 0) {
                    break;
                }
                int i24 = i2;
                i2++;
                cArr3[i24] = (char) (48 + iIntValue2);
            }
            if (z2 && z) {
                jCompareTo = bigIntegerMultiply2.shiftLeft(1).compareTo(bigIntegerMultiply);
            } else {
                jCompareTo = 0;
            }
        }
        this.decimalExponent = iFloor + 1;
        this.digits = cArr3;
        this.nDigits = i2;
        if (z2) {
            if (!z) {
                roundup();
                return;
            }
            if (jCompareTo == 0) {
                if ((cArr3[this.nDigits - 1] & 1) != 0) {
                    roundup();
                }
            } else if (jCompareTo > 0) {
                roundup();
            }
        }
    }

    private static int countSignificantBits(long j) {
        if (j != 0) {
            return (64 - Long.numberOfLeadingZeros(j)) - Long.numberOfTrailingZeros(j);
        }
        return 0;
    }

    private static BigInteger bigPowerOf5(int i) {
        if (powerOf5Cache == null) {
            powerOf5Cache = new BigInteger[i + 1];
        } else if (powerOf5Cache.length <= i) {
            BigInteger[] bigIntegerArr = new BigInteger[i + 1];
            System.arraycopy(powerOf5Cache, 0, bigIntegerArr, 0, powerOf5Cache.length);
            powerOf5Cache = bigIntegerArr;
        }
        if (powerOf5Cache[i] != null) {
            return powerOf5Cache[i];
        }
        if (i < powersOf5.length) {
            BigInteger[] bigIntegerArr2 = powerOf5Cache;
            BigInteger bigIntegerValueOf = BigInteger.valueOf(powersOf5[i]);
            bigIntegerArr2[i] = bigIntegerValueOf;
            return bigIntegerValueOf;
        }
        int i2 = i >> 1;
        int i3 = i - i2;
        BigInteger bigIntegerBigPowerOf5 = powerOf5Cache[i2];
        if (bigIntegerBigPowerOf5 == null) {
            bigIntegerBigPowerOf5 = bigPowerOf5(i2);
        }
        if (i3 < powersOf5.length) {
            BigInteger[] bigIntegerArr3 = powerOf5Cache;
            BigInteger bigIntegerMultiply = bigIntegerBigPowerOf5.multiply(BigInteger.valueOf(powersOf5[i3]));
            bigIntegerArr3[i] = bigIntegerMultiply;
            return bigIntegerMultiply;
        }
        BigInteger bigIntegerBigPowerOf52 = powerOf5Cache[i3];
        if (bigIntegerBigPowerOf52 == null) {
            bigIntegerBigPowerOf52 = bigPowerOf5(i3);
        }
        BigInteger[] bigIntegerArr4 = powerOf5Cache;
        BigInteger bigIntegerMultiply2 = bigIntegerBigPowerOf5.multiply(bigIntegerBigPowerOf52);
        bigIntegerArr4[i] = bigIntegerMultiply2;
        return bigIntegerMultiply2;
    }

    private static BigInteger multiplyPowerOf5And2(BigInteger bigInteger, int i, int i2) {
        BigInteger bigIntegerShiftLeft = bigInteger;
        if (i != 0) {
            bigIntegerShiftLeft = bigIntegerShiftLeft.multiply(bigPowerOf5(i));
        }
        if (i2 != 0) {
            bigIntegerShiftLeft = bigIntegerShiftLeft.shiftLeft(i2);
        }
        return bigIntegerShiftLeft;
    }

    private static BigInteger constructPowerOf5And2(int i, int i2) {
        BigInteger bigIntegerBigPowerOf5 = bigPowerOf5(i);
        if (i2 != 0) {
            bigIntegerBigPowerOf5 = bigIntegerBigPowerOf5.shiftLeft(i2);
        }
        return bigIntegerBigPowerOf5;
    }

    private void roundup() {
        char c;
        char[] cArr = this.digits;
        int i = this.nDigits - 1;
        int i2 = i;
        char c2 = cArr[i];
        while (true) {
            c = c2;
            if (c != '9' || i2 <= 0) {
                break;
            }
            if (this.decimalExponent < 0) {
                this.nDigits--;
            } else {
                this.digits[i2] = '0';
            }
            i2--;
            c2 = this.digits[i2];
        }
        if (c == '9') {
            this.decimalExponent++;
            this.digits[0] = '1';
        } else {
            this.digits[i2] = (char) (c + 1);
        }
    }

    public String toString() {
        int i;
        int i2;
        StringBuilder sb = new StringBuilder(32);
        if (this.isNegative) {
            sb.append('-');
        }
        if (this.isNaN) {
            sb.append(this.digits, 0, this.nDigits);
        } else if (this.decimalExponent > 0 && this.decimalExponent <= 21) {
            int iMin = Math.min(this.nDigits, this.decimalExponent);
            sb.append(this.digits, 0, iMin);
            if (iMin < this.decimalExponent) {
                sb.append(zeroes, 0, this.decimalExponent - iMin);
            } else if (iMin < this.nDigits) {
                sb.append('.');
                sb.append(this.digits, iMin, this.nDigits - iMin);
            }
        } else if (this.decimalExponent <= 0 && this.decimalExponent > -6) {
            sb.append('0');
            sb.append('.');
            if (this.decimalExponent != 0) {
                sb.append(zeroes, 0, -this.decimalExponent);
            }
            sb.append(this.digits, 0, this.nDigits);
        } else {
            sb.append(this.digits[0]);
            if (this.nDigits > 1) {
                sb.append('.');
                sb.append(this.digits, 1, this.nDigits - 1);
            }
            sb.append('e');
            if (this.decimalExponent <= 0) {
                sb.append('-');
                int i3 = (-this.decimalExponent) + 1;
                i = i3;
                i2 = i3;
            } else {
                sb.append('+');
                int i4 = this.decimalExponent - 1;
                i = i4;
                i2 = i4;
            }
            if (i2 > 99) {
                sb.append((char) ((i / 100) + 48));
                i %= 100;
            }
            if (i2 > 9) {
                sb.append((char) ((i / 10) + 48));
                i %= 10;
            }
            sb.append((char) (i + 48));
        }
        return sb.toString();
    }
}
