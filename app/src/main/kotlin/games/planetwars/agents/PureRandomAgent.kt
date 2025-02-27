package games.planetwars.agents

import games.planetwars.core.GameParams
import games.planetwars.core.GameState
import games.planetwars.core.GameStateFactory
import games.planetwars.core.Player

class PureRandomAgent(val player: Player) : PlanetWarsAgent {
    override fun getAction(gameState: GameState): Action {
        val source = gameState.planets.random()
        val target = gameState.planets.random()
        return Action(player, source.id, target.id, source.nShips/2)
    }

    override fun getAgentType(): String {
        return "RandomAgent"
    }
}

fun main() {
    val agent = PureRandomAgent(Player.Player1)
    val gameState = GameStateFactory(GameParams()).createGame()
    val action = agent.getAction(gameState)
    println(action)
}
