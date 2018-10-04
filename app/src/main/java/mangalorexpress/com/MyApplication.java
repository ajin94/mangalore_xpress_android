package mangalorexpress.com;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by akhil on 4/10/18.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
        Fresco.initialize(this);
    }
}
