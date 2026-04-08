package com.ktdsuniversity.edu.actor.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktdsuniversity.edu.actor.service.ActorService;
import com.ktdsuniversity.edu.actor.vo.ActorVO;
import com.ktdsuniversity.edu.actor.vo.request.InsertVO;
import com.ktdsuniversity.edu.actor.vo.request.UpdateVO;

@Controller
public class ActorController {

	@Autowired
	private ActorService actorService;

	@GetMapping("/actor/view")
	public String viewActorListPage(Model model) {
		List<ActorVO> actorList = this.actorService.readAllActor();
		model.addAttribute("actorList", actorList);

		return "actor/list";
	}

	@GetMapping("/actor/view/{actorId}")
	public String viewActorPage(@PathVariable String actorId, Model model) {
		ActorVO actor = this.actorService.readActorByActorId(actorId);
		model.addAttribute("actor", actor);

		return "actor/view" + actorId;
	}

	@GetMapping("/actor/insert")
	public String viewInsertPage() {
		return "actor/insert";
	}

	@PostMapping("/actor/insert")
	public String doInsertPage(InsertVO insertVO) {
		boolean insertSuccess = this.actorService.InsertActor(insertVO);
		if (insertSuccess) {
			return "redirect:/";
		} else {
			return "error/404";
		}
	}

	@GetMapping("/actor/update/{actorId}")
	public String viewUpdatePage(@PathVariable String actorId , Model model) {
		ActorVO actor = this.actorService.readActorByActorId(actorId);
		model.addAttribute("actor", actor);
		return "actor/update";
	}
	
	@PostMapping("/actor/update/{actorId}")
	public String doUpdate(@PathVariable String actorId, UpdateVO updateVO) {
		updateVO.setActorId(actorId);
		boolean updateSuccess = this.actorService.updateActorByActorId(updateVO);
		return "";
	}

	@PostMapping("/actor/delete/{actorId}")
	public String doDelete(@PathVariable String actorId) {
		boolean deleteSuccess = this.actorService.deleteActorByActorId(actorId);
		return "";
	}
}
