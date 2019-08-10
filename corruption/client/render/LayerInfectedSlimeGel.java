package com.corruption.client.render;

import com.corruption.client.models.InfectedSlimeModel;
import com.corruption.entity.InfectedSlime;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerInfectedSlimeGel extends LayerRenderer<InfectedSlime, InfectedSlimeModel> {
   private final EntityModel<InfectedSlime> slimeModel = new InfectedSlimeModel(0);

   public LayerInfectedSlimeGel(IEntityRenderer<InfectedSlime, InfectedSlimeModel> p_i50923_1_) {
      super(p_i50923_1_);
   }

   public void render(InfectedSlime entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      if (!entityIn.isInvisible()) {
         GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableNormalize();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
         this.getEntityModel().setModelAttributes(this.slimeModel);
         this.slimeModel.render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
         GlStateManager.disableBlend();
         GlStateManager.disableNormalize();
      }
   }

   public boolean shouldCombineTextures() {
      return true;
   }
}