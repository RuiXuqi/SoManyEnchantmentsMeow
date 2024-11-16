package com.shultrea.rin.properties;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ArrowPropertiesStorage implements IStorage<IArrowProperties> {
	
	@Override
	public NBTBase writeNBT(Capability<IArrowProperties> capability, IArrowProperties instance, EnumFacing side) {
		NBTTagCompound compound = new NBTTagCompound();
//		compound.setFloat("ExplosionPower", instance.getExplosionPower());
//		compound.setBoolean("CanDestroyBlocks", instance.getCanDestroyBlocks());
//		compound.setBoolean("CanRecover", instance.getCanRecover());
//		compound.setInteger("enchlevel", instance.getLevel());
//		compound.setBoolean("didStarFall", instance.didStarFall());
//		compound.setBoolean("isStarFallMade", instance.getIsStarFallMade());
		compound.setInteger("flameLevel", instance.getFlameLevel());
		compound.setBoolean("doesPierceInvulnerability", instance.getArrowResetsIFrames());
		compound.setInteger("armorPierceLevel", instance.getArmorPiercingLevel());
		compound.setFloat("draggingPower", instance.getDraggingPower());
		return compound;
	}
	
	@Override
	public void readNBT(Capability<IArrowProperties> capability, IArrowProperties instance, EnumFacing side, NBTBase nbt) {
//		instance.setExplosion(((NBTTagCompound)nbt).getFloat("ExplosionPower"), ((NBTTagCompound)nbt).getBoolean("CanDestroyBlocks"));
//		instance.setCanRecover(((NBTTagCompound)nbt).getBoolean("CanRecover"));
//		instance.setLevel(((NBTTagCompound)nbt).getInteger("enchlevel"));
//		instance.setDidStarFall(((NBTTagCompound)nbt).getBoolean("didStarFall"));
//		instance.setIsStarFallMade(((NBTTagCompound)nbt).getBoolean("isStarFallMade"));
		instance.setFlameLevel(((NBTTagCompound)nbt).getInteger("flameLevel"));
		instance.setArrowResetsIFrames(((NBTTagCompound)nbt).getBoolean("doesPierceInvulnerability"));
		instance.setArmorPiercingLevel(((NBTTagCompound)nbt).getInteger("armorPierceLevel"));
		instance.setDraggingPower(((NBTTagCompound)nbt).getFloat("draggingPower"));
	}
}
