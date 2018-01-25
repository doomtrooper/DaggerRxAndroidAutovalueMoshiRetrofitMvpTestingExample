package com.anandp.nasaapod.ui.mainscreen;

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

import com.anandp.nasaapod.R;
import com.anandp.nasaapod.data.GalleryItem;

import java.util.ArrayList;
import java.util.List;

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
    private GalleryContract.Presenter mPresenter;
    private GalleryRecyclerAdapter mAdapter;

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
        mPresenter = new GalleryPresenter();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int numberOfColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(), numberOfColumns));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.loadGalleryItems();
    }

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

    }

    @Override
    public void showGallery(List<GalleryItem> items) {

    }

    @Override
    public void showError(String error) {

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

    @OnClick(R.id.retry_button)
    public void onViewClicked() {
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
