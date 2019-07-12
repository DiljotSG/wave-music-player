package com.team_ten.wavemusic.application;

import android.widget.ListView;

/**
 * The callback interface used by {@link SwipeDismissListViewTouchListener} to inform its
 * client
 * about a successful dismissal of one or more list item positions.
 */
public interface IDismissCallbacks
{
	/**
	 * Called to determine whether the given position can be dismissed.
	 */
	boolean canDismiss(int position);

	/**
	 * Called when the user has indicated they she would like to dismiss one or more list item
	 * positions.
	 *
	 * @param listView               The originating {@link ListView}.
	 * @param reverseSortedPositions An array of positions to dismiss, sorted in descending
	 *                               order for convenience.
	 */
	void onDismiss(ListView listView, int[] reverseSortedPositions);
}