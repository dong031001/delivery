package party.lemons.delivery;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.delivery.block.tileentity.TileEntityCrate;
import party.lemons.delivery.entity.EntityFallingBlockExt;
import party.lemons.delivery.network.MessageBuySuccess;
import party.lemons.delivery.network.MessageBuyTrade;
import party.lemons.delivery.network.MessageCloseGui;
import party.lemons.delivery.network.MessageOpenStore;
import party.lemons.delivery.proxy.IProxy;
import party.lemons.delivery.store.GuiHandler;

/**
 * Created by Sam on 7/11/2018.
 */
@Mod(modid = Delivery.MODID, name = Delivery.NAME, version = Delivery.VERSION)
public class Delivery
{
	public static final String MODID = "delivery";
	public static final String NAME = "Delivery";
	public static final String VERSION = "1.6.2";

	@Mod.Instance(MODID)
	public static Delivery INSTANCE;

	public static SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	@SidedProxy(clientSide = "party.lemons.delivery.proxy.ClientProxy", serverSide = "party.lemons.delivery.proxy.ServerProxy")
	public static IProxy proxy;

	@Mod.EventHandler
	public static void onPreInit(FMLPreInitializationEvent event)
	{
		proxy.preInitSided(event);

		NETWORK.registerMessage(MessageOpenStore.Handler.class, MessageOpenStore.class, 0, Side.SERVER);
		NETWORK.registerMessage(MessageBuyTrade.Handler.class, MessageBuyTrade.class, 1, Side.SERVER);
		NETWORK.registerMessage(MessageCloseGui.Handler.class, MessageCloseGui.class, 2, Side.CLIENT);
		NETWORK.registerMessage(MessageBuySuccess.Handler.class, MessageBuySuccess.class, 3, Side.CLIENT);

		GameRegistry.registerTileEntity(TileEntityCrate.class, new ResourceLocation(MODID, "crate"));
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());

		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "falling_block_ext"), EntityFallingBlockExt.class, "Falling Block Ext", 0, INSTANCE, 160, 20, true);
	}
}
