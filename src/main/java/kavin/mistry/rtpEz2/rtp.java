package kavin.mistry.rtpEz2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.*;

public class rtp implements TabExecutor, CommandExecutor {

    public Map<UUID, Instant> time = new HashMap<>();
    int cool;

    public List<String> OnlinePlayers()
    {
        Collection<? extends Player> list = Bukkit.getOnlinePlayers();
        List<String> list2 = new ArrayList<>();
        for(Player p : list)
        {
            list2.add(p.getName());
        }
        return list2;
    }

    public void startTeleportCountdown(Player player)
    {
        World world = player.getWorld();
        List<String> Dworlds = rtpSettings.getInstance().getWorlds();
        if (Dworlds.contains(world.getName())) {
            if (player.hasPermission("rtpez.command.admin"))
            {
                player.sendMessage(rtpSettings.getInstance().rtpDisabledAdminWorld());
            } else
            {
                player.sendMessage(rtpSettings.getInstance().rtpDisabledWorld());
            }
            return;
        }
        UUID uuid = player.getUniqueId();
        if((!time.containsKey(uuid)) || ((time.get(uuid).toEpochMilli() + cool) <= Instant.now().toEpochMilli()) || cool == 0)
        {
            Instant cooldown = Instant.now();
            time.put(uuid, cooldown);
        }
        else
        {
            long timeleft = (time.get(uuid).toEpochMilli() + cool) - Instant.now().toEpochMilli();
            player.sendMessage(rtpSettings.getInstance().rtpCooldown1() + timeleft/1000 + rtpSettings.getInstance().rtpCooldown2());
            return;
        }

        Location loc = player.getLocation();
        boolean onCount = rtpSettings.getInstance().getSoundCount();
        new BukkitRunnable()
        {
            int secondsLeft = rtpSettings.getInstance().getCountdownTime();

            @Override
            public void run()
            {
                Location loc1 = player.getLocation();
                if(secondsLeft > 0 && (loc.equals(loc1)))
                {
                    player.sendTitle(rtpSettings.getInstance().teleportingTitle() + secondsLeft, null,0 ,30, 0);
                    if(onCount)
                    {
                        player.playSound(player , Sound.ENTITY_ARROW_HIT_PLAYER, 3f, 2f);
                    }
                    secondsLeft--;
                }
                else if((rtpSettings.getInstance().MoveCancel()) && !(loc.equals(loc1)))
                {
                    player.sendTitle(rtpSettings.getInstance().movedTitle(), null, 0, 15, 0);
                    cancel();
                }
                else
                {
                    player.teleport(LocationFind((player)));
                    cancel();
                }
            }
        }.runTaskTimer(RtpEz2.getInstance(), 0L, 20L);
    }

    public Location LocationFind(Player player)
    {
        int Depth = rtpSettings.getInstance().getDepth();
        for(int i = 0; i < Depth; i ++)
        {
            World world = player.getWorld();
            List<Biome> Dbiome = Arrays.asList(Biome.OCEAN, Biome.COLD_OCEAN, Biome.DEEP_COLD_OCEAN, Biome.DEEP_OCEAN, Biome.DEEP_FROZEN_OCEAN, Biome.DEEP_LUKEWARM_OCEAN, Biome.WARM_OCEAN, Biome.LUKEWARM_OCEAN, Biome.FROZEN_OCEAN);
            List<String> Dblock = rtpSettings.getInstance().DisabledBlocks();
            int end = rtpSettings.getInstance().getEnd(world), start = rtpSettings.getInstance().getStart(world);
            double x = start + (Math.random() * (end - start)), z = start + (Math.random() * (end - start));
            Block block = world.getHighestBlockAt((int) x, (int) z);
            if(Dblock.contains(block.getType().name())) break;
            Location loc = new Location(world, x, block.getY()+1, z);
            if (!(Dbiome.contains(world.getBiome(loc))))
            {
                player.sendTitle(rtpSettings.getInstance().teleportedTitle(), null, 0, 10, 0);
                boolean onTeleport = rtpSettings.getInstance().getSoundTeleport();
                if(onTeleport)
                {
                    player.playSound(player , Sound.BLOCK_BEACON_POWER_SELECT, 3f, 2f);
                }
                return loc;
            }
        }
        player.sendMessage(rtpSettings.getInstance().noSafeLocation());
        return player.getLocation();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {

        if(!(commandSender instanceof Player player))
        {
            commandSender.sendMessage(rtpSettings.getInstance().nonPlayerSender());
            return false;
        }
        World world = player.getWorld();
        cool = rtpSettings.getInstance().getCooldown(world);
        if(strings.length > 1)
        {
            return false;
        }
        if(strings.length == 0)
        {
            startTeleportCountdown(player);
            return true;
        }
        if((strings[0].equalsIgnoreCase("refresh")) && player.hasPermission("rtpez.command.admin"))
        {
            rtpSettings.getInstance().load();
            player.sendMessage("§3Refreshed changes made to settings.yml!");
            return true;
        }
        Player SUDOplayer = Bukkit.getPlayer(strings[0]);
        if(SUDOplayer != null && player.hasPermission("rtpez.command.admin"))
        {
            SUDOplayer.performCommand("rtp");
        }
        else if(SUDOplayer == null && player.hasPermission("rtpez.command.admin"))
        {
            player.sendMessage(rtpSettings.getInstance().invalidPlayer());
        }
        else
        {
            return false;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if((commandSender instanceof Player) && commandSender.hasPermission("rtpez.command.admin"))
        {
            List<String> list =  OnlinePlayers();
            list.add("refresh");
            if(strings.length == 1)
            {
                return list;
            }
        }
        return new ArrayList<>();
    }
}
