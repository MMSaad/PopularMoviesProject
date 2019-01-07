package me.aratech.popularmovies.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import me.aratech.popularmovies.R;
import me.aratech.popularmovies.interfaces.IFilterChangeListener;
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
    private Constants.SortType mSelectedFilter = Constants.SortType.Popular;


    public static MoviesFilterSheet newInstance(Constants.SortType selectedFilter) {

        Bundle args = new Bundle();
        args.putInt(Constants.SELECTED_FILTER_FLAG, selectedFilter.ordinal());
        MoviesFilterSheet fragment = new MoviesFilterSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.fragment_movies_filter, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog d = (BottomSheetDialog) dialogInterface;

                FrameLayout bottomSheet = d.findViewById(android.support.design.R.id.design_bottom_sheet);
                if (bottomSheet != null)
                    BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });


        if (getArguments() != null && getArguments().get(Constants.SELECTED_FILTER_FLAG) != null) {
            mSelectedFilter = Constants.SortType.values()[getArguments().getInt(Constants.SELECTED_FILTER_FLAG)];
            updateUi();
        }

    }


    private void updateUi() {
        rbMostPopular.setChecked(mSelectedFilter == Constants.SortType.Popular);
        rb_highest_rated.setChecked(mSelectedFilter == Constants.SortType.TopRated);
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    /***
     * UI Actions
     */


    @OnClick(R.id.btnSort)
    void sortButtonPressed(View v) {
        if (getActivity() != null && getActivity() instanceof IFilterChangeListener) {
            ((IFilterChangeListener) getActivity())
                    .filterChanged(mSelectedFilter);
            dismiss();
        }
    }

    @OnClick(R.id.btnCancel) void buttonCancelPressed(View v) {
        dismiss();
    }

    @OnCheckedChanged(R.id.rbMostPopular)
    void mostPopularChecked(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            mSelectedFilter = Constants.SortType.Popular;
        }
    }

    @OnCheckedChanged(R.id.rb_highest_rated)
    void highestRatedChecked(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            mSelectedFilter = Constants.SortType.TopRated;
        }
    }


}

