package com.ktdsuniversity.edu.producer.sevice;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.common.handler.FileHandler;
import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.producer.dao.ProducerDao;
import com.ktdsuniversity.edu.producer.vo.ProducerVO;
import com.ktdsuniversity.edu.producer.vo.request.InsertVO;

@Service
public class ProducerServiceImpl implements ProducerService {

	@Autowired
	private ProducerDao producerDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private FileHandler fileHandler;

	@Override
	public List<ProducerVO> readAllProducer() {
		List<ProducerVO> producerList = this.producerDao.selectAllProducer();
		return producerList;
	}

	@Override
	public boolean insertProducer(InsertVO insertVO) {
//		if (insertVO.getFileGroupId() != null) {
//			String path = this.fileDao.selectPathByFileGroupId(insertVO.getFileGroupId());
//			new File(path).delete();
//
//			this.fileDao.deleteFileByFileGroupId(insertVO.getFileGroupId());
//
//			this.fileHandler.uploadOneFile(insertVO.getFile(), insertVO.getFileGroupId());
//		} else {
			String fileGroupId = this.fileHandler.uploadOneFile(insertVO.getFile());
			insertVO.setFileGroupId(fileGroupId);
//		}

		int insertSuccessCount = this.producerDao.insertProducer(insertVO);
		return insertSuccessCount == 1;
	}

}
