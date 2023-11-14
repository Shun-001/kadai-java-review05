import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // MySQLのパスワードを入力してください
        final String PASSWORD = "yourPassword";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // localhost内のkadaidbに接続
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    PASSWORD);

            String sql = "SELECT * FROM person WHERE id = ?";
            // プリペアドステートメントを使用
            pstmt = con.prepareStatement(sql);

            System.out.print("検索キーワードを入力してください > ");
            int input = keyInId(); // keyInIdメソッドでキーボード入力を受け付ける

            // setIntメソッドで値をセット
            pstmt.setInt(1, input);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(name);
                System.out.println(age);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        }

    }

    private static int keyInId() {
        int result = 0;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            result = Integer.parseInt(key.readLine());
        } catch (IOException e) {
        }
        return result;
    }

}
