package cat.dao;

import cat.dto.BoardDTO;
import cat.dto.SearchCondition;

import java.util.List;
import java.util.Map;

public interface BoardDAO {
    BoardDTO select(Integer bno) throws Exception;
    int delete(Integer bno, String writer) throws Exception;
    int insert(BoardDTO boardDto) throws Exception;

    List<BoardDTO> selectPage(Map map) throws Exception;

    List<BoardDTO> searchSelectPage(SearchCondition sc) throws Exception;

    List<BoardDTO> selectAll() throws Exception;
    int deleteAll() throws Exception;
    int count() throws Exception;
    int update(BoardDTO dto) throws Exception;

    int increaseViewCnt(Integer bno) throws Exception;

    int searchResultCnt(SearchCondition sc) throws Exception;


    int updateCommentCnt(Integer bno, int cnt);
}
