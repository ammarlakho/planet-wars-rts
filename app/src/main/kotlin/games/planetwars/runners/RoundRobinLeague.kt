package games.planetwars.runners

import games.planetwars.agents.PlanetWarsAgent
import games.planetwars.core.GameParams
import games.planetwars.core.GameStateFactory
import games.planetwars.core.Player

fun main() {
    // TODO: Implement this using a factory pattern to specify agent constructors
    // need this to run agents with different parameters
    // or else could just make the agents with different params and add a reset method to prepare them for each new game
    val agents = SamplePlayerLists().getRandomTrio()
    val league = RoundRobinLeague(agents)
    val results = league.runRoundRobin()
    // use the League utils to print the results
    println(results)
    val writer = LeagueWriter()
    val leagueResult = LeagueResult(results.values.toList())
    val markdownContent = writer.generateMarkdownTable(leagueResult)
    writer.saveMarkdownToFile(markdownContent)
}

class SamplePlayerLists {
    fun getRandomTrio(): List<PlanetWarsAgent> {
        return listOf(
            games.planetwars.agents.PureRandomAgent(),
            games.planetwars.agents.BetterRandomAgent(),
            games.planetwars.agents.CarefulRandomAgent(),
        )
    }
}

data class RoundRobinLeague(
    val agents: List<PlanetWarsAgent>,
    val gamesPerPair: Int = 10,
    val gameParams: GameParams = GameParams(numPlanets = 20),
) {
    fun runPair(agent1: PlanetWarsAgent, agent2: PlanetWarsAgent): Map<Player, Int> {
        val gameParams = GameParams(numPlanets = 20)
        val gameState = GameStateFactory(gameParams).createGame()
        val gameRunner = GameRunner(gameState, agent1, agent2, gameParams)
        return gameRunner.runGames(gamesPerPair)
    }

    fun runRoundRobin(): Map<String, LeagueEntry> {
        val scores = mutableMapOf<String, LeagueEntry>()
        for (agent in agents) {
            scores[agent.getAgentType()] = LeagueEntry(agent.getAgentType())
        }
        for (i in 0 until agents.size) {
            for (j in 0 until agents.size) {
                if (i == j) {
                    continue
                }
                val agent1 = agents[i]
                val agent2 = agents[j]
                val result = runPair(agent1, agent2)
                // update the league scores for each agent
                val leagueEntry1 = scores[agent1.getAgentType()]!!
                val leagueEntry2 = scores[agent2.getAgentType()]!!
                leagueEntry1.points += result[Player.Player1]!!
                leagueEntry2.points += result[Player.Player2]!!
                leagueEntry1.nGames += gamesPerPair
                leagueEntry2.nGames += gamesPerPair
            }
        }
        return scores
    }
}