package com.nhom9.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhom9.models.Appointment;
import com.nhom9.models.MyPet;
import com.nhom9.myapplication.R;

import java.util.ArrayList;

public class AppointmentAdapter extends BaseAdapter {

    Context context;
    int appointment_layout;
    ArrayList<Appointment> appointments;

    //constructor
    public AppointmentAdapter(Context context, int appointment_layout, ArrayList<Appointment> appointments) {
        this.context = context;
        this.appointment_layout = appointment_layout;
        this.appointments = appointments;
    }


    @Override
    public int getCount() {
        return appointments.size();
    }

    @Override
    public Object getItem(int position) {
        return appointments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(appointment_layout, null);
            holder = new ViewHolder();
            holder.txtTime = convertView.findViewById(R.id.txtTimeAppointment);
            holder.txtServiceType = convertView.findViewById(R.id.txtServiceType);
            holder.txtPetName = convertView.findViewById(R.id.txtPetName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Appointment a = appointments.get(position);
        holder.txtTime.setText(a.getTime());
        holder.txtServiceType.setText(a.getServicetype());
        holder.txtPetName.setText(a.getPetname());

        return convertView;
    }

    class ViewHolder{
        TextView txtTime, txtServiceType, txtPetName;
    }
}
