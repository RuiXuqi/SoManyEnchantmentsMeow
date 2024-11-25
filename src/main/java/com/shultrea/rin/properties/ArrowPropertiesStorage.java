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
		compound.setBoolean("propertiesHandled", instance.getPropertiesHandled());
		compound.setInteger("flameLevel", instance.getFlameLevel());
		compound.setBoolean("doesPierceInvulnerability", instance.getArrowResetsIFrames());
		compound.setInteger("armorPierceLevel", instance.getArmorPiercingLevel());
		compound.setFloat("draggingPower", instance.getDraggingPower());
		return compound;
	}
	
	@Override
	public void readNBT(Capability<IArrowProperties> capability, IArrowProperties instance, EnumFacing side, NBTBase nbt) {
		instance.setPropertiesHandled(((NBTTagCompound)nbt).getBoolean("propertiesHandled"));
		instance.setFlameLevel(((NBTTagCompound)nbt).getInteger("flameLevel"));
		instance.setArrowResetsIFrames(((NBTTagCompound)nbt).getBoolean("doesPierceInvulnerability"));
		instance.setArmorPiercingLevel(((NBTTagCompound)nbt).getInteger("armorPierceLevel"));
		instance.setDraggingPower(((NBTTagCompound)nbt).getFloat("draggingPower"));
	}
}