// Created by Torben R.
package de.northernsi.mineplace.types;

import java.util.List;
import java.util.UUID;

public class Team {

	String name;
	List<TeamMember> members;
	List<UUID> bannedPlayers;

	public Team(String name, List<TeamMember> members, List<UUID> bannedPlayers) {
		this.name = name;
		this.members = members;
		this.bannedPlayers = bannedPlayers;
	}

	public String getName() {
		return this.name;
	}

	public List<TeamMember> getMembers() {
		return this.members;
	}

	public List<UUID> getBannedPlayers() {
		return this.bannedPlayers;
	}

	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Team)) {
			return false;
		}
		final Team other = (Team) o;
		if (!other.canEqual(this)) {
			return false;
		}
		final Object this$name = this.getName();
		final Object other$name = other.getName();
		if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
			return false;
		}
		final Object this$members = this.getMembers();
		final Object other$members = other.getMembers();
		if (this$members == null ? other$members != null : !this$members.equals(other$members)) {
			return false;
		}
		final Object this$bannedPlayers = this.getBannedPlayers();
		final Object other$bannedPlayers = other.getBannedPlayers();
		return this$bannedPlayers == null ? other$bannedPlayers == null : this$bannedPlayers.equals(
				other$bannedPlayers);
	}

	protected boolean canEqual(final Object other) {
		return other instanceof Team;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $name = this.getName();
		result = result * PRIME + ($name == null ? 43 : $name.hashCode());
		final Object $members = this.getMembers();
		result = result * PRIME + ($members == null ? 43 : $members.hashCode());
		final Object $bannedPlayers = this.getBannedPlayers();
		result = result * PRIME + ($bannedPlayers == null ? 43 : $bannedPlayers.hashCode());
		return result;
	}
}
