package com.ktdsuniversity.edu.movie.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.movie.dao.MovieDao;
import com.ktdsuniversity.edu.movie.vo.MovieVO;
import com.ktdsuniversity.edu.movie.vo.request.InsertVO;
import com.ktdsuniversity.edu.movie.vo.request.UpdateVO;
import com.ktdsuniversity.edu.movie.vo.response.MovieListVO;
import com.ktdsuniversity.edu.movie.vo.response.OneMovieVO;
import com.ktdsuniversity.edu.utils.FileHandler;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieDao movieDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private FileHandler fileHandler;

	@Override
	public List<MovieListVO> readAllMovie() {
		List<MovieListVO> movieList = this.movieDao.selectAllMovie();

		return movieList;
	}

	@Override
	public OneMovieVO readMovieById(String movieId) {
		OneMovieVO oneMovieById = this.movieDao.selectMovieById(movieId);

		return oneMovieById;
	}

	@Override
	public boolean insertMovie(InsertVO insertVO) {
		String filegroupId = this.fileHandler.uploadOneFile(insertVO.getFile());
		insertVO.setFileGroupId(filegroupId);

		String posterUrl = this.fileDao.selectPathByFileGroupId(insertVO.getFileGroupId());
		insertVO.setPosterUrl(posterUrl);

		int insertSuccessCount = this.movieDao.insertMovie(insertVO);

		return insertSuccessCount == 1;
	}

	@Override
	public boolean updateMovieById(UpdateVO updateVO) {
		if (updateVO.getFileGroupId() != null) {
			String path = this.fileDao.selectPathByFileGroupId(updateVO.getFileGroupId());
			new File(path).delete();

			this.fileDao.deleteFileByFileGroupId(updateVO.getFileGroupId());

			this.fileHandler.uploadOneFile(updateVO.getFile(), updateVO.getFileGroupId());
		} else {
			String filegroupId = this.fileHandler.uploadOneFile(updateVO.getFile());
			updateVO.setFileGroupId(filegroupId);
		}

		String posterUrl = this.fileDao.selectPathByFileGroupId(updateVO.getFileGroupId());
		updateVO.setPosterUrl(posterUrl);

		int updateSuccessCount = this.movieDao.updateMovieByMovieId(updateVO);

		return updateSuccessCount == 1;
	}

	@Override
	public boolean deleteMovieById(String movieId) {
		MovieVO movie = this.readMovieById(movieId);
		if (movie != null) {
			this.fileDao.deleteFileByFileGroupId(movie.getFileGroupId());
		}

		int deleteSuccessCount = this.movieDao.deleteMovieById(movieId);
		return deleteSuccessCount == 1;
	}
}
