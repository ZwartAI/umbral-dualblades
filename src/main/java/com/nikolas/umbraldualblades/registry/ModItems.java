package com.nikolas.umbraldualblades.registry;

import com.nikolas.umbraldualblades.UmbralDualBlades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UmbralDualBlades.MOD_ID);

    /**
     * Netherite contributes 4 attack damage. The constructor modifier contributes 8,
     * producing 12 total attack damage before enchantments and external attributes.
     */
    public static final RegistryObject<Item> SHADOW_BLADE = ITEMS.register("shadow_blade",
        () -> new SwordItem(
            Tiers.NETHERITE,
            8,
            -1.2F,
            new Item.Properties().durability(2460).fireResistant().stacksTo(1)
        )
    );

    private ModItems() { }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
