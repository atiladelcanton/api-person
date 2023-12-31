package br.com.zensolutions.services;

import br.com.zensolutions.config.FileStorageConfig;
import br.com.zensolutions.exceptions.FileStorageException;
import br.com.zensolutions.exceptions.MyFIleNotFoundException;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {


    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {

        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception exception) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored!", exception);
        }
    }

    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileStorageException("Failed to store empty file.");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            if(fileName.contains("..")){
                throw new FileStorageException("Filename contains invalid path sequence "+fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }

            return fileName;
        } catch (Exception exception) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", exception);
        }
    }

    public Resource loadFileAsResource(String fileName){
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(!resource.exists()){
                throw new MyFIleNotFoundException("File not found");
            }
            return resource;
        }catch (Exception exception){
            throw new MyFIleNotFoundException("File not found: "+fileName,exception);
        }
    }

}
