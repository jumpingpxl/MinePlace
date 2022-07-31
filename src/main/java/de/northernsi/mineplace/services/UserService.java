package de.northernsi.mineplace.services;

import de.northernsi.mineplace.MinePlace;
import de.northernsi.mineplace.misc.Rank;
import de.northernsi.mineplace.misc.data.DataHolder;
import de.northernsi.mineplace.models.TeamData;
import de.northernsi.mineplace.models.UserData;
import org.bukkit.entity.Player;

public class UserService extends DataHolder<UserData> {

	private final MinePlace minePlace;

	public UserService(MinePlace minePlace) {
		super(minePlace.dataSupplier(), "users", UserData.class);

		this.minePlace = minePlace;
	}

	public Rank getRank(Player player) {
		return this.getOrCreateData(player.getUniqueId()).getRank();
	}

	public void setRank(Player player, Rank rank) {
		this.getOrCreateData(player.getUniqueId()).setRank(rank);
	}

	public String getTeamName(Player player) {
		return this.getOrCreateData(player.getUniqueId()).getTeam();
	}

	public TeamData getTeam(Player player) {
		String teamName = this.getTeamName(player);
		if (teamName == null) {
			return null;
		}

		return this.minePlace.teamService().getTeam(teamName);
	}
}
