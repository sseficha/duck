package com.solonsef.duck.dao;

import com.solonsef.duck.entities.Image;

import java.util.Optional;

public interface ImageDAO {
    Image findRandom();

    Optional<Image> findById(int id);

    int getLastId();

    int getQueueCount();

    void save(Image image);
}
