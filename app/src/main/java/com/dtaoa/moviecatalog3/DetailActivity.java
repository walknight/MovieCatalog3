package com.dtaoa.moviecatalog3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dtaoa.moviecatalog3.ViewModel.DataModel;

public class DetailActivity extends AppCompatActivity {

    ImageView imgPoster, imgThumbnail;
    TextView txtTitle, txtSinopsis, txtReleaseYear, txtRatings;


    public static final String EXTRA_DATA = "extra_move";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String [] releaseDate;
        DataModel selectedData = getIntent().getParcelableExtra(EXTRA_DATA);

        if(selectedData != null){

            txtTitle = findViewById(R.id.txt_title_detail);
            txtTitle.setText(selectedData.getTitle());

            txtSinopsis = findViewById(R.id.txt_sinopsis_detail);
            txtSinopsis.setText(selectedData.getSinopsis());

            txtRatings = findViewById(R.id.txt_rating_detail);
            txtRatings.setText(selectedData.getRatings());

            releaseDate = selectedData.getYear().split("-");
            txtReleaseYear = findViewById(R.id.txt_year_detail);
            txtReleaseYear.setText(releaseDate[0]);

            imgThumbnail = findViewById(R.id.img_thumbnail_detail);
            Glide.with(this)
                    .load(selectedData.getImageThumbnail())
                    .apply(new RequestOptions().override(100,140))
                    .into(imgThumbnail);

            imgPoster = findViewById(R.id.img_poster_detail);
            Glide.with(this)
                    .load(selectedData.getImagePoster())
                    .into(imgPoster);
        }

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(selectedData.getTitle());
        }
    }
}
