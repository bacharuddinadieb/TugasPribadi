package id.sch.smktelkom_mlg.privateassignment.xirpl507.mymovie.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.privateassignment.xirpl507.mymovie.R;
import id.sch.smktelkom_mlg.privateassignment.xirpl507.mymovie.adapter.upcomingadapter;
import id.sch.smktelkom_mlg.privateassignment.xirpl507.mymovie.model.source;
import id.sch.smktelkom_mlg.privateassignment.xirpl507.mymovie.model.sourcesresponse;
import id.sch.smktelkom_mlg.privateassignment.xirpl507.mymovie.service.GsonGetRequest;
import id.sch.smktelkom_mlg.privateassignment.xirpl507.mymovie.service.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class upcoming_frag extends Fragment {

    ArrayList<source> mList = new ArrayList<>();
    upcomingadapter mAdapter;

    public upcoming_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_frag, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new upcomingadapter(this.getActivity(), mList);
        recyclerView.setAdapter(mAdapter);

        downloadDataComing();
    }

    private void downloadDataComing() {
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=485c73486b9905807301e4a5f0985349";

        GsonGetRequest<sourcesresponse> myRequest = new GsonGetRequest<sourcesresponse>
                (url, sourcesresponse.class, null, new Response.Listener<sourcesresponse>() {

                    @Override
                    public void onResponse(sourcesresponse response) {
                        Log.d("FLOW", "onResponse: " + (new Gson().toJson(response)));

                        mList.addAll(response.results);
                        mAdapter.notifyDataSetChanged();
                    }


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FLOW", "onErrorResponse: ", error);
                    }
                });
        VolleySingleton.getInstance(this.getActivity()).addToRequestQueue(myRequest);
    }
}
