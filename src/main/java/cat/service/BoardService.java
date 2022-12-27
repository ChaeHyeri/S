package cat.service;

import cat.dto.BoardDTO;
import cat.dto.SearchCondition;

import java.util.List;
import java.util.Map;

public interface BoardService {
    int getCount() throws Exception;

    int viewCount(Integer bno) throws Exception;

    int remove(Integer bno, String writer) throws Exception;

    int write(BoardDTO boardDto) throws Exception;

    List<BoardDTO> getList() throws Exception;

    BoardDTO read(Integer bno) throws Exception;

    List<BoardDTO> getPage(Map map) throws Exception;

    int modify(BoardDTO boardDto) throws Exception;

    List<BoardDTO> getSearchResultPage(SearchCondition sc) throws Exception;

    int getSearchResultCnt(SearchCondition sc) throws Exception;


}

