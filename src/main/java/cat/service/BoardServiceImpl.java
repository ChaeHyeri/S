package cat.service;

import cat.dao.BoardDAO;
import cat.dto.BoardDTO;
import cat.dto.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardDAO boardDao;

    @Override
    public int getCount() throws Exception {
        return boardDao.count();
    }

    @Override
    public int viewCount(Integer bno) throws Exception {
        return boardDao.increaseViewCnt(bno);
    }

    @Override
    public int remove(Integer bno, String writer) throws Exception {
        return boardDao.delete(bno, writer);
    }

    @Override
    public int write(BoardDTO boardDto) throws Exception {
        return boardDao.insert(boardDto);
    }

    @Override
    public List<BoardDTO> getList() throws Exception {
        return boardDao.selectAll();
    }

    @Override
    public BoardDTO read(Integer bno) throws Exception {
        //        boardDao.increaseViewCnt(board_num);

        return boardDao.select(bno);
    }

    @Override
    public List<BoardDTO> getPage(Map map) throws Exception {
        return boardDao.selectPage(map);
    }

    @Override
    public int modify(BoardDTO boardDto) throws Exception {
        return boardDao.update(boardDto);
    }

    @Override
    public List<BoardDTO> getSearchResultPage(SearchCondition sc) throws Exception {
        return boardDao.searchSelectPage(sc);
    }

    @Override
    public int getSearchResultCnt(SearchCondition sc) throws Exception {
        return boardDao.searchResultCnt(sc);
    }




}


