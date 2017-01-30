package ru.kukanov.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;
import ru.kukanov.logwebservice.ws.*;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.SQLException;



/**
 * Created by kukanovpavel on 13.01.17.
 */
@Controller
public class ControllerSearch {

    @RequestMapping(value = "/views/search", method = RequestMethod.POST)
    public ModelAndView printWelcome(HttpServletRequest request, ObjectRequest objectRequest) throws ServletException, IOException {
        ControllerSOAP service = new ControllerSOAPService().getControllerSOAPPort();


        String[] fromDate = request.getParameterValues("fromDate");
        String[] toDate = request.getParameterValues("toDate");

        for (int i = 0; i < fromDate.length; i++) {
            PairDateXML pairDateXML = new PairDateXML();
            pairDateXML.setFromDate(fromDate[i]);
            pairDateXML.setToDate(toDate[i]);
            objectRequest.getPeriodDate().add(pairDateXML);
        }

        if (objectRequest.isIsFile() == null){
            objectRequest.setIsFile(false);

        }

        try {
            ru.kukanov.db.JavaToMySQL.insertRecordIntoTable(request.getRemoteUser(), "SOAP request, param: " + "Text: " + objectRequest.getInText()
                    + " ServerName: " + objectRequest.getServerName() + " FromDate: " + fromDate + "  ToDate: " + toDate + " Is File: "
                    + objectRequest.isIsFile() + "  FileFormat: " + objectRequest.getFileFormat());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("search");
        model.addObject("list", service.searchLog(objectRequest).getReturnSet());

        return model;

    }

}
