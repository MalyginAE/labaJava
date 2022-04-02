package com.andrey.demo.controllers;

import com.andrey.demo.util.fileManager.FileUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private FileUtil fileUtil;

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/list")
    public String getDataFolder(@CookieValue("folder") String folder, Model model) {
        List<String> fileList = Collections.EMPTY_LIST;
        if (folder != null) {
            try {
                fileList = fileUtil.getFileNameListInChosenFolder(URLDecoder.decode(folder));
                model.addAttribute("fileList", fileList);
            } catch (Exception e) {
                model.addAttribute("fileList", fileList);
                e.printStackTrace();
                return "redirect:/";
            }
        }
        return "showFiles";
    }

    @PostMapping("/rename")
    public String renameFiles(@RequestParam String reg1, @RequestParam String reg2, @CookieValue String folder, Model model) {
        fileUtil.renameFiles(reg1, reg2, folder);
        return "redirect:/list";
    }

    @PostMapping("/")
    public String checkFolderAndSetCookie(@RequestParam("folder") String folder, Model model, HttpServletResponse response) {

        if (Files.isRegularFile(Path.of(folder)) || !Files.isDirectory(Path.of(folder))) {
            model.addAttribute("warning", "Проверьте правильность введеного пути");
            return "main";
        }
        Cookie cookie = new Cookie("folder", URLEncoder.encode(folder)
        );
        response.addCookie(cookie);

        return "redirect:/list";
    }


}
