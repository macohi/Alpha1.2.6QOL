package net.minecraft.src;

import java.util.Random;

public class BlockFadeTorch extends BlockTorch {
	private boolean torchActive = false;

	public Integer tickInteger;
	public Integer tickIntegerStartVal;

	protected BlockFadeTorch(int var1, int var2, boolean torchActive) {
		super(var1, var2);

		this.torchActive = torchActive;

		tickIntegerStartVal = 600 * (tickRate() * 20);

		if (!torchActive)
			tickInteger = 0;
		else
			tickInteger = tickIntegerStartVal;

		System.out.println("tickInteger: " + tickInteger);
	}
	
	public void writeEntityToNBT(NBTTagCompound var1) {
		var1.setBoolean("torchActive", torchActive);
		var1.setInteger("tickInteger", tickInteger);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		this.torchActive = var1.getBoolean("torchActive");
		this.tickInteger = var1.getInteger("tickInteger");
		if(!var1.hasKey("tickInteger")) {
			this.tickInteger = tickIntegerStartVal;
		}
	}


	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		if (var5.inventory.getCurrentItem() == null)
			return false;

		if (torchActive)
			if (var5.inventory.getCurrentItem().itemID == Item.stick.shiftedIndex) {
				tickInteger += 240 * (tickRate() * 20);
				var5.inventory.consumeInventoryItem(var5.inventory.getCurrentItem().itemID);

				randomDisplayTick(var1, var2, var3, var4, null, true);

				return true;
			}

		return false;
	}

	private void burnoutProcess() {
		if (tickInteger > 0)
			tickInteger--;

		if (tickInteger < 0)
			tickInteger = 0;

		if (torchActive) {
			this.blockIndexInTexture = Block.fadeTorchActive.blockIndexInTexture;

			if (tickInteger > tickIntegerStartVal / 2)
				this.blockIndexInTexture = Block.torchWood.blockIndexInTexture;
		}
	}

	public int tickRate() {
		return 2;
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		this.burnoutProcess();

		if (torchActive && tickInteger < 1) {
			var1.setBlockAndMetadataWithNotify(var2, var3, var4, Block.fadeTorchIdle.blockID,
					var1.getBlockMetadata(var2, var3, var4));
		}

		if (!torchActive && tickInteger > 0) {
			var1.setBlockAndMetadataWithNotify(var2, var3, var4, Block.fadeTorchActive.blockID,
					var1.getBlockMetadata(var2, var3, var4));
		}

	}

	public int idDropped(int var1, Random var2) {
		if (torchActive)
			return Block.fadeTorchActive.blockID;

		return Block.fadeTorchIdle.blockID;
	}

	public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
		if (torchActive)
			super.randomDisplayTick(var1, var2, var3, var4, var5);
	}

	public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5, boolean force) {
		if (torchActive || force)
			super.randomDisplayTick(var1, var2, var3, var4, var5);
	}
}
