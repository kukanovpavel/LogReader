package ru.kukanov.logwebservice.types;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;
import java.util.TreeSet;

@XmlRootElement(name = "ObjectRestResponse")
public class ObjectResponse {

    private Set<BlockLog> returnSet = new TreeSet<>();
    private String pathFile;
    private String errorText;

    public Set<BlockLog> getReturnSet() {
        return returnSet;
    }

    public void setReturnSet(Set<BlockLog> returnSet) {
        this.returnSet = returnSet;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }
}
