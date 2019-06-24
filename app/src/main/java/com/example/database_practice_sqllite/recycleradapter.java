package com.example.database_practice_sqllite;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;





import java.util.ArrayList;

public class recycleradapter extends RecyclerView.Adapter<recycleradapter.myViewHolder> {
    @NonNull
    private Context mContext;
    private ArrayList<employeeData> mEmployeeData;





    recycleradapter(Context context, ArrayList<employeeData> adapterItemtLists) {
        this.mContext = context;
        this.mEmployeeData = adapterItemtLists;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);

        return new myViewHolder(view , mContext , mEmployeeData);
    }




    @Override
    public void onBindViewHolder(@NonNull myViewHolder viewHolder, int i) {

        final employeeData nEmployeeData = mEmployeeData.get(i);

        TextView name, loc1, loc2;


        name = viewHolder.name;
        loc1 = viewHolder.loc1;
        loc2 = viewHolder.loc2;



        name.setText("Name: "+nEmployeeData.getName());
        loc1.setText("Latitude: "+nEmployeeData.getLatitudeLocation());
        loc2.setText("Longitude: "+nEmployeeData.getLongitudeLocation());

    }

    @Override
    public int getItemCount() {
        return mEmployeeData.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView name , loc1 , loc2 ;

        ArrayList<employeeData> mycontactLists = new ArrayList<>();
        Context myContext;


        myViewHolder(@NonNull View itemView , Context context , ArrayList<employeeData> itemtLists) {
            super(itemView);
            //
            this.mycontactLists = itemtLists;
            this.myContext=context;
            itemView.setOnClickListener(this);
            //
            name = itemView.findViewById(R.id.name);
            loc1 = itemView.findViewById(R.id.latitude);
            loc2  = itemView.findViewById(R.id.longitude);

        }


        @Override
        public void onClick(View v) {

            int pos = getAdapterPosition();
            employeeData onClickItemtList =this.mycontactLists.get(pos);


            if(onClickItemtList.latitudeLocation == "null" && onClickItemtList.longitudeLocation =="null" ){

                Intent intent = new Intent(this.myContext , SetLocation.class);
                intent.putExtra("userName" , onClickItemtList.name);
                this.myContext.startActivity(intent);

            }else{
                Intent intent = new Intent(this.myContext, LocationMaps.class);
                intent.putExtra("name" , onClickItemtList.name);
                intent.putExtra("latitude" , onClickItemtList.latitudeLocation);
                intent.putExtra("longitude" , onClickItemtList.longitudeLocation);
                this.myContext.startActivity(intent);
            }

        }
    }
}
