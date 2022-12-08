import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import models.*;

public class DOMParser {
    private static Manager manager1;

    public static class SimpleErrorHandler implements ErrorHandler {
        public void warning(SAXParseException e) throws SAXException {
            System.out.println("Line " +e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }
        public void error(SAXParseException e) throws SAXException {
            System.out.println("Line " +e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }
        public void fatalError(SAXParseException e) throws SAXException {
            System.out.println("Line " +e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }
    }

    public static Manager parse(String path) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new SimpleErrorHandler());
        Document doc = builder.parse(new File(path));
        doc.getDocumentElement().normalize();

        Manager manager1 = new Manager();
        Manager manager = manager1;
        NodeList nodes = doc.getElementsByTagName("Team");

        for(int i = 0; i < nodes.getLength(); ++i) {
            Element n = (Element)nodes.item(i);
            Team team = new Team();
            team.setTeamId(n.getAttribute("id"));
            team.setName(n.getAttribute("name"));
            manager.addTeam(team);
        }

        nodes = doc.getElementsByTagName("Player");
        for(int j =0; j < nodes.getLength(); ++j) {
            Element e = (Element) nodes.item(j);
            Player player = new Player();
            player.setPlayerId(e.getAttribute("playerId"));
            player.setTeamId(e.getAttribute("teamId"));
            player.setName(e.getAttribute("name"));
            player.setAge(Integer.parseInt(e.getAttribute("age")));
            manager.addPlayer(player, e.getAttribute("teamId"));
        }

        return manager;
    }

    public static void write(Manager manager, String path) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element root = doc.createElement("Manager");
        doc.appendChild(root);

        Map<String, Team> teams = manager.getTeams();
        for(Map.Entry<String, Team> entry : teams.entrySet()) {
            Element gnr = doc.createElement("Team");
            gnr.setAttribute("id", entry.getValue().getTeamId());
            gnr.setAttribute("name", entry.getValue().getName());
            root.appendChild(gnr);

            for(Player player: entry.getValue().getPlayers()) {
                Element mv = doc.createElement("Player");
                mv.setAttribute("playerId", player.getPlayerId());
                mv.setAttribute("teamId", player.getTeamId());
                mv.setAttribute("name", player.getName());
                mv.setAttribute("age", String.valueOf(player.getAge()));
                gnr.appendChild(mv);
            }
        }
        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File(path));
        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer transformer = tfactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "manager.dtd");
        transformer.transform(domSource, fileResult);
    }
}