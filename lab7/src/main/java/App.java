import models.Manager;
import models.Player;
import models.Team;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class App {

    private final static String path = "src/main/java/manager.xml";
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        Manager manager = DOMParser.parse(path);
        manager.outputAllContents();

        System.out.println("\nAdd a new Team Roma\n");
        Team team = new Team("Roma", manager);
        manager.addTeam(team);
        manager.outputAllContents();

        System.out.println("\nAdd some new players for author\n");
        Player player1 = new Player("Leonelya", 22, manager);
        manager.addPlayer(player1, team.getTeamId());
        Player player2 = new Player("Kekoldinio", 28, manager);
        manager.addPlayer(player2, team.getTeamId());
        manager.outputAllContents();

        System.out.println("\nGet Roma's players\n");
        List<Player> players = manager.getTeamPlayers("Roma");
        for (Player p : players)
            p.outputPlayer();

        System.out.println("\nDelete a Roma's player\n");
        manager.removePlayer(player1);
        players = manager.getTeamPlayers("Roma");
        for (Player p : players)
            p.outputPlayer();

        System.out.println("\nChange Player's name\n");
        manager.renamePlayer(manager.getPlayer("Kekoldinio"), "Ronaldo");
        manager.outputAllContents();

        System.out.println("\nChange team's name\n");
        manager.renameTeam(manager.getTeam("Roma"), "Dynamo");
        manager.outputAllContents();



        System.out.println("\nGet all teams\n");
        Map<String, Team> teams = manager.getTeams();
        for (String id : teams.keySet()){
            teams.get(id).outputTeam();
        }



        System.out.println("\nDelete Dynamo\n");
        manager.removeTeam(team);
        manager.outputAllContents();
        DOMParser.write(manager, path);
    }
}