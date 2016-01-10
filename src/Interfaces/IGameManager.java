package Interfaces;

import GameObjects.GAME_STATE;

/**
 * Created by RogerB on 1/9/2016.
 */
public interface IGameManager {
    public GAME_STATE GetCurrentState();
    public void SetCurrentState(GAME_STATE state);
    public void UpdateGameState();
    public void IncrementPlayer();
    public void DecrementPlayer();
    public int GetCurrentPlayerCount();
    public void SetPlayerCountToDefault();
    public void ResetGame();
    public void StartGame();
}
