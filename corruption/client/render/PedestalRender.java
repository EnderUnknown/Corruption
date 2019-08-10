package com.corruption.client.render;

import com.corruption.tileentity.PedestalTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PedestalRender extends TileEntityRenderer<PedestalTileEntity>{
	
	private  ItemRenderer itemRenderer;
	
	
	@Override
	public void render(PedestalTileEntity tileEntityIn, double x, double y, double z, float partialTicks,
			int destroyStage) {
		if(itemRenderer == null)
			itemRenderer = Minecraft.getInstance().getItemRenderer();
		ItemStack stack = tileEntityIn.GetContainedItem();
		if(!stack.isEmpty()) {
			//GlStateManager.enableRescaleNormal();
			//GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
			//GlStateManager.enableBlend();
			RenderHelper.enableStandardItemLighting();
			//GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			GlStateManager.pushMatrix();
			
			GlStateManager.translated(x + 0.5, y + 1.5, z + 0.5);
			GlStateManager.scalef(0.5f, 0.5f, 0.5f);
			GlStateManager.rotatef((float)MathHelper.lerp((double)partialTicks, tileEntityIn.GetPrevRotation(), tileEntityIn.GetCurrentRotation()) * 10.0F, 0.0F, 1.0F, 0.0F);
			
			GlStateManager.enableLighting();
			this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
			
			
	
			
			
			//ItemEntity entity = new ItemEntity(tileEntityIn.getWorld(), 0,0,0, stack);
			//Minecraft.getInstance().getRenderManager().renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
			
			GlStateManager.popMatrix();
			//GlStateManager.disableRescaleNormal();
			//GlStateManager.disableBlend();
		}
		super.render(tileEntityIn, x, y, z, partialTicks, destroyStage);
	}
}
