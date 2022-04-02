package com.andrey.demo.util.fileManager;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileUtilImpl implements FileUtil{
    @Override
    public List<String> getFileNameListInChosenFolder(String folder) throws NoSuchFileException {
        Path choosingFolder = Path.of(folder);
        if (!Files.isDirectory(choosingFolder))throw new NoSuchFileException(choosingFolder.toString());
        try {
            return Files.walk(choosingFolder).filter(Files::isRegularFile).map(Path::getFileName).map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @SneakyThrows
    @Override
    public void renameFiles(String reg1, String reg2, String folder) {
        List<String> path = getAbsoluteFileList(folder);
        System.out.println(path.get(1));

        path.stream().map(Path::of).forEach(x -> {
            x.toFile().renameTo(new File(folder + File.separator + x.getFileName().toString().replaceAll(reg1, reg2)));
        });
    }

    @Override
    public List<String> getAbsoluteFileList(String folder) throws NoSuchFileException{
        Path choosingFolder = Path.of(folder);
        if (!Files.isDirectory(choosingFolder))throw new NoSuchFileException(choosingFolder.toString());
        try {
            return Files.walk(choosingFolder).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }



}
