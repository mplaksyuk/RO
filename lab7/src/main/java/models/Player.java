package models;

public class Player {

    private String playerId;

    private String teamId;

    private String name;

    private Integer  age;

    public Player(String name, Integer age, Manager manager){
        this.name=name;
        this.age=age;
        manager.createId(this);
    }

    public Player() {

    }

    public String getPlayerId(){return playerId;}

    public void setPlayerId(String playerId){this.playerId=playerId;}

    public String getTeamId(){return teamId;}

    public void setTeamId(String teamId){this.teamId=teamId;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {return age;}

    public void setAge(Integer age) {
        this.age = age;
    }

    public void outputPlayer(){
        System.out.println("\tid= "+playerId+"\tname= " + name + "\tage=" +age);
    }
}
