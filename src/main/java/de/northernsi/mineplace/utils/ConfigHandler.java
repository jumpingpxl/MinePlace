// Credits: Zeichenfolge

package de.northernsi.mineplace.utils;

import de.northernsi.mineplace.MinePlace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ConfigHandler {

	private static ConfigHandler instance;
	private final String pluginDirPath = MinePlace.getInstance().getDataFolder().getPath();

	public static ConfigHandler getInstance() {
		if (ConfigHandler.instance == null) {
			ConfigHandler.instance = new ConfigHandler();
		}

		return ConfigHandler.instance;
	}

	public void createTeam(String teamName, UUID ownerUUID) {
		this.setTeamMemberRole(teamName, ownerUUID, "owner");
		this.addTeamToUser(teamName, ownerUUID);
	}

	public void deleteTeam(String teamName, UUID ownerUUID) {
		File file = new File(this.pluginDirPath, "teams/" + teamName + ".yml");
		file.delete();
		this.deleteUserFile(ownerUUID);
	}

	public String getTeamMemberRole(String teamName, UUID pUUID) {
		File file = new File(this.pluginDirPath, "teams/" + teamName + ".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		return (String) cfg.getConfigurationSection("members").get(String.valueOf(pUUID));
	}

	public void addTeamMember(String teamName, UUID pUUID) {
		this.setTeamMemberRole(teamName, pUUID, "member");
		this.addTeamToUser(teamName, pUUID);
	}

	private void addTeamToUser(String teamName, UUID pUUID) {
		File file = new File(this.pluginDirPath, "users/" + pUUID + ".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (cfg.getConfigurationSection("team") == null) {
			cfg.createSection("team");
		}

		ConfigurationSection teamSection = cfg.getConfigurationSection("team");
		teamSection.set("name", teamName);

		try {
			cfg.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void deleteUserFile(UUID pUUID) {
		File file = new File(this.pluginDirPath, "users/" + pUUID + ".yml");
		if (file.exists()) {
			file.delete();
		}
	}

	public boolean existsTeam(String teamName) {
		File file = new File(this.pluginDirPath, "teams/" + teamName + ".yml");

		return file.exists();
	}

	public void removeTeamMember(String teamName, UUID pUUID) {
		File teamFile = new File(this.pluginDirPath, "teams/" + teamName + ".yml");
		FileConfiguration teamCfg = YamlConfiguration.loadConfiguration(teamFile);

		ConfigurationSection membersSection = teamCfg.getConfigurationSection("members");
		membersSection.set(String.valueOf(pUUID), null);

		try {
			teamCfg.save(teamFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		this.deleteUserFile(pUUID);
	}

	public String getTeamByUUID(UUID pUUID) {
		File file = new File(this.pluginDirPath, "users/" + pUUID + ".yml");

		if (file.exists()) {
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			return (String) cfg.getConfigurationSection("team").get("name");
		}

		return null;
	}

	public void banTeamMember(String teamName, UUID pUUID) {
		this.setTeamMemberRole(teamName, pUUID, "banned");
		this.deleteUserFile(pUUID);
	}

	private void setTeamMemberRole(String teamName, UUID pUUID, String role) {
		File file = new File(this.pluginDirPath, "teams/" + teamName + ".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (cfg.getConfigurationSection("members") == null) {
			cfg.createSection("members");
		}

		ConfigurationSection membersSection = cfg.getConfigurationSection("members");
		membersSection.set(String.valueOf(pUUID), role);

		try {
			cfg.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void promoteTeamMember(String teamName, UUID pUUID) {
		this.setTeamMemberRole(teamName, pUUID, "mod");
	}

	public void demoteTeamMember(String teamName, UUID pUUID) {
		this.setTeamMemberRole(teamName, pUUID, "member");
	}

	public void unbanTeamMember(String teamName, UUID pUUID) {
		this.removeTeamMember(teamName, pUUID);
	}

	public void createConfig() {
		File file = new File(this.pluginDirPath, "config.yml");
		if (file.exists()) {
			return;
		}

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("webhook_url", "UNSET");

		try {
			cfg.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getWebhookURL() {
		File file = new File(this.pluginDirPath, "config.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		return cfg.getString("webhook_url");
	}
}
