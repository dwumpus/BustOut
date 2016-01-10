package GameListeners;

import Interfaces.IGameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by RogerB on 1/9/2016.
 */
public class JoinGameListener implements Listener {

    public IGameManager _gameState;

    public JoinGameListener(IGameManager GameState) {
        _gameState = GameState;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin ( PlayerJoinEvent event ) {
        Player player = event.getPlayer();
        player.sendMessage(String.format("Welcome to Jail"));
        _gameState.IncrementPlayer();
        _gameState.UpdateGameState();
    }

    @EventHandler
    public void onPlayerQuit ( PlayerQuitEvent event ) {
        //// TODO: 1/9/2016 Need to implement a way to handle a player quitting during the game started.
        Bukkit.getLogger().info("Player Left");
        _gameState.DecrementPlayer();
        _gameState.UpdateGameState();
    }


}
