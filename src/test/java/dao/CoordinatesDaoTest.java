package dao;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class CoordinatesDaoTest {

    @Test
    public void testsReadToDatabase() throws IOException {

        //given

        TestHelper th = new TestHelper();

        try {
            CoordinatesDao cd = new CoordinatesDao();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/imgw_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8&allowPublicKeyRetrieval=true", "root", "Mandok01");


            //when
            cd.readToDatabase(con);

            Statement stmt = con.createStatement();
            String query = "SELECT * FROM t_coordinates";

            String result = "";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                result += rs.getString(1) + " : " + rs.getString(2) + "\n";
            }

            //then
            assertEquals(th.txtToString("txt/coordinates-mysql-to-beans-test.txt"), result);

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
 }

