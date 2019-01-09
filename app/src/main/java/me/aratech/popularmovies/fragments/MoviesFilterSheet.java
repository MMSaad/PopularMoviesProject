package me.aratech.popularmovies.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import me.aratech.popularmovies.R;
import me.aratech.popularmovies.interfaces.ISortTypeChangeListener;
import me.aratech.popularmovies.utils.Constants;

/***
 * Movie Filter Bottom sheet
 */
public class MoviesFilterSheet extends BottomSheetDialogFragment {


    /***
     * Views
     */
    @BindView(R.id.rbMostPopular) RadioButton rbMostPopular;
    @BindView(R.id.rb_highest_rated) RadioButton rb_highest_rated;

    /***
     * Vars
     */
    private Constants.SortType mSortType = Constants.SortType.Popular;


    /***
     * Create new instance with bundled selected sort type
     * @param selectedFilter Sort type
     * @return New Movies Filter sheet fragment
     */
    public static MoviesFilterSheet newInstance(Constants.SortType selectedFilter) {
        Bundle args = new Bundle();
        args.putInt(Constants.SELECTED_SORT_TYPE, selectedFilter.ordinal());
        MoviesFilterSheet fragment = new MoviesFilterSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.fragment_movies_filter, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog d = (BottomSheetDialog) dialogInterface;

                FrameLayout bottomSheet = d.findViewById(android.support.design.R.id.design_bottom_sheet);
                if (bottomSheet != null)
                    BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });


        if (getArguments() != null && getArguments().get(Constants.SELECTED_SORT_TYPE) != null) {
            mSortType = Constants.SortType.values()[getArguments().getInt(Constants.SELECTED_SORT_TYPE)];
            updateUi();
        }

    }


    private void updateUi() {
        rbMostPopular.setChecked(mSortType == Constants.SortType.Popular);
        rb_highest_rated.setChecked(mSortType == Constants.SortType.TopRated);
    }


    /***
     * UI Actions
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btnSort)
    void sortButtonPressed(View v) {
        if (getActivity() != null && getActivity() instanceof ISortTypeChangeListener) {
            ((ISortTypeChangeListener) getActivity())
                    .sortTypeChanged(mSortType);
            dismiss();
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnCancel)
    void buttonCancelPressed(View v) {
        dismiss();
    }

    @SuppressWarnings("unused")
    @OnCheckedChanged(R.id.rbMostPopular)
    void mostPopularChecked(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            mSortType = Constants.SortType.Popular;
        }
    }

    @SuppressWarnings("unused")
    @OnCheckedChanged(R.id.rb_highest_rated)
    void highestRatedChecked(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            mSortType = Constants.SortType.TopRated;
        }
    }


}

