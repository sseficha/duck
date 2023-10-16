package com.solonsef.duck.rest;

import com.solonsef.duck.entities.Image;
import com.solonsef.duck.entities.User;
import com.solonsef.duck.exceptions.StorageException;
import com.solonsef.duck.services.ImageService;
import com.solonsef.duck.services.StorageService;
import com.solonsef.duck.services.UserService;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.security.Principal;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Validated
@RestController
@RequestMapping("/images")
public class ImageController {

    StorageService storageService;
    ImageService imageService;

    UserService userService;

    public ImageController(StorageService storageService, ImageService imageService, UserService userService) {
        this.storageService = storageService;
        this.imageService = imageService;
        this.userService = userService;
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public void saveImage(@RequestParam("file") MultipartFile file, Principal principal) {
        User user = userService.findByUserName(principal.getName()).get();
        Image image = new Image("duck" + imageService.getLastId());
        image.setUser(user);
        try {
            storageService.saveImage(image.getName(), file);
            imageService.save(image);
        } catch (Exception e) {
            throw new StorageException("Unable to store uploaded file", e); //add throwable to get complete stack trace
        }
    }

    @GetMapping(value = "/{id}")
    public URL getImage(@PathVariable @Min(1) int id) {
        Image image = imageService.findById(id);
        return getImageFromStorage(image);

    }

    @GetMapping(value = "/random")
    public URL getRandomImage() {
        Image image = imageService.findRandom();
        return getImageFromStorage(image);
    }

    public URL getImageFromStorage(Image image) {
        try {
            return storageService.getImage(image.getName());
        } catch (Exception e) {
            throw new StorageException("Unable to get file: " + image.getName(), e);
        }
    }

}


