package main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NPC {
    private final Player owner;
    private final Entity entity;
    private boolean listening;
    private final List<String> interactionMessages = new ArrayList<>();
    private int page;

    public NPC(Player owner) {
        this(owner.getWorld().spawnEntity(owner.getLocation(), EntityType.VILLAGER, true), owner);
    }

    public NPC(Entity entity, Player owner) {
        this.owner = owner;
        this.entity = entity;
        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.setAI(false);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    public boolean isListening() {
        return listening;
    }

    public List<String> getInteractionMessages() {
        return interactionMessages;
    }

    public void addInteractionLine(String message) {
        interactionMessages.add(message);
    }

    public void setInteractionPage(int page) {
        this.page = page;
    }

    public int getInteractionPage() {
        return page;
    }

    public void removeInteractionLine(String name) {
        interactionMessages.remove(ChatColor.stripColor(name));
    }

    public void moveInteractionLine(String name, boolean up) {
        name = ChatColor.stripColor(name);
        int index = interactionMessages.indexOf(name) + (up ? -1 : 1);

        interactionMessages.remove(name);
        if (index < 0) {
            index = 0;
        }
        else if (index > interactionMessages.size()) {
            index -= 1;
        }
        interactionMessages.add(index, name);
    }

    public static void sendChatMessages(Player player, List<String> messages, int i) {
        if (!messages.isEmpty()) {
            player.sendMessage(messages.get(0));
            messages.remove(0);

            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> sendChatMessages(player, messages, i), i);
        }
    }
}
