package nl.multitime.timeBungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import nl.multitime.timeBungee.commands.ModCommand;
import nl.multitime.timeBungee.commands.StreamCommand;
import nl.multitime.timeBungee.listeners.TeamJoinListener;

import java.io.File;
import java.util.*;

public final class TimeBungee extends Plugin {
    private static TimeBungee instance;


    private HashMap<UUID, List<UUID>> friendsList;

    private File configFile;
    private Configuration config;

    @Override
    public void onEnable() {
        instance = this;
        friendsList = new HashMap<>();

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        configFile = new File(getDataFolder(), "friends.yml");
        loadConfig();

        // Register all commands
        getProxy().getPluginManager().registerCommand(this, new FriendCommand());
        getProxy().getPluginManager().registerCommand(this, new TeamMessage());
        getProxy().getPluginManager().registerCommand(this, new StreamCommand());
        getProxy().getPluginManager().registerCommand(this, new ModCommand());

        // Register listener
        getProxy().getPluginManager().registerListener(this, new TeamJoinListener());
    }

    private void loadConfig() {
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFriend(UUID player, UUID friend) {
        List<UUID> friends = friendsList.getOrDefault(player, new ArrayList<>());
        friends.add(friend);
        friendsList.put(player, friends);
        saveToConfig(player, friends);
    }

    private void saveToConfig(UUID player, List<UUID> friends) {
        List<String> friendStrings = new ArrayList<>();
        for (UUID friend : friends) {
            friendStrings.add(friend.toString());
        }
        config.set(player.toString(), friendStrings);
        saveConfig();
    }

    @Override
    public void onDisable() {

    }

    public List<UUID> getFriends(UUID player) {
        return friendsList.getOrDefault(player, new ArrayList<>());
    }

    public void removeFriend(UUID player, UUID friend) {
        List<UUID> friends = friendsList.get(player);
        if (friends != null) {
            friends.remove(friend);
            saveToConfig(player, friends);
        }

    }

    public static TimeBungee getInstance() {
        return instance;
    }


    private Set<UUID> frozenPlayers = new HashSet<>();

    public boolean isPlayerFrozen(UUID playerUUID) {
        return frozenPlayers.contains(playerUUID);
    }

    public void freezePlayer(UUID playerUUID) {
        frozenPlayers.add(playerUUID);
    }

    public void unfreezePlayer(UUID playerUUID) {
        frozenPlayers.remove(playerUUID);
    }

}