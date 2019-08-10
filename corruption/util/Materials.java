package com.corruption.util;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class Materials {
	public static final Material EBONWOOD = (new Materials.Builder(MaterialColor.PURPLE).build());
	public static final Material EBONICPLANT = (new Materials.Builder(MaterialColor.PURPLE)).doesNotBlockMovement().notSolid().notOpaque().pushDestroys().build();
	public static final Material EBONLEAVES = (new Materials.Builder(MaterialColor.PURPLE)).notOpaque().pushDestroys().build();
	public static final Material EBONICGROUND = (new Materials.Builder(MaterialColor.PURPLE_TERRACOTTA).build());
	
	
	public static class Builder {
	      private PushReaction pushReaction = PushReaction.NORMAL;
	      private boolean blocksMovement = true;
	      private boolean canBurn;
	      private boolean requiresNoTool = true;
	      private boolean isLiquid;
	      private boolean isReplaceable;
	      private boolean isSolid = true;
	      private final MaterialColor color;
	      private boolean isOpaque = true;

	      public Builder(MaterialColor color) {
	         this.color = color;
	      }

	      public Materials.Builder liquid() {
	         this.isLiquid = true;
	         return this;
	      }

	      public Materials.Builder notSolid() {
	         this.isSolid = false;
	         return this;
	      }

	      public Materials.Builder doesNotBlockMovement() {
	         this.blocksMovement = false;
	         return this;
	      }

	      private Materials.Builder notOpaque() {
	         this.isOpaque = false;
	         return this;
	      }

	      protected Materials.Builder requiresTool() {
	         this.requiresNoTool = false;
	         return this;
	      }

	      protected Materials.Builder flammable() {
	         this.canBurn = true;
	         return this;
	      }

	      public Materials.Builder replaceable() {
	         this.isReplaceable = true;
	         return this;
	      }

	      protected Materials.Builder pushDestroys() {
	         this.pushReaction = PushReaction.DESTROY;
	         return this;
	      }

	      protected Materials.Builder pushBlocks() {
	         this.pushReaction = PushReaction.BLOCK;
	         return this;
	      }

	      public Material build() {
	         return new Material(this.color, this.isLiquid, this.isSolid, this.blocksMovement, this.isOpaque, this.requiresNoTool, this.canBurn, this.isReplaceable, this.pushReaction);
	      }
	   }
}
