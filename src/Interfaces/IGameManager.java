package Interfaces;

import GameObjects.GAME_STATE;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by RogerB on 1/9/2016.
 */
public interface IGameManager {
    public FileConfiguration GetConfig();
    public GAME_STATE GetCurrentState();
    public void SetCurrentState(GAME_STATE state);
    public void UpdateGameState();
    public void IncrementPlayer();
    public void DecrementPlayer();
    public int GetCurrentPlayerCount();
    public void SetPlayerCountToDefault();
    public void ResetGame();
    public void StartGame();
    public void BanPlayer(String playerName);
    public boolean PlayerBanned(String playerName);
}
