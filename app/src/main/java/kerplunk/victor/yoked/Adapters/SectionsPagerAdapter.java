package kerplunk.victor.yoked.Adapters;

/**
 * Created by Victor on 5/23/2015.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import kerplunk.victor.yoked.Activities.PastWorkoutsFragment;
import kerplunk.victor.yoked.Activities.SelectProgramActivityFragment;
import kerplunk.victor.yoked.R;

/**
 * Created by Victor on 5/23/2015.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    protected Context mContext;


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm); mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position) {
            case 0:
                return new SelectProgramActivityFragment();

            case 1:
                return new PastWorkoutsFragment();

        }

        return new SelectProgramActivityFragment();
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return mContext.getString(R.string.title_section2).toUpperCase(l);

        }
        return null;
    }
}