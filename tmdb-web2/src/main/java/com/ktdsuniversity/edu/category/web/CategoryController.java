package com.ktdsuniversity.edu.category.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktdsuniversity.edu.category.service.CategoryService;
import com.ktdsuniversity.edu.category.vo.CategoryVO;
import com.ktdsuniversity.edu.category.vo.request.InsertVO;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/category/view")
	public String viewCategoryListPage(Model model) {
		List<CategoryVO> categoryList = this.categoryService.readAllCategory();
		model.addAttribute("categoryList", categoryList);

		return "category/list";
	}

	@GetMapping("/category/insert")
	public String viewInsertPage() {
		return "category/insert";
	}

	@PostMapping("/category/insert")
	public String doInsert(InsertVO insertVO) {
		boolean insertSuccess = this.categoryService.insertCategory(insertVO);
		if (insertSuccess) {
			return "redirect:/";
		} else {
			return "error/404";
		}
	}
}
