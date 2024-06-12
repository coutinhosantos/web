package com.mobilidade.web.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mobilidade.web.config.StorageProperties;
import com.mobilidade.web.exception.StorageException;
import com.mobilidade.web.exception.StorageFileNotFoundException;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation;

    @Autowired
    public StorageServiceImpl(StorageProperties properties) {
        
        if(properties.getLocation().trim().length() == 0){
            throw new StorageException("File upload location can not be Empty."); 
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Não foi possível inicializar o armazenamento", e);
        }
    }

    @Override
    public void store(MultipartFile file, String id) {
        try {

            if (file.isEmpty()) {
                throw new StorageException("Falha ao armazenar arquivo vazio.");
            }
            Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize()
                    .toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Não é possível armazenar arquivos fora do diretório atual.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                String extensao = file.getOriginalFilename().substring(file.getOriginalFilename().length()-4, file.getOriginalFilename().length());
                
                Path arquivoUpload = this.rootLocation.resolve(Paths.get(id.concat(extensao))).normalize().toAbsolutePath();
                File diretorio = new File(arquivoUpload.toString());
                
                if (!diretorio.exists()) { 
                    diretorio.mkdirs();
                }
                
                Files.copy(inputStream, arquivoUpload, StandardCopyOption.REPLACE_EXISTING);
                loadAll();
            }
        } catch (IOException e) {
            throw new StorageException("Falha ao armazenar arquivo.", e);
        }

    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Falha ao ler arquivos armazenados", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Não foi possível ler o arquivo: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Não foi possível ler o arquivo: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
