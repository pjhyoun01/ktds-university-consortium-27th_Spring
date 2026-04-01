package com.ktdsuniversity.edu.board.service;

import java.io.File;
import java.util.List;
import com.ktdsuniversity.edu.utils.MultiFileHandler;
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

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDao;

	@Autowired
	private FilesDao filesDao;

	@Autowired
	private MultiFileHandler multiFileHandler;

	@Override
	public SearchResultVO getAllBoard() {
		List<BoardVO> list = this.boardDao.getAllBoard();
		int count = this.boardDao.getBoardCount();

		SearchResultVO result = new SearchResultVO();
		result.setResult(list);
		result.setCount(count);

		return result;
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
	public boolean createBoard(WriteVO writeVO) {
		int insertScuccessCount = this.boardDao.createBoard(writeVO);

		System.out.println("writeVO: " + writeVO.getAttachFile());
		// 첨부 파일 업로드
		List<MultipartFile> attachFiles = writeVO.getAttachFile();
		this.multiFileHandler.upload(attachFiles, writeVO.getId());

		return insertScuccessCount == 1;
	}

	@Override
	public boolean updateOneArticle(UpdateVO updateVO) {
		int updateScuccessCount = this.boardDao.updateOneBoardById(updateVO);

		if (updateVO.getDeleteFileNum() != null && updateVO.getDeleteFileNum().size() >= 0) {

			List<String> deleteTargets = this.filesDao.selectFilePathByFileGroupIdAndFileNums(updateVO);
			for (String target : deleteTargets) {
				new File(target).delete();
			}

			this.filesDao.delectFilePathByFileGroupIdAndFileNums(updateVO);
		}
		List<MultipartFile> attachFiles = updateVO.getAttachFile();
		this.multiFileHandler.upload(attachFiles, updateVO.getId());

		return updateScuccessCount == 1;
	}

	@Override
	public boolean deleteOneBoardById(String boardId) {
		int deleteSuccessCount = this.boardDao.deleteBoardById(boardId);
		
		List<String> filePathByGroupId = this.filesDao.selectFilePathByGroupId(boardId);
		for (String filePath : filePathByGroupId) {
			new File(filePath).delete();
		}
		
		this.filesDao.delectFilePathByGroupId(filePathByGroupId);
		
		return deleteSuccessCount == 1;
	}

}
