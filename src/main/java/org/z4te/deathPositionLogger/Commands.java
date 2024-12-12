package org.z4te.deathPositionLogger;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("recovery")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("This command can only be executed by players");
                return true;
            }
            Location location = player.getLastDeathLocation();
            World.Environment environment = Objects.requireNonNull(Objects.requireNonNull(player.getLastDeathLocation()).getWorld()).getEnvironment();

            if (location != null) {
                int x = location.getBlockX();
                int y = location.getBlockY();
                int z = location.getBlockZ();

                String message = String.format("You died at " + ChatColor.GREEN + "[%d, %d, %d]" + ChatColor.RESET + " in %s", x, y, z, environment);
                player.sendMessage(message);
            } else {
                player.sendMessage("You haven't died yet in this server!");
            }
            return true;
        }

        return false;
    }
}
