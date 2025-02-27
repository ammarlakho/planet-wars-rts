package games.planetwars.agents

import games.planetwars.core.GameParams
import games.planetwars.core.GameState
import games.planetwars.core.GameStateFactory
import games.planetwars.core.Player

class CarefulRandomAgent(val player: Player) : PlanetWarsAgent {
    override fun getAction(gameState: GameState): Action {
        // filter the planets that are owned by the player AND have a transporter available
        val myPlanets = gameState.planets.filter { it.owner == player && it.transporter == null }
        // filter the planets that are owned by the player AND have a transporter available
        if (myPlanets.isEmpty()) {
            return Action.doNothing()
        }
        // now find a random target planet
        val opponentPlanets = gameState.planets.filter { it.owner == player.opponent() }
        if (opponentPlanets.isEmpty()) {
            return Action.doNothing()
        }
        val source = myPlanets.random()
        val target = opponentPlanets.random()
        return Action(player, source.id, target.id, source.nShips/2)
    }

    override fun getAgentType(): String {
        return "RandomAgent"
    }
}

fun main() {
    val agent = CarefulRandomAgent(Player.Player1)
    val gameState = GameStateFactory(GameParams()).createGame()
    val action = agent.getAction(gameState)
    println(action)
}
