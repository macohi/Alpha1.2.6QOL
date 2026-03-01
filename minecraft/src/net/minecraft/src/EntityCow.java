package net.minecraft.src;

import java.util.Random;

public class EntityCow extends EntityAnimals {
	public boolean unusedBoolean = false;

	public EntityCow(World var1) {
		super(var1);
		this.texture = "/mob/cow.png";
		this.setSize(0.9F, 1.3F);
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
	}

	protected String getLivingSound() {
		return "mob.cow";
	}

	protected String getHurtSound() {
		return "mob.cowhurt";
	}

	protected String getDeathSound() {
		return "mob.cowhurt";
	}

	protected float getSoundVolume() {
		return 0.4F;
	}

	protected int getDropItemId() {
		Integer nextInt = this.worldObj.rand.nextInt();

		System.out.println("Cow nextInt: " + nextInt.toString());
		if (nextInt % 10 == 0)
			return Item.porkRaw.shiftedIndex;

		return Item.leather.shiftedIndex;
	}

	public boolean interact(EntityPlayer var1) {
		ItemStack var2 = var1.inventory.getCurrentItem();
		if (var2 != null && var2.itemID == Item.bucketEmpty.shiftedIndex) {
			var1.inventory.setInventorySlotContents(var1.inventory.currentItem,
					new ItemStack(Item.bucketMilk));
			return true;
		} else {
			return false;
		}
	}
}
