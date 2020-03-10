import dao.StationDao;

public class ImgwResourcesApp {
    public static void main(String args[]) {

        StationDao sd = new StationDao();
        System.out.println(sd.readToDatabase());

    }
}



