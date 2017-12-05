package adam.flappywing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HighscoreAdapter extends ArrayAdapter<ScoreEntry> {

    Context context;
    int layoutResourceId;
    List<ScoreEntry> data = null;

    public HighscoreAdapter(Context context, int layoutResourceId, List<ScoreEntry> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ScoreHolder holder = null;

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ScoreHolder();
            holder.text = (TextView)row.findViewById(R.id.highscoreTextViewEntry);

            row.setTag(holder);
        } else {
            holder = (ScoreHolder)row.getTag();
        }


        ScoreEntry score = data.get(position);
        holder.text.setText(score.name + ": " + score.value);

        return row;
    }

    static class ScoreHolder
    {
        TextView text;
    }
}
