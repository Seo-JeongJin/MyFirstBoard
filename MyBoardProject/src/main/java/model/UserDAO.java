package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DButil;

public class UserDAO {
	/**
     * 회원가입 메서드
     * -----------------
     * users 테이블에 새로운 사용자 정보를 추가한다.
     * @param user User 객체 (userid, userpwd, username 필수)
     * @return 성공 시 true, 실패 시 false
     */
    public boolean register(UserDTO user) {
        String sql = "INSERT INTO users (userid, userpwd, username) VALUES (?, ?, ?)";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUserid());
            pstmt.setString(2, user.getUserpwd());
            pstmt.setString(3, user.getUsername());
            int result = pstmt.executeUpdate();

            return result == 1; // 한 행이 추가되면 true

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 로그인 검증 메서드
     * -----------------
     * DB에서 userid, userpwd가 일치하는 사용자가 있는지 확인.
     * @param userid 입력된 ID
     * @param userpwd 입력된 비밀번호
     * @return 로그인 성공 시 User 객체, 실패 시 null
     */
    public UserDTO login(String userid, String userpwd) {
        String sql = "SELECT * FROM users WHERE userid=? AND userpwd=?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userid);
            pstmt.setString(2, userpwd);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UserDTO user =  new UserDTO (
                    rs.getInt("id"),
                    rs.getString("userid"),
                    rs.getString("userpwd"),
                    rs.getString("username"),
                    rs.getString("created_at")
                );
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ID로 사용자 정보 조회
     * -----------------
     * 특정 ID(userid)에 해당하는 사용자의 전체 정보를 반환.
     */
    public UserDTO getUserById(String userid) {
        String sql = "SELECT * FROM users WHERE userid=?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userid);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UserDTO user = new UserDTO (
                    rs.getInt("id"),
                    rs.getString("userid"),
                    rs.getString("userpwd"),
                    rs.getString("username"),
                    rs.getString("created_at")
                );
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}