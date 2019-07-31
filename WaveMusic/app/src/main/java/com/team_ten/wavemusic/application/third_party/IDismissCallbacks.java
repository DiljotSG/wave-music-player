package com.team_ten.wavemusic.application.third_party;

import android.widget.ListView;

/*
 * File modified from the original source code to adhere to SOLID principles.
 * Modifications Copyright 2019 - Tyler Loewen, Lukas Timmerman, Jiehao Luo, and Diljot Garcha
 */

/**
 * The callback interface used by {@link SwipeDismissListViewTouchListener} to inform its
 * client
 * about a successful dismissal of one or more list item positions.
 */
@SuppressWarnings("unused") public interface IDismissCallbacks
{
	/**
	 * Called to determine whether the given position can be dismissed.
	 */
	@SuppressWarnings("SameReturnValue") boolean canDismiss(int position);

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