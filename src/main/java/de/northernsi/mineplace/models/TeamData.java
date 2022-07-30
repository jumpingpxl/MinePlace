package de.northernsi.mineplace.models;

import com.google.common.collect.Lists;
import de.northernsi.mineplace.misc.data.DataModel;
import de.northernsi.mineplace.types.TeamMember;

import java.util.List;
import java.util.UUID;

public class TeamData extends DataModel {

	private final List<TeamMember> members = Lists.newArrayList();
	private final List<UUID> bannedPlayers = Lists.newArrayList();
	private String name = "";

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TeamMember> getMembers() {
		return this.members;
	}

	public List<UUID> getBannedPlayers() {
		return this.bannedPlayers;
	}
}
