package com.nikolas.umbraldualblades.event;

import com.nikolas.umbraldualblades.UmbralDualBlades;
import com.nikolas.umbraldualblades.combat.ComboTracker;
import com.nikolas.umbraldualblades.config.CommonConfig;
import com.nikolas.umbraldualblades.registry.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UmbralDualBlades.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class CommonCombatEvents {
    private CommonCombatEvents() { }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onLivingHurt(LivingHurtEvent event) {
        Entity causingEntity = event.getSource().getEntity();
        Entity directEntity = event.getSource().getDirectEntity();

        if (!(causingEntity instanceof Player player) || directEntity != player) {
            return;
        }
        if (!isDualWieldingShadowBlades(player)) {
            return;
        }

        LivingEntity target = event.getEntity();
        int comboStage = ComboTracker.recordSuccessfulImpact(
            player.getUUID(),
            player.level().getGameTime(),
            CommonConfig.COMBO_RESET_TICKS.get()
        );

        double multiplier = 1.0D;
        boolean backstab = isBehindTarget(player, target);
        if (backstab) {
            multiplier *= CommonConfig.BACKSTAB_MULTIPLIER.get();
        }
        if (comboStage == 7) {
            multiplier *= CommonConfig.SEVENTH_HIT_MULTIPLIER.get();
        }

        if (multiplier != 1.0D) {
            event.setAmount((float) (event.getAmount() * multiplier));
        }

        playImpactEffects(player, target, comboStage, backstab);
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        ComboTracker.clear(event.getEntity().getUUID());
    }

    public static boolean isDualWieldingShadowBlades(Player player) {
        return player.getMainHandItem().is(ModItems.SHADOW_BLADE.get())
            && player.getOffhandItem().is(ModItems.SHADOW_BLADE.get());
    }

    private static boolean isBehindTarget(Player attacker, LivingEntity target) {
        Vec3 targetLook = target.getLookAngle();
        Vec3 horizontalLook = new Vec3(targetLook.x, 0.0D, targetLook.z);
        Vec3 targetToAttacker = attacker.position().subtract(target.position());
        Vec3 horizontalDirection = new Vec3(targetToAttacker.x, 0.0D, targetToAttacker.z);

        if (horizontalLook.lengthSqr() < 1.0E-6D || horizontalDirection.lengthSqr() < 1.0E-6D) {
            return false;
        }

        double dot = horizontalLook.normalize().dot(horizontalDirection.normalize());
        return dot <= CommonConfig.BACKSTAB_DOT_THRESHOLD.get();
    }

    private static void playImpactEffects(Player player, LivingEntity target, int comboStage, boolean backstab) {
        if (!(target.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (CommonConfig.ENABLE_SHADOW_PARTICLES.get()) {
            int particleCount = comboStage == 7 ? 16 : (backstab ? 9 : 5);
            double spread = comboStage == 7 ? 0.45D : 0.25D;
            serverLevel.sendParticles(
                comboStage == 7 ? ParticleTypes.SCULK_SOUL : ParticleTypes.SOUL,
                target.getX(),
                target.getY(0.55D),
                target.getZ(),
                particleCount,
                spread,
                spread,
                spread,
                0.015D
            );
        }

        if (CommonConfig.ENABLE_SWING_SOUND_VARIATION.get()) {
            float randomPitch = 0.88F + player.getRandom().nextFloat() * 0.24F;
            float pitch = comboStage == 7 ? Mth.clamp(randomPitch * 0.72F, 0.5F, 1.3F) : randomPitch;
            serverLevel.playSound(
                null,
                target.blockPosition(),
                comboStage == 7 ? SoundEvents.PLAYER_ATTACK_KNOCKBACK : SoundEvents.PLAYER_ATTACK_SWEEP,
                SoundSource.PLAYERS,
                comboStage == 7 ? 0.85F : 0.45F,
                pitch
            );
        }

        if (comboStage == 7) {
            UmbralDualBlades.LOGGER.debug("{} completed the seven-impact Umbral combo", player.getGameProfile().getName());
        }
    }
}
