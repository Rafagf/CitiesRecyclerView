package com.example.rafagarcia.citiesrecyclerview;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Garcia on 23/08/15.
 */

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<City> cityList = new ArrayList<>();
    int viewState = 0;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Lovely cities");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.material_deep_teal_200)));

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        //GridLayoutManager
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

        //LinearLayoutManager
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        //StaggeredGridLayoutManager (3 horizontal rows)
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL);


        //LinearLayoutManager as default LayoutManager
        recyclerView.setLayoutManager(linearLayoutManager);

        //Loading cities and passing them to the Recycler View Adapter
        loadCities();
        RVcitiesAdapter citiesAdapter = new RVcitiesAdapter(getApplicationContext(), cityList);
        recyclerView.setAdapter(citiesAdapter);
    }

    public void loadCities(){

        City city1 = new City("Granada", "http://cdni.condenast.co.uk/646x430/g_j/granada_cnt_24nov09_iStock_b_1.jpg");
        City city2 = new City("Malaga", "http://www.topratedflights.co.uk/wp-content/uploads/2013/04/malaga1.jpg");
        City city3 = new City("Brighton", "http://i.telegraph.co.uk/multimedia/archive/02472/brighton_2472753b.jpg");
        City city4 = new City("London", "http://cdn.londonandpartners.com/assets/73295-640x360-london-skyline-ns.jpg");
        City city5 = new City("Warsaw", "http://www.bargaintravel4u.co.uk/assets/warsaw-hotels_1.jpg");
        City city6 = new City("Bialystok", "http://msetours.hu/uploads/publish/assets/images/bal2.jpg");
        City city7 = new City("Praga", "http://cdn.traveler.es/uploads/images/thumbs/201146/puente_de_carlos__en_praga_1507_966x.jpg");
        City city8 = new City("Budapest", "http://images.nationalgeographic.com/wpf/media-live/photos/000/076/cache/budapest-thermal-baths_7607_600x450.jpg");
        City city9 = new City("Mallorca", "http://cdni.condenast.co.uk/646x430/k_n/mallorca_cnt_24nov09_iStock_.jpg");
        City city10 = new City("Nice", "http://cdni.condenast.co.uk/646x430/k_n/nice_cnt_18nov09_iStock_b_1.jpg");
        City city11 = new City("Rome", "http://www.telegraph.co.uk/travel/hotel/article129671.ece/ALTERNATES/w620/rometravelguide5.jpg");
        City city12 = new City("Paris", "http://www.telegraph.co.uk/travel/destination/article130148.ece/ALTERNATES/w620/parisguidetower.jpg");
        City city13 = new City("Berlin", "http://www.telegraph.co.uk/travel/destination/article128328.ece/ALTERNATES/w620/berlin.jpg");
        City city14 = new City("Bruges", "http://travelcocktail.org/wp-content/uploads/2014/11/Bruges-river1.jpg");

        cityList.add(city1); cityList.add(city2); cityList.add(city3); cityList.add(city4);
        cityList.add(city5); cityList.add(city6); cityList.add(city7); cityList.add(city8);
        cityList.add(city9); cityList.add(city10); cityList.add(city11); cityList.add(city12);
        cityList.add(city13); cityList.add(city14);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.changeViewButton:
                //List view
                if (viewState == 0) {
                    recyclerView.setLayoutManager(gridLayoutManager);
                    viewState = 1;
                }
                //Grid view
                else if(viewState == 1) {
                    recyclerView.setLayoutManager(staggeredGridLayoutManager);
                    viewState = 2;
                }

                //Staggered View
                else if(viewState == 2){
                    recyclerView.setLayoutManager(linearLayoutManager);
                    viewState = 0;
                }

                break;
            default:
                break;
        }

        return true;
    }
}
