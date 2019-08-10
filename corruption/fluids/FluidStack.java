package com.corruption.fluids;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.corruption.Corruption;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;

/**
 * ItemStack substitute for Fluids.
 *
 * NOTE: Equality is based on the Fluid, not the amount. Use
 * {@link #isFluidStackIdentical(FluidStack)} to determine if FluidID, Amount and NBT Tag are all
 * equal.
 *
 */
public class FluidStack
{
    private static final Logger LOGGER = LogManager.getLogger();

    public int amount;
    public CompoundNBT tag;
    public String fluid;
    

    public FluidStack(String fluid, int amount)
    {
        if (fluid == null)
        {
            LOGGER.fatal("Null fluid supplied to fluidstack. Did you try and create a stack for an unregistered fluid?");
            throw new IllegalArgumentException("Cannot create a fluidstack from a null fluid");
        }
        this.fluid = fluid;
        this.amount = amount;
    }

    public FluidStack(String fluid, int amount, CompoundNBT nbt)
    {
        this(fluid, amount);

        if (nbt != null)
        {
            tag = (CompoundNBT) nbt.copy();
        }
    }

    public FluidStack(FluidStack stack, int amount)
    {
        this(stack.getFluid(), amount, stack.tag);
    }

    /**
     * This provides a safe method for retrieving a FluidStack - if the Fluid is invalid, the stack
     * will return as null.
     */
    @Nullable
    public static FluidStack loadFluidStackFromNBT(CompoundNBT nbt)
    {
        if (nbt == null)
        {
            return null;
        }
        if (!nbt.contains("FluidType", Constants.NBT.TAG_STRING))
        {
            return null;
        }
        if(!nbt.contains("Amount",Constants.NBT.TAG_INT)) {
        	return null;
        }
        FluidStack fluid = new FluidStack(nbt.getString("FluidType"),nbt.getInt("Amount"));
        if(nbt.contains("Tag"))
        	fluid.tag = nbt.getCompound("Tag");
        return fluid;

        
    }

    public CompoundNBT writeToNBT(CompoundNBT nbt)
    {
        nbt.putString("FluidType", fluid);
    	nbt.putInt("Amount", amount);
        if (tag != null)
        {
            nbt.put("Tag", tag);
        }
        return nbt;
    }

    public final String getFluid()
    {
        return fluid;
    }


    /**
     * @return A copy of this FluidStack
     */
    public FluidStack copy()
    {
        return new FluidStack(getFluid(), amount, tag);
    }

    /**
     * Determines if the FluidIDs and NBT Tags are equal. This does not check amounts.
     *
     * @param other
     *            The FluidStack for comparison
     * @return true if the Fluids (IDs and NBT Tags) are the same
     */
    public boolean isFluidEqual(@Nullable FluidStack other)
    {
        return other != null && getFluid() == other.getFluid() && isFluidStackTagEqual(other);
    }

    private boolean isFluidStackTagEqual(FluidStack other)
    {
        return tag == null ? other.tag == null : other.tag == null ? false : tag.equals(other.tag);
    }

    /**
     * Determines if the NBT Tags are equal. Useful if the FluidIDs are known to be equal.
     */
    public static boolean areFluidStackTagsEqual(@Nullable FluidStack stack1, @Nullable FluidStack stack2)
    {
    	if(stack1.fluid=="empty"||stack2.fluid=="empty")
    		return true;
        return stack1 == null && stack2 == null ? true : stack1 == null || stack2 == null ? false : stack1.isFluidStackTagEqual(stack2);
    }

    /**
     * Determines if the Fluids are equal and this stack is larger.
     *
     * @param other
     * @return true if this FluidStack contains the other FluidStack (same fluid and >= amount)
     */
    public boolean containsFluid(@Nullable FluidStack other)
    {
        return isFluidEqual(other) && amount >= other.amount;
    }

    /**
     * Determines if the FluidIDs, Amounts, and NBT Tags are all equal.
     *
     * @param other
     *            - the FluidStack for comparison
     * @return true if the two FluidStacks are exactly the same
     */
    public boolean isFluidStackIdentical(FluidStack other)
    {
        return isFluidEqual(other) && amount == other.amount;
    }

    /**
     * Determines if the FluidIDs and NBT Tags are equal compared to a registered container
     * ItemStack. This does not check amounts.
     *
     * @param other
     *            The ItemStack for comparison
     * @return true if the Fluids (IDs and NBT Tags) are the same
     */
    public boolean isFluidEqual(ItemStack other)
    {
        if (other == null)
        {
            return false;
        }

        return false;
    }

    public static FluidStack GetVanillaValue(Item item) {
    	String type = null;
    	if(item==Items.WATER_BUCKET)
    		type = "water";
    	else if(item==Items.LAVA_BUCKET)
    		type = "lava";
    	else if(item==Items.MILK_BUCKET)
    		type="milk";
    	int amount = 1000;
    	
    	if(type == null)
    		return null;
    	else
    		return new FluidStack(type,amount);
    }
    
    public static Item VanillaFluidToBucket(String fluid) {
    	if(fluid == "water")
    		return Items.WATER_BUCKET;
    	else if(fluid=="lava")
    		return Items.LAVA_BUCKET;
    	else if(fluid == "milk")
    		return Items.MILK_BUCKET;
    	return null;
    }
    
    @Override
    public final int hashCode()
    {
        int code = 1;
        code = 31*code + getFluid().hashCode();
        code = 31*code + amount;
        if (tag != null)
            code = 31*code + tag.hashCode();
        return code;
    }

    /**
     * Default equality comparison for a FluidStack. Same functionality as isFluidEqual().
     *
     * This is included for use in data structures.
     */
    @Override
    public final boolean equals(Object o)
    {
        if (!(o instanceof FluidStack))
        {
            return false;
        }

        return isFluidEqual((FluidStack) o);
    }
}