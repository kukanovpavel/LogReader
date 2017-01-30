package ru.kukanov.logwebservice.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.kukanov.logwebservice.types.BlockLog;

import ru.kukanov.logwebservice.types.PairDateXML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SearchLog {

    private String inputText;
    private List<PairDateXML> datePeriod;
    private String serverName;
    private String homeDirectory = System.getProperty("user.dir");
    private final static Logger logger = Logger.getLogger(SearchLog.class.getName());

    public SearchLog(String inputText, List<PairDateXML> datePeriod, String serverName) {
        this.inputText = inputText;
        this.datePeriod = datePeriod;
        this.serverName = serverName;
    }


    public Set<BlockLog> Search() {

        List<Path> pathList = getDirectoryList();
        Set<BlockLog> returnList = new TreeSet<>(new SortBlock());
        String currentLine;
        String nextline;
        Process proc;
        Runtime rt = Runtime.getRuntime();
        StringBuilder sB = new StringBuilder();

        for (Path path : pathList) {
            try {
                // proc = rt.exec("cmd /c findstr /O /I /R /S /C:\"" + text + "\" " + path.toString());
                String str = "awk /" + inputText + "/ RS=#### ORS=nextBlock\\n " + path;
                proc = rt.exec(str);

                try (BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {

                    while ((currentLine = in.readLine()) != null) {
                        ////####<05.12.2016, 5:38:54,119 PM MSK> <Info> <Server> <kukanov-pc> <server_1> <[ACTIVE] ExecuteThread: '7' for queue: 'weblogic.kernel.Default (self-tuning)'> <<WLS Kernel>> <> <ec019c93-714a-402c-8d2b-b7ad199214c7-0000006f> <1480948734119> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-002635> <The server "AdminServer" connected to this server.>
                        String[] words = currentLine.split("> <");
                        Long time = Long.parseLong(words[9]);

                        if (dateUtils.isEntry(time, datePeriod)) {
                            String thread = words[5];
                            sB.append(currentLine).append("\n");

                            while (!(nextline = in.readLine()).contains("nextBlock")) {
                                sB.append(nextline).append("\n");
                            }
                            returnList.add(new BlockLog(dateUtils.timestampToGC(time), thread, sB.toString()));
                            sB.setLength(0);
                        }
                    }
                    proc.destroy();
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }
        return returnList;
    }


    private List<Path> getDirectoryList() {

        Process proc;
        String line;

        List<String> serverList = getServerList();
        List<Path> listPath = new ArrayList<>();
        Runtime rt = Runtime.getRuntime();

        for (String s : serverList) {
            try {
                //  String str = "awk '/Tunneling/' RS=\"####\" "+ homeDirectory +"/servers/AdminServer/logs/AdminServer.log";
                String str = "find " + homeDirectory + "/ -name " + s + ".log*";
                proc = rt.exec(str);
                //proc = rt.exec("cmd /c findstr /I /S /M /C:\"" + inputText + "\" " + homeDirectory + "\\" + s + "*.log*");
                try (BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {

                    while ((line = in.readLine()) != null) {
//                        Path file = Paths.get(line);
//                        BasicFileAttributes attr = null; 3
//                        try {
//                            attr = Files.readAttributes(file, BasicFileAttributes.class);
//                        } catch (IOException e) {
//                            System.err.println(e.toString());
//                        }
//                        if (dateUtils.isEntry(attr.creationTime().toMillis(), attr.lastModifiedTime().toMillis(), datePeriod)) {
//                            listPath.add(Paths.get(line));
//                        }
                        listPath.add(Paths.get(line));
                    }
                    proc.destroy();
                }
            } catch (IOException | NullPointerException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }
        return listPath;
    }

    private List<String> getServerList() {
        List<String> serverList = new ArrayList<>();

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(homeDirectory + "/config/config.xml"));
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("server");

            for (int index = 0; index < nList.getLength(); index++) {
                Node nNode = nList.item(index);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    if (eElement.getElementsByTagName("cluster").item(0) != null && eElement.getElementsByTagName("cluster").item(0).getTextContent().contains(serverName)) {
                        serverList.add(eElement.getElementsByTagName("name").item(0).getTextContent());
                    } else if (eElement.getElementsByTagName("name").item(0).getTextContent().contains(serverName)) {
                        serverList.add(eElement.getElementsByTagName("name").item(0).getTextContent());
                        return serverList;
                    }
                    if (serverName.equals("domain")) {
                        serverList.add(eElement.getElementsByTagName("name").item(0).getTextContent());
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }

        return serverList;
    }

}
