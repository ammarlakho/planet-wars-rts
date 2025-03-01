package games.planetwars.runners

import java.io.File

class League {
}

data class LeagueEntry(
    val agentName: String,
    var points: Double = 0.0,
    var nGames: Int = 0,
) {
    fun winRate(): Double {
        return 100 * points  / nGames
    }
}

data class LeagueResult(
    val entries: List<LeagueEntry>
)

fun generateMarkdownTable(league: LeagueResult): String {
    // Sort by wins (descending), then losses (ascending), then draws (descending)
    val sortedEntries = league.entries.sortedWith(
        compareByDescending<LeagueEntry> { it.winRate() }
    )

    val header = "| Rank | Agent Name | Played | Win Rate |\n|------|------------|------|-------|\n"
    val rows = sortedEntries.mapIndexed { index, entry ->
        "| ${index + 1} | ${entry.agentName} | ${entry.winRate()} | ${entry.nGames} |"
    }.joinToString("\n")

    return header + rows
}

// Function to save the Markdown string to a file
fun saveMarkdownToFile(markdownContent: String, outputDir: String, filename: String) {
    val dir = File(outputDir)
    if (!dir.exists()) dir.mkdirs() // Ensure the directory exists

    val outputFile = File(dir, filename)
    outputFile.writeText(markdownContent)

    println("League results saved to ${outputFile.absolutePath}")
}

// Example usage
fun main() {
    val league = LeagueResult(
        listOf(
            LeagueEntry("AlphaBot", 10.0, 20),
            LeagueEntry("BetaAI", 8.0, 4),
            LeagueEntry("GammaSolver", 8.0, 3),
            LeagueEntry("DeltaAgent", 6.0, 5)
        )
    )

    val markdownContent = generateMarkdownTable(league)
    saveMarkdownToFile(markdownContent, "results/sample/", "league.md")
}
