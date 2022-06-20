package com.geonoo.magazine.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AwsS3ServiceTest {

//    File file = new File("src/test/java/com/geonoo/magazine/img/" +"밥.png");
//    FileItem fileItem = new DiskFileItem(
//            "밥.png",
//            Files.probeContentType(file.toPath()),
//            false,
//            file.getName(),
//            (int) file.length(),
//            file.getParentFile()
//    );
//        IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
//    MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
//    List<MultipartFile> fileList = null;
//        fileList.add(multipartFile);

}