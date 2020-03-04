package mordernfurnitures.co.ke;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class tipsadapter extends RecyclerView.Adapter<tipsadapter.tipholder> {
    private ArrayList tipslist;
    private Context context;

    public tipsadapter(ArrayList tipslist, Context context) {
        this.tipslist = tipslist;
        this.context = context;
    }

    @NonNull
    @Override
    public tipholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflator=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflator.inflate(R.layout.tiplayout,viewGroup,false);
        tipholder vh=new tipholder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull tipholder tipholder, int i) {
        tipz tipz=(tipz)tipslist.get(i);
        tipholder.tvtip.setText(tipz.tip);
    }

    @Override
    public int getItemCount() {
        if (tipslist!=null) {
            return  tipslist.size();
        }
        return 0;
    }

    public  static class tipholder extends RecyclerView.ViewHolder {
        private TextView tvtip;
        public tipholder(@NonNull View itemView) {
            super(itemView);
            tvtip=(TextView)itemView.findViewById(R.id.tiptext);
        }
    }
}

