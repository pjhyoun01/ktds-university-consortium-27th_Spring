package com.ktdsuniversity.edu.movie.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktdsuniversity.edu.actor.service.ActorService;
import com.ktdsuniversity.edu.actor.vo.ActorVO;
import com.ktdsuniversity.edu.category.service.CategoryService;
import com.ktdsuniversity.edu.movie.service.MovieService;
import com.ktdsuniversity.edu.movie.vo.MovieVO;
import com.ktdsuniversity.edu.movie.vo.request.InsertVO;
import com.ktdsuniversity.edu.movie.vo.request.UpdateVO;
import com.ktdsuniversity.edu.movie.vo.response.MovieListVO;
import com.ktdsuniversity.edu.movie.vo.response.OneMovieVO;

import jakarta.validation.Valid;

@Controller
public class MovieController {
//	기능		Controller		Service 		Mapper/DAO
//	조회 목록	view이름List		readAll이름		selectAll이름
//	조회 상세	view이름			read이름ById		select이름ById
//	생성 	doInsert이름		insert이름		insert이름
//	수정		doUpdate이름		update이름		update이름
//	삭제		doDelete이름		delete이름		delete이름
	@Autowired
	private MovieService movieService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ActorService actorService;

	@GetMapping("/")
	public String viewMovieListPage(Model model) {
		List<MovieListVO> movieList = this.movieService.readAllMovie();

		model.addAttribute("movieList", movieList);
		return "movie/list";
	}

	@GetMapping("/movie/view/{movieId}")
	public String viewMoviePage(@PathVariable String movieId, Model model) {
		OneMovieVO oneMovieById = this.movieService.readMovieById(movieId);
		List<String> categoryNameListById = this.categoryService.readAllCategoryNameByMovieId(movieId);
		List<ActorVO> actorList = this.actorService.readActorByMovieId(movieId);

		model.addAttribute("movie", oneMovieById);
		model.addAttribute("categoryNameList", categoryNameListById);
		model.addAttribute("actorList", actorList);
		return "movie/view";
	}

	@GetMapping("/movie/insert")
	public String viewInsertPage() {
		return "movie/insert";
	}

	@PostMapping("/movie/insert")
	public String doInsert(@Valid @ModelAttribute InsertVO insertVO, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(error -> {
//				System.out.println("메시지: " + error.getDefaultMessage());
			});
			model.addAttribute("errerMessage", insertVO);
			return "movie/insert";
		}
		boolean isSuccess = this.movieService.insertMovie(insertVO);

		if (isSuccess) {
			return "redirect:/";
		} else {
			return "errer/404";
		}
	}

	@GetMapping("/movie/update/{movieId}")
	public String viewUpdatePage(@PathVariable String movieId, Model model) {
		MovieVO movie = this.movieService.readMovieById(movieId);
		if (movie != null) {
			model.addAttribute("movieData", movie);
			return "movie/update";
		} else {
			return "error/404";
		}

	}

	@PostMapping("/movie/update/{movieId}")
	public String doUpdateMovie(@PathVariable String movieId, UpdateVO updateVO) {
		updateVO.setMovieId(movieId);

		boolean updateSuccess = this.movieService.updateMovieById(updateVO);
		if (updateSuccess) {
			return "redirect:/movie/view/" + movieId;
		} else {
			return "error/404";
		}
	}

	@PostMapping("/movie/delete/{movieId}")
	public String doDeleteMovie(@PathVariable String movieId) {
		boolean deleteSuccess = this.movieService.deleteMovieById(movieId);
		if (deleteSuccess) {
			return "redirect:/";
		} else {
			return "error/404";
		}
	}
}
