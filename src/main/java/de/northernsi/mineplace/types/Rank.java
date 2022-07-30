// Created by Torben R.

package de.northernsi.mineplace.types;

public enum Rank {
	ADMIN(10, "Admin", 'c'),
	DEV(20, "Dev", 'b'),
	MOD(30, "Mod", '6'),
	VIP(40, "VIP", 'd'),
	GUEST(99, null, '7');

	private static final Rank[] VALUES = values();

	private final int id;
	private final String prefix;
	private final char primaryColor;

	Rank(int id, String prefix, char color) {
		this.id = id;
		this.prefix = prefix;
		this.primaryColor = color;
	}

	public static Rank byId(int id) {
		Rank rankById = GUEST;
		for (Rank rank : VALUES) {
			if (rank.id == id) {
				rankById = rank;
			}
		}

		return rankById;
	}

	public static Rank[] getValues() {
		return VALUES;
	}

	public int getId() {
		return this.id;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public char getPrimaryColor() {
		return this.primaryColor;
	}
}
