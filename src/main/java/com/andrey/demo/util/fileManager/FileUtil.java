package com.andrey.demo.util.fileManager;

import java.nio.file.NoSuchFileException;
import java.util.List;

public interface FileUtil {
    List<String> getFileNameListInChosenFolder(String folder) throws NoSuchFileException;
    void renameFiles(String reg1, String reg2, String folder);
    void renameFiles(List<String> fileNameList, String folder);
    List<String> getAbsoluteFileList(String folder) throws NoSuchFileException;
    List<String> fileNameListFromFile(String filePath);
    void renameFileFromFileData(String fileSource , String folder);
    List<String> getListFoldrers(String folder);
    List<String> getPatternsNameList(String pattern, int size);
    void renameFileFromPattern(String pattern , String folder);






}
