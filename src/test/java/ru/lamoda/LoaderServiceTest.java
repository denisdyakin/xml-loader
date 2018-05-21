package ru.lamoda;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.lamoda.jdo.UploadFileResponse;
import ru.lamoda.service.LoaderService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/Test.xml")
public class LoaderServiceTest {

    @Autowired
    LoaderService loaderService;

    @Test
    @Ignore
    public void testUploadFiles() throws Exception {
        List<FileItem> files = new ArrayList<>(2);
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();

        File file = getFile("testXml/x.xml");
        System.out.println(file.getName());

        InputStream inputStream  = new FileInputStream(file);

        int availableBytes = inputStream.available();

        // Write the inputStream to a FileItem
        File outFile = new File(file.getAbsolutePath()); // This is your tmp file, the code stores the file here in order to avoid storing it in memory
        FileItem fileItem = new DiskFileItem("fileUpload", "plain/text", false, "x.xml", availableBytes, outFile);

        fileItem.write(file);

        files.add(fileItem);

        loaderService.processFiles(new UploadFileResponse(), files);
    }

    private File getFile(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

}
