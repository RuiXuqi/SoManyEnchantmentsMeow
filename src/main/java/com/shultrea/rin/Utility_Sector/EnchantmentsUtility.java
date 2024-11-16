package com.shultrea.rin.Utility_Sector;

import com.shultrea.rin.Config.ModConfig;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Recreated by Rin on 13/05/2017. This is an utility to be used for some enchantments.
 */
public class EnchantmentsUtility {
	
	public static final Random RANDOM = new Random();
	
	public static boolean entityCanSeeSky(EntityLivingBase attacker) {
		BlockPos pos = new BlockPos(attacker.posX, attacker.posY, attacker.posZ);
		return attacker.world.canBlockSeeSky(pos);
	}

	public static void setRaining(World world) {
		if(!ModConfig.miscellaneous.enableWeatherChanges) return;
		world.getWorldInfo().setRainTime(0);
		world.getWorldInfo().setRaining(true);
	}
	
	public static void setThunderstorm(World world) {
		if(!ModConfig.miscellaneous.enableWeatherChanges) return;
		world.getWorldInfo().setThunderTime(0);
		world.getWorldInfo().setRaining(true);
		world.getWorldInfo().setThundering(true);
	}

	public static void setClearWeather(World world) {
		if(!ModConfig.miscellaneous.enableWeatherChanges) return;
		if(world.getWorldInfo().isRaining()) world.getWorldInfo().setRaining(false);
		if(world.getWorldInfo().isThundering()) {
			world.getWorldInfo().setThundering(false);
			world.getWorldInfo().setRaining(false);
		}
	}
	
	/**
	 * Used to calculate modified damage
	 * @param damage - The original damage.
	 * @param level - The enchantment level.
	 * @param multiplier - Multiplies the given value based on the enchantment's level you want to add in the
	 * calculation.
	 * @param constant - The constant damage you want to add in the calculation.
	 * @param trueMultiplier - Multiplies the damage based on the total damage and not the level of the enchantment you
	 * want to add in the calculation. Avoid using zero as this negates your damage.
	 * @return The finished calculated damage.
	 */
	public static float modifyDamage(float damage, float constant, float multiplier, float trueMultiplier, int level) {
		if(damage <= 1.0f) return 1.0f;
		return (damage + constant + level * multiplier) * trueMultiplier;
	}
	
	/**
	 * An improved vanilla knockback mechanic that ignores the knockback resistance of a mob
	 */
	public static void knockBackIgnoreKBRes(Entity entityIn, float strength, double xRatio, double zRatio) {
		entityIn.isAirBorne = true;
		float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
		entityIn.motionX /= 2.0;
		entityIn.motionZ /= 2.0;
		entityIn.motionX -= xRatio / (double)f * (double)strength;
		entityIn.motionZ -= zRatio / (double)f * (double)strength;
		//Protection from non-finite XZ
		if(!Double.isFinite(entityIn.motionX)) entityIn.motionX = 0;
		if(!Double.isFinite(entityIn.motionZ)) entityIn.motionZ = 0;
		if(entityIn.onGround) {
			entityIn.motionY /= 2.0;
			entityIn.motionY += strength;
			if(entityIn.motionY > 0.4) {
				entityIn.motionY = 0.4;
			}
		}
		//Protection from non-finite Y
		if(!Double.isFinite(entityIn.motionY)) entityIn.motionY = 0;
		entityIn.velocityChanged = true;
	}
	
	public static boolean canBlockDamageSource(DamageSource damageSourceIn, EntityLivingBase entity) {
		if(!damageSourceIn.isUnblockable() && entity.isActiveItemStackBlocking()) {
			Vec3d vec3d = damageSourceIn.getDamageLocation();
			if(vec3d != null) {
				Vec3d vec3d1 = entity.getLook(1.0F);
				Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(entity.posX, entity.posY, entity.posZ)).normalize();
				vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);
				return vec3d2.dotProduct(vec3d1) < 0.0D;
			}
		}
		return false;
	}

	/**
	 * For armors, get total sum of this enchantments levels on equipment
	 */
	public static int getTotalEnchantmentLevel(Enchantment enchantment, EntityLivingBase user) {
		Iterable<ItemStack> iterable = enchantment.getEntityEquipment(user);
		if(iterable == null)
			return 0;
		else {
			int enchantLevelSum = 0;
			for(ItemStack itemstack : iterable)
				enchantLevelSum += EnchantmentHelper.getEnchantmentLevel(enchantment, itemstack);
			return enchantLevelSum;
		}
	}
	
	public static float getDamageAfterMagicAbsorb(float damage, float enchantModifiers) {
		float f = MathHelper.clamp(enchantModifiers * 1.5f, 0.0F, 60.0F);
		return damage * (1.0F - f / 80.0F);
	}
	
	@Nullable
	public static Potion getNonInstantNegativePotion() {
		if(PotionLister.debuff_ids.isEmpty()) return null;
		int index = RANDOM.nextInt(PotionLister.debuff_ids.size());
		return Potion.getPotionById(PotionLister.debuff_ids.get(index));
	}
	
	@Nullable
	public static Potion getInstantNegativePotion() {
		if(PotionLister.debuff_instant_ids.isEmpty()) return null;
		int index = RANDOM.nextInt(PotionLister.debuff_instant_ids.size());
		return Potion.getPotionById(PotionLister.debuff_instant_ids.get(index));
	}
	
	@Nullable
	public static Potion getNonInstantPositivePotion() {
		if(PotionLister.buff_ids.isEmpty()) return null;
		int index = RANDOM.nextInt(PotionLister.buff_ids.size());
		return Potion.getPotionById(PotionLister.buff_ids.get(index));
	}
	
	@Nullable
	public static Potion getInstantPositivePotion() {
		if(PotionLister.buff_instant_ids.isEmpty()) return null;
		int index = RANDOM.nextInt(PotionLister.buff_instant_ids.size());
		return Potion.getPotionById(PotionLister.buff_instant_ids.get(index));
	}
	
	//For sunshine and moonlight
	public static float modifyDamageForDaytime(EntityLivingBase attacker, boolean mustBeDaytime, ItemStack stack, Enchantment enchantment) {
		boolean isCorrectDayTime = attacker.world.isDaytime() == mustBeDaytime;
		int level = EnchantmentHelper.getEnchantmentLevel(enchantment, stack);
		float damage = level * 0.5f + 1.5f;
		if(!isCorrectDayTime)
			damage *= -1f;
		if(!EnchantmentsUtility.entityCanSeeSky(attacker)) {
			if(isCorrectDayTime)
				damage -= 0.75f * level;
			else
				damage -= 0.5f * level;
		}
		if(attacker.world.isRaining())
			damage -= 0.5f * level;
		return damage;
	}

	/**
	 * Adds the item stack to the inventory, returns false if it is impossible.
	 */
	public static boolean addItemStackToInventoryWithoutHolding(ItemStack itemStackIn, InventoryPlayer e) {
		return addItemStackToInventorySpecificSlot(-1, itemStackIn, e);
	}
	
	public static boolean addItemStackToInventorySpecificSlot(int slot, final ItemStack stack, InventoryPlayer e) {
		if(stack.isEmpty()) {
			return false;
		}
		else {
			try {
				if(stack.isItemDamaged()) {
					if(slot == -1) {
						slot = getFirstEmptyStackWithoutHolding(e.mainInventory, e);
					}
					if(slot >= 0) {
						e.mainInventory.set(slot, stack.copy());
						// ((ItemStack)e.mainInventory.get(slot)).setAnimationsToGo(0);
						stack.setCount(0);
						return true;
					}
					else if(e.player.capabilities.isCreativeMode) {
						stack.setCount(0);
						return true;
					}
					else {
						return false;
					}
				}
				else {
					int i;
					while(true) {
						i = stack.getCount();
						if(slot == -1) {
							stack.setCount(storePartialItemStack(stack, e));
						}
						else {
							stack.setCount(addResource(slot, stack, e));
						}
						if(stack.isEmpty() || stack.getCount() >= i) {
							break;
						}
					}
					if(stack.getCount() == i && e.player.capabilities.isCreativeMode) {
						stack.setCount(0);
						return true;
					}
					else {
						return stack.getCount() < i;
					}
				}
			}
			catch(Throwable throwable) {
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being added");
				crashreportcategory.addCrashSection("Item ID", Integer.valueOf(Item.getIdFromItem(stack.getItem())));
				crashreportcategory.addCrashSection("Item data", Integer.valueOf(stack.getMetadata()));
				crashreportcategory.addDetail("Registry Name", () -> String.valueOf(stack.getItem().getRegistryName()));
				crashreportcategory.addDetail("Item Class", () -> stack.getItem().getClass().getName());
				crashreportcategory.addDetail("Item name", new ICrashReportDetail<String>() {
					public String call() throws Exception {
						return stack.getDisplayName();
					}
				});
				throw new ReportedException(crashreport);
			}
		}
	}
	
	private static int storePartialItemStack(ItemStack itemStackIn, InventoryPlayer e) {
		int i = e.storeItemStack(itemStackIn);
		if(i == -1) {
			i = getFirstEmptyStackWithoutHolding(e.mainInventory, e);
		}
		return i == -1 ? itemStackIn.getCount() : addResource(i, itemStackIn, e);
	}
	
	private static int addResource(int p_191973_1_, ItemStack p_191973_2_, InventoryPlayer e) {
		Item item = p_191973_2_.getItem();
		int i = p_191973_2_.getCount();
		ItemStack itemstack = e.getStackInSlot(p_191973_1_);
		if(itemstack.isEmpty()) {
			itemstack = p_191973_2_.copy(); // Forge: Replace Item clone above to preserve item capabilities when picking the item up.
			itemstack.setCount(0);
			if(p_191973_2_.hasTagCompound()) {
				itemstack.setTagCompound(p_191973_2_.getTagCompound().copy());
			}
			e.setInventorySlotContents(p_191973_1_, itemstack);
		}
		int j = i;
		if(i > itemstack.getMaxStackSize() - itemstack.getCount()) {
			j = itemstack.getMaxStackSize() - itemstack.getCount();
		}
		if(j > e.getInventoryStackLimit() - itemstack.getCount()) {
			j = e.getInventoryStackLimit() - itemstack.getCount();
		}
		if(j == 0) {
			return i;
		}
		else {
			i = i - j;
			itemstack.grow(j);
			itemstack.setAnimationsToGo(5);
			return i;
		}
	}
	
	public static int getFirstEmptyStackWithoutHolding(List list, InventoryPlayer e) {
		int slot = e.currentItem;
		for(int i = 0; i < list.size(); ++i) {
			if(((ItemStack)list.get(i)).isEmpty() && i != slot) {
				// e.mainInventory.set(i, newTool);
				return i;
			}
		}
		return -1;
	}
}