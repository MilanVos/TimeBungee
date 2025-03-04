package nl.multitime.timeBungee.listeners;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import nl.multitime.timeBungee.TimeBungee;

public class FreezeListener implements Listener {

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (TimeBungee.getInstance().isPlayerFrozen(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage(new TextComponent(ChatColor.RED + "You cannot switch servers while frozen!"));
        }
    }
}
