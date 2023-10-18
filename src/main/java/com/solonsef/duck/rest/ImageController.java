package com.solonsef.duck.rest;

import com.solonsef.duck.dto.CountDTO;
import com.solonsef.duck.dto.ImageDTO;
import com.solonsef.duck.entities.Image;
import com.solonsef.duck.entities.User;
import com.solonsef.duck.exceptions.StorageException;
import com.solonsef.duck.services.ImageService;
import com.solonsef.duck.services.StorageService;
import com.solonsef.duck.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.security.Principal;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "Images")
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

    @SecurityRequirement(name = "basic")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
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

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ImageDTO getImage(@PathVariable @Min(1) int id) {
        Image image = imageService.findById(id);
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setUrl(getImageFromStorage(image).toString());
        return imageDTO;

    }

    @GetMapping(value = "/random", produces = {"application/json"})
    public ImageDTO getRandomImage() {
        Image image = imageService.findRandom();
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setUrl(getImageFromStorage(image).toString());
        return imageDTO;
    }

    @GetMapping(value = "/queue", produces = {"application/json"})
    public CountDTO getQueueCount() {
        CountDTO countDTO = new CountDTO();
        countDTO.setCount(imageService.getQueueCount());
        return countDTO;
    }

    public URL getImageFromStorage(Image image) {
        try {
            return storageService.getImage(image.getName());
        } catch (Exception e) {
            throw new StorageException("Unable to get file: " + image.getName(), e);
        }
    }

}


