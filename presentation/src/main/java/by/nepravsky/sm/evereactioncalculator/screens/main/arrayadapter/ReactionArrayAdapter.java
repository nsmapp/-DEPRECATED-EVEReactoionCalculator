package by.nepravsky.sm.evereactioncalculator.screens.main.arrayadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import by.nepravsky.sm.domain.entity.presentation.FormulaName;
import by.nepravsky.sm.evereactioncalculator.R;

public class ReactionArrayAdapter extends ArrayAdapter<FormulaName> {

    private LayoutInflater inflater;
    private List<FormulaName> nameList;
    private List<FormulaName> filterList;
    private FormulaFilter filter = new FormulaFilter();


    public ReactionArrayAdapter(@NonNull Context context, int resource, @NonNull List<FormulaName> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        nameList = new ArrayList<>(objects);
        filterList = new ArrayList<>(objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view;
        final TextView text;
        final  ImageView image;

        if (convertView == null) {
            view = inflater.inflate(R.layout.item_drop_down_image, parent, false);
        } else {
            view = convertView;
        }

        text = view.findViewById(R.id.text);
        text.setText(filterList.get(position).getName());

        image = view.findViewById(R.id.image);
        Picasso.get()
                .load("https://imageserver.eveonline.com/Type/" + filterList.get(position).getId() + "_128.png")
                .error(R.drawable.reaction_info)
                .into(image);

        return view;
    }

    @Override
    public int getCount() {
        return filterList.size();
    }

    @Override
    public FormulaName getItem(int position) {
        return filterList.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    class FormulaFilter extends Filter{

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            FormulaName formula = (FormulaName) resultValue;
            return formula.getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                filterList.clear();
                for (FormulaName formula : nameList) {
                    if (formula.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterList.add(formula);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                filterResults.count = filterList.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0){
                notifyDataSetChanged();
            }

        }
    }


}
