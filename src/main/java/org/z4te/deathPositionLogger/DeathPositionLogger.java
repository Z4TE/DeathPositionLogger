package org.z4te.deathPositionLogger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class DeathPositionLogger extends JavaPlugin implements Listener {

    private final HashMap<UUID, HashMap<Location, World.Environment>> outerMap = new HashMap<>();

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
        UUID playerId = player.getUniqueId();
        Location deathLocation = player.getLocation();
        World.Environment dimension =event.getEntity().getWorld().getEnvironment();

        HashMap<Location, World.Environment> innerMap = new HashMap<>();
        innerMap.put(deathLocation, dimension);

        outerMap.put(playerId, innerMap);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {

        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        for (Map.Entry<UUID, HashMap<Location, World.Environment>> outerEntry : outerMap.entrySet()) {
            HashMap<Location, World.Environment> innerMapValue = outerEntry.getValue();

            for (Map.Entry<Location, World.Environment> innerEntry : innerMapValue.entrySet()) {
                Location location = innerEntry.getKey();
                World.Environment environment = innerEntry.getValue();

                int x = location.getBlockX();
                int y = location.getBlockY();
                int z = location.getBlockZ();

                String message = String.format("You died at " + ChatColor.GREEN + "[%d, %d, %d]" + ChatColor.RESET + " in %s", x, y, z, environment);
                player.sendMessage(message);
            }
        }
        outerMap.remove(playerId);
    }
}
