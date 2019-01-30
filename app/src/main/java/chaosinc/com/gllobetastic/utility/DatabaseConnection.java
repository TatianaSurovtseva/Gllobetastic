package chaosinc.com.gllobetastic.utility;

import java.util.List;

public class DatabaseConnection {

    private DatabaseConnection() {
    }

    private static final DatabaseConnection instance = new DatabaseConnection();

    public static DatabaseConnection getInstance() {
        return instance;
    }

    public List<Picture> arrayList;

    public List<Picture> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<Picture> arrayList) {
        this.arrayList = arrayList;
    }


}
