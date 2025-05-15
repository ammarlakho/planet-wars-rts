package games.planetwars.runners

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
) {
    fun getSortedEntries(): List<LeagueEntry> {
        return entries.sortedByDescending { it.winRate() }
    }
}

data class LeagueWriter(
    val outputDir: String = "results/sample/",
    val filename: String = "league.md",
) {

    fun generateMarkdownTable(league: LeagueResult): String {
        val sortedEntries = league.getSortedEntries()
        val header = "| Rank | Agent Name | Win Rate % | Played |\n|------|------------|----------|--------|\n"
        val rows = sortedEntries.mapIndexed { index, entry ->
            val formattedWinRate = "%.1f".format(entry.winRate())
            "| ${index + 1} | ${entry.agentName} | $formattedWinRate | ${entry.nGames} |"
        }.joinToString("\n")

        return header + rows
    }

    // Function to save the Markdown string to a file with robust error handling
    fun saveMarkdownToFile(markdownContent: String) {
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
        val filenameWithTimestamp = filename.replace(".md", "_$timestamp.md")
        
        // Get the location of the class file to find project root
        val classLocation = LeagueWriter::class.java.protectionDomain.codeSource.location.toURI()
        val projectRoot = File(classLocation).parentFile.parentFile.parentFile.parentFile.parentFile
        
        // Try to create results directory in project root
        val resultsDir = File(projectRoot, outputDir)
        
        resultsDir.mkdirs()
        val outputFile = File(resultsDir, filenameWithTimestamp)
        outputFile.writeText(markdownContent)
        println("League results saved to ${outputFile.absolutePath}")

    }
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

    val writer = LeagueWriter()
    val markdownContent = writer.generateMarkdownTable(league)
    writer.saveMarkdownToFile(markdownContent)
}
