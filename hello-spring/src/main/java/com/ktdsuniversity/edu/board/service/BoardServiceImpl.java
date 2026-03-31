package com.ktdsuniversity.edu.board.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;
import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.request.UploadVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private FilesDao filesDao;
	
	@Override
	public SearchResultVO getAllBoard() {
		List<BoardVO> list= this.boardDao.getAllBoard();
		int count = this.boardDao.getBoardCount();
		
		SearchResultVO result = new SearchResultVO();
		result.setResult(list);
		result.setCount(count);
		
		return result;
	}


	@Override
	public boolean createBoard(WriteVO writeVO) {
		int  isSuccess = this.boardDao.createBoard(writeVO);
		
		// 첨부 파일 업로드
	      List<MultipartFile> attachFiles = writeVO.getAttachFile();
	      if (attachFiles != null && attachFiles.size() > 0) {
	         for (int i = 0; i < attachFiles.size(); i++) {
	            File storeFile = new File("C:\\dev_programs\\test", attachFiles.get(i).getOriginalFilename());
	            // C:\\uploadfiles 폴더가 없으면 생성해라
	            if (!storeFile.getParentFile().exists()) {
	               storeFile.getParentFile().mkdirs();
	            }
	            try {
	               attachFiles.get(i).transferTo(storeFile);
	               // FILES 테이블에 첨부파일 데이터를 INSERT
	               UploadVO uploadVO = new UploadVO();
	               String fileName = attachFiles.get(i).getOriginalFilename();
	               String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
	               uploadVO.setFileNum(i + 1);
	               uploadVO.setFileGroupId(writeVO.getId());
	               uploadVO.setObfuscateName(fileName);
	               uploadVO.setDisplayName(fileName);
	               uploadVO.setExtendName(ext);
	               uploadVO.setFileLength(storeFile.length());
	               uploadVO.setFilePath(storeFile.getAbsolutePath());
	               this.filesDao.insertAttachFile(uploadVO);
	            } catch (IllegalStateException | IOException e) {
	               e.printStackTrace();
	            }
	         }
	      }
	      
	      return isSuccess == 1;
	   }


	@Override
	public BoardVO readBoardById(String boardId, ReadType readType) {
//		int updateCount = 0;
//		if (readType == ReadType.VIEW) {
//			updateCount = this.boardDao.updateBoardViewCount(boardId);			
//		}
//		if (updateCount == 0) {
//			return null;
//		}
		BoardVO boardById = this.boardDao.selectBoardById(boardId);
		
		
		return boardById;
	}


	@Override
	public boolean updateOneArticle(UpdateVO updateVO) {
		int isSuccess = this.boardDao.updateOneBoardById(updateVO);
		return isSuccess == 1;
	}


}
