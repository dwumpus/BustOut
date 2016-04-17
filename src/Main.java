import GameListeners.JoinGameListener;
import GameObjects.GAME_STATE;
import GameObjects.GameManager;
import Interfaces.IGameManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * Created by RogerB on 1/9/2016.
 */
public final class Main extends JavaPlugin implements Listener {
    private IGameManager game;
    private Location startLocation;
    public static final String ConfigFile = "banned_players.yml";

    @Override
    public void onEnable() {
        getLogger().info("Enabling...");
        World world = getServer().getWorlds().get(0);
        startLocation = new Location(world, 100, 100, 100, 20, 50);
        game = new GameManager(2, startLocation);

        game.SetPlayerCountToDefault();
        Bukkit.getServer().getPluginManager().registerEvents(new JoinGameListener(game), this);
        getLogger().info("Enabled");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player)sender;

        switch (label.toLowerCase()) {

            case "getstate":
            {
                player.sendMessage(String.format("The current game state is: %s", game.GetCurrentState().toString()));
                break;
            }

            case "getplayercount":
            {
                player.sendMessage(String.format("The current game player count is: %s", game.GetCurrentPlayerCount()));
                break;
            }
            case "start":
            {
                if(game.GetCurrentState() == GAME_STATE.GAME_STARTING){
                    game.StartGame();
                }
                else {
                    player.sendMessage(String.format("Game is not ready to start."));
                }
                break;
            }

            default:
                break;
        }

        return true;
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling...");
        getLogger().info("Disabled.");
        try {
            game.GetConfig().save(ConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoad() {
        getLogger().info("Loading...");
        getLogger().info("Loaded.");
    }

}
