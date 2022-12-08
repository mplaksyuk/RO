package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {

    private Map<String, Team> teams= new HashMap<>();
    private Map<String, String> teamNames= new HashMap<>();

    private Map<String, Player> players= new HashMap<>();
    private Map<String, String> playerNames= new HashMap<>();

    public Manager() { }

    public void createId(Team team){
        int id=teams.size();
        String idToString="Team"+id;
        while(teams.get(idToString)!=null){
            id++;
            idToString="Team"+id;
        }
        team.setTeamId(idToString);
    }

    public void createId(Player player) {
        int id = players.size();
        String idToString = "id" + id;
        while(players.get(idToString) != null) {
            id++;
            idToString = "id" + id;
        }
        player.setPlayerId(idToString);
    }

    public void addTeam(Team team){
       teams.put(team.getTeamId(),team);
       teamNames.put(team.getName(),team.getTeamId());
    }

    public void addPlayer(Player player, String teamId){
        Team team=teams.get(teamId);
        player.setTeamId(teamId);
        players.put(player.getPlayerId(),player);
        playerNames.put(player.getName(),player.getPlayerId());
        team.getPlayers().add(player);
    }

    public void removeTeam(Team team){
        teams.remove(team.getTeamId());
        teamNames.remove(team.getName());
        for(Player player: team.getPlayers()){
            players.remove(player.getPlayerId());
        }
    }

    public void removePlayer(Player player){
        players.remove(player.getPlayerId());
        playerNames.remove(player.getName());
        teams.get(player.getTeamId()).getPlayers().remove(player);
    }

    public void changePlayerTeam(Player player, Team team){
        Team prev= teams.get(player.getTeamId());
        if(prev!=null){
            prev.getPlayers().remove(player);
        }
        team.setTeamId(team.getTeamId());
        team.getPlayers().add(player);
    }

    public void renamePlayer(Player player, String newName){
        playerNames.remove(player.getName());
        player.setName(newName);
        playerNames.put(player.getName(),player.getPlayerId());
    }

    public void renameTeam(Team team, String newName){
        playerNames.remove(team.getName());
        team.setName(newName);
        playerNames.put(team.getName(),team.getTeamId());
    }

    public Player getPlayer(String name){
        String id=playerNames.get(name);
        if(id!=null){
            return players.get(id);
        }
        return null;
    }

    public Team getTeam(String name){
        String id=teamNames.get(name);
        if(id!=null){
            return teams.get(id);
        }
        return null;
    }

    public List<Player> getTeamPlayers(String name){
        return getTeam(name).getPlayers();
    }

    public Map<String,Team>getTeams(){
        return teams;
    }

    public void outputAllContents(){
        for(String id: teams.keySet()){
            Team team=teams.get(id);
            team.outputTeam();
            List<Player> players=team.getPlayers();
            for(Player player: players) player.outputPlayer();
        }
    }
}
