package com.ktdsuniversity.edu.producer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.producer.vo.ProducerVO;
import com.ktdsuniversity.edu.producer.vo.request.InsertVO;

@Mapper
public interface ProducerDao {

	List<ProducerVO> selectAllProducer();

	int insertProducer(InsertVO insertVO);

}
