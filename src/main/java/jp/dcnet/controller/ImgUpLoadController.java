package jp.dcnet.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ImgUpLoadController {
//相对路径namess
	@Value( "${imagesurl}")
	private String UPLOAD_DIR;
//action
	@PostMapping("/upload")
	//获取本地地址
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        // check if file is empty
        if (file.isEmpty()) {
        	//保存在server
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            //返回根页面
            return "redirect:/";
        }

        // normalize the file path
        //获取图片名字
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // save the file on the local file system
        try {
        	//取得项目地址+图片名字=path
            Path path = Paths.get(UPLOAD_DIR + fileName);
            System.out.println(path);
            //本地file文件流copy给项目path
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/";
    }

//	@GetMapping("/")
//    public String homepage() {
//
//		String path = "..\\images"
//        return "index";
//    }

	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		//项目文件夹+从数据库里取出来的文件名字
		String path = "..\\images\\" +"04.jpg";
		//key value
		mv.addObject("path", path);
		mv.setViewName("index");
        return mv;
	}

}
