package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.registry.IafBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class RenderDragonCharge extends EntityRenderer<AbstractFireballEntity> {
    public final boolean isFire;

    public RenderDragonCharge(EntityRendererFactory.Context context, boolean isFire) {
        super(context);
        this.isFire = isFire;
    }

    @Override
    public Identifier getTexture(AbstractFireballEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }

    @Override
    public void render(AbstractFireballEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.5D, 0.0D);
        matrixStackIn.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));
        matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
        matrixStackIn.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(this.isFire ? Blocks.MAGMA_BLOCK.getDefaultState() : IafBlocks.DRAGON_ICE.get().getDefaultState(), matrixStackIn, bufferIn, packedLightIn, OverlayTexture.DEFAULT_UV);
        matrixStackIn.pop();
    }
}
