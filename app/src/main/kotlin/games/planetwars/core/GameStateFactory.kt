package games.planetwars.core

import util.Vec2d

class GameStateFactory (val params: GameParams) {

    fun makeRandomPlanet(params: GameParams, owner: Player) : Planet {
        val x = (Math.random() * params.width).toInt()
        val y = (Math.random() * params.height).toInt()
        val numShips = (Math.random() * (params.maxInitialShipsPerPlanet - params.minInitialShipsPerPlanet)
                + params.minInitialShipsPerPlanet).toInt()
        val growthRate = Math.random() * params.maxGrowthRate - params.minGrowthRate
        return Planet(owner, numShips, Vec2d(x.toDouble(), y.toDouble()), growthRate)
    }

    fun canAdd(planets: List<Planet>, candidate: Planet, radialSeparation: Double) : Boolean {
        val candidateRadius = candidate.growthRate * params.growthToRadiusFactor
        for (planet in planets) {
            val planetRadius = planet.growthRate * params.growthToRadiusFactor
            if (planet.position.distance(candidate.position) < radialSeparation * (planetRadius + candidateRadius)) {
                return false
            }
        }
        return true
    }

    fun createGame() : GameState {
        val planets = mutableListOf<Planet>()
        // make allocation symmetrical around the vertical centerline
        // when reflecting them also reflect in Y to make for a more interesting looking map
        val centreX = params.width / 2
        val centreY = params.height / 2
        // now to do the allocation, allocate half randomly within the left half of the screen
        // and then their reflections on the right half
        // the neutral ones are duplicated directly, while the player allocated ones are switched in allocation

        val nNeutral = (params.numPlanets * params.initialNeutralRatio).toInt()
        while (planets.size < nNeutral) {
            val candidate = makeRandomPlanet(params, Player.Neutral)
            if (canAdd(planets, candidate, params.radialSeparation)) {
                planets.add(candidate)
            }
            planets.add(makeRandomPlanet(params, Player.Neutral))
        }

        return GameState(planets)
    }
}

fun main() {
    val params = GameParams()
    val factory = GameStateFactory(params)
    val game = factory.createGame()
    println(game)
}
