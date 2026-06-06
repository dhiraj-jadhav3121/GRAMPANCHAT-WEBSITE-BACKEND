package com.jambharun.controller;

import com.jambharun.entity.Gallery;
import com.jambharun.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gallery")
@CrossOrigin(origins = {"http://localhost:5173"})
public class GalleryController {

    @Autowired
    private GalleryRepository galleryRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public GalleryController(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    @GetMapping
    public List<Gallery> getAllPhotos() {
        return galleryRepository.findAll();
    }

    @PostMapping("/upload")
    public Gallery uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID() + "_" + originalFileName;

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Gallery gallery = new Gallery();
            gallery.setImageUrl("/uploads/gallery/" + fileName);

            return galleryRepository.save(gallery);

        } catch (Exception e) {
            throw new RuntimeException("Photo upload failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deletePhoto(@PathVariable Long id) {
        galleryRepository.deleteById(id);
    }
}