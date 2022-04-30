package com.andrey.demo.util.fileManager;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileUtilImpl implements FileUtil {
    @Override
    public List<String> getFileNameListInChosenFolder(String folder) throws NoSuchFileException {
        Path choosingFolder = Path.of(folder);
        if (!Files.isDirectory(choosingFolder)) throw new NoSuchFileException(choosingFolder.toString());
        try {
            return Files.walk(choosingFolder,1).filter(Files::isRegularFile).map(Path::getFileName).map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @SneakyThrows
    @Override
    public void renameFiles(String reg1, String reg2, String folder) {
        List<String> path = getAbsoluteFileList(folder);
        path.stream().map(Path::of).forEach(x -> {
            x.toFile().renameTo(new File(folder + File.separator + x.getFileName().toString().replaceAll(reg1, reg2)));
        });
    }

    @SneakyThrows
    @Override
    public void renameFiles(List<String> fileNameList, String folder) {
        List<String> pathsInFolder = getAbsoluteFileList(folder);
        List<Path> filePath = fileNameList.stream().map(x -> Path.of(folder + File.separator + x)).toList();
        for (int i = 0; i < pathsInFolder.size(); i++) {
            if (i < filePath.size())
                Path.of(pathsInFolder.get(i)).toFile().renameTo(filePath.get(i).toFile());
        }
    }

    @Override
    public List<String> getAbsoluteFileList(String folder) throws NoSuchFileException {
        Path choosingFolder = Path.of(folder);
        if (!Files.isDirectory(choosingFolder)) throw new NoSuchFileException(choosingFolder.toString());
        try {
            return Files.walk(choosingFolder,1).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    @SneakyThrows
    public List<String> fileNameListFromFile(String filePath) {
        return Files.lines(Path.of(filePath)).toList();
    }

    @Override
    public void renameFileFromFileData(String fileSource, String folder) {
        List<String> stringsFiles = fileNameListFromFile(fileSource);
        renameFiles(stringsFiles, folder);
    }

    @SneakyThrows
    @Override
    public List<String> getListFoldrers(String folder) {
        return Files.walk(Path.of(folder), 1).filter(Files::isDirectory).map(Path::toString).
                filter(x ->!x.equals(folder)).toList();
    }

    @Override
    public List<String> getPatternsNameList(String pattern, int size) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            String temp = pattern + "(" + i + ").txt";
            list.add(temp);
        }
        return list;
    }

    @SneakyThrows
    @Override
    public void renameFileFromPattern(String pattern , String folder) {
        List<String> absoluteFileList = getAbsoluteFileList(folder);
        List<String> patternsNameList = getPatternsNameList(pattern, absoluteFileList.size());
        renameFiles(patternsNameList, folder);
    }

    //TODO сделать переименоание файла с помощью паттерна , реализовать две функции


}
