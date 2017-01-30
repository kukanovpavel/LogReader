package ru.kukanov.logwebservice.types;

import javax.xml.datatype.XMLGregorianCalendar;


public class PairDateXML {

    private XMLGregorianCalendar fromDate;

    private XMLGregorianCalendar toDate;

    public PairDateXML() {

    }

    public PairDateXML(XMLGregorianCalendar fromDate, XMLGregorianCalendar toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public XMLGregorianCalendar getFromDate() {
        return fromDate;
    }

    public void setFromDate(XMLGregorianCalendar fromDate) {
        this.fromDate = fromDate;
    }

    public XMLGregorianCalendar getToDate() {
        return toDate;
    }

    public void setToDate(XMLGregorianCalendar toDate) {
        this.toDate = toDate;
    }

    public long getToDateLong() {
        if (toDate == null){
            return Long.MAX_VALUE;
        }
        return toDate.toGregorianCalendar().getTimeInMillis();
    }

    public long getFromDateLong() {
        if (fromDate == null){
            return  Long.MIN_VALUE;
        }
        return fromDate.toGregorianCalendar().getTimeInMillis();
    }

    @Override
    public String toString() {
        return "PairDateXML{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
