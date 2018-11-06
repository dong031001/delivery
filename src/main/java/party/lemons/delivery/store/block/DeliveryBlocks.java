package party.lemons.delivery.store.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.delivery.Delivery;

/**
 * Created by Sam on 7/11/2018.
 */
@Mod.EventBusSubscriber(modid = Delivery.MODID)
@GameRegistry.ObjectHolder(value = Delivery.MODID)
public class DeliveryBlocks
{
    public static final Block CRATE = Blocks.AIR;
    public static final Block STORE = Blocks.AIR;

    @SubscribeEvent
    public static void onRegisterBlock(RegistryEvent.Register<Block> event)
    {
        BlockStoreCrate crate = new BlockStoreCrate();
        crate.setRegistryName(new ResourceLocation(Delivery.MODID, "crate"));
        crate.setTranslationKey(Delivery.MODID + ".crate");
        crate.setHardness(0.25F);

        BlockStore store = new BlockStore();
        store.setRegistryName(new ResourceLocation(Delivery.MODID, "store"));
        store.setTranslationKey(Delivery.MODID + ".store");
        store.setHardness(1.25F);

        event.getRegistry().register(crate);
        event.getRegistry().register(store);
    }

    @SubscribeEvent
    public static void onRegisterItem(RegistryEvent.Register<Item> event)
    {
        ItemBlock ib = new ItemBlock(STORE);
        ib.setRegistryName(new ResourceLocation(Delivery.MODID, "store"));

        event.getRegistry().register(ib);
    }
}