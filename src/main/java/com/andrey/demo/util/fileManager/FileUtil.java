package com.andrey.demo.util.fileManager;

import java.nio.file.NoSuchFileException;
import java.util.List;

public interface FileUtil {
    List<String> getFileNameListInChosenFolder(String folder) throws NoSuchFileException;
    void renameFiles(String reg1, String reg2, String folder);
    List<String> getAbsoluteFileList(String folder) throws NoSuchFileException;


}
