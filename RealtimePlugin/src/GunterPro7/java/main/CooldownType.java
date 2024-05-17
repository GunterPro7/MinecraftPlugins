package main;

public enum CooldownType {
    ETERNITYS_EDGE_TELEPORT(10),
    ETERNITYS_EDGE_THUNDER(30),
    ETERNITYS_PICKAXE_EXPLOSION(30),
    ETERNITYS_ARMOR_HEAL(10),

    ILLUSIONER_WAND(1),
    BEACON_EFFECT(10),
    BED_RETURNING(10);

    private final int duration;
    CooldownType(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}
