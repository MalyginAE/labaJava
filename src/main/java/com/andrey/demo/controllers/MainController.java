package com.andrey.demo.controllers;

import com.andrey.demo.util.fileManager.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.Collections;
import java.util.List;

@Controller
public class MainController {
    @Value("${startFolder}")
    String startStorage;
    @Autowired
    private FileUtil fileUtil;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("currentDir", startStorage);
        model.addAttribute("folders", fileUtil.getListFoldrers(startStorage));
        return "main";
    }

    @PostMapping("/list")
    public String getDataFolder(@RequestParam("dir") String folder, Model model) {
        List<String> fileList = Collections.EMPTY_LIST;
        if (folder != null) {
            try {
                fileList = fileUtil.getFileNameListInChosenFolder(URLDecoder.decode(folder));
                model.addAttribute("fileList", fileList);
                model.addAttribute("currentDir", folder);
            } catch (Exception e) {
                model.addAttribute("fileList", fileList);
                model.addAttribute("currentDir", folder);
                e.printStackTrace();
                return "redirect:/";
            }
        }
        return "showFiles";
    }

    @PostMapping("/rename")
    public String renameFiles(@RequestParam String reg1, @RequestParam String reg2, @RequestParam String dir,
                              @RequestParam(required = false, name = "otherFile") String renameInOtherFile,
                              @RequestParam(required = false, name = "regex") String isReg,
                              @RequestParam(required = false, name = "pat") String isPattern,
                              @RequestParam(required = false, name = "fileSource") String fileSourceToReadFile,
                              @RequestParam(required = false, name = "pattern") String pattern,
                              Model model) {

        if (isReg != null) {
            fileUtil.renameFiles(reg1, reg2, dir);
        } else if (renameInOtherFile != null) {
            fileUtil.renameFileFromFileData(fileSourceToReadFile, dir);
        }
        else if (isPattern != null){
            fileUtil.renameFileFromPattern(pattern,dir);
        }


        List<String> fileList = Collections.EMPTY_LIST;
        if (dir != null) {
            try {
                fileList = fileUtil.getFileNameListInChosenFolder(URLDecoder.decode(dir));
                model.addAttribute("fileList", fileList);
                model.addAttribute("currentDir", dir);
            } catch (Exception e) {
                model.addAttribute("fileList", fileList);
                model.addAttribute("currentDir", dir);
                e.printStackTrace();
                return "redirect:/";
            }
        }
        return "showFiles";
    }


    @GetMapping("/folders")
    public String showFolder(@RequestParam("folder") String folder, Model model) {
        model.addAttribute("currentDir", URLDecoder.decode(folder));
        model.addAttribute("folders", fileUtil.getListFoldrers(folder));
        return "main";
    }


}
