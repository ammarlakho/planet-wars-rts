package games.planetwars.view

import games.planetwars.core.GameParams
import games.planetwars.runners.GameRunner
import games.planetwars.core.GameStateFactory
import games.planetwars.core.Player
import xkg.jvm.AppLauncher

fun main() {
    val gameParams = GameParams(numPlanets = 20)
    val gameState = GameStateFactory(gameParams).createGame()
    val agent2 = games.planetwars.agents.BetterRandomAgent()
    val agent1 = games.planetwars.agents.PureRandomAgent()
//    val agent1 = games.planetwars.agents.DoNothingAgent()
//    val agent1 = games.planetwars.agents.BetterRandomAgent()
    val gameRunner = GameRunner(gameState, agent1, agent2, gameParams)

    val title = "${agent1.getAgentType()} : Planet Wars : ${agent2.getAgentType()}"
    AppLauncher(
        preferredWidth = gameParams.width,
        preferredHeight = gameParams.height,
        app = GameView(params = gameParams, gameState = gameState, gameRunner = gameRunner),
        title = title,
        frameRate = 1.0,
    ).launch()
}
