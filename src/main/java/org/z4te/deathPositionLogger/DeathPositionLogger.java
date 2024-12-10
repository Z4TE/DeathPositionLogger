package org.z4te.deathPositionLogger;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class DeathPositionLogger extends JavaPlugin implements Listener {

    private final HashMap<UUID, Location> deathLocationStore = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Location deathLocation = player.getLocation();
        String world = event.getEntity().getWorld().getName();

        deathLocationStore.put(player.getUniqueId(), deathLocation);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {

        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (deathLocationStore.containsKey(playerId)) {
            Location location = deathLocationStore.get(playerId);

            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();

            String recoveryMessage = String.format("You died at " + ChatColor.GREEN + "[%d, %d, %d]",x ,y, z);

            player.sendMessage("");
        }
    }

}
