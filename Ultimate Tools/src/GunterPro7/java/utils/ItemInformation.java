package utils;

import org.bukkit.inventory.ItemStack;

public class ItemInformation {
    String name;
    ItemStack item;
    String nameID;
    String permission;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public ItemInformation(String name, ItemStack item, String nameID) {
        this.name = name;
        this.item = item;
        this.nameID = nameID;
        permission = "ultimatetools.use." + nameID;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String permission) {
        this.nameID = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
