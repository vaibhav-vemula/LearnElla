package com.example.start;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import org.w3c.dom.Text;

import java.io.IOException;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class MainActivity extends AppCompatActivity
{
    DBHelper dbHelper;
    TextView heading;

    MeowBottomNavigation bnav;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        try {
            dbHelper.createDB();
        } catch (IOException e) {
            System.out.println("error doinbackground" +e);
            throw new Error("Database was not created");
        }
        dbHelper.close();


        heading = (TextView)findViewById(R.id.heading);

        bnav = (MeowBottomNavigation) findViewById(R.id.bottomnav);
        bnav.add(new MeowBottomNavigation.Model(1,R.drawable.ic_outline_quiz_24));
        bnav.add(new MeowBottomNavigation.Model(2,R.drawable.dic));
        bnav.add(new MeowBottomNavigation.Model(3,R.drawable.lib));

        bnav.show(1,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer,new QuizFragment()).commit();
        bnav.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                Fragment frag = null;
                switch (model.getId())
                {
                    case 1: frag = new QuizFragment();
                            heading.setText("Quiz");
                        break;
                    case 2: frag = new DictionaryFragment();
                        heading.setText("Dictionary");
                        break;
                    case 3: frag = new LibraryFragment();
                        heading.setText("Library");
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer,frag).commit();

                return null;
            }
        });
    }
}