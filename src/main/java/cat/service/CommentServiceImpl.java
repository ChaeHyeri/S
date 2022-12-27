package cat.service;

import cat.dao.BoardDAO;
import cat.dao.CommentDAO;
import cat.dao.EventDAO;
import cat.dto.CommentDTO;
import cat.event.NewCommentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    EventDAO eventDAO;

    BoardDAO boardDao;

    CommentDAO commentDao;

    @Autowired // 생성자주입
    public CommentServiceImpl(CommentDAO commentDao, BoardDAO boardDao) {
        this.commentDao = commentDao;
        this.boardDao = boardDao;
    }

    @Override
    public int getCount(Integer bno) throws Exception {
        return commentDao.count(bno);
    }

    @Override

    @Transactional(rollbackFor = Exception.class) // 예외발생 시 rollback
    public int remove(Integer cno, Integer bno, String commenter) throws Exception {
        // 댓글을 삭제하면 댓글수 -1 처리 및 해당 댓글을 삭제(트랜젝션)
        int rowCnt = boardDao.updateCommentCnt(bno, -1);
        System.out.println("updateCommentCnt - rowCnt = " + rowCnt);
        rowCnt = commentDao.delete(cno, commenter);
        System.out.println("rowCnt = " + rowCnt);
        return rowCnt;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int write(CommentDTO commentDto) throws Exception {
        boardDao.updateCommentCnt(commentDto.getBno(), 1);

        String writer = boardDao.select(commentDto.getBno()).getWriter();
        eventDAO.saveNewCommentAlarm(writer,commentDto);

        publisher.publishEvent(new NewCommentEvent(commentDto.getBno()));

        return commentDao.insert(commentDto);
    }

    @Override
    public List<CommentDTO> getList(Integer bno) throws Exception {
//        throw new Exception("test");
        return commentDao.selectAll(bno);
    }

    @Override
    public CommentDTO read(Integer cno) throws Exception {
        return commentDao.select(cno);
    }

    @Override
    public int modify(CommentDTO commentDto) throws Exception {
        return commentDao.update(commentDto);
    }
}

