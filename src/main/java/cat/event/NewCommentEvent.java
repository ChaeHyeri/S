package cat.event;

import org.springframework.context.ApplicationEvent;

import java.util.Objects;

public class NewCommentEvent {

    private Integer seq;
    private String writer;
    private String commenter;
    private int bno;
    private String comment;
    private Integer like;


    public NewCommentEvent(){}
    public NewCommentEvent(int bno) {
        this.bno = bno;
    }

    public NewCommentEvent(Integer seq, String writer, String commenter, int bno, String comment, Integer like) {
        this.seq = seq;
        this.writer = writer;
        this.commenter = commenter;
        this.bno = bno;
        this.comment = comment;
        this.like = like;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewCommentEvent)) return false;
        NewCommentEvent that = (NewCommentEvent) o;
        return bno == that.bno && Objects.equals(seq, that.seq) && Objects.equals(writer, that.writer) && Objects.equals(commenter, that.commenter) && Objects.equals(comment, that.comment) && Objects.equals(like, that.like);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, writer, commenter, bno, comment, like);
    }

    @Override
    public String toString() {
        return "NewCommentEvent{" +
                "seq=" + seq +
                ", writer='" + writer + '\'' +
                ", commenter='" + commenter + '\'' +
                ", bno=" + bno +
                ", comment='" + comment + '\'' +
                ", like=" + like +
                '}';
    }
}
