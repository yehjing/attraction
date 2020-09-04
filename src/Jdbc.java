import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.*;

public class Jdbc {
    public static void main(String[] args) {
        FileDownload file = new FileDownload();

        List<AttractionVo> data = file.getArrList("https://gis.taiwan.net.tw/XMLReleaseALL_public/scenic_spot_C_f.json");

//        System.out.println("data => " + data);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded!");

            Connection conn = getConnection(); // 建立連線

//            Statement stmt = conn.createStatement(); // 建立 Statement
            // stmt.executeUpdate() // 新刪修
//            ResultSet resultSet = stmt.executeQuery("SELECT * FROM attraction "); // => 執行一句 SQL 語法 查

            String sql = "insert into attraction (info_uuid,name,intro,tel,address,region,town,travel_info,open_time,web_site,zipcode,ticket_info,px,py) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql); // 建立 PreparedStatement

            for (AttractionVo item : data) {
                pstmt.setString(1, item.getId());
                pstmt.setString(2, item.getName());
                pstmt.setString(3,item.getDescription());
                pstmt.setString(4, item.getTel());
                pstmt.setString(5, item.getAdd());
                pstmt.setString(6, item.getRegion());
                pstmt.setString(7, item.getTown());
                pstmt.setString(8, item.getTravellinginfo());
                pstmt.setString(9, item.getOpentime());
                pstmt.setString(10, item.getWebsite());
                pstmt.setInt(11, item.getZipcode());
                pstmt.setString(12, item.getTicketinfo());
                pstmt.setBigDecimal(13, item.getPx());
                pstmt.setBigDecimal(14, item.getPy());

                pstmt.executeUpdate(); // 新修
            }

//            while (resultSet.next()) { // 查
//                System.out.println(resultSet.getString("Name"));
//            }

            conn.close(); // 關閉連線
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驅動程式類別");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL ERR!");
            e.printStackTrace();
        }
    }

    //設定檔
    private static Connection getConnection() throws SQLException {
        String serverName = "localhost";
        String database = "db01";
        String url = "jdbc:mysql://" + serverName + "/" + database + "?serverTimezone=GMT%2B8";
        // 帳號和密碼
        String user = "db01";
        String password = "aa123456AA";
        return DriverManager.getConnection(url, user, password);
    }
}
