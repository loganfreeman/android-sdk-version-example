/*
 * Copyright (c) 2015 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.continents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContinentSelectedListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

  android.widget.ListView mListView;
  android.widget.ArrayAdapter listAdapter;

  private ContinentSelectedListener mListener;

  private static final String ARG_PARAM1 = "continentNames";

  private String[] continents;

  private Button searchButton;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment MainFragment.
   */
  public static MainFragment newInstance(String[] continents) {
    MainFragment fragment = new MainFragment();
    Bundle args = new Bundle();
    args.putStringArray(ARG_PARAM1, continents);
    fragment.setArguments(args);
    return fragment;
  }

  public MainFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      continents = getArguments().getStringArray(ARG_PARAM1);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_main, container, false);



    // Find the ListView resource.
    mListView = (android.widget.ListView) view.findViewById(R.id.mainListView);

    // Create ArrayAdapter using the continent list.
    listAdapter = new android.widget.ArrayAdapter<>(this.getActivity(), android.R.layout
        .simple_list_item_1, continents);

    mListView.setAdapter(listAdapter);

    mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view,
                              int position, long id) {
        if (mListener == null) {
          mListener = (ContinentSelectedListener) getActivity();
        }
        mListener.onContinentSelected(continents[position]);
      }
    });
    return view;
  }

  @Override
  public void onAttach(android.app.Activity context) {
    super.onAttach(context);
    try {
      mListener = (ContinentSelectedListener) context;
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString()
          + " must implement ContinentSelectedListener");
    }
  }

  public void updateContinents(String[] continentList) {
    continents = continentList;
    listAdapter = new android.widget.ArrayAdapter<>(this.getActivity(), android.R.layout
        .simple_list_item_1, continents);

    mListView.setAdapter(listAdapter);
    listAdapter.notifyDataSetChanged();
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }


}
