package ru.kukanov.servlets;


import com.google.gson.Gson;
import ru.kukanov.logwebservice.ws.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;



/**
 * Created by Pkukanov on 13.12.2016.
 */
@WebServlet("/Search")
public class Search extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ControllerSOAP service = new ControllerSOAPService().getControllerSOAPPort();
        ObjectRequest objectRequest = new ObjectRequest();

        String inText = request.getParameter("inText");
        String serverName = request.getParameter("serverName");
        String[] fromDate = request.getParameterValues("fromDate");
        String[] toDate = request.getParameterValues("toDate");
      //  Boolean isFile = request.getParameterValues("isFile");
        boolean isFile = false;
        String fileFormat = request.getParameter("fileFormat");

        objectRequest.setFileFormat(fileFormat);
        objectRequest.setInText(inText);
        objectRequest.setServerName(serverName);
        for (int i = 0; i < fromDate.length; i++) {

            PairDateXML pairDateXML = new PairDateXML();
            pairDateXML.setFromDate(fromDate[i]);
            pairDateXML.setToDate(toDate[i]);
            objectRequest.getPeriodDate().add(pairDateXML);
        }
        objectRequest.setIsFile(isFile);


        try {
            ru.kukanov.db.JavaToMySQL.insertRecordIntoTable(request.getRemoteUser(), "SOAP request, param: " + "Text: " +inText
                    + " ServerName: " + serverName + " FromDate: " + fromDate + "  ToDate: " + toDate + " Is File: "
                    + isFile + "  FileFormat: " + fileFormat);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(service.searchLog(objectRequest).getReturnSet()));




    }
}
