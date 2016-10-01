package viljo.finalproj;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Viljo on 10/01/2016.
 */

public class Welcome extends Activity {
    ImageView iv;
    TextView first, seco, diff, moti, quad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        iv = (ImageView)findViewById(R.id.iv);
        first = (TextView)findViewById(R.id.first);
        seco = (TextView)findViewById(R.id.seco);
        diff = (TextView)findViewById(R.id.diff);
        moti = (TextView)findViewById(R.id.moti);
        quad = (TextView)findViewById(R.id.quad);

        iv.setOnTouchListener(new View.OnTouchListener() {
            String f,s,d,q,ud,lr;
            float x1,y1,x2,y2;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getX();
                        y1 = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = motionEvent.getX();
                        y2 = motionEvent.getY();
                        if (x1>x2) lr = "left"; else if (x1<x2) lr = "right"; else lr="";
                        if (y1>y2) ud = "up"; else if (y1<y2) ud = "down"; else ud="";
                        f = String.format("%.4f",x1)+", "+String.format("%.4f",y1);
                        s = String.format("%.4f",x2)+", "+String.format("%.4f",y2);
                        d = Math.abs(x1-x2)+","+Math.abs(y1-y2);
                        first.setText(f);
                        seco.setText(s);
                        diff.setText(d);

                        if (lr.equals("") && ud.equals(""))
                            moti.setText("");
                        else
                            moti.setText("Moved "+lr+" and "+ud);
                        if (x1 < x2 && y1 > y2) q = "1";
                        else if (x1 > x2 && y1 > y2) q = "2";
                        else if (x1 < x2  && y1 < y2) q = "4";
                        else if (x1 > x2 && y1 < y2) q = "3";
                        else q = "";
                        quad.setText(q);
                        break;
                }
                return true;
            }
        });
    }
}