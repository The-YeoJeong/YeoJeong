package com.project.yeojeong.controller;

import com.project.yeojeong.entity.UploadFile;
import com.project.yeojeong.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final ResourceLoader resourceLoader;

    public ImageController(ImageService imageService, ResourceLoader resourceLoader) {
        this.imageService = imageService;
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("/image")
    public ResponseEntity<?> imageUpload(@RequestParam("file") MultipartFile file) {
        try {
            UploadFile uploadFile = imageService.store(file);
            return ResponseEntity.ok().body("api/image/" + uploadFile.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/image/{fileId}")
    public ResponseEntity<?> serveFile(@PathVariable Long fileId) {
        try {
            UploadFile uploadFile = imageService.load(fileId);
            Resource resource = resourceLoader.getResource("file:" + uploadFile.getFilePath());
            return ResponseEntity.ok().body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}