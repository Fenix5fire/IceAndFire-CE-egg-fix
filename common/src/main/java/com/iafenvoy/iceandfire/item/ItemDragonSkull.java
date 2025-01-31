package com.iafenvoy.iceandfire.item;

import com.iafenvoy.iceandfire.data.DragonType;
import com.iafenvoy.iceandfire.entity.EntityDragonSkull;
import com.iafenvoy.iceandfire.registry.IafEntities;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

public class ItemDragonSkull extends Item {
    private final DragonType dragonType;

    public ItemDragonSkull(DragonType dragonType) {
        super(new Settings().maxCount(1));
        this.dragonType = dragonType;
    }

    @Override
    public void onCraft(ItemStack itemStack, World world) {
        itemStack.setNbt(new NbtCompound());
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (stack.getNbt() == null) {
            stack.setNbt(new NbtCompound());
            stack.getNbt().putInt("Stage", 4);
            stack.getNbt().putInt("DragonAge", 75);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        String iceorfire = "dragon." + this.dragonType.getName();
        tooltip.add(Text.translatable(iceorfire).formatted(Formatting.GRAY));
        if (stack.getNbt() != null)
            tooltip.add(Text.translatable("dragon.stage").formatted(Formatting.GRAY).append(Text.literal(" " + stack.getNbt().getInt("Stage"))));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        assert context.getPlayer() != null;
        ItemStack stack = context.getPlayer().getStackInHand(context.getHand());
        /*
         * EntityDragonEgg egg = new EntityDragonEgg(worldIn);
         * egg.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() +
         * 0.5); if(!worldIn.isRemote){ worldIn.spawnEntityInWorld(egg); }
         */
        if (stack.getNbt() != null) {
            EntityDragonSkull skull = new EntityDragonSkull(IafEntities.DRAGON_SKULL.get(), context.getWorld());
            skull.setDragonType(this.dragonType.getName());
            skull.setStage(stack.getNbt().getInt("Stage"));
            skull.setDragonAge(stack.getNbt().getInt("DragonAge"));
            BlockPos offset = context.getBlockPos().offset(context.getSide(), 1);
            skull.refreshPositionAndAngles(offset.getX() + 0.5, offset.getY(), offset.getZ() + 0.5, 0, 0);
            float yaw = context.getPlayer().getYaw();
            if (context.getSide() != Direction.UP)
                yaw = context.getPlayer().getHorizontalFacing().asRotation();
            skull.setYaw(yaw);
            if (stack.hasCustomName())
                skull.setCustomName(stack.getName());
            if (!context.getWorld().isClient)
                context.getWorld().spawnEntity(skull);
            if (!context.getPlayer().isCreative())
                stack.decrement(1);
        }
        return ActionResult.SUCCESS;
    }
}
