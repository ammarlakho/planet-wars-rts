package games.planetwars.agents

import games.planetwars.core.GameState
import games.planetwars.core.Player

class AgentInterfaces {
}

interface PlanetWarsAgent {
    fun getAction(gameState: GameState): Action
    fun getAgentType(): String
}

interface SimplePlayerInterface {
    fun getAction(gameState: AbstractGameState) : Int
    fun reset() : SimplePlayerInterface
    fun getAgentType(): String
}
