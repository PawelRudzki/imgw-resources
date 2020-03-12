package dao;

import beans.ClimaticRecordBean;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import utils.Unzip;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;


public class ClimaticRecordDaoTest {

    @Test
    public void testsReadToDatabase() throws IOException {

        //given

        TestHelper th = new TestHelper();

        try {
            ClimaticRecordDao crd = new ClimaticRecordDao();

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/imgw_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8&allowPublicKeyRetrieval=true", "root", "Mandok01");


            //when
            th.unzipExampleRecords();
            crd.readToDatabase(con);

            Statement stmt = con.createStatement();
            String query = "SELECT * FROM t_climatic_records";

            String result = "";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                result += rs.getString(1) + " : " + rs.getString(2) + "\n";
            }

            //then
            assertEquals(th.txtToString("txt/climatic-records-mysql-to-beans-test.txt"), result);

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        FileUtils.cleanDirectory(new File("output/"));

    }


    @Test
    public void testsDataReadedToClimaticRecordBeans() throws Exception {

        //given
        TestHelper th = new TestHelper();
        ClimaticRecordDao crd = new ClimaticRecordDao();


        //when
        InputStream is = new FileInputStream("csv/k_d_t_01_2019.csv");
        List<ClimaticRecordBean> resultList = crd.toBeans(is);
        is.close();

        String resultString = resultList.get(resultList.size() - 1).toString();

        //then
        assertEquals(th.readLineByLineJava8("txt/climatic-records-test.txt"), resultString + "\n");
    }

    @Test
    public void testsPullData() throws Exception {
        //given
        TestHelper th = new TestHelper();
        ClimaticRecordDao crd = new ClimaticRecordDao();


        //when
        crd.pullData();


        //then
        assertEquals(558, new File("output/").listFiles().length);
        FileUtils.cleanDirectory(new File("output/"));

    }

    @Test
    public void testsReadingFiles() throws Exception {

        //given
        TestHelper th = new TestHelper();
        ClimaticRecordDao crd = new ClimaticRecordDao();

        //when

        th.unzipExampleRecords();

        Set<ClimaticRecordBean> crbSet = crd.readData();

        //conver Set to List so we can get an element to assert
        List<ClimaticRecordBean> crbList = new LinkedList<>();
        for (ClimaticRecordBean crb : crbSet) {
            crbList.add(crb);
        }

        String result = crbList.get(33).toString();

        //then
        assertEquals(th.readLineByLineJava8("txt/read-climatic-records-test.txt"), result + "\n");
        FileUtils.cleanDirectory(new File("output/"));

    }


}
