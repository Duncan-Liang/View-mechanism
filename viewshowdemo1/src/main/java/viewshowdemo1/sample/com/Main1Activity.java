package viewshowdemo1.sample.com;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class Main1Activity extends Activity {
    private ArrayList<ValueAnimator> mSinValueAnimator;
    private final String TAG = "Main1Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSinValueAnimator = new ArrayList<ValueAnimator>();
        setContentView(R.layout.activity_main1);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.anim_wave);

        for (int i = 0; i < 4; i++) {
            SinCanvas tmpCanvas = new SinCanvas(this, i, 160);
            long time = 0;
            switch (i) {
                case 0:
                    time = 11000;   //11000
                    break;
                case 1:
                    time = 7000;    //7000
                    break;
                case 2:
                    time = 5000;    //5000
                    break;
                case 3:
                    time = 3000;    //3000
                    break;
                default:
                    break;
            }
            ValueAnimator tmpValueAnimator = tmpCanvas.getSinValueAnimator(tmpCanvas.getBarY(), time);
            tmpValueAnimator.start();
            mSinValueAnimator.add(0, tmpValueAnimator);
            relativeLayout.addView(tmpCanvas);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSinValueAnimator != null && mSinValueAnimator.size() > 0) {
            for (int i = 0; i < mSinValueAnimator.size(); i++) {
                mSinValueAnimator.get(i).end();
                mSinValueAnimator.get(i).removeAllUpdateListeners();
            }
        }
    }
}
