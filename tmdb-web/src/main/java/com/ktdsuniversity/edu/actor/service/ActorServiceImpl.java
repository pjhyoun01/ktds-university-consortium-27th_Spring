package com.ktdsuniversity.edu.actor.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.actor.dao.ActorDao;
import com.ktdsuniversity.edu.actor.vo.ActorVO;
import com.ktdsuniversity.edu.actor.vo.request.InsertVO;
import com.ktdsuniversity.edu.actor.vo.request.UpdateVO;
import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.utils.FileHandler;

@Service
public class ActorServiceImpl implements ActorService {

	@Autowired
	private ActorDao actorDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private FileHandler fileHandler;

	@Override
	public List<ActorVO> readAllActor() {
		List<ActorVO> actorList = this.actorDao.seleteAllActor();
		return actorList;
	}

	@Override
	public List<ActorVO> readActorByMovieId(String movieId) {
		List<ActorVO> actorList = this.actorDao.selectActorByMovieId(movieId);
		return actorList;
	}

	@Override
	public ActorVO readActorByActorId(String movieId) {
		ActorVO actor = this.actorDao.selectActorByActorId(movieId);
		return actor;
	}

	@Override
	public boolean InsertActor(InsertVO insertVO) {
		String fileGroupId = this.fileHandler.uploadOneFile(insertVO.getFile());
		insertVO.setFileGroupId(fileGroupId);

		String path = this.fileDao.selectPathByFileGroupId(fileGroupId);
		insertVO.setActorProfileUrl(path);

		int insertSuccessCount = this.actorDao.insertActor(insertVO);
		return insertSuccessCount == 1;
	}

	@Override
	public boolean updateActorByActorId(UpdateVO updateVO) {
		if (updateVO.getFileGroupId() != null) {
			String path = this.fileDao.selectPathByFileGroupId(updateVO.getFileGroupId());
			new File(path).delete();

			this.fileDao.deleteFileByFileGroupId(updateVO.getFileGroupId());

			this.fileHandler.uploadOneFile(updateVO.getFile(), updateVO.getFileGroupId());
		} else {
			String fileGroupId = this.fileHandler.uploadOneFile(updateVO.getFile());
			updateVO.setFileGroupId(fileGroupId);
		}

		String path = this.fileDao.selectPathByFileGroupId(updateVO.getFileGroupId());
		updateVO.setActorProfileUrl(path);

		int updateSuccessCount = this.actorDao.updateActorByActorId(updateVO);
		return updateSuccessCount == 1;
	}

	@Override
	public boolean deleteActorByActorId(String actorId) {
		int deleteSuccessCount = this.actorDao.deleteActorByActorId(actorId);
		return deleteSuccessCount == 1;
	}

}
