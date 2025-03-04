package nl.multitime.timeBungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class FriendCommand extends Command {

    public FriendCommand() {
        super("friend", "timebungee.friend", "f");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Only players can use this command!"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length < 2) {
            sendHelp(player);
            return;
        }

        String subCommand = args[0].toLowerCase();
        String targetName = args[1];
        ProxiedPlayer target = TimeBungee.getInstance().getProxy().getPlayer(targetName);

        switch (subCommand) {
            case "add":
                if (target == null) {
                    player.sendMessage(new TextComponent(ChatColor.RED + "Player not found!"));
                    return;
                }
                TimeBungee.getInstance().addFriend(player.getUniqueId(), target.getUniqueId());
                player.sendMessage(new TextComponent(ChatColor.GREEN + "Added " + target.getName() + " as friend!"));
                break;

            case "remove":
                TimeBungee.getInstance().removeFriend(player.getUniqueId(), target.getUniqueId());
                player.sendMessage(new TextComponent(ChatColor.GREEN + "Removed " + targetName + " from friends!"));
                break;

            case "list":
                player.sendMessage(new TextComponent(ChatColor.GOLD + "Your friends:"));
                for (UUID friendUUID : TimeBungee.getInstance().getFriends(player.getUniqueId())) {
                    String friendName = TimeBungee.getInstance().getProxy().getPlayer(friendUUID) != null ?
                            TimeBungee.getInstance().getProxy().getPlayer(friendUUID).getName() : "Offline";
                    player.sendMessage(new TextComponent(ChatColor.YELLOW + "- " + friendName));
                }
                break;

            default:
                sendHelp(player);
                break;
        }
    }

    private void sendHelp(ProxiedPlayer player) {
        player.sendMessage(new TextComponent(ChatColor.GOLD + "Friend Commands:"));
        player.sendMessage(new TextComponent(ChatColor.YELLOW + "/friend add <player> - Add a friend"));
        player.sendMessage(new TextComponent(ChatColor.YELLOW + "/friend remove <player> - Remove a friend"));
        player.sendMessage(new TextComponent(ChatColor.YELLOW + "/friend list - List your friends"));
    }
}
