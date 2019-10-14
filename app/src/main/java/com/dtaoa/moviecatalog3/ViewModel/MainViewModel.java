package com.dtaoa.moviecatalog3.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dtaoa.moviecatalog3.BuildConfig;
import com.dtaoa.moviecatalog3.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<DataModel>> listMovies = new MutableLiveData<>();

    public void setListMovies(final Context context, String language, final String type){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<DataModel> listItems = new ArrayList<>();
        final String API_KEY = BuildConfig.API_KEY; //context.getResources().getString(R.string.API_KEY);
        String URL;
        //Log.d("Language Change", language);
        if(language.equals("in") || language == "in_ID"){
            language = "id";
        }
        if(type.equals("movie")){
            URL = context.getResources().getString(R.string.URL_MOVIE) + API_KEY + "&language=" + language;
        } else {
            URL = context.getResources().getString(R.string.URL_TV) + API_KEY + "&language=" + language;
        }

        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    String title;
                    String release;
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for(int i = 0; i < list.length(); i++){
                        JSONObject items = list.getJSONObject(i);
                        DataModel itemData = new DataModel();
                        if(type == "movie"){
                            title = items.getString("title");
                            release = items.getString("release_date");
                        } else {
                            release = items.getString("first_air_date");
                            title = items.getString("name");
                        }
                        itemData.setTitle(title);
                        itemData.setGenre(items.getString("genre_ids"));
                        itemData.setYear(release);
                        itemData.setRatings(items.getString("vote_average"));
                        itemData.setSinopsis(items.getString("overview"));
                        itemData.setImageThumbnail(context.getResources().getString(R.string.path_thumbnail) + items.getString("poster_path"));
                        itemData.setImagePoster(context.getResources().getString(R.string.path_poster) + items.getString("backdrop_path"));
                        listItems.add(itemData);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e){
                    //Log.d("Exception : ", e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.d("onFailure", error.getMessage());
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<ArrayList<DataModel>> getMovies(){
        return listMovies;
    }
}
