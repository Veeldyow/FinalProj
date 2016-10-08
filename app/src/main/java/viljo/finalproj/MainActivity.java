package viljo.finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class MainActivity extends AppCompatActivity {

    Button login;
    EditText mail, pwd;
    TextView pwd_show,reg, show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login =  (Button)findViewById(R.id.button_login);
        mail = (EditText)findViewById(R.id.editText_email);
        pwd = (EditText)findViewById(R.id.editText_pwd);
        pwd_show = (TextView)findViewById(R.id.pwd_show);
        reg = (TextView)findViewById(R.id.text_sign);

        pwd_show.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent event) {
                                            final int cursor = pwd.getSelectionStart();
                                            switch (event.getAction() ) {
                                                case MotionEvent.ACTION_DOWN:
                                                    pwd.setTransformationMethod(null);
                                                    pwd.setSelection(cursor);
                                                    Log.d("Cursor","ACTION_DOWN");
                                                    break;
                                                case MotionEvent.ACTION_UP:
                                                    pwd.setTransformationMethod(new PasswordTransformationMethod());
                                                    pwd.setSelection(cursor);
                                                    Log.d("Cursor","ACTION_UP");
                                                    break;
                                            }
                                            return true;
                                        }
                                    }
        );

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    DatabaseAdapter db = new DatabaseAdapter(MainActivity.this);
                    System.out.println(mail.getText().toString().trim() + " " +pwd.getText().toString().trim());
                    if (db.validateUser(mail.getText().toString().trim(),pwd.getText().toString().trim())){
                        Toast.makeText(getApplicationContext(),"Credentials matched. Redirecting...", Toast.LENGTH_LONG).show();
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    Thread.sleep(1000);
                                    Intent intent_wel = new Intent(MainActivity.this, Welcome.class);
                                    startActivity(intent_wel);
                                    finish();
                                }
                                catch (InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Credentials do not match..", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mail.setText("");
                pwd.setText("");
                Intent intent_reg = new Intent(MainActivity.this, Register.class);
                startActivity(intent_reg);
            }

        });
    }

   @Override
    protected  void onPause(){
        super.onPause();
       super.finish();
    }
    public boolean check(){
        boolean valid = true;
        String _mail = mail.getText().toString();
        String _pwd = pwd.getText().toString();
        String email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern, ppattern;
        Matcher matcher, pmatcher;
        pattern = Pattern.compile(email_pattern);
        ppattern = Pattern.compile("^[A-Z0-9a-z]+$");
        matcher = pattern.matcher(_mail);
        pmatcher = ppattern.matcher(_mail);

        if (_mail.isEmpty()){
           Toast.makeText(getApplicationContext(),"Enter valid email",Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (_pwd.isEmpty()){
            Toast.makeText(getApplicationContext(),"Password field is empty",Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;
    }
}