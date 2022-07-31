package de.northernsi.mineplace.models;

import com.google.common.collect.Lists;
import de.northernsi.mineplace.misc.data.DataModel;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("FieldMayBeFinal")
public class TeamData extends DataModel {

	private List<Member> members = Lists.newArrayList();
	private List<UUID> bannedPlayers = Lists.newArrayList();
	private String name = "";

	public TeamData(String name, UUID owner) {
		this.name = name;
		this.members.add(new Member(owner, Role.OWNER));
	}

	public String getName() {
		return this.name;
	}

	public List<Member> getMembers() {
		return this.members;
	}

	public List<UUID> getBannedPlayers() {
		return this.bannedPlayers;
	}

	public enum Role {
		OWNER,
		MODERATOR,
		MEMBER,
		BANNED
	}

	public class Member {

		private UUID uniqueId;

		private Role role;

		public Member(UUID uniqueId, Role role) {
			this.uniqueId = uniqueId;
			this.role = role;
		}

		public UUID getUniqueId() {
			return this.uniqueId;
		}

		public Role getRole() {
			return this.role;
		}
	}
}
