package nl.multitime.timeBungee.listeners;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import nl.multitime.timeBungee.TimeBungee;

public class TeamJoinListener implements Listener {

    @EventHandler
    public void onServerConnect(ServerConnectedEvent event) {
        ProxiedPlayer player = event.getPlayer();
        String serverName = event.getServer().getInfo().getName();

        for (ProxiedPlayer staff : TimeBungee.getInstance().getProxy().getPlayers()) {
            if (staff.hasPermission("timebungee.team")) {
                staff.sendMessage(new TextComponent(
                    ChatColor.GOLD + "[Team] " +
                    ChatColor.YELLOW + player.getName() +
                    ChatColor.WHITE + " connected to " +
                    ChatColor.GREEN + serverName
                ));
            }
        }
    }
}
