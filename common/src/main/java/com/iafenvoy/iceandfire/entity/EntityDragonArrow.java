package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.registry.IafItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class EntityDragonArrow extends PersistentProjectileEntity {
    public EntityDragonArrow(EntityType<? extends PersistentProjectileEntity> typeIn, World worldIn) {
        super(typeIn, worldIn, new ItemStack(IafItems.DRAGONBONE_ARROW.get()));
        this.setDamage(10);
    }

    public EntityDragonArrow(EntityType<? extends PersistentProjectileEntity> typeIn, double x, double y, double z, World world) {
        super(typeIn, x, y, z, world, new ItemStack(IafItems.DRAGONBONE_ARROW.get()));
        this.setDamage(10);
    }

    public EntityDragonArrow(EntityType<? extends PersistentProjectileEntity> typeIn, LivingEntity shooter, World worldIn) {
        super(typeIn, shooter, worldIn, new ItemStack(IafItems.DRAGONBONE_ARROW.get()));
        this.setDamage(10.0F);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tagCompound) {
        super.writeCustomDataToNbt(tagCompound);
        tagCompound.putDouble("damage", 10);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tagCompund) {
        super.readCustomDataFromNbt(tagCompund);
        this.setDamage(tagCompund.getDouble("damage"));
    }
}