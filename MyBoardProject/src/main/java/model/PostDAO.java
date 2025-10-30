package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DButil;

public class PostDAO {

    // 게시글 전체 조회 메서드
    public List<PostDTO> getAllPosts() {
    	// 게시글 목록 저장 빈 리스트
        List<PostDTO> posts = new ArrayList<>();
        // 실행할 sql 문 정의
        String sql = "SELECT * FROM posts ORDER BY created_at DESC";

        try (
            Connection conn = DButil.getConnection();     // DB 연결 객체 생성
            PreparedStatement pstmt = conn.prepareStatement(sql); // SQL 준비
            ResultSet rs = pstmt.executeQuery();          // 쿼리 실행 후 결과 담기
        ) {
            // 쿼리 결과를 순회하면서 각 행을 Post 객체로 매핑
            while (rs.next()) {
                PostDTO p = new PostDTO (
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("writer"),
                    rs.getString("userid"),
                    rs.getString("created_at")
                );
                posts.add(p); // 리스트에 추가
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    
    // 게시글 등록 메서드
    // 새로운 게시글을 DB에 추가한다.
    public boolean insertPost(PostDTO post) {
        String sql = "INSERT INTO posts (title, content, writer, userid) VALUES (?, ?, ?, ?)";

        try (
            Connection conn = DButil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            // ? 에 실제 데이터 바인딩
            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.setString(3, post.getAuthor());
            pstmt.setString(4, post.getUserid());

            int result = pstmt.executeUpdate(); // 쿼리 실행
            return result == 1; // 한 행이 추가되면 성공

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

  
    // 게시글 상세 조회 메서드
    // 특정 게시글 ID로 해당 게시글 정보를 가져옴
    public PostDTO getPostById(int id) {
        String sql = "SELECT * FROM posts WHERE id=?";
        try (
            Connection conn = DButil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            // 결과가 존재하면 Post 객체로 변환하여 반환
            if (rs.next()) {
                PostDTO post = new PostDTO (
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("writer"),
                    rs.getString("userid"),
                    rs.getString("created_at")
                );
                return post;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 존재하지 않으면 null 반환
    }

    // 게시글 수정 메서드 (작성자 검증 포함)
    // 주어진 게시글 ID에 대해 title 과 content 를 수정
    // 수정 요청자의 userid가 실제 작성자의 userid와 일치해야만 가능
    public String updatePost(PostDTO post) {
        // 먼저 작성자 일치 여부를 확인하기 위해 해당 글 조회
        String selectSql = "SELECT userid FROM posts WHERE id=?";
        String updateSql = "UPDATE posts SET title=?, content=? WHERE id=? AND userid=?";

        try (Connection conn = DButil.getConnection()) {
            // 글 존재 여부 + 작성자 확인
            try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
                pstmt.setInt(1, post.getId());
                ResultSet rs = pstmt.executeQuery();

                if (!rs.next()) {
                    return "존재하지 않는 게시글입니다.";
                }

                String userId = rs.getString("userid");
                if (!userId.equals(post.getUserid())) {
                    // 작성자 불일치 → 수정 불가
                    return "권한이 없습니다.";
                }
            }

            // 작성자 일치 시, 수정 쿼리 실행
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setString(1, post.getTitle());
                pstmt.setString(2, post.getContent());
                pstmt.setInt(3, post.getId());
                pstmt.setString(4, post.getUserid());

                int result = pstmt.executeUpdate();
                return (result == 1) ? "게시글 수정 완료" : "DB 오류 발생";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "DB 오류 발생";
        }
    }

    
    // 게시글 삭제 메서드 (작성자 검증 포함)
    // 주어진 게시글 ID를 삭제한다.
    // 삭제 요청자의 userid가 실제 작성자의 userid와 일치해야만 가능
    public String deletePost(int id, String userid) {
        String selectSql = "SELECT userid FROM posts WHERE id=?";
        String deleteSql = "DELETE FROM posts WHERE id=? AND userid=?";

        try (Connection conn = DButil.getConnection()) {

            // 해당 글 존재 여부와 작성자 확인
            try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                
                if (!rs.next() ) { // 레코드 존재 확인 여부
                	return "존재하지 않는 게시글입니다.";
                }
                
                // next() 하지 않았는데 값을 꺼낼 수 없기 때문에 위 제어문 추가
                String userId = rs.getString("userid");
                if (!userId.equals(userid)) {
                    // 작성자 불일치 시 삭제 거부
                    return "권한이 없습니다.";
                }
            }

            // 작성자 일치 시 삭제 실행
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, userid);
                int result = pstmt.executeUpdate();
                return (result == 1) ? "게시글 삭제" : "DB 오류 발생";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "DB 오류 발생";
        }
    }
}
