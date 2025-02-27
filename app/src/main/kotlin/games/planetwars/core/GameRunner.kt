package games.planetwars.core

import games.planetwars.agents.PlanetWarsAgent

data class GameRunner(
    val gameState: GameState,
    val agent1: PlanetWarsAgent,
    val agent2: PlanetWarsAgent,
    val gameParams: GameParams,
) {
    fun runGame() : ForwardModel {
        val forwardModel = ForwardModel(gameState, gameParams)
        while (!forwardModel.isTerminal()) {
            val actions = mapOf(
                Player.Player1 to agent1.getAction(gameState),
                Player.Player2 to agent2.getAction(gameState),
            )
            forwardModel.step(actions)
        }
        return forwardModel
    }
}

fun main() {
    val gameParams = GameParams()
    val gameState = GameStateFactory(gameParams).createGame()
    val agent1 = games.planetwars.agents.PureRandomAgent(Player.Player1)
    val agent2 = games.planetwars.agents.PureRandomAgent(Player.Player2)
    val gameRunner = GameRunner(gameState, agent1, agent2, gameParams)
    val finalModel = gameRunner.runGame()
    println("Game over!")
    println(finalModel.statusString())

}