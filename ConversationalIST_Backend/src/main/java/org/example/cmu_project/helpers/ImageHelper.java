package org.example.cmu_project.helpers;

import org.example.cmu_project.ConversationalISTServer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import static javax.imageio.ImageIO.*;

public class ImageHelper {

    private static final Logger logger = Logger.getLogger(ImageHelper.class.getName());
    private static final String FORMAT_NAME = ".png";
    private static int img_sequence_num = 0;

    private BufferedImage load(String img_sequence_num) {
        BufferedImage img = null;
        try {
            img = read(new File("IMG_" + img_sequence_num + FORMAT_NAME));
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
        return img;
    }

    private void save(BufferedImage img) {
        try {
            File outputfile = new File("IMG_" + img_sequence_num + FORMAT_NAME);
            img_sequence_num++;
            ImageIO.write(img, FORMAT_NAME, outputfile);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

}
