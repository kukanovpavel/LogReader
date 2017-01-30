package ru.kukanov.logwebservice.types;

import javax.xml.datatype.XMLGregorianCalendar;


public class BlockLog {

    private XMLGregorianCalendar date;
    private String thread;
    private String text;


    public BlockLog() {

    }

    public BlockLog(XMLGregorianCalendar date, String thread, String text) {
        this.date = date;
        this.thread = thread;
        this.text = text;

    }


    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public XMLGregorianCalendar getDate() {
        return date;
    }

    public void setDate(XMLGregorianCalendar date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return date + "\n" + thread + "\n" + text + "\n";
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BlockLog other = (BlockLog) obj;
        if (!thread.contains(other.thread)) {
            return false;
        }
        if (text.contains(other.text)) {
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + date.getMillisecond();
        result = prime * result + date.getMillisecond();
        return result;
    }


}
