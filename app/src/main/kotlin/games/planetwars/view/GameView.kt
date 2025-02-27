package games.planetwars.view

import games.planetwars.core.GameParams
import games.planetwars.core.GameState
import games.planetwars.core.GameStateFactory
import util.Vec2d
import xkg.gui.*
import xkg.jvm.AppLauncher

class GameView (
    var gameState: GameState,
    val params: GameParams = GameParams(),
    val colors: ColorScheme = ColorScheme(),
    ): XApp {


    override fun paint(xg: XGraphics) {
        drawBackground(xg)
        drawPlanets(xg)
    }

    private fun drawPlanets(xg: XGraphics) {

        for (planet in gameState.planets) {
            val color = colors.getColor(planet.owner)
            val radius = params.growthToRadiusFactor * planet.growthRate
            val circle = XEllipse(
                planet.position,
                radius, radius,
                XStyle(fg = color, fill = true))
            xg.draw(circle)
        }

    }

    fun drawBackground(xg: XGraphics) {
        val centre = Vec2d(xg.width() / 2, xg.height() / 2)
        val rect = XRect(centre, xg.width(), xg.height(), XStyle())
        rect.dStyle = XStyle(fg = colors.background, lineWidth = 10.0)
        xg.draw(rect)
    }

}

fun main() {
    val params = GameParams()
    val gameState = GameStateFactory(params).createGame()
    val title = "Planet Wars"
    AppLauncher(GameView(gameState), title = title).launch()
}
