package com.nikolas.umbraldualblades.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class CommonConfig {
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue COMBO_RESET_TICKS;
    public static final ForgeConfigSpec.DoubleValue SEVENTH_HIT_MULTIPLIER;
    public static final ForgeConfigSpec.DoubleValue BACKSTAB_MULTIPLIER;
    public static final ForgeConfigSpec.DoubleValue BACKSTAB_DOT_THRESHOLD;
    public static final ForgeConfigSpec.BooleanValue ENABLE_SHADOW_PARTICLES;
    public static final ForgeConfigSpec.BooleanValue ENABLE_SWING_SOUND_VARIATION;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("combat");
        COMBO_RESET_TICKS = builder
            .comment("Maximum gap between successful melee impacts before the seven-hit counter resets. 20 ticks = 1 second.")
            .defineInRange("comboResetTicks", 12, 4, 40);
        SEVENTH_HIT_MULTIPLIER = builder
            .comment("Damage multiplier applied to the seventh successful impact.")
            .defineInRange("seventhHitMultiplier", 1.30D, 1.0D, 5.0D);
        BACKSTAB_MULTIPLIER = builder
            .comment("Damage multiplier when striking a living target from behind.")
            .defineInRange("backstabMultiplier", 1.20D, 1.0D, 5.0D);
        BACKSTAB_DOT_THRESHOLD = builder
            .comment("Behind-angle threshold. More negative requires the attacker to be more directly behind the target.")
            .defineInRange("backstabDotThreshold", -0.50D, -1.0D, 0.0D);
        builder.pop();

        builder.push("effects");
        ENABLE_SHADOW_PARTICLES = builder
            .comment("Spawn restrained sculk/soul particles on successful impacts.")
            .define("enableShadowParticles", true);
        ENABLE_SWING_SOUND_VARIATION = builder
            .comment("Play subtle pitch-varied attack sounds on successful impacts.")
            .define("enableSwingSoundVariation", true);
        builder.pop();

        SPEC = builder.build();
    }

    private CommonConfig() { }
}
