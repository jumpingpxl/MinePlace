package de.northernsi.mineplace.misc.minecraft;

import com.google.common.collect.Maps;
import de.northernsi.mineplace.MinePlace;
import de.northernsi.mineplace.misc.Constants;
import de.northernsi.mineplace.misc.Rank;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Map;

public class ScoreboardController {

	private final Map<Rank, String> teamNames;
	private final MinePlace minePlace;
	private final Scoreboard scoreboard;

	public ScoreboardController(MinePlace minePlace) {
		this.minePlace = minePlace;

		// Replace with #getNewScoreboard if each user has its own.
		this.scoreboard = minePlace.getServer().getScoreboardManager().getMainScoreboard();

		this.teamNames = Maps.newHashMap();
		this.fillScoreboard();
	}

	public void fillScoreboard() {
		this.teamNames.clear();
		for (Team team : this.scoreboard.getTeams()) {
			team.unregister();
		}

		for (Rank rank : Rank.getValues()) {
			String teamName = "0" + rank.getId() + rank.name().toLowerCase();
			this.teamNames.put(rank, teamName);

			String prefix;
			if (rank.getPrefix() == null) {
				prefix = String.format(Constants.SCOREBOARD_WITHOUT_PREFIX, rank.getPrimaryColor());
			} else {
				prefix = String.format(Constants.SCOREBOARD_WITH_PREFIX, rank.getPrimaryColor(),
						rank.getPrefix());
			}

			this.scoreboard.registerNewTeam(teamName).setPrefix(prefix);
		}
	}

	public void setTeam(Player player) {
		Rank rank = this.minePlace.userService().getRank(player);
		String teamName = this.teamNames.get(rank);
		for (Team team : this.scoreboard.getTeams()) {
			if (team.getName().equals(teamName)) {
				team.addEntry(player.getName());
				continue;
			}

			team.removeEntry(player.getName());
		}

		//player.setScoreboard(this.scoreboard);
	}
}
