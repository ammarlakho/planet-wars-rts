package games.planetwars.agents

import games.planetwars.core.GameState
import games.planetwars.core.Player

class AgentInterfaces {
}

/*
    * This interface defines the methods that an agent must implement to play the game
 */
interface PlanetWarsAgent {
    fun getAction(gameState: GameState): Action
    fun getAgentType(): String
    fun prepareToPlayAs(player: Player): PlanetWarsAgent

    // this is provided as a default implementation, but can be overridden if needed
    fun processGameOver(finalState: GameState) {}
}

/*
    * Kotlin only has single code inheritance, so we use an abstract class to provide a default implementation
    * of prepareToPlayAs - this is useful because many agents will need to know which player they are playing as
    * and may need other resets or initializations prior to playing
 */
abstract class PlanetWarsPlayer : PlanetWarsAgent {
    protected var player: Player = Player.Neutral

    override fun prepareToPlayAs(player: Player): PlanetWarsAgent {
        this.player = player
        return this
    }
}

//
//interface SimplePlayerInterface {
//    fun getAction(gameState: AbstractGameState) : Int
//    fun reset() : SimplePlayerInterface
//    fun getAgentType(): String
//}
