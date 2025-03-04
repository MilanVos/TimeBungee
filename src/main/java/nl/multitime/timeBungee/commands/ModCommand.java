package nl.multitime.timeBungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import nl.multitime.timeBungee.TimeBungee;

import java.util.UUID;

public class ModCommand extends Command {

    public ModCommand() {
        super("mod", "timebungee.mod");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sendHelp(sender);
            return;
        }

        String action = args[0].toLowerCase();
        String targetName = args[1];
        ProxiedPlayer target = TimeBungee.getInstance().getProxy().getPlayer(targetName);

        switch (action) {
            case "kick":
                if (target != null && args.length > 2) {
                    String reason = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);
                    target.disconnect(new TextComponent(ChatColor.RED + "You have been kicked: " + ChatColor.WHITE + reason));
                    broadcastToStaff(ChatColor.RED + targetName + " was kicked by " + sender.getName() + " for: " + reason);
                }
                break;

            case "warn":
                if (target != null && args.length > 2) {
                    String reason = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);
                    target.sendMessage(new TextComponent(ChatColor.RED + "Warning: " + ChatColor.WHITE + reason));
                    broadcastToStaff(ChatColor.YELLOW + targetName + " was warned by " + sender.getName() + " for: " + reason);
                }
                break;

            case "freeze":
                if (target != null) {
                    UUID targetUUID = target.getUniqueId();
                    if (TimeBungee.getInstance().isPlayerFrozen(targetUUID)) {
                        TimeBungee.getInstance().unfreezePlayer(targetUUID);
                        target.sendMessage(new TextComponent(ChatColor.GREEN + "You have been unfrozen!"));
                        broadcastToStaff(ChatColor.AQUA + targetName + " was unfrozen by " + sender.getName());
                    } else {
                        TimeBungee.getInstance().freezePlayer(targetUUID);
                        target.sendMessage(new TextComponent(ChatColor.RED + "You have been frozen!"));
                        broadcastToStaff(ChatColor.AQUA + targetName + " was frozen by " + sender.getName());
                    }
                }
                break;
        }
    }

    private void broadcastToStaff(String message) {
        for (ProxiedPlayer staff : TimeBungee.getInstance().getProxy().getPlayers()) {
            if (staff.hasPermission("timebungee.mod")) {
                staff.sendMessage(new TextComponent(ChatColor.GOLD + "[Mod] " + message));
            }
        }
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(new TextComponent(ChatColor.GOLD + "Moderation Commands:"));
        sender.sendMessage(new TextComponent(ChatColor.YELLOW + "/mod kick <player> <reason>"));
        sender.sendMessage(new TextComponent(ChatColor.YELLOW + "/mod warn <player> <reason>"));
        sender.sendMessage(new TextComponent(ChatColor.YELLOW + "/mod freeze <player>"));
    }
}
