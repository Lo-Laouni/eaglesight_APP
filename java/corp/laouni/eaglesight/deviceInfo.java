package corp.laouni.eaglesight;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ishimwe on 24/2/2018.
 */

public class deviceInfo extends AppCompatActivity {

    public void getdeviceInfo(){
        String Device, Model, Product, Brand, Serial, Id;

        Device = Build.DEVICE;
        Model = Build.MODEL;
        Product = Build.PRODUCT;
        Brand = Build.BRAND;
        //Serial = Build.SERIAL; (SERIAL is deprecated)
        Id = Build.ID;
    }
}
