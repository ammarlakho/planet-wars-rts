package competition_entry

import games.planetwars.agents.random.CarefulRandomAgent
import games.planetwars.agents.strategic.StrategicHeuristicAgent
import json_rmi.GameAgentServer

fun main() {
    val server = GameAgentServer(port = 8080, agentClass = StrategicHeuristicAgent::class)
    server.start(wait = true)
}
