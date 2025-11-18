package kavin.mistry.rtpEz2;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class rtpSettings
{
    private final static rtpSettings instance = new rtpSettings();

    private File file;
    private YamlConfiguration config;

    private int Depth;
    private List<String> DWorlds;
    private List<String> DisabledBlocks;
    private int CountdownTime;
    private boolean MoveCancel;
    private boolean OnTeleport;
    private boolean TeleportCountdown;
    private String rtpDisabledWorld, rtpDisabledAdminWorld, rtpCooldown1, rtpCooldown2, teleportingTitle, movedTitle, teleportedTitle, noSafeLocation, nonPlayerSender, invalidPlayer;

    private rtpSettings() {
    }

    public void load(){
        file = new File(RtpEz2.getInstance().getDataFolder(), "settings.yml");

        if(!(file.exists())){
            RtpEz2.getInstance().saveResource("settings.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception ignored){
        }

        Depth = config.getInt("Depth");
        DWorlds = new ArrayList<>(config.getStringList("DisabledWorlds"));
        CountdownTime = config.getInt("CountdownTime");
        MoveCancel = config.getBoolean("MoveCancel");
        OnTeleport = config.getBoolean("OnTeleport");
        TeleportCountdown = config.getBoolean("TeleportCountdown");
        DisabledBlocks = new ArrayList<>(config.getStringList("DisabledBlocks"));

        rtpDisabledWorld = config.getString("rtpDisabledWorld");
        rtpDisabledAdminWorld = config.getString("rtpDisabledAdminWorld");
        rtpCooldown1 = config.getString("rtpCooldown1");
        rtpCooldown2 = config.getString("rtpCooldown2");
        movedTitle = config.getString("movedTitle");
        teleportingTitle = config.getString("teleportingTitle");
        teleportedTitle = config.getString("teleportedTitle");
        noSafeLocation = config.getString("noSafeLocation");
        nonPlayerSender = config.getString("nonPlayerSender");
        invalidPlayer = config.getString("invalidPlayer");
    }

    public String invalidPlayer()
    {
        return invalidPlayer;
    }

    public String nonPlayerSender()
    {
        return nonPlayerSender;
    }

    public String noSafeLocation()
    {
        return noSafeLocation;
    }

    public String teleportedTitle()
    {
        return teleportedTitle;
    }

    public String teleportingTitle()
    {
        return teleportingTitle;
    }

    public String movedTitle()
    {
        return movedTitle;
    }

    public String rtpCooldown1()
    {
        return rtpCooldown1;
    }

    public String rtpCooldown2()
    {
        return rtpCooldown2;
    }

    public String rtpDisabledAdminWorld()
    {
        return rtpDisabledAdminWorld;
    }

    public String rtpDisabledWorld()
    {
        return rtpDisabledWorld;
    }

    public int getStart(World world)
    {
        if(config.contains(world.getName()))
        {
            return config.getInt(world.getName() + ".Range.Start");
        }
        else
        {
            return config.getInt("world.Range.Start");
        }
    }

    public int getEnd(World world)
    {
        if(config.contains(world.getName()))
        {
            return config.getInt(world.getName() + ".Range.End");
        }
        else
        {
            return config.getInt("world.Range.End");
        }
    }

    public int getCooldown(World world)
    {
        if(config.contains(world.getName()))
        {
            return config.getInt(world.getName() + ".Cooldown");
        }
        else
        {
            return config.getInt("world.Cooldown");
        }
    }

    public int getDepth()
    {
        return Depth;
    }

    public int getCountdownTime()
    {
        return CountdownTime;
    }

    public boolean MoveCancel()
    {
        return MoveCancel;
    }

    public List<String> getWorlds()
    {
        return DWorlds;
    }

    public List<String> DisabledBlocks()
    {
        return DisabledBlocks;
    }

    public boolean getSoundTeleport()
    {
        return OnTeleport;
    }

    public boolean getSoundCount()
    {
        return TeleportCountdown;
    }

    public static rtpSettings getInstance()
    {
        return instance;
    }
}
