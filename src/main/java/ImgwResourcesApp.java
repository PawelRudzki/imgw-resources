import dao.ClimaticRecordDao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ImgwResourcesApp {
    public static void main(String args[]) {


        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/imgw_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8&allowPublicKeyRetrieval=true", "root", "Mandok01");

//            CoordinatesDao cd = new CoordinatesDao();
//            cd.readToDatabase(con);


//            StationDao sd = new StationDao();
//            sd.readToDatabase(con);


            ClimaticRecordDao crd = new ClimaticRecordDao();
//            crd.pullData();
            crd.readToDatabase(con);

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}



