package me.flashyreese.fabricmm.api.schema.repository;

import java.util.List;

public class Project {
    private String id;
    private String name;
    private String description;
    private String iconUrl;
    private String sourcesUrl;
    private int curseForgeProject;

    private List<MinecraftVersion> minecraftVersions;//Curse will parse to this

    private User user;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getSourcesUrl() {
        return sourcesUrl;
    }

    public int getCurseForgeProject() {
        return curseForgeProject;
    }

    public List<MinecraftVersion> getMinecraftVersions() {
        return minecraftVersions;
    }

    public void setMinecraftVersions(List<MinecraftVersion> minecraftVersions) {
        this.minecraftVersions = minecraftVersions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean containsMinecraftVersion(String ver){
        for (MinecraftVersion minecraftVersion: getMinecraftVersions()){
            if (minecraftVersion.getMinecraftVersion().equalsIgnoreCase(ver)){
                return true;
            }
        }
        return false;
    }

    public MinecraftVersion getMinecraftVersion(String ver){
        for (MinecraftVersion version: getMinecraftVersions()){
            if (version.getMinecraftVersion().equalsIgnoreCase(ver)){
                return version;
            }
        }
        return null;
    }
}
