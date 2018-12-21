package kr.or.connect.resv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.connect.resv.service.ResvmanagerService;

@Controller
@RequestMapping("/productImages")
public class ResvmanagerProductImageController {
	@Autowired
	ResvmanagerService resvmanagerService;
	
	@GetMapping("/{id}")
	public String productFileName( @PathVariable Integer id, @RequestParam(value = "type", required = true, defaultValue="th") String type) {
		String FileName = resvmanagerService.getFileName(id, type);
		return "forward:/img/"+FileName;
	}
}
