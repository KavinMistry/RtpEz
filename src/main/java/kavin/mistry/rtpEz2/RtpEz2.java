package kavin.mistry.rtpEz2;

import org.bukkit.plugin.java.JavaPlugin;

public final class RtpEz2 extends JavaPlugin {

    @Override
    public void onEnable()
    {
        getCommand("rtp").setExecutor(new rtp());
        rtpSettings.getInstance().load();
    }

    @Override
    public void onDisable()
    {

    }

    public static RtpEz2 getInstance()
    {
        return getPlugin(RtpEz2.class);
    }
}
