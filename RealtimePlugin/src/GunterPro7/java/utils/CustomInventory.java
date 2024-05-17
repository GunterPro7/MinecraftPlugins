package utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomInventory {
    private final ItemStack[] matrix;
    private final ItemStack result;

    public CustomInventory(CustomItem[] itemStacks, CustomItem result) {
        ItemStack[] matrix = new ItemStack[9];
        for (int i = 0; i < itemStacks.length; i++) {
            if (itemStacks[i] != null) {
                matrix[i] = itemStacks[i].getItemStack();
            }
        }
        this.matrix = matrix;
        this.result = result.getItemStack();
    }

    public CustomInventory(CustomItem itemStacks, CustomItem result, int... locations) {
        ItemStack[] matrix = new ItemStack[9];

        ItemStack itemStack = itemStacks.getItemStack();

        for (int location : locations) {
            matrix[location] = itemStack;
        }
        this.matrix = matrix;
        this.result = result.getItemStack();
    }

    public CustomInventory(Material[] itemStacks, CustomItem result) {
        ItemStack[] matrix = new ItemStack[9];
        for (int i = 0; i < itemStacks.length; i++) {
            if (itemStacks[i] != null) {
                matrix[i] = new ItemStack(itemStacks[i]);
            }
        }
        this.matrix = matrix;
        this.result = result.getItemStack();
    }

    public CustomInventory(Material itemStacks, CustomItem result, int... locations) {
        ItemStack[] matrix = new ItemStack[9];

        ItemStack itemStack = new ItemStack(itemStacks);

        for (int location : locations) {
            matrix[location] = itemStack;
        }
        this.matrix = matrix;
        this.result = result.getItemStack();
    }

    public ItemStack[] getMatrix() {
        return matrix;
    }

    public ItemStack getResult() {
        return result;
    }
}
