import dao.StationDao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ImgwResourcesApp {
    public static void main(String args[]) {


        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/imgw_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8&allowPublicKeyRetrieval=true", "root", "Mandok01");

            StationDao sd = new StationDao();

            System.out.println(sd.readToDatabase(con));

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}



