package com.ktdsuniversity.edu.producer.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktdsuniversity.edu.producer.sevice.ProducerService;
import com.ktdsuniversity.edu.producer.vo.ProducerVO;
import com.ktdsuniversity.edu.producer.vo.request.InsertVO;

@Controller
public class ProducerController {

	@Autowired
	private ProducerService producerService;

	@GetMapping("/producer/view")
	public String viewProducerListPage(Model model) {
		List<ProducerVO> producerList = this.producerService.readAllProducer();
		model.addAttribute("producerList", producerList);

		return "producer/list";
	}

	@GetMapping("/producer/insert")
	public String viewProducerInsertPage() {
		return "producer/insert";
	}

	@PostMapping("/producer/insert")
	public String doProducerInsert(InsertVO insertVO) {
		boolean insertSuccess = this.producerService.insertProducer(insertVO);
		return "redirect:/producer/view";
	}

}
