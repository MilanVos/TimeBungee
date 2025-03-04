package nl.multitime.timeBungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import nl.multitime.timeBungee.TimeBungee;

public class StreamCommand extends Command {

    public StreamCommand() {
        super("stream", "timebungee.stream");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Only players can use this command!"));
            return;
        }

        ProxiedPlayer streamer = (ProxiedPlayer) sender;
        String serverName = streamer.getServer().getInfo().getName();

        TextComponent message = new TextComponent(
            ChatColor.DARK_PURPLE + "➤ " +
            ChatColor.LIGHT_PURPLE + streamer.getName() +
            ChatColor.WHITE + " is now streaming! " +
            ChatColor.GRAY + "[Click to Join]"
        );

        message.setClickEvent(new ClickEvent(
            ClickEvent.Action.RUN_COMMAND,
            "/server " + serverName
        ));

        message.setHoverEvent(new HoverEvent(
            HoverEvent.Action.SHOW_TEXT,
            new ComponentBuilder(ChatColor.GREEN + "Click to join " + streamer.getName() + "'s server!").create()
        ));

        TimeBungee.getInstance().getProxy().broadcast(message);
    }
}
