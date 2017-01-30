package ru.kukanov.logwebservice.types;


import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "ObjectRest")
public class ObjectRequest {

    private String inText;
    private String serverName;
    private List<PairDateXML> periodDate;
    private Boolean isFile;
    private String fileFormat;


    public Boolean getIsFile() {
        return isFile;
    }

    public void setIsFile(Boolean isFile) {
        this.isFile = isFile;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getInText() {
        return inText;
    }

    public void setInText(String inText) {
        this.inText = inText;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public List<PairDateXML> getPeriodDate() {
     return periodDate;
    }

    public void setPeriodDate(List<PairDateXML> periodDate) {
        this.periodDate = periodDate;
    }

}
