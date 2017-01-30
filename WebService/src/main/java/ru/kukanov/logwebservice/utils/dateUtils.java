package ru.kukanov.logwebservice.utils;

import ru.kukanov.logwebservice.types.PairDateXML;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Pkukanov on 06.12.2016.
 */
class dateUtils {

    private final static Logger logger = Logger.getLogger(dateUtils.class.getName());

    static boolean isEntry(Long currentDate, List<PairDateXML> rangeDate) {

        for (PairDateXML i : rangeDate) {
            if (currentDate >= i.getFromDateLong() && currentDate <= i.getToDateLong()) {
                return true;
            }
        }
        return false;
    }

    static boolean isEntry(long creationDate, long lastModifiedDate, List<PairDateXML> rangeDate) {

        for (PairDateXML i : rangeDate) {
            if (creationDate >= i.getFromDateLong() && lastModifiedDate <= i.getFromDateLong()) {
                return true;
            }
        }
        return false;
    }

    static XMLGregorianCalendar timestampToGC(Long timestamp) {

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(timestamp);
        XMLGregorianCalendar xmlC = null;
        try {
            xmlC = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return xmlC;
    }

}
