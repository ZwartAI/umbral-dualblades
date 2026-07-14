package com.nikolas.umbraldualblades;

import com.mojang.logging.LogUtils;
import com.nikolas.umbraldualblades.config.CommonConfig;
import com.nikolas.umbraldualblades.registry.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(UmbralDualBlades.MOD_ID)
public final class UmbralDualBlades {
    public static final String MOD_ID = "umbral_dualblades";
    public static final Logger LOGGER = LogUtils.getLogger();

    public UmbralDualBlades() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, "umbral_dualblades-common.toml");

        LOGGER.info("Umbral Dual Blades initialized. Parry logic is delegated to Epic Fight / installed parry addons.");
    }
}
