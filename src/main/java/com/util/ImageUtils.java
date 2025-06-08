package com.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/** Assorted image helpers. */
public final class ImageUtils {

    private ImageUtils() { /* util – no instances */ }

    /**
     * Load <code>file</code> and scale it to fit within <code>maxW × maxH</code>,
     * keeping aspect-ratio.  Throws if the file is not an image or unreadable.
     */
    public static ImageIcon createThumbnail(File file, int maxW, int maxH)
            throws IOException {

        BufferedImage raw = ImageIO.read(file);          // may throw
        if (raw == null)
            throw new IOException("Unsupported image type: " + file);

        double scale = Math.min(
                maxW / (double) raw.getWidth(),
                maxH / (double) raw.getHeight());

        int w = (int) Math.round(raw.getWidth()  * scale);
        int h = (int) Math.round(raw.getHeight() * scale);

        Image scaled = raw.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
