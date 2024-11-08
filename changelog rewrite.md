## Broad Cleanup
- Sorted enchants into groups
- Moved maxlvl, treasure, enabled, enchantabilities into configs
- Moved isOffensivePetDisallowed to isDamageSourceAllowed
- Added canApply override to EnchantmentCurse to properly disallow curses on e-table without disabling them on anvil
- Added EnumSlots to move checked slots (getMaxEnchantmentLevel and getEnchantedItem) to EnchantRegistry
- Renamed EnumList to EnumTypes (additional types of items this enchant can go on, vanilla got EnumEnchantmentType)
- Merged calcDamageForNegativeSwipe and calcDamageIgnoreSwipe to modifyDamage and changed arguments
- removed some redundant EnumTypes that are also in vanilla EnumEnchantmentType
- moved incompatible enchants to config, deleted all canApplyTogether overrides
- cleaned up incompatible enchant groups

## Renames
- WellTilled = Moisturized
- TillingPower = Plowing
- Sharper(ed) Edge = Reinforced Sharpness
- Pulling/Drag = Dragging
- Clear Skies' Favor = Clearskies' Favor
- Luna's Blessing = Lunar's Blessing

## Code cleanup by Enchant
- Pushing.repelEntitiesInAABBFromPoint math cleanup
  - capped denominators to avoid dividing by 0 or negative numbers
- Both Ancient enchants implement IAncient
- Adv Thorns and Burning Thorns search through the whole equipment, not just armor to do dura dmg
  - this is to allow them on shields for example or on any other equipment on main/offhand if wanted
- Burning Thorns properly does dura dmg now (was searching for an item that has Thorns, not burn thorns)
- Added IEnchantmentFire to move fireticks per level to enchant and be able to call that from arc slash
- Freezing code cleanup having 5 lines per block thats turned to ice and having lots of code to differentiate between players and non-players just to add one extra amplifier
- Cleaned up OtherHandler."enchHand", renamed to subjectHandler
- Flame Tier Types set to Bow (why was it Weapon=Sword?)
- Cleaned up TierDamage - only actual change is entitydamagedalt mistakingly checking 5 and 6 instead of 4 and 5 (both BoA's)
- Cleaned up Weather Enchants except for rain having an if bracket thats never called - unsure whether to fix and thus nerf it or to delete it
- Cleaned up CombatVeterancy code - seems op
- Cleaned up CounterAttack - mainly deleting commented out code and replaced 20 with maxhurtresistanttime
- fixed unreasonable always proccing (roll was wrong) and not stopping to go through entity list after one random target was found
- cleaned up rune magical blessing and fixed it only working (and crashing) if the player DOESNT have it