package GameObjects;

import Interfaces.IGameManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Date;

import static net.minecraft.server.v1_8_R3.MinecraftServer.getServer;

/**
 * Created by RogerB on 1/9/2016.
 */
public class GameManager implements IGameManager {
    private GAME_STATE _state;
    private int _gameReadyCount;
    private int _currentPlayerCount;
    public Location _startingLocation;

    public GameManager(int playersNeeded, Location startingLocation) {
        _state = GAME_STATE.WAITING_FOR_PLAYERS;
        _gameReadyCount = playersNeeded;
        _startingLocation = startingLocation;

    }

    public FileConfiguration GetConfig() {
        return Bukkit.getServer().getPluginManager().getPlugin("BustOut").getConfig();
    }

    public GAME_STATE GetCurrentState() {
        return _state;
    }

    public void SetCurrentState(GAME_STATE state) {
        _state = state;
    }

    public void UpdateGameState() {
        switch (_state) {
            case WAITING_FOR_PLAYERS: {
                if (_currentPlayerCount == _gameReadyCount) {
                    _state = GAME_STATE.GAME_STARTING;
                }
                break;
            }
            default:
                break;
        }

        //Always check to see if someone left and put the game into a waiting state if someone leaves.
        if (_currentPlayerCount < _gameReadyCount)
            _state = GAME_STATE.WAITING_FOR_PLAYERS;
    }

    public void IncrementPlayer() {
        _currentPlayerCount++;
    }

    public void DecrementPlayer() {
        _currentPlayerCount--;
    }

    public int GetCurrentPlayerCount() {
        return _currentPlayerCount;
    }

    public void SetPlayerCountToDefault() {
        _currentPlayerCount = Bukkit.getServer().getOnlinePlayers().size();
        UpdateGameState();
    }

    public void ResetGame() {
        // TODO: 1/9/2016 Write code here to set all the essential game stats back to initial values.  
    }

    public void StartGame() {
        if(_state == GAME_STATE.GAME_STARTING){
            for (Player p: Bukkit.getOnlinePlayers())
            {
                p.teleport(_startingLocation);
            }
        }
        // TODO: 1/9/2016 check some new stuff in 
        _state = GAME_STATE.GAME_RUNNING;
    }

    public void BanPlayer(String playerName) {
        GetConfig().set("Banned-Players." + playerName, GetAllowedInTime(30));
        Bukkit.getServer().getPluginManager().getPlugin("BustOut").saveConfig();
    }

    public boolean PlayerBanned(String playerName) {
        Date currentDateTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDateTime);
        Date d = new Date(Date.parse(GetConfig().getString("Banned-Players."+playerName)));
        if(cal.getTime().before(d)){
            return true;
        }
        else{
            return false;
        }
    }

    private String GetAllowedInTime(int minutesToBan) {
        Date currentDateTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDateTime);
        cal.add(Calendar.MINUTE, minutesToBan);
        return cal.getTime().toString();
    }
}
