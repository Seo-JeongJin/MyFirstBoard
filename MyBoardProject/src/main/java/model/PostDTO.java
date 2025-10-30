package model;

public class PostDTO {
	
	private int id;             // 게시글 고유 ID (AUTO_INCREMENT)
    private String title;       // 게시글 제목
    private String content;     // 게시글 내용
    private String writer;      // 작성자 이름
    private String userid;      // 작성자의 로그인 ID (권한 검사용)
    private String createdAt;   // 작성 시간 (문자열)
	
    public PostDTO() {
    	
    }
    
    public PostDTO (int id, String title, String content, String writer, String userid, String createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.userid = userid;
        this.createdAt = createdAt;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return writer;
    }
    
    public void setAuthor(String writer) {
        this.writer = writer;
    }

    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
	
}
