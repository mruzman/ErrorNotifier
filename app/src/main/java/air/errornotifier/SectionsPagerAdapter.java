package air.errornotifier;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Tomi on 22.11.2017..
 */

class SectionsPagerAdapter extends FragmentPagerAdapter{

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                ApplicationsFragment applicationsFragment = new ApplicationsFragment();
                return applicationsFragment;
            case 1:
                UsersFragment usersFragment = new UsersFragment();
                return usersFragment;

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Applications";
            case 1:
                return "Users";
            default:
                return null;
        }

    }
}


