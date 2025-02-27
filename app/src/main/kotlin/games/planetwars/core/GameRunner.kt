package games.planetwars.core

import games.planetwars.agents.PlanetWarsAgent

data class GameRunner(
    val gameState: GameState,
    val agent1: PlanetWarsAgent,
    val agent2: PlanetWarsAgent,
    val gameParams: GameParams,
) {
    fun runGame() : ForwardModel {
        // runs with a fresh copy of the game state each time
        val forwardModel = ForwardModel(gameState.deepCopy(), gameParams)
        while (!forwardModel.isTerminal()) {
            val actions = mapOf(
                Player.Player1 to agent1.getAction(gameState),
                Player.Player2 to agent2.getAction(gameState),
            )
            forwardModel.step(actions)
        }
        return forwardModel
    }

    fun runGames(nGames: Int) : Map<Player, Int> {
        val scores = mutableMapOf(Player.Player1 to 0, Player.Player2 to 0, Player.Neutral to 0)
        for (i in 0 until nGames) {
            val finalModel = runGame()
            val winner = finalModel.getLeader()
            scores[winner] = scores[winner]!! + 1
        }
        return scores
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
    val results = gameRunner.runGames(100)
    println(results)
}
