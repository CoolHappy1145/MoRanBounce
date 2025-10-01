package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector3d;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/PathUtils.class */
public final class PathUtils extends MinecraftInstance {
    public static List findBlinkPath(double d, double d2, double d3) {
        ArrayList arrayList = new ArrayList();
        double posX = f157mc.getThePlayer().getPosX();
        double posY = f157mc.getThePlayer().getPosY();
        double posZ = f157mc.getThePlayer().getPosZ();
        double dAbs = Math.abs(posX - d) + Math.abs(posY - d2) + Math.abs(posZ - d3);
        int i = 0;
        while (dAbs > 0.0d) {
            dAbs = Math.abs(posX - d) + Math.abs(posY - d2) + Math.abs(posZ - d3);
            double d4 = posX - d;
            double d5 = posY - d2;
            double d6 = posZ - d3;
            double d7 = (i & 1) == 0 ? 0.4d : 0.1d;
            double dMin = Math.min(Math.abs(d4), d7);
            if (d4 < 0.0d) {
                posX += dMin;
            }
            if (d4 > 0.0d) {
                posX -= dMin;
            }
            double dMin2 = Math.min(Math.abs(d5), 0.25d);
            if (d5 < 0.0d) {
                posY += dMin2;
            }
            if (d5 > 0.0d) {
                posY -= dMin2;
            }
            double dMin3 = Math.min(Math.abs(d6), d7);
            if (d6 < 0.0d) {
                posZ += dMin3;
            }
            if (d6 > 0.0d) {
                posZ -= dMin3;
            }
            arrayList.add(new Vector3d(posX, posY, posZ));
            i++;
        }
        return arrayList;
    }

    public static List findPath(double d, double d2, double d3, double d4) {
        ArrayList arrayList = new ArrayList();
        double dCeil = Math.ceil(getDistance(f157mc.getThePlayer().getPosX(), f157mc.getThePlayer().getPosY(), f157mc.getThePlayer().getPosZ(), d, d2, d3) / d4);
        double posX = d - f157mc.getThePlayer().getPosX();
        double posY = d2 - f157mc.getThePlayer().getPosY();
        double posZ = d3 - f157mc.getThePlayer().getPosZ();
        double d5 = 1.0d;
        while (true) {
            double d6 = d5;
            if (d6 <= dCeil) {
                arrayList.add(new Vector3d(f157mc.getThePlayer().getPosX() + ((posX * d6) / dCeil), f157mc.getThePlayer().getPosY() + ((posY * d6) / dCeil), f157mc.getThePlayer().getPosZ() + ((posZ * d6) / dCeil)));
                d5 = d6 + 1.0d;
            } else {
                return arrayList;
            }
        }
    }

    private static double getDistance(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d - d4;
        double d8 = d2 - d5;
        double d9 = d3 - d6;
        return Math.sqrt((d7 * d7) + (d8 * d8) + (d9 * d9));
    }
}
