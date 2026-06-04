package com.jambharun.controller;

import com.jambharun.entity.Gallery;
import com.jambharun.repository.GalleryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GalleryControllerTest {

    @Mock
    private GalleryRepository galleryRepository;

    @InjectMocks
    private GalleryController galleryController;

    @Test
    void getAllPhotosTest() {

        Gallery photo1 = new Gallery();
        photo1.setId(1L);

        Gallery photo2 = new Gallery();
        photo2.setId(2L);

        List<Gallery> photos = Arrays.asList(photo1, photo2);

        when(galleryRepository.findAll()).thenReturn(photos);

        List<Gallery> result = galleryController.getAllPhotos();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(galleryRepository, times(1)).findAll();
    }

    @Test
    void addPhotoTest() {

        Gallery gallery = new Gallery();
        gallery.setId(1L);

        when(galleryRepository.save(gallery)).thenReturn(gallery);

        Gallery result = galleryController.addPhoto(gallery);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(galleryRepository, times(1)).save(gallery);
    }

    @Test
    void deletePhotoTest() {

        Long id = 1L;

        doNothing().when(galleryRepository).deleteById(id);

        galleryController.deletePhoto(id);

        verify(galleryRepository, times(1)).deleteById(id);
    }
}