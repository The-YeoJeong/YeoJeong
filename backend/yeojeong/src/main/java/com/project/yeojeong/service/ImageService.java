package com.project.yeojeong.service;

import com.project.yeojeong.entity.UploadFile;
import com.project.yeojeong.repository.UploadFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    private final Path rootLocation = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\file");
    private final UploadFileRepository uploadFileRepository;

    public ImageService(UploadFileRepository uploadFileRepository) {
        this.uploadFileRepository = uploadFileRepository;
    }

    public UploadFile store(MultipartFile file) throws Exception {
        //		 fileName : 예류2.jpg
        //		 filePath : d:/images/uuid-예류2.jpg
        //		 saveFileName : uuid-예류2.png
        //		 contentType : image/jpeg
        //		 size : 4994942
        //		 registerDate : 2020-02-06 22:29:57.748
        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file " + file.getOriginalFilename());
            }
            String saveFileName = fileSave(rootLocation.toString(), file);
            UploadFile saveFile = new UploadFile();
            saveFile.setFileName(file.getOriginalFilename());
            saveFile.setFileSaveName(saveFileName);
            saveFile.setFileSize(file.getResource().contentLength());
            saveFile.setFilePath(rootLocation.toString().replace(File.separatorChar, '/') + '/' + saveFileName);

            return uploadFileRepository.save(saveFile);

        } catch (IOException e) {
            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public UploadFile load(Long fileId) {
        return uploadFileRepository.findById(fileId).get();
    }

    public String fileSave(String rootLocation, MultipartFile file) throws IOException {
        File uploadDir = new File(rootLocation);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // saveFileName 생성
        UUID uuid = UUID.randomUUID();
        String saveFileName = uuid.toString() + file.getOriginalFilename();
        File saveFile = new File(rootLocation, saveFileName);
        FileCopyUtils.copy(file.getBytes(), saveFile);

        return saveFileName;
    }
}
