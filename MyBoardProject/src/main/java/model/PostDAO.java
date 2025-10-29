package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DButil;

public class PostDAO {
	
	/**
     * 게시글 전체 조회 메서드
     * --------------------------------------------------------
     * 모든 게시글을 DB에서 꺼내와 작성일 내림차순(최신순)으로 반환한다.
     * @return 게시글 리스트 (List<Post>)
     */
    public List<PostDTO> getAllPosts() {
        List<PostDTO> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts ORDER BY created_at DESC";

        try (
            Connection conn = DButil.getConnection();     // DB 연결 객체 생성
            PreparedStatement pstmt = conn.prepareStatement(sql); // SQL 준비
            ResultSet rs = pstmt.executeQuery();          // 쿼리 실행
        ) {
            // 쿼리 결과(ResultSet)를 순회하면서 각 행(row)을 Post 객체로 매핑
            while (rs.next()) {
                PostDTO p = new PostDTO (
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("author"),
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

    /**
     * 게시글 등록 메서드
     * --------------------------------------------------------
     * 새로운 게시글을 DB에 추가한다.
     * @param post Post 객체 (title, content, author, userid 필수)
     * @return true = 등록 성공 / false = 등록 실패
     */
    public boolean insertPost(PostDTO post) {
        String sql = "INSERT INTO posts (title, content, author, userid) VALUES (?, ?, ?, ?)";

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

    /**
     * 게시글 상세 조회 메서드
     * --------------------------------------------------------
     * 특정 게시글 ID로 해당 게시글 정보를 가져온다.
     * @param id 게시글 ID
     * @return Post 객체 / 존재하지 않으면 null
     */
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
                return new PostDTO (
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("author"),
                    rs.getString("userid"),
                    rs.getString("created_at")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 존재하지 않으면 null 반환
    }

    /**
     * 게시글 수정 메서드 (작성자 검증 포함)
     * --------------------------------------------------------
     * 주어진 게시글 ID에 대해 title과 content를 수정한다.
     * 단, 수정 요청자의 userid가 실제 작성자의 userid와 일치해야만 가능하다.
     *
     * @param post 수정할 게시글 객체 (id, title, content, userid 포함)
     * @return 메시지(String)
     *         "수정이 완료되었습니다."  : 수정 성공
     *         "권한이 없습니다."       : 작성자 불일치
     *         "존재하지 않는 게시글입니다." : 해당 id의 게시글 없음
     *         "DB 오류 발생"            : DB 예외 발생
     */
    public String updatePost(PostDTO post) {
        // 1️⃣ 먼저 작성자 일치 여부를 확인하기 위해 해당 글 조회
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
                return (result == 1) ? "게시글이 수정되었습니다." : "DB 오류 발생";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "DB 오류 발생";
        }
    }

    /**
     * 게시글 삭제 메서드 (작성자 검증 포함)
     * --------------------------------------------------------
     * 주어진 게시글 ID를 삭제한다.
     * 단, 삭제 요청자의 userid가 실제 작성자의 userid와 일치해야만 가능하다.
     *
     * @param id 삭제할 게시글의 ID
     * @param userid 삭제를 시도하는 사용자의 ID
     * @return 메시지(String)
     *         "삭제가 완료되었습니다."   : 삭제 성공
     *         "권한이 없습니다."       : 작성자 불일치
     *         "존재하지 않는 게시글입니다." : 해당 id의 게시글 없음
     *         "DB 오류 발생"            : DB 예외 발생
     */
    public String deletePost(int id, String userid) {
        String selectSql = "SELECT userid FROM posts WHERE id=?";
        String deleteSql = "DELETE FROM posts WHERE id=? AND userid=?";

        try (Connection conn = DButil.getConnection()) {

            // 1️⃣ 해당 글 존재 여부와 작성자 확인
            try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                String userId = rs.getString("userid");
                if (!userId.equals(userid)) {
                    // 작성자 불일치 시 삭제 거부
                    return "권한이 없습니다.";
                }
            }

            // 2️⃣ 작성자 일치 시 삭제 실행
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, userid);
                int result = pstmt.executeUpdate();
                return (result == 1) ? "게시글이 삭제되었습니다." : "DB 오류 발생";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "DB 오류 발생";
        }
    }
}
