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

import android.widget.ImageView;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link DescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescriptionFragment extends android.support.v4.app.Fragment {
  private static final String ARG_PARAM1 = "name";
  private static final String ARG_PARAM2 = "desc";
  private static final String ARG_PARAM3 = "image";

  private String continentName;
  private String continentDescription;
  private int continentImageRes;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param continent the selected continent
   * @return A new instance of fragment DescriptionFragment.
   */
  public static DescriptionFragment newInstance(Continent continent) {
    DescriptionFragment fragment = new DescriptionFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, continent.getName());
    args.putString(ARG_PARAM2, continent.getDescription());
    args.putInt(ARG_PARAM3, continent.getImageResource());
    fragment.setArguments(args);
    return fragment;
  }

  public DescriptionFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      continentName = getArguments().getString(ARG_PARAM1);
      continentDescription = getArguments().getString(ARG_PARAM2);
      continentImageRes = getArguments().getInt(ARG_PARAM3);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_description, container, false);
    ImageView continentImage = (ImageView)view.findViewById(R.id.continentImage);
    continentImage.setImageResource(continentImageRes);

    TextView description = (TextView)view.findViewById(R.id.continentDescription);
    description.setText(continentDescription);
    return view;
  }

}
