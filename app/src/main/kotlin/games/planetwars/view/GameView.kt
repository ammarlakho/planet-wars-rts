package games.planetwars.view

import games.planetwars.core.GameParams
import games.planetwars.core.GameRunner
import games.planetwars.core.GameState
import games.planetwars.core.GameStateFactory
import util.Vec2d
import xkg.gui.*
import xkg.jvm.AppLauncher

class GameView (
    var gameState: GameState,
    val params: GameParams = GameParams(),
    val colors: ColorScheme = ColorScheme(),
    var gameRunner: GameRunner? = null,
    ): XApp {


//    val gameRunner = GameRunner(gameState, params)

    override fun paint(xg: XGraphics) {
        val runner = gameRunner
        if (runner != null) {
            if (runner.forwardModel.isTerminal()) {
                runner.newGame()
            }
            gameState = runner.stepGame().state
            println(runner.forwardModel.statusString())
        }
        drawBackground(xg)
        drawPlanets(xg)
        drawTransporters(xg)
    }

    private fun drawPlanets(xg: XGraphics) {
        for (planet in gameState.planets) {
            val color = colors.getColor(planet.owner)
            val size = 2 * planet.radius
            val circle = XEllipse(
                planet.position,
                size, size,
                XStyle(fg = color, fill = true))
            xg.draw(circle)
        }
    }

    private fun drawTransporters(xg: XGraphics) {
        for (planet in gameState.planets) {
            val transporter = planet.transporter
            if (transporter != null) {
                val color = colors.getColor(transporter.owner)
                val size = 20.0
                val circle = XEllipse(
                    transporter.s,
                    size, size,
                    XStyle(fg = color, fill = true))
                xg.draw(circle)
            }
        }
    }

    private fun drawBackground(xg: XGraphics) {
        val centre = Vec2d(xg.width() / 2, xg.height() / 2)
        val rect = XRect(centre, xg.width(), xg.height(), XStyle())
        rect.dStyle = XStyle(fg = colors.background, lineWidth = 10.0)
        xg.draw(rect)
    }

}

fun main() {
    val params = GameParams(
        numPlanets = 10,
        initialNeutralRatio = 0.3,
        height = 200,
    )
    val gameState = GameStateFactory(params).createGame()
    for (planet in gameState.planets) {
        println(planet)
    }
    val title = "Planet Wars"
    AppLauncher(
        preferredWidth = params.width,
        preferredHeight = params.height,
        app = GameView(params = params, gameState = gameState),
        title = title).launch()
}
