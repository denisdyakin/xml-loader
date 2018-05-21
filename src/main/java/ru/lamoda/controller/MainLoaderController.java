package ru.lamoda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.lamoda.jdo.UploadFileResponse;
import ru.lamoda.service.LoaderService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainLoaderController {
    private final static Logger log = LoggerFactory.getLogger(MainLoaderController.class);

    @Autowired
    private LoaderService loaderService;

    @RequestMapping(method = RequestMethod.POST, path = "/uploadXml")
    public ResponseEntity<UploadFileResponse> uploadXmlFile(HttpServletRequest request) {
        log.info("upload new files for session {}", request.getSession().getId());
        return new ResponseEntity(loaderService.upload(request), HttpStatus.OK);
    }

}
