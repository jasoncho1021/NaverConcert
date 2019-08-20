package kr.or.connect.resv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/productImages")
public class ImageController {

	@GetMapping
	public String productFileName() {
		System.out.println("imageController");
		return "forward:/img/1_ma_2.png";
	}

	@GetMapping("/{id}")
	public String productFileName(@PathVariable Integer id,
			@RequestParam(value = "type", required = true, defaultValue = "th") String type) {
		//String FileName = resvmanagerService.getFileName(id, type);
		//return "forward:/img/" + FileName;
		return "";
	}
}
