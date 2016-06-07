package org.sogrey.weibo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * The type Main activity.
 * Created by Sogrey on 06.06.2016
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    
    /**
     * Init views.
     * Created by Sogrey on 06.06.2016
     */
    private void initViews() {}
}
