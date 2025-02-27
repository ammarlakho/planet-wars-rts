package games.planetwars.core

class ForwardModel(val state: GameState, val params: GameParams) {
    // the forward model applies the current set of actions to the current game state
    // and updates the game state in place


    // keep track of the total number of calls to the model across all instances
    companion object {
        var nUpdates = 0
    }

    fun transporterArrival(destination: Planet, transporter: Transporter) {
        // process an arriving transporter
        destination.pending[transporter.owner] = destination.pending[transporter.owner]!! + transporter.nShips
    }

    // apply the actions to the game state
    private fun updateTransporters() {
        // for each transit, update its progress and resolve if it has reached its destination
        // TODO: for each destination we should keep a list of incoming ships to resolve conflicts
        // we do this for fair resolution
        for (planet in state.planets) {
            val transporter = planet.transporter
            if (transporter != null) {
                val destinationPlanet = state.planets[transporter.destinationIndex]
                // check whether the transporter has arrived
                if (transporter.s.distance(destinationPlanet.position) < destinationPlanet.radius) {
                    transporterArrival(destinationPlanet, transporter)
                    planet.transporter = null
                } else {
                    // update the position of the transporter
                    transporter.s += transporter.v
                }
            }
        }
    }


    /*
    To update the planets we first find which player has the most incoming ships
    and subtract the number of ships from the other player, leaving only one with a positive balance
    or possibly both with zero balance.  For a neutral planet, we then subtract the number of ships
    incoming, and if positive, make it belong to that player.

     */

    private fun updateNeutralPlanet(planet: Planet) {

        val incoming = planet.pending[Player.Player1]!! - planet.pending[Player.Player2]!!
        // reduce neutral ships by absolute value of incoming
        planet.nShips -= Math.abs(incoming)
        // if the number is negative, we switch ownership to the player with the most incoming based on the sign
        if (planet.nShips < 0) {
            planet.owner = if (incoming > 0) Player.Player1 else Player.Player2
            planet.nShips = -planet.nShips
        }

    }

    private fun updatePlayerPlanet(planet: Planet) {
        // this is simple:
        planet.nShips += planet.growthRate.toInt()
        // resolve any pending ships on the planet
        planet.nShips += planet.pending[planet.owner]!!
        planet.pending[planet.owner] = 0
        planet.nShips -= planet.pending[planet.owner.opponent()]!!
        planet.pending[planet.owner.opponent()] = 0
        // we check if it has switched ownership
        if (planet.nShips < 0) {
            planet.owner = planet.owner.opponent()
            planet.nShips = -planet.nShips
        }
    }


    private fun updatePlanets() {
        // update the number of ships on each planet
        for (planet in state.planets) {
            // update the number of ships on the planet
            // we treat neutral planets differently
            if (planet.owner == Player.Neutral) {
                updateNeutralPlanet(planet)
            } else {
                updatePlayerPlanet(planet)
            }
        }
    }

    fun step(actions: Map<Player, Action>) {
        // increment the number of updates

        updateTransporters()
        nUpdates += 1
        updatePlanets()


        // apply the actions to the game state
//        state.applyActions(actions, params)
    }

}

fun main() {
    val state = GameStateFactory(GameParams()).createGame()
    val model = ForwardModel(state, GameParams())
    val nSteps = 1000000
    val t = System.currentTimeMillis()
    for (i in 0 until nSteps) {
        val actions = HashMap<Player, Action>()
        model.step(actions)
    }
    val dt = System.currentTimeMillis() - t
    println("Time per step: ${dt.toDouble() / ForwardModel.nUpdates} ms")
}

