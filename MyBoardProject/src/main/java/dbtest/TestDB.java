package dbtest;

import java.sql.Connection;
import util.DButil;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection conn = DButil.getConnection();
            if(conn != null) {
                System.out.println("DB 연결 성공");
                conn.close(); // 연결 닫기
            } else {
                System.out.println("DB 연결 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
