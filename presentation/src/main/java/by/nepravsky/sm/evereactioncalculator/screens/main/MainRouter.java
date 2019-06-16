package by.nepravsky.sm.evereactioncalculator.screens.main;

import androidx.fragment.app.DialogFragment;

import by.nepravsky.sm.evereactioncalculator.screens.base.activity.BaseRouter;
import by.nepravsky.sm.evereactioncalculator.screens.main.aboutfragment.AboutFragment;

public class MainRouter  extends BaseRouter<MainActivity> {

    public MainRouter(MainActivity activity) {
        super(activity);

    }

    public void startAboutFragment(){
        DialogFragment df = new AboutFragment();
        df.show(activity.getSupportFragmentManager(), "About");
    }

}
