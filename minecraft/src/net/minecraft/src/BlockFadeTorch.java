package net.minecraft.src;

import java.util.Random;

public class BlockFadeTorch extends BlockTorch {
	private boolean torchActive = false;

	public Integer tickInteger;

	protected BlockFadeTorch(int var1, int var2, boolean torchActive) {
		super(var1, var2);

		this.torchActive = torchActive;

		// 60s(?)
		tickInteger = 1200 * (tickRate() / 20);
	}

	public void onBlockClicked(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		if (var5.inventory.currentItem == Block.planks.blockID) {
			// +5s(?)
			tickInteger += 100 * (tickRate() / 20);
			var5.inventory.consumeInventoryItem(var5.inventory.currentItem);
		}
	}

	private void burnoutProcess() {
		if (tickInteger > 0)
			tickInteger--;
		else
			tickInteger = 0;
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
}
