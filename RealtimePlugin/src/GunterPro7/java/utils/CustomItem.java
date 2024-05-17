package utils;
import fragments.*;
import items.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum CustomItem {
    ETERNITYS_EDGE(new EternitysEdge(), CustomItemType.WEAPON),
    ETERNITYS_PICKAXE(new EternitysPickaxe(), CustomItemType.TOOL),
    ETERNITYS_CHESTPLATE(new EternitysArmor(Material.DIAMOND_CHESTPLATE), CustomItemType.ARMOR),
    ETERNITYS_HELMET(new EternitysArmor(Material.DIAMOND_HELMET), CustomItemType.ARMOR),
    ETERNITYS_LEGGINGS(new EternitysArmor(Material.DIAMOND_LEGGINGS), CustomItemType.ARMOR),
    ETERNITYS_BOOTS(new EternitysArmor(Material.DIAMOND_BOOTS), CustomItemType.ARMOR),
    ILLUSIONER_WAND(new IllusionerWand(), CustomItemType.WEAPON),
    RUBY_CHESTPLATE(new RubyArmor(Material.LEATHER_CHESTPLATE), CustomItemType.ARMOR),
    RUBY_HELMET(new RubyArmor(Material.LEATHER_HELMET), CustomItemType.ARMOR),
    RUBY_LEGGINGS(new RubyArmor(Material.LEATHER_LEGGINGS), CustomItemType.ARMOR),
    RUBY_BOOTS(new RubyArmor(Material.LEATHER_BOOTS), CustomItemType.ARMOR),
    RUBY_PICKAXE(new RubyPickaxe(), CustomItemType.TOOL),
    ULTIMATE_TOTEM(new UltimateTotem(), CustomItemType.TOOL),
    AUTO_CRAFTER(new AutoCrafter(), CustomItemType.TOOL),
    SILKY_HOE(new SilkyHoe(), CustomItemType.TOOL),
    LUMBERJACK_AXE(new LumberjackAxe(), CustomItemType.TOOL),

    CHARCOLE_FRAGMENT(new CharcoleFragment(), CustomItemType.FRAGMENT),
    CRAFTING_FRAGMENT(new CraftingFragment(), CustomItemType.FRAGMENT),
    EMERALD_FRAGMENT(new EmeraldFragment(), CustomItemType.FRAGMENT),
    ILLUSIONER_FRAGMENT(new IllusionerFragment(), CustomItemType.FRAGMENT),
    NAUTILUS_FRAGMENT(new NautilusFragment(), CustomItemType.FRAGMENT),
    RUBY_FRAGMENT(new RubyFragment(), CustomItemType.FRAGMENT),
    TREASURE_FRAGMENT(new TreasureFragment(), CustomItemType.FRAGMENT),
    WITHER_ESSENCE(new WitherEssence(), CustomItemType.FRAGMENT),

    FARMING_FRAGMENT_POTATO(new FarmingFragment(Material.POTATO), CustomItemType.FRAGMENT),
    FARMING_FRAGMENT_CARROT(new FarmingFragment(Material.CARROT), CustomItemType.FRAGMENT),
    FARMING_FRAGMENT_SEEDS(new FarmingFragment(Material.WHEAT_SEEDS), CustomItemType.FRAGMENT),
    FARMING_FRAGMENT_BEETROOT(new FarmingFragment(Material.BEETROOT_SEEDS), CustomItemType.FRAGMENT),
    FARMING_FRAGMENT_COCOA(new FarmingFragment(Material.COCOA_BEANS), CustomItemType.FRAGMENT),
    CHARCOLE_BLOCK_FRAGMENT(new CharcoleBlockFragment(), CustomItemType.FRAGMENT),

    CHARCOLE_BUNDLE_FRAGMENT(new CharcoleBundleFragment(), CustomItemType.FRAGMENT),
    ;

    CustomItem(ItemStack item, CustomItemType type) {
        this.item = item;
        this.type = type;
    }

    private final ItemStack item;
    private final CustomItemType type;

    public ItemStack getItemStack() {
        return item;
    }

    public CustomItemType getType() {
        return type;
    }
}