package ru.kukanov.logwebservice.ws;


import ru.kukanov.logwebservice.types.ObjectRequest;
import ru.kukanov.logwebservice.types.ObjectResponse;
import ru.kukanov.logwebservice.utils.MyThread;
import ru.kukanov.logwebservice.utils.ReadConfig;
import ru.kukanov.logwebservice.utils.SearchLog;


import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebService(name = "ControllerSOAP")
@Stateless
public class ControllerSOAP {

    private final static Logger logger = Logger.getLogger(ControllerSOAP.class.getName());

    @WebMethod(operationName = "SearchLog")
    public ObjectResponse getLog(@WebParam(name = "ObjectRequest") ObjectRequest objectRequest) {

        logger.log(Level.INFO, "SOAP request, param: " + " inText: " + objectRequest.getInText() +" datePeriod: "
               + objectRequest.getPeriodDate() +" serverName: "+ objectRequest.getServerName() + " fileFormat: " + objectRequest.getFileFormat());


        LocalTime startTime = LocalTime.now();

        ObjectResponse objectResponse = new ObjectResponse();

        if (objectRequest.getIsFile()) {
            Path pathFile = Paths.get(new ReadConfig().getDirectoryResultFile(), new Date().getTime() + "." + objectRequest.getFileFormat());
            new Thread(new MyThread(objectRequest, pathFile)).start();
            objectResponse.setPathFile(pathFile.toAbsolutePath().toString());

        } else {
            objectResponse.setReturnSet(new SearchLog(objectRequest.getInText(), objectRequest.getPeriodDate(), objectRequest.getServerName()).Search());

        }

        LocalTime finishTime = LocalTime.now();

        logger.log(Level.INFO, "Start: " + startTime + " Finish: " + finishTime);

        return objectResponse;
    }
}
