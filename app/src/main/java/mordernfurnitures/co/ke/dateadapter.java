package mordernfurnitures.co.ke;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class dateadapter extends RecyclerView.Adapter<dateadapter.dateholder> {
    private ArrayList datelist;
    private Context context;

    public dateadapter(ArrayList datelist, Context context) {
        this.datelist = datelist;
        this.context = context;
    }

    @NonNull
    @Override
    public dateholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflator= LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflator.inflate(R.layout.datesinf,viewGroup,false);
        dateholder vh=new dateholder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull dateholder dateholder, int i) {
        datz datz=(datz)datelist.get(i);
        dateholder.evddate.setText(datz.date);
        dateholder.ev.setText(datz.medication);
    }

    @Override
    public int getItemCount() {
        if(datelist!=null){
            return  datelist.size();
        }
        return 0;

    }

    public static  class dateholder extends RecyclerView.ViewHolder{
        public TextView evddate;
        public TextView ev;
        public dateholder(@NonNull View itemView) {
            super(itemView);
            evddate=(TextView)itemView.findViewById(R.id.vdates);
            ev=(TextView)itemView.findViewById(R.id.vacine);
        }
    }
}
