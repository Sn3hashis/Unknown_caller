package com.mhvmedia.unknowncaller.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhvmedia.unknowncaller.Activities.MainActivity;
import com.mhvmedia.unknowncaller.Extra.Function;
import com.mhvmedia.unknowncaller.R;
import com.mhvmedia.unknowncaller.Model.ContactInfo;

import java.util.ArrayList;
import java.util.List;
/** Created by AwsmCreators * */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<ContactInfo> contactList;
    private List<ContactInfo> contactListFiltered;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;
        LinearLayout itemclick;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.displayName);
            phone = view.findViewById(R.id.phoneNumber);
            itemclick = view.findViewById(R.id.item_click);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    //listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public ContactAdapter(Context context, List<ContactInfo> contactList) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_info, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ContactInfo contact = contactListFiltered.get(position);
        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getNumber());
        holder.itemclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(contact.getName(),contact.getNumber());
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<ContactInfo> filteredList = new ArrayList<>();
                    for (ContactInfo row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getNumber().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<ContactInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    private void showDialog(String Contactname, String Contactnumber) {
        Dialog callOptionDialog = new Dialog(context);
        callOptionDialog.setContentView(R.layout.call_option_dialog);
        callOptionDialog.getWindow().setBackgroundDrawable(new ColorDrawable
                (Color.TRANSPARENT));
        ImageView call, back;
        TextView name, number, charges;
        call = (ImageView) callOptionDialog.findViewById
                (R.id.call);
        back = (ImageView) callOptionDialog.findViewById(R.id.back);
        name = callOptionDialog.findViewById(R.id.contact_name);
        number = callOptionDialog.findViewById(R.id.contact_number);
        charges = callOptionDialog.findViewById(R.id.charges);
        name.setText(Contactname);
        number.setText(Contactnumber);
        String callrate = Function.checkCountry(Contactnumber);
        if (Contactnumber.startsWith("+")){
            charges.setText("Call charges: "+callrate+" credits/min");
        }else {
            charges.setText("Call charges: Unknown/min");
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).CallNumber(Contactnumber);
                callOptionDialog.dismiss();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callOptionDialog.dismiss();
            }
        });


        callOptionDialog.setCancelable(false);
        callOptionDialog.show();
    }
}
