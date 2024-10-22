## Broad Cleanup
- Sorted enchants into groups
- Moved maxlvl, treasure, enabled, enchantabilities into configs
- Moved isOffensivePetDisallowed to isDamageSourceAllowed
- Added canApply override to EnchantmentCurse to properly disallow curses on e-table without disabling them on anvil
- Added EnumSlots to move checked slots (getMaxEnchantmentLevel and getEnchantedItem) to EnchantRegistry
- Renamed EnumList to EnumTypes (additional types of items this enchant can go on, vanilla got EnumEnchantmentType)
- Merged calcDamageForNegativeSwipe and calcDamageIgnoreSwipe to modifyDamage and changed arguments
- removed some redundant EnumTypes that are also in vanilla EnumEnchantmentType

## Renames
- WellTilled = Moisturized
- TillingPower = Plowing
- Sharper(ed) Edge = Reinforced Sharpness

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