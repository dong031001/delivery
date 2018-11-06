package party.lemons.delivery.store;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.Loader;

/**
 * Created by Sam on 7/11/2018.
 */
public class Trade
{
    public ItemStack result = ItemStack.EMPTY;
    public Ingredient[] cost;
    public String gameStage;

    public Trade(ItemStack result, Ingredient... cost)
    {
        this("", result, cost);
    }

    public Trade(String gamestage, ItemStack result, Ingredient... cost)
    {
        this.result = result;
        this.cost = cost;
        this.gameStage = gamestage;
    }

    public boolean canPurchase(EntityPlayer player)
    {
        if(!isUnlocked(player))
            return false;

        for(Ingredient ingredient : cost)
        {
            boolean found = false;
            for (ItemStack stack : player.inventory.mainInventory)
            {
                if(ingredient.apply(stack) && stack.getCount() >= ingredient.getMatchingStacks()[0].getCount())
                {
                    found = true;
                    break;
                }
            }

            if(!found)
                return false;
        }
        return true;
    }

    public void takeCost(EntityPlayer player)
    {
        for(Ingredient ingredient : cost)
        {
            for (ItemStack stack : player.inventory.mainInventory)
            {
                if (ingredient.apply(stack)  && stack.getCount() >= ingredient.getMatchingStacks()[0].getCount())
                {
                    stack.shrink(ingredient.getMatchingStacks()[0].getCount());
                    break;
                }
            }
        }
        player.inventoryContainer.detectAndSendChanges();
    }

    public boolean isUnlocked(EntityPlayer player)
    {
        if(Loader.isModLoaded("gamestages") && !gameStage.isEmpty())
        {
            return GameStageHelper.hasStage(player, gameStage);
        }

        return true;
    }
}