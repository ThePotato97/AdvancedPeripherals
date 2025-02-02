package de.srendi.advancedperipherals.common.items;

import de.srendi.advancedperipherals.common.configuration.APConfig;
import de.srendi.advancedperipherals.common.items.base.BaseItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MemoryCardItem extends BaseItem {

    public MemoryCardItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public boolean isEnabled() {
        return APConfig.PERIPHERALS_CONFIG.enableInventoryManager.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, levelIn, tooltip, flagIn);
        if (stack.getOrCreateTag().contains("owner")) {
            tooltip.add(Component.translatable("item.advancedperipherals.tooltip.memory_card.bound", stack.getOrCreateTag().getString("owner")));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!worldIn.isClientSide) {
            ItemStack stack = playerIn.getItemInHand(handIn);
            if (stack.getOrCreateTag().contains("owner")) {
                playerIn.displayClientMessage(Component.translatable("text.advancedperipherals.removed_player"), true);
                stack.getOrCreateTag().remove("owner");
            } else {
                playerIn.displayClientMessage(Component.translatable("text.advancedperipherals.added_player"), true);
                stack.getOrCreateTag().putString("owner", playerIn.getName().getString());
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }
}
