// Created by Torben R.

package de.northernsi.mineplace.types;

import java.util.UUID;

public class TeamMember {

	UUID uuid;
	String teamname;
	TeamRole role;

	public TeamMember(UUID uuid, String teamname, TeamRole role) {
		this.uuid = uuid;
		this.teamname = teamname;
		this.role = role;
	}

	public UUID getUuid() {
		return this.uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public TeamRole getRole() {
		return this.role;
	}

	public void setRole(TeamRole role) {
		this.role = role;
	}

	public String getTeamname() {
		return this.teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof TeamMember)) {
			return false;
		}
		final TeamMember other = (TeamMember) o;
		if (!other.canEqual(this)) {
			return false;
		}
		final Object this$uuid = this.getUuid();
		final Object other$uuid = other.getUuid();
		if (this$uuid == null ? other$uuid != null : !this$uuid.equals(other$uuid)) {
			return false;
		}
		final Object this$teamname = this.getTeamname();
		final Object other$teamname = other.getTeamname();
		if (this$teamname == null ? other$teamname != null : !this$teamname.equals(other$teamname)) {
			return false;
		}
		final Object this$role = this.getRole();
		final Object other$role = other.getRole();
		return this$role == null ? other$role == null : this$role.equals(other$role);
	}

	protected boolean canEqual(final Object other) {
		return other instanceof TeamMember;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $uuid = this.getUuid();
		result = result * PRIME + ($uuid == null ? 43 : $uuid.hashCode());
		final Object $teamname = this.getTeamname();
		result = result * PRIME + ($teamname == null ? 43 : $teamname.hashCode());
		final Object $role = this.getRole();
		result = result * PRIME + ($role == null ? 43 : $role.hashCode());
		return result;
	}
}
