package gg.tgb.defaultspeed;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class DefaultSpeed extends JavaPlugin implements Listener {

    private static File configFile;
    private static YamlConfiguration config;

    private float defaultWalkSpeed = 0.2f;
    private float defaultFlySpeed = 0.1f;

    @Override
    public void onEnable() {
        System.out.println("DefaultSpeed is enabling");
        getServer().getPluginManager().registerEvents(this, this);
        configFile = new File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        try {
            if(!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                config.set("walk-speed", defaultWalkSpeed);
                config.set("fly-speed", defaultFlySpeed);
                config.save(configFile);
            } else {
                config.load(configFile);
                defaultWalkSpeed = (float) config.getDouble("walk-speed");
                defaultFlySpeed = (float) config.getDouble("fly-speed");

                if(defaultFlySpeed > 1f) defaultFlySpeed = 1f;
                if(defaultWalkSpeed > 1f) defaultWalkSpeed = 1f;
                if(defaultWalkSpeed < -1f) defaultWalkSpeed = -1f;
                if(defaultFlySpeed < -1f) defaultFlySpeed = -1f;
            }
            System.out.println("Default walk speed set to: " + defaultWalkSpeed);
            System.out.println("Default fly speed set to: " + defaultFlySpeed);
        } catch (Exception e) {
            System.out.println("Failed to load config");
            e.printStackTrace();
        }
        System.out.println("DefaultSpeed has started");
    }

    @Override
    public void onDisable() {
        System.out.println("DefaultSpeed is disabling");
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.setWalkSpeed(defaultWalkSpeed);
        p.setFlySpeed(defaultFlySpeed);
    }
}
