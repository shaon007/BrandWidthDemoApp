package com.brandwidth.demoapp.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.brandwidth.demoapp.Model.BeanClass;
import com.brandwidth.demoapp.DetailsActivity;
import com.brandwidth.demoapp.Model.BeanDetails;
import com.brandwidth.demoapp.MyHttpHandler;
import com.brandwidth.demoapp.Adapters.MyRecycleAdapter;
import com.brandwidth.demoapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment implements SearchView.OnQueryTextListener
{

    ArrayList<BeanClass> list_beanObjects;

    private ProgressDialog pDialog;

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private static final int SPAN_COUNT = 2;

    protected LayoutManagerType mCurrentLayoutManagerType;

    private RecyclerView mRecyclerView;
    private MyRecycleAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;


    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }


    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        list_beanObjects = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView =  inflater.inflate(R.layout.fragment_one, container, false);
        rootView.setTag(TAG);

        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);

        mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }

        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new MyRecycleAdapter(getActivity(), list_beanObjects);

        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(isNetworkAvailable(getActivity()))
            {
            if(list_beanObjects!=null)
                list_beanObjects.clear();

                new getData().execute();}
    }


    private class getData extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            BeanClass aObj;

            MyHttpHandler sh = new MyHttpHandler();
            String url = "https://api.themoviedb.org/3/movie/popular?api_key=d0aea524bd07ed49cbc26dff63f357dd&language=en-US&page=1";
            String jsonStr = sh.makeServiceCall(url);

            Log.i("logResult", "Response from url: " + jsonStr);

            if (jsonStr != null)
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray contacts = jsonObj.getJSONArray("results");

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject aJsonObj = contacts.getJSONObject(i);
                        String id = aJsonObj.getString("id");
                        String name = aJsonObj.getString("title");
                        String path = aJsonObj.getString("poster_path");
                        String vote = aJsonObj.getString("vote_average");

                        aObj = new BeanClass(id,name,path,vote);
                        list_beanObjects.add(aObj);

                        //  Log.d("logResult", aObj.getId() + " >> " +aObj.getName()+ ">> " + aObj.getPath());

                    }
                }
                catch (final Exception e)
                {
                    Log.d("logResult", "There was an error : " + e);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mAdapter.notifyDataSetChanged();

            mAdapter.setOnItemClickListener(new MyRecycleAdapter.onRecyclerViewItemClickListener()
            {
                @Override
                public void onItemClickListener(View view, int position)
                {
                    if(isNetworkAvailable(getActivity()))
                         new getDetailAsync().execute(list_beanObjects.get(position).getId().toString());
                    else
                         Toast.makeText(getActivity(),"Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }


// --------------- asynctask for retreiving details

    private class getDetailAsync extends AsyncTask<String, Void, Void>
    {
        BeanDetails aDetailObj;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params)
        {
            MyHttpHandler sh = new MyHttpHandler();

            String url = "https://api.themoviedb.org/3/movie/"+params[0]+"?api_key=d0aea524bd07ed49cbc26dff63f357dd&language=en-US";
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null)
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                  JSONArray genres = jsonObj.getJSONArray("genres");
                  String strGenre = jsonArrToString(genres,"name");

                   // jsonObj.get("runtime");

                    JSONArray languages = jsonObj.getJSONArray("spoken_languages");
                    String strLanguages = jsonArrToString(languages,"name");


                    StringBuilder strExtras = new StringBuilder();
                    strExtras.append( "Genres: "+strGenre +
                            "\nSpokenLanguages: "+strLanguages+
                            "\nRuntime: "+ jsonObj.get("runtime").toString());


                    aDetailObj = new BeanDetails(params[0], jsonObj.get("backdrop_path").toString(), jsonObj.get("vote_average").toString(),
                            jsonObj.get("title").toString(), "Release Date: "+ jsonObj.get("release_date").toString(), "Overview: "+jsonObj.get("overview").toString(),
                            "Production company: "+jsonArrToString(jsonObj.getJSONArray("production_companies") , "name"),
                            strExtras.toString()
                    );
                }
                catch (final Exception e)
                {
                    Log.d("logResult2", "There was an error : " + e);
                }


            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            pDialog.dismiss();

            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("extra_parcel_obj", aDetailObj);
            startActivity(intent);
         }

        private String jsonArrToString(JSONArray jArr, String keyName)
        {
            StringBuilder mStr = new StringBuilder();
            try {
                for (int i = 0; i < jArr.length(); i++)
                {
                    JSONObject _obj = jArr.getJSONObject(i);
                    mStr.append(_obj.getString(keyName));
                    if (i != (jArr.length() - 1))
                        mStr.append(" , ");
                }
            }
            catch(Exception ex)
            {Log.d("ErrorDetailsAsync", ex.toString());}

            return mStr.toString();
        }
    }


    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        mAdapter.setFilter(list_beanObjects);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }
                });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<BeanClass> filteredModelList = filter(list_beanObjects, newText);
        mAdapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<BeanClass> filter(List<BeanClass> models, String query) {
        query = query.toLowerCase();

        final List<BeanClass> filteredModelList = new ArrayList<>();
        for (BeanClass model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


}