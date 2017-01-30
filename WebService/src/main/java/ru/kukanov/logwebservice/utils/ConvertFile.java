package ru.kukanov.logwebservice.utils;

import org.apache.fop.apps.*;
import ru.kukanov.logwebservice.types.BlockLog;
import ru.kukanov.logwebservice.types.ObjectResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

/**
 * Created by Pkukanov on 07.12.2016.
 */
class ConvertFile {

    static void toLOG(Set<BlockLog> returnList, Path pathFile) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(pathFile)))) {
            for (BlockLog blockLog : returnList) {
                writer.write(blockLog.toString());
            }
        }
    }

    static void toHTMLorDOC(Path pathFile, Path pathFileXML) throws TransformerException, IOException {

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer(new StreamSource("html.xsl"));
        transformer.transform(new javax.xml.transform.stream.StreamSource(pathFileXML.toFile()), new javax.xml.transform.stream.StreamResult(new FileOutputStream(pathFile.toFile())));
        Files.delete(pathFileXML);
    }


    static void toXML(Set<BlockLog> returnList, Path pathFile) throws JAXBException {

        ObjectResponse objectRestResponse = new ObjectResponse();
        objectRestResponse.setReturnSet(returnList);

        JAXBContext jaxbContext = JAXBContext.newInstance(ObjectResponse.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(objectRestResponse, new File(pathFile.toAbsolutePath().toString()));
    }

    static void toPDForRTF(Path pathFile, Path pathFileXML, String fileFormat) throws IOException, FOPException, TransformerException {

        StreamSource xmlSource = new StreamSource(pathFileXML.toFile());
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        try (OutputStream out = new FileOutputStream(pathFile.toFile())) {
            Fop fop;
            if (fileFormat.toLowerCase().contains("pdf")) {
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            } else {
                fop = fopFactory.newFop(MimeConstants.MIME_RTF, foUserAgent, out);
            }

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource("pdf.xsl"));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(xmlSource, res);

            Files.delete(pathFileXML);
        }
    }
}

