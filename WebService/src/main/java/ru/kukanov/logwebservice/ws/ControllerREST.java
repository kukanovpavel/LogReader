package ru.kukanov.logwebservice.ws;



import ru.kukanov.logwebservice.types.ObjectRequest;
import ru.kukanov.logwebservice.types.ObjectResponse;
import ru.kukanov.logwebservice.utils.SearchLog;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import java.util.logging.Level;
import java.util.logging.Logger;



@javax.ws.rs.Path("/")

public class ControllerREST {

    private final static Logger logger = Logger.getLogger(ControllerSOAP.class.getName());

    @POST
    @javax.ws.rs.Path("/")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})

    public ObjectResponse createProductInJSON(ObjectRequest objectRequest) {

        logger.log(Level.INFO, "SOAP request, param: " + " inText: " + objectRequest.getInText() + " datePeriod: " + objectRequest.getPeriodDate()
                + " serverName: " + objectRequest.getServerName() + " fileFormat: " + objectRequest.getFileFormat());

        ObjectResponse objectResponse = new ObjectResponse();
        objectResponse.setReturnSet(new SearchLog(objectRequest.getInText(), objectRequest.getPeriodDate(), objectRequest.getServerName()).Search());
        return objectResponse;
    }

}
