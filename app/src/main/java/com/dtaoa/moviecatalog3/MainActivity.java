package com.dtaoa.moviecatalog3;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dtaoa.moviecatalog3.Adapter.ViewPagerAdapter;
import com.dtaoa.moviecatalog3.Fragment.MovieFragment;
import com.dtaoa.moviecatalog3.Fragment.TvFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    AlertDialog dialogLang;
    int checkedItem = 0;
    CharSequence[] options = {"Bahasa", "English"};



    private int[] tabIcons = {
            R.drawable.ic_movie_white,
            R.drawable.ic_tv_white
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String Lang = Locale.getDefault().getLanguage();
        loadLanguage(Lang);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setElevation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.btn_ic_lang){
            CreateDialogLang();
        }

        return super.onOptionsItemSelected(item);
    }

    //setup view fragments method
    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //add fragment
        adapter.AddFragment(new MovieFragment(), getString(R.string.lbl_tab_movie));
        adapter.AddFragment(new TvFragment(), getString(R.string.lbl_tab_tv_show));

        viewPager.setAdapter(adapter);
    }

    //setup tab icon
    private void setupTabIcons(){
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    //create dialog
    public void CreateDialogLang(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle(R.string.dialog_title);

        builder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                checkedItem = item;
            }
        });
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(MainActivity.this, options[checkedItem], Toast.LENGTH_SHORT).show();
                switch (checkedItem){
                    case 0:
                        //change to bahasa indonesia
                        changeLang("id");
                        break;
                    case 1:
                        //change to bahasa inggris
                        changeLang("en");
                        break;
                }
                Toast.makeText(MainActivity.this, options[checkedItem], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialogLang = builder.create();
        dialogLang.show();

    }

    public void changeLang(String lang){
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale mLang = new Locale(lang);
        Locale.setDefault(mLang);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(mLang);
        } else {
            config.locale = mLang;
        }
        resources.updateConfiguration(config, dm);

        recreate();
    }

    public void loadLanguage(String language){
        Locale locale = new Locale(language);
        locale.setDefault(locale);
        Configuration config = new Configuration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
