package ru.lamoda.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.lamoda.dao.LoaderDao;
import ru.lamoda.jdo.LoadingStockStateResponseType;
import ru.lamoda.jdo.Pair;
import ru.lamoda.jdo.UploadFileResponse;
import ru.lamoda.utils.FileXmlValidator;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class LoaderService {
    private static final Logger log = LoggerFactory.getLogger(LoaderService.class);

    @Autowired
    private LoaderDao loaderDao;

    @Autowired
    private DiskFileItemFactory diskFileItemFactory;

    @Autowired
    private AsyncTaskExecutor taskExecutor;

    public UploadFileResponse upload(HttpServletRequest request) {

        UploadFileResponse uploadFileResponse = new UploadFileResponse();

        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);

        List<FileItem> files = null;
        try {
            files = servletFileUpload.parseRequest(request);
        } catch (FileUploadException e) {
            log.error("File upload exception ", e);
            return uploadFileResponse;
        }

        if (CollectionUtils.isEmpty(files)) return uploadFileResponse;

        return processFiles(uploadFileResponse, files);
    }

    public UploadFileResponse processFiles(final UploadFileResponse uploadFileResponse, List<FileItem> files) {
        List<LoadingStockStateResponseType> messages = new CopyOnWriteArrayList<>();

        List<Future<Pair>> areLoaded = new ArrayList<>();

        for (FileItem file : files)
            areLoaded.add(taskExecutor.submit(() -> processFile(file, messages)));

        for (Future<Pair> isLoaded : areLoaded) {
            try {
                Pair info = isLoaded.get();
                uploadFileResponse.addFileUploadedInfo(info.getFileName(), info.isUploaded());
            } catch (InterruptedException | ExecutionException e) {
                log.error("Error - ", e);
                return uploadFileResponse;
            }
        }

        loaderDao.saveBatchOfItems(messages);
        return uploadFileResponse;
    }


    private Pair processFile(FileItem file, List<LoadingStockStateResponseType> messages) {
        String fileName = file.getName();
        if (file.isFormField()) return new Pair(fileName, false);

        log.info("try to upload file {} into database", file.getName());

        try (InputStream uploadedStream = file.getInputStream()) {

            if (!FileXmlValidator.isValid(uploadedStream)) {
                log.info("file is not valid");
                return new Pair(fileName, false);
            }

            uploadedStream.reset();

            messages.add(parseXml(uploadedStream));
        } catch (Exception ex ) {
            log.error("Can't load file {}, because of ", fileName, ex);
            return new Pair(fileName, false);
        }
        return new Pair(fileName, true);
    }

    private LoadingStockStateResponseType parseXml(InputStream inputStream) throws XMLStreamException, JAXBException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
        xmlStreamReader.nextTag(); //to envelope
        xmlStreamReader.nextTag(); //to header
        xmlStreamReader.next();
        xmlStreamReader.nextTag(); //to body
        xmlStreamReader.nextTag(); // to response

        JAXBContext jaxbContext = JAXBContext.newInstance(LoadingStockStateResponseType.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<LoadingStockStateResponseType> element =
                unmarshaller.unmarshal(xmlStreamReader, LoadingStockStateResponseType.class);

        return element.getValue();
    }

}
