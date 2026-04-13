package com.ktdsuniversity.edu.genre.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktdsuniversity.edu.genre.service.GenreService;
import com.ktdsuniversity.edu.genre.vo.request.InsertVO;

@Controller
public class GenreController {

	@Autowired
	private GenreService genreService;

	@PostMapping("/genre/insert")
	public String doInsertGenre(InsertVO insertVO) {
		boolean insertSuccess = this.genreService.insertGenre(insertVO);
		if (insertSuccess) {
			return "redirect:/";
		} else {
			return "error/404";
		}
	}
}
