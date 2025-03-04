package nl.multitime.timeBungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TeamMessage extends Command {

    public TeamMessage() {
        super("tm", "timebungee.team", "teammsg");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Only players can use this command!"));
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Usage: /tm <message>"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        String message = String.join(" ", args);

        for (ProxiedPlayer target : TimeBungee.getInstance().getProxy().getPlayers()) {
            if (target.hasPermission("timebungee.team")) {
                target.sendMessage(new TextComponent(
                        ChatColor.GOLD + "[Team] " +
                                ChatColor.YELLOW + player.getName() + ": " +
                                ChatColor.WHITE + message
                ));
            }
        }
    }
}
