package com.example.tseytwa.tinder.service;

import com.example.tseytwa.tinder.model.Photo;
import com.example.tseytwa.tinder.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PhotoService {
    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo savePhoto(String profileId, MultipartFile file, boolean isMain) throws IOException {
        if (isMain) {
            photoRepository.findByProfileId(profileId).forEach(photo -> {
                photo.setMain(false);
                photoRepository.save(photo);
            });
        }

        Photo photo = new Photo();
        photo.setProfileId(profileId);
        photo.setFileName(file.getOriginalFilename());
        photo.setContentType(file.getContentType());
        photo.setData(file.getBytes());
        photo.setMain(isMain);

        return photoRepository.save(photo);
    }

    public List<Photo> getProfilePhotos(String profileId) {
        return photoRepository.findByProfileId(profileId);
    }

    public Photo getMainPhoto(String profileId) {
        return photoRepository.findByProfileIdAndIsMainTrue(profileId)
                .orElse(null);
    }

    public void deletePhoto(String photoId) {
        photoRepository.deleteById(photoId);
    }

    public void deleteAllProfilePhotos(String profileId) {
        photoRepository.deleteByProfileId(profileId);
    }

    public void setMainPhoto(String photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));

        // Сбрасываем флаг isMain у всех фото профиля
        photoRepository.findByProfileId(photo.getProfileId()).forEach(p -> {
            p.setMain(false);
            photoRepository.save(p);
        });

        // Устанавливаем новое главное фото
        photo.setMain(true);
        photoRepository.save(photo);
    }

    public Photo getPhotoById(String photoId) {
        return photoRepository.findById(photoId).orElse(null);
    }
}
