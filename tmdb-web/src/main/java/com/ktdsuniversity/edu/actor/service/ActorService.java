package com.ktdsuniversity.edu.actor.service;

import java.util.List;

import com.ktdsuniversity.edu.actor.vo.ActorVO;
import com.ktdsuniversity.edu.actor.vo.request.InsertVO;
import com.ktdsuniversity.edu.actor.vo.request.UpdateVO;

public interface ActorService {

	List<ActorVO> readAllActor();

	List<ActorVO> readActorByMovieId(String movieId);

	ActorVO readActorByActorId(String movieId);

	boolean InsertActor(InsertVO insertVO);

	boolean updateActorByActorId(UpdateVO updateVO);

	boolean deleteActorByActorId(String actorId);

}
