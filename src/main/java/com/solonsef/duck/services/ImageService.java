package com.solonsef.duck.services;

import com.solonsef.duck.entities.Image;

public interface ImageService {
    void save(Image image);

    Image findRandom();

    Image findById(int id);

    int getLastId();
}
