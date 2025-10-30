package com.ds3c.tcc.ApiTcc.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalStorageService {

    private final Path rootLocation = Paths.get("uploads"); // pasta local do projeto

    public String saveFile(MultipartFile file, String folder) {
        try {
            if (file == null || file.isEmpty()) {
                return null;
            }

            // Cria diretório se não existir
            Path directory = rootLocation.resolve(folder);
            Files.createDirectories(directory);

            // Nome do arquivo original
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Caminho completo
            Path destinationFile = directory.resolve(filename).normalize();

            // Salva o arquivo
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            // Retorna o caminho para acessar futuramente
            return "/uploads/" + folder + "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar arquivo localmente: " + e.getMessage(), e);
        }
    }
}
