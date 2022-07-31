package de.northernsi.mineplace.models;

import de.northernsi.mineplace.misc.Rank;
import de.northernsi.mineplace.misc.data.DataModel;

@SuppressWarnings("FieldMayBeFinal")
public class UserData extends DataModel {

	private String team = null;
	private int rank = Rank.GUEST.getId();

	public String getTeam() {
		return this.team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Rank getRank() {
		return Rank.byId(this.rank);
	}

	public void setRank(Rank rank) {
		this.rank = rank.getId();
	}
}
