package com.example.tseytwa.tinder.controller;

import com.example.tseytwa.tinder.model.Photo;
import com.example.tseytwa.tinder.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/upload/{profileId}")
    public ResponseEntity<Photo> uploadPhoto(
            @PathVariable String profileId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "isMain", defaultValue = "false") boolean isMain
    ) throws IOException {
        Photo photo = photoService.savePhoto(profileId, file, isMain);
        return ResponseEntity.ok(photo);
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<Photo>> getProfilePhotos(@PathVariable String profileId) {
        return ResponseEntity.ok(photoService.getProfilePhotos(profileId));
    }

    @GetMapping("/main/{profileId}")
    public ResponseEntity<Photo> getMainPhoto(@PathVariable String profileId) {
        Photo photo = photoService.getMainPhoto(profileId);
        return photo != null ? ResponseEntity.ok(photo) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> deletePhoto(@PathVariable String photoId) {
        photoService.deletePhoto(photoId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{photoId}/main")
    public ResponseEntity<Void> setMainPhoto(@PathVariable String photoId) {
        photoService.setMainPhoto(photoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String photoId) {
        try {
            Photo photo = photoService.getPhotoById(photoId);
            if (photo != null) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(photo.getContentType()))
                        .body(photo.getData());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
} 