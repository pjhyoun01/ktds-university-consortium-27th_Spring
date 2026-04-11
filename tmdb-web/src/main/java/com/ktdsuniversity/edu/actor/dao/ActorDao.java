package com.ktdsuniversity.edu.actor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.actor.vo.ActorVO;
import com.ktdsuniversity.edu.actor.vo.request.InsertVO;
import com.ktdsuniversity.edu.actor.vo.request.UpdateVO;

@Mapper
public interface ActorDao {

	List<ActorVO> seleteAllActor();

	List<ActorVO> selectActorByMovieId(String movieId);

	ActorVO selectActorByActorId(String movieId);

	int insertActor(InsertVO insertVO);

	int updateActorByActorId(UpdateVO updateVO);

	int deleteActorByActorId(String actorId);

}
