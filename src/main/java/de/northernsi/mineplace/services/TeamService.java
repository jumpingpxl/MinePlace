package de.northernsi.mineplace.services;

import de.northernsi.mineplace.MinePlace;
import de.northernsi.mineplace.misc.data.DataHolder;
import de.northernsi.mineplace.models.TeamData;

public class TeamService extends DataHolder<TeamData> {

	private final MinePlace minePlace;

	public TeamService(MinePlace minePlace) {
		super(minePlace.dataSupplier(), "teams", TeamData.class);

		this.minePlace = minePlace;
	}

	public TeamData getTeam(String teamName) {
		return this.getData(teamName);
	}
}
