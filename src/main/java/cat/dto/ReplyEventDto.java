package cat.dto;

import java.util.Objects;

public class ReplyEventDto {
    private int seq;
    private String writer;
    private String commenter;
    private Integer bno;
    private String comment;
    private int like;

    public ReplyEventDto(){}
    public ReplyEventDto(int seq, String writer, String commenter, Integer bno, String comment, int like) {
        this.seq = seq;
        this.writer = writer;
        this.commenter = commenter;
        this.bno = bno;
        this.comment = comment;
        this.like = like;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getTargetId() {
        return writer;
    }

    public void setTargetId(String targetId) {
        this.writer = targetId;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public Integer getBno() {
        return bno;
    }

    public void setBno(Integer bno) {
        this.bno = bno;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReplyEventDto)) return false;
        ReplyEventDto that = (ReplyEventDto) o;
        return seq == that.seq && like == that.like && Objects.equals(writer, that.writer) && Objects.equals(commenter, that.commenter) && Objects.equals(bno, that.bno) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, writer, commenter, bno, comment, like);
    }

    @Override
    public String toString() {
        return "ReplyEventDto{" +
                "seq=" + seq +
                ", writer='" + writer + '\'' +
                ", commenter='" + commenter + '\'' +
                ", bno=" + bno +
                ", comment='" + comment + '\'' +
                ", like=" + like +
                '}';
    }
}
