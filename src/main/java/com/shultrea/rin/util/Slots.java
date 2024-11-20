package com.shultrea.rin.util;

import net.minecraft.inventory.EntityEquipmentSlot;

public abstract class Slots {

	public static final EntityEquipmentSlot[] NONE = {};
	public static final EntityEquipmentSlot[] ALL = EntityEquipmentSlot.values();

	public static final EntityEquipmentSlot[] BODY = {EntityEquipmentSlot.HEAD,EntityEquipmentSlot.CHEST,EntityEquipmentSlot.LEGS,EntityEquipmentSlot.FEET};
	public static final EntityEquipmentSlot[] HEAD = {EntityEquipmentSlot.HEAD};
	public static final EntityEquipmentSlot[] CHEST = {EntityEquipmentSlot.CHEST};
	public static final EntityEquipmentSlot[] LEGS = {EntityEquipmentSlot.LEGS};
	public static final EntityEquipmentSlot[] FEET = {EntityEquipmentSlot.FEET};
	
	public static final EntityEquipmentSlot[] HAND = {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND};
	public static final EntityEquipmentSlot[] MAINHAND = {EntityEquipmentSlot.MAINHAND};
	public static final EntityEquipmentSlot[] OFFHAND = {EntityEquipmentSlot.OFFHAND};
}