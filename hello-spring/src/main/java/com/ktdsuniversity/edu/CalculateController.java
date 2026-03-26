package com.ktdsuniversity.edu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CalculateController {

	@GetMapping("/calc")
	public String viewCalc(Model model) {
		
		int firstnum = 1;
		int secondnum = 2;
		model.addAttribute("firstnum", firstnum);
		model.addAttribute("secondnum", secondnum);
		model.addAttribute("result", firstnum + secondnum);
		
		
		return "calc";
	}
	
	@GetMapping("/calc2")
	public String viewCalc2(@RequestParam(required = false, defaultValue = "0") int f, 
							@RequestParam(required = false, defaultValue = "0") int s, 
							Model model) {
		int result = f + s;
		
		model.addAttribute("f", f);
		model.addAttribute("s", s);
		model.addAttribute("result", result);
		
		return "calc";
	}
	
}
