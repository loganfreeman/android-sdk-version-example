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

import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Spinner;

public class MainActivity extends android.support.v7.app.AppCompatActivity
    implements ContinentSelectedListener {

  TextView title;
  Spinner orderContinents;
  com.raywenderlich.continents.MainFragment mMainFragment;

  java.util.Map<String, Continent> continentMap = new java.util.HashMap<>();


  final String[] continentNames = new String[]{"Asia", "Africa", "Australia", "Antarctica",
      "Europe",
      "North America", "South America"};
  final String[] continentDescs = new String[]{
      "Asia is the Earth's largest and most populous continent. " +
          "It covers 8.7% of Earth's total surface area. " +
          "As of 2013, it had a population of 4.3 billion. It consists of 47 countries.",
      "Africa is the Earth's second largest and second most populous continent. " +
          "It covers 6% of Earth's total surface area. " +
          "As of 2013, it had a population of 1.1 billion. It consists of 54 countries. ",
      "Australia is both a country and a continent. " +
          "As of 2013, it had a population of 23 million.",
      "Antarctica is the Earth's southernmost continent. It is the fifth largest continent. About" +
          " 98% of Antarctica is covered by ice.",
      "Europe is the Earth's second smallest continent. It covers 2% of Earth's surface area. " +
          "As of 2013, it had a population of 742.5 million. It consists of 50 countries.",
      "North America is the Earth's third largest continent and fourth most populous continent. " +
          "It covers about 16.5% of the Earth's surface area. " +
          "As of 2013, it had a population of about 565 million. " +
          "It consists of 23 countries",
      "South America is the Earth's fourth largest continent and fifth most populous continent. " +
          "As of 2011, it had a population of about 385.7 million. It consists of 12 countries"
  };
  final Integer[] imageResources = new Integer[] {R.drawable.asia, R.drawable.africa, R.drawable
      .australia, R.drawable.antarctica, R.drawable.europe, R.drawable.north_america, R.drawable
      .south_america};


  @Override
  protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    for (int i = 0; i < continentNames.length; i++) {
      Continent continent = new Continent(continentNames[i], continentDescs[i], imageResources[i]);
      continentMap.put(continentNames[i], continent);
    }

    // Set a toolbar to replace the action bar.
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    title = (TextView) toolbar.findViewById(R.id.title);
    orderContinents = (Spinner)toolbar.findViewById(R.id.toolbar_spinner);
    android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(this,
        android.R.layout.simple_spinner_item, new String[] {"A - Z", "Z - A"});
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    orderContinents.setAdapter(adapter);
    orderContinents.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view,
                                 int position, long id) {
        switch (position) {
          case 0:
            java.util.Arrays.sort(continentNames);
            if(mMainFragment != null) {
              mMainFragment.updateContinents(continentNames);
            }
            break;
          case 1:
            java.util.Arrays.sort(continentNames, java.util.Collections.reverseOrder());
            if(mMainFragment != null) {
              mMainFragment.updateContinents(continentNames);
            }
            break;
          default:
            break;
        }

      }

      @Override
      public void onNothingSelected(android.widget.AdapterView<?> parent) {

      }
    });

    setSupportActionBar(toolbar);
    goToContinentList();

  }

  @Override
  public boolean onOptionsItemSelected(android.view.MenuItem item) {
    goToContinentList();
    return true;
  }

  @Override
  public void onContinentSelected(String selectedContinent) {
    Continent continent = continentMap.get(selectedContinent);
    title.setText(continent.getName());
    orderContinents.setVisibility(android.view.View.INVISIBLE);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(continent);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      descriptionFragment.setEnterTransition(new Fade());
      mMainFragment.setExitTransition(new Fade());
      descriptionFragment.setExitTransition(new Slide(Gravity.BOTTOM));
      mMainFragment.setReenterTransition(new Fade());
      descriptionFragment.setAllowReturnTransitionOverlap(true);
    }

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.frameLayout, descriptionFragment)
        .commit();
  }

  private void goToContinentList() {
    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    title.setText("Continents");
    orderContinents.setVisibility(android.view.View.VISIBLE);
    mMainFragment = MainFragment.newInstance(continentNames);
    getSupportFragmentManager().
        beginTransaction().
        replace(R.id.frameLayout, mMainFragment).commit();
  }

}
