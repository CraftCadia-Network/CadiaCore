package me.dinosphere.CadiaCore.ghost;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class GhostManager
{
  private static final String GHOST_TEAM_NAME = "Ghosts";
  private static final long UPDATE_DELAY = 20L;
  private static final OfflinePlayer[] EMPTY_PLAYERS = new OfflinePlayer[0];
  private Team ghostTeam;
  private BukkitTask task;
  private boolean closed;
  private final Set<String> ghosts = new HashSet();
  
  public GhostManager(Plugin plugin)
  {
    createTask(plugin);
    createGetTeam();
  }
  
  private void createGetTeam()
  {
    Scoreboard board = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
    
    this.ghostTeam = board.getTeam("Ghosts");
    if (this.ghostTeam == null) {
      this.ghostTeam = board.registerNewTeam("Ghosts");
    }
    this.ghostTeam.setCanSeeFriendlyInvisibles(true);
  }
  
  private void createTask(Plugin plugin)
  {
    this.task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable()
    {
      public void run()
      {
        OfflinePlayer[] arrayOfOfflinePlayer;
        int j = (arrayOfOfflinePlayer = GhostManager.this.getMembers()).length;
        for (int i = 0; i < j; i++)
        {
          OfflinePlayer member = arrayOfOfflinePlayer[i];
          Player player = member.getPlayer();
          if (player != null)
          {
            GhostManager.this.setGhost(player, GhostManager.this.isGhost(player));
          }
          else
          {
            GhostManager.this.ghosts.remove(member.getName());
            GhostManager.this.ghostTeam.removePlayer(member);
          }
        }
      }
    }, 20L, 20L);
  }
  
  public void clearMembers()
  {
    if (this.ghostTeam != null)
    {
      OfflinePlayer[] arrayOfOfflinePlayer;
      int j = (arrayOfOfflinePlayer = getMembers()).length;
      for (int i = 0; i < j; i++)
      {
        OfflinePlayer player = arrayOfOfflinePlayer[i];
        this.ghostTeam.removePlayer(player);
      }
    }
  }
  
  public void addPlayer(Player player)
  {
    validateState();
    if (!this.ghostTeam.hasPlayer(player))
    {
      this.ghostTeam.addPlayer(player);
      player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15));
    }
  }
  
  public boolean isGhost(Player player)
  {
    return (player != null) && (hasPlayer(player)) && (this.ghosts.contains(player.getName()));
  }
  
  public boolean hasPlayer(Player player)
  {
    validateState();
    return this.ghostTeam.hasPlayer(player);
  }
  
  public void setGhost(Player player, boolean isGhost)
  {
    if (!hasPlayer(player)) {
      addPlayer(player);
    }
    if (isGhost)
    {
      this.ghosts.add(player.getName());
      player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15));
    }
    else if (!isGhost)
    {
      this.ghosts.remove(player.getName());
      player.removePotionEffect(PotionEffectType.INVISIBILITY);
    }
  }
  
  public void removePlayer(Player player)
  {
    validateState();
    if (this.ghostTeam.removePlayer(player)) {
      player.removePotionEffect(PotionEffectType.INVISIBILITY);
    }
  }
  
  public OfflinePlayer[] getGhosts()
  {
    validateState();
    Set<OfflinePlayer> players = new HashSet(this.ghostTeam.getPlayers());
    for (Iterator<OfflinePlayer> it = players.iterator(); it.hasNext();) {
      if (!this.ghosts.contains(((OfflinePlayer)it.next()).getName())) {
        it.remove();
      }
    }
    return toArray(players);
  }
  
  public OfflinePlayer[] getMembers()
  {
    validateState();
    return toArray(this.ghostTeam.getPlayers());
  }
  
  private OfflinePlayer[] toArray(Set<OfflinePlayer> players)
  {
    if (players != null) {
      return (OfflinePlayer[])players.toArray(new OfflinePlayer[0]);
    }
    return EMPTY_PLAYERS;
  }
  
  public void close()
  {
    if (!this.closed)
    {
      this.task.cancel();
      this.ghostTeam.unregister();
      this.closed = true;
    }
  }
  
  public boolean isClosed()
  {
    return this.closed;
  }
  
  private void validateState()
  {
    if (this.closed) {
      throw new IllegalStateException("Ghost factory has closed. Cannot reuse instances.");
    }
  }
}
