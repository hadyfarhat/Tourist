package com.example.tourist;

import java.io.File;

/**
 * this class represents the data supporting the adapter
 */

class ImageElement {
    int image = -1;
    File file = null;

    public ImageElement(int image) {
        this.image = image;
    }

    public ImageElement(File file) {
        this.file = file;
    }
}
