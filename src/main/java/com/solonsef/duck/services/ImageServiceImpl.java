package com.solonsef.duck.services;

import com.solonsef.duck.dao.ImageDAO;
import com.solonsef.duck.entities.Image;
import com.solonsef.duck.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private ImageDAO imageDAO;

    public ImageServiceImpl(ImageDAO imageDAO) {
        this.imageDAO = imageDAO;
    }

    @Transactional
    @Override
    public void save(Image image) {
        imageDAO.save(image);
    }


    public Image findRandom() {
        return imageDAO.findRandom();
    }

    public Image findById(int id) {
        Optional<Image> image = imageDAO.findById(id);
        if (image.isEmpty()) {
            throw new EntityNotFoundException(String.format("Could not find image with id %d", id));
        }
        return image.get();
    }

    @Override
    public int getQueueCount() {
        return imageDAO.getQueueCount();
    }

    public int getLastId() {
        return imageDAO.getLastId();
    }


}
