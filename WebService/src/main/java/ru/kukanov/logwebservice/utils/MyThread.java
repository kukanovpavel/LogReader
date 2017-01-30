package ru.kukanov.logwebservice.utils;

import ru.kukanov.logwebservice.types.BlockLog;

import ru.kukanov.logwebservice.types.ObjectRequest;
import ru.kukanov.logwebservice.types.PairDateXML;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyThread implements Runnable {
    private String inputText;
    private List<PairDateXML> rangeDate;
    private String serverName;
    private Path pathFile;
    private String fileFormat;
    private final static Logger logger = Logger.getLogger(MyThread.class.getName());

     public MyThread(ObjectRequest objectRequest, Path pathFile) {
        this.inputText = objectRequest.getInText();
        this.rangeDate = objectRequest.getPeriodDate();
        this.serverName = objectRequest.getServerName();
        this.pathFile = pathFile;
        this.fileFormat = objectRequest.getFileFormat();

    }

    @Override
    public void run() {

        try {
            Set<BlockLog> returnList = new SearchLog(inputText, rangeDate, serverName).Search();

            switch (fileFormat.toLowerCase()) {
                case "log": {
                    ConvertFile.toLOG(returnList, pathFile);
                    break;
                }
                case "xml": {
                    ConvertFile.toXML(returnList, pathFile);
                    break;
                }
                case "html":
                case "pdf":
                case "rtf": {
                    Path tempXML = Paths.get(new ReadConfig().getDirectoryResultFile(), new Date().getTime() + ".xml");
                    ConvertFile.toXML(returnList, tempXML);
                    if (fileFormat.toLowerCase().contains("html")) {
                        ConvertFile.toHTMLorDOC(pathFile, tempXML);
                    } else if (fileFormat.toLowerCase().contains("pdf")) {
                        ConvertFile.toPDForRTF(pathFile, tempXML, fileFormat);

                    } else {
                        ConvertFile.toPDForRTF(pathFile, tempXML, fileFormat);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            logger.log(Level.WARNING, e.getMessage());
        }
    }


}

