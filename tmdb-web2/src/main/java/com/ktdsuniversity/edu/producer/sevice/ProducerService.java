package com.ktdsuniversity.edu.producer.sevice;

import java.util.List;

import com.ktdsuniversity.edu.producer.vo.ProducerVO;
import com.ktdsuniversity.edu.producer.vo.request.InsertVO;

public interface ProducerService {

	List<ProducerVO> readAllProducer();

	boolean insertProducer(InsertVO insertVO);

}
