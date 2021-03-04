package com.example.cardoso_brach_projectiut.model;

public class Team {

    private String name;
    private String badge;
    private String league;

    public Team(String name, String badge, String league) {
        this.name = name;
        this.badge = badge;
        this.league = league;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }
}

