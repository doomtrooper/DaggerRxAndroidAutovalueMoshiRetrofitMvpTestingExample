package com.anandp.nasaapod.ui.mainscreen;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anandp.nasaapod.NasaApodApp;
import com.anandp.nasaapod.R;
import com.anandp.nasaapod.data.model.GalleryItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GalleryFragment extends Fragment implements GalleryContract.View {
    public static String TAG = GalleryFragment.class.getSimpleName();

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.error_tv)
    TextView mErrorTv;
    @BindView(R.id.retry_button)
    Button mRetryButton;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    //private GalleryContract.Presenter mPresenter;
    private GalleryRecyclerAdapter mAdapter;

    @Inject
    GalleryPresenter mPresenter;

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAdapter = new GalleryRecyclerAdapter(new ArrayList<>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        unbinder = ButterKnife.bind(this, view);
        //mPresenter = new GalleryPresenter();
        NasaApodApp.getAppContext().getRootComponent().galleryBuilder().build().inject(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mAdapter.getItemCount()<=0) {
            showItems(false);
            showErrorView(false);
            setLoadingIndicator(true);
        }else {
            showErrorView(false);
            setLoadingIndicator(false);
            showItems(true);
        }
        int numberOfColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(), numberOfColumns));
        mRecyclerView.setAdapter(mAdapter);
        if (mAdapter.getItemCount()<=0) mPresenter.loadGalleryItems();
    }

    @OnClick(R.id.retry_button)
    void onRetryClick(){
        mPresenter.loadGalleryItems();
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @SuppressWarnings("Deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void setLoadingIndicator(boolean indicator) {
        mProgressBar.setVisibility(indicator?View.VISIBLE:View.GONE);
    }

    @Override
    public void showItems(boolean bool) {
        mRecyclerView.setVisibility(bool?View.VISIBLE:View.GONE);
    }

    @Override
    public void showErrorView(boolean bool) {
        mErrorTv.setVisibility(bool?View.VISIBLE:View.GONE);
        mRetryButton.setVisibility(bool?View.VISIBLE:View.GONE);
    }

    @Override
    public void showError(String error) {
        showItems(false);
        setLoadingIndicator(false);
        showErrorView(true);
        mErrorTv.setText(error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void addGalleryItem(GalleryItem item) {
        mAdapter.addGalleryItems(item);
    }

    @Override
    public void addGalleryItem(List<GalleryItem> items) {
        mAdapter.addGalleryItems(items);
    }

    @OnClick(R.id.retry_button)
    public void onViewClicked() {
        showItems(false);
        showErrorView(false);
        setLoadingIndicator(true);
        mPresenter.loadGalleryItems();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
