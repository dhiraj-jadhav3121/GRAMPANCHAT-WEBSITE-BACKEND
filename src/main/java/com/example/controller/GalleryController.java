package com.example.controller;

import com.example.entity.Gallery;
import com.example.repository.GalleryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@CrossOrigin(origins = "http://localhost:5173")
public class GalleryController {

    private final GalleryRepository galleryRepository;

    public GalleryController(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    @GetMapping
    public List<Gallery> getAllPhotos() {
        return galleryRepository.findAll();
    }

    @PostMapping
    public Gallery addPhoto(@RequestBody Gallery gallery) {
        return galleryRepository.save(gallery);
    }

    @DeleteMapping("/{id}")
    public void deletePhoto(@PathVariable Long id) {
        galleryRepository.deleteById(id);
    }
}