package net.ccbluex.liquidbounce.utils.Skid;

import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Skid/PotionData.class */
public class PotionData {
    public final IPotion potion;
    public int maxTimer = 0;
    public float animationX = 0.0f;
    public final Translate translate;
    public final int level;

    public PotionData(IPotion iPotion, Translate translate, int i) {
        this.potion = iPotion;
        this.translate = translate;
        this.level = i;
    }

    public float getAnimationX() {
        return this.animationX;
    }

    public IPotion getPotion() {
        return this.potion;
    }

    public int getMaxTimer() {
        return this.maxTimer;
    }
}
