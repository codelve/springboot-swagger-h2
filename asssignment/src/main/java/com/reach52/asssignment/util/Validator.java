package com.reach52.asssignment.util;

import com.reach52.asssignment.exceptions.InvalidImageException;
import com.reach52.asssignment.exceptions.NoExtensionFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Validator {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean isValidImageFile(MultipartFile imageFile) {
        try {
            String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new InvalidImageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            String fileExtension = getImageFileExtension(imageFile);
            if (fileExtension.toLowerCase().equals(APPConstants.PNG_EXTENTION) ||
                    fileExtension.toLowerCase().equals(APPConstants.JPEG_EXTENTION)) {
                return true;
            } else {
                return false;
            }
        } catch (NoExtensionFoundException e) {
            logger.error("Image does not have extension");
            return false;
        } catch (InvalidImageException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public static String getImageFileExtension(MultipartFile imageFile) throws NoExtensionFoundException {
        String arrStr[] = imageFile.getOriginalFilename().split(APPConstants.DOT_REGEX);
        if (arrStr.length >= 2) {
            return APPConstants.DOT + arrStr[arrStr.length - 1];
        } else {
            throw new NoExtensionFoundException();
        }
    }

}
