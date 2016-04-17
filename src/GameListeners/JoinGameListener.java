package GameListeners;

import GameObjects.GAME_STATE;
import Interfaces.IGameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Calendar;
import java.util.Date;

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
        if(_gameState.PlayerBanned(event.getPlayer().getName())) {
            try {
                Bukkit.broadcastMessage(event.getPlayer().getName() + " Is banned");
                event.getPlayer().kickPlayer("Sorry you are currently banned for leaving early.");
                Date currentDateTime = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentDateTime);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        _gameState.IncrementPlayer();
        _gameState.UpdateGameState();
    }

    @EventHandler
    public void onPlayerQuit ( PlayerQuitEvent event ) {
        if(_gameState.GetCurrentState().equals(GAME_STATE.GAME_RUNNING)){
            _gameState.BanPlayer(event.getPlayer().getName());
        }
        Bukkit.getServer().broadcastMessage("SHAMALAMADINGDONG");
        Bukkit.getLogger().info("Player Left");
        _gameState.DecrementPlayer();
        _gameState.UpdateGameState();
    }

  /*  @EventHandler
    public void onPlayerKick ( PlayerKickEvent event ) {
        //// TODO: 1/9/2016 Need to implement a way to handle a player quitting during the game started.
        Bukkit.getLogger().info("Player Left");
        _gameState.DecrementPlayer();
        _gameState.UpdateGameState();
    }
*/
}
