package chaosinc.com.gllobetastic.utility;

import java.io.Serializable;

/**
 * POJO class for picture
 */
public class Picture implements Serializable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureBitmap() {
        return pictureBitmapBase64;
    }

    private String name;
    private String pictureBitmapBase64;
    private String city;

    public String getCity() {
        return city;
    }

    public Picture(String name, String pictureBitmapBase64, String city) {
        this.name = name;
        this.pictureBitmapBase64 = pictureBitmapBase64;
        this.city = city;
    }


}
