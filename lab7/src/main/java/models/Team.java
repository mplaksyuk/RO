package models;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private String teamId;

    private String name;

    private List<Player> players=new ArrayList<>();


    public Team(){
    }

    public Team(String name, Manager manager){
        this.name=name;
        manager.createId(this);
    }



    public String getTeamId(){return teamId;}
    public void setTeamId(String teamId) {

        this.teamId=teamId;
    }

    public String getName(){return name;}

    public void setName(String name){
        this.name=name;
    }

    public List<Player> getPlayers(){
        return players;
    }

    public void setPlayers(List<Player> players){
        this.players=players;
    }

    public void outputTeam(){
        System.out.println("id="+teamId+"\tname="+name);
    }
}
