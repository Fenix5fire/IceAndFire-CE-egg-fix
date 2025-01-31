package com.iafenvoy.iceandfire.entity.ai;

import com.google.common.base.Predicate;
import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.EntityGorgon;
import com.iafenvoy.iceandfire.entity.EntityStymphalianBird;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

public class StymphalianBirdAITarget extends ActiveTargetGoal<LivingEntity> {
    private final EntityStymphalianBird bird;

    public StymphalianBirdAITarget(EntityStymphalianBird entityIn, Class<LivingEntity> classTarget, boolean checkSight) {
        super(entityIn, classTarget, 0, checkSight, false, (Predicate<LivingEntity>) entity -> {
            if (EntityGorgon.isStoneMob(entity)) return false;
            if (entity instanceof PlayerEntity && !((PlayerEntity) entity).isCreative() || entity instanceof MerchantEntity || entity instanceof GolemEntity)
                return true;
            if (!(entity instanceof AnimalEntity)) return false;
            return IafCommonConfig.INSTANCE.stymphalianBird.attackAnimals.getValue();
        });
        this.bird = entityIn;
    }

    @Override
    public boolean canStart() {
        boolean supe = super.canStart();
        if (this.targetEntity != null && this.bird.getVictor() != null && this.bird.getVictor().getUuid().equals(this.targetEntity.getUuid()))
            return false;
        return supe && this.targetEntity != null && !this.targetEntity.getClass().equals(this.bird.getClass());
    }

    @Override
    protected Box getSearchBox(double targetDistance) {
        return this.bird.getBoundingBox().expand(targetDistance, targetDistance, targetDistance);
    }
}