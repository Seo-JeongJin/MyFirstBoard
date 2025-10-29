package model;

public class UserDTO {
	
	private int id;             // 고유 ID (AUTO_INCREMENT)
    private String userid;      // 로그인용 사용자 ID
    private String userpwd;     // 사용자 비밀번호
    private String username;    // 사용자 이름
    private String createdAt;   // 가입 일자 (문자열로 처리, 필요시 Timestamp 로 변경 가능)
	
    public UserDTO() {
    	
    }
    
    public UserDTO (int id, String userid, String userpwd, String username, String createdAt) {
        this.id = id;
        this.userid = userid;
        this.userpwd = userpwd;
        this.username = username;
        this.createdAt = createdAt;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpwd() {
        return userpwd;
    }
    
    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
}
