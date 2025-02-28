package games.planetwars.agents

import games.planetwars.core.*

class SlowRandomAgent(val player: Player, val delayMillis: Long = 1000) : PlanetWarsAgent {

    override fun getAction(gameState: GameState): Action {
        // Introduce an artificial delay using Thread.sleep (blocking, but compatible with the interface)
        Thread.sleep(delayMillis)

        // Logic similar to BetterRandomAgent
        val myPlanets = gameState.planets.filter { it.owner == player && it.transporter == null }
        if (myPlanets.isEmpty()) {
            return Action.doNothing()
        }

        val otherPlanets = gameState.planets.filter { it.owner == player.opponent() || it.owner == Player.Neutral }
        if (otherPlanets.isEmpty()) {
            return Action.doNothing()
        }

        val source = myPlanets.random()
        val target = otherPlanets.random()
        return Action(player, source.id, target.id, source.nShips / 2)
    }

    override fun getAgentType(): String {
        return "Slow Random Agent (delay: ${delayMillis}ms)"
    }
}

fun main() {
    val agent = SlowRandomAgent(Player.Player1, delayMillis = 2000)
    val gameState = GameStateFactory(GameParams()).createGame()

    val action = agent.getAction(gameState)
    println(action)
}
