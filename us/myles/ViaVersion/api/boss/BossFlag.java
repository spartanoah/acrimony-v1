/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package us.myles.ViaVersion.api.boss;

@Deprecated
public enum BossFlag {
    DARKEN_SKY(1),
    PLAY_BOSS_MUSIC(2);

    private final int id;

    private BossFlag(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}

