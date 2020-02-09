package by.nepravsky.sm.evereactioncalculator.screens.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.DialogFragment;

import java.util.List;

import by.nepravsky.sm.domain.entity.presentation.FormulaName;
import by.nepravsky.sm.evereactioncalculator.R;
import by.nepravsky.sm.evereactioncalculator.screens.base.activity.BaseRouter;
import by.nepravsky.sm.evereactioncalculator.screens.main.aboutfragment.AboutFragment;
import by.nepravsky.sm.evereactioncalculator.screens.main.arrayadapter.ReactionArrayAdapter;

import static by.nepravsky.sm.evereactioncalculator.utils.IDs.ALL_FORMULS;
import static by.nepravsky.sm.evereactioncalculator.utils.IDs.FORMULS;
import static by.nepravsky.sm.evereactioncalculator.utils.IDs.FORMULS_STATE;

public class MainRouter  extends BaseRouter<MainActivity> {

    private SharedPreferences sp = activity.getSharedPreferences(FORMULS, Context.MODE_PRIVATE);
    private ReactionArrayAdapter reactionAdapter;

    public MainRouter(final MainActivity activity) {
        super(activity);


    }

    public void startAboutFragment(){
        DialogFragment df = new AboutFragment();
        df.show(activity.getSupportFragmentManager(), "About");
    }

    public void saveFormulaState(Integer state){
        sp.edit().putInt(FORMULS_STATE, state).commit();
    }

    public int getFormulaState(){
        return sp.getInt(FORMULS_STATE, ALL_FORMULS);
    }

    public void initAutoCompliteAdapter(List<FormulaName> nameList){
        reactionAdapter = new ReactionArrayAdapter(
                activity, R.layout.item_drop_down_image, nameList);
        activity.binding.reaction.setAdapter(reactionAdapter);
    }

    public void showDropDownMenu(){
        activity.binding.reaction.showDropDown();
    }

    public void dismisDropDownMenu(){
        activity.binding.reaction.dismissDropDown();
    }

}
