package com.ibrahim.selfsoultiontask.teamLeader;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ibrahim.selfsoultiontask.R;

public class AddNewProjectFragment extends DialogFragment implements View.OnClickListener  {
    private static final String TAG = "AddNewLocalHagzFragment";

    //views
    EditText projectName, description;
    Button mButton ;

    OnAddNewHags mOnAddNewHags;
    int requestHour;
    String hagsname;
    String hagsphone;


    public interface OnAddNewHags{
        void onAddNewProject(String projectName, String projectDescription);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_pproject_fragment , container , false);

//        requestHour = getArguments().getInt("num");
//        positionInRecycler = getArguments().getInt("requestHour");
//        hagsname = getArguments().getString("projectName","");
//        hagsphone = getArguments().getString("description","");
//        hagsnote = getArguments().getString("note","");
//        editedHours = getArguments().getInt("editedHours");
//        description.setText(hagsphone);
//        projectName.setText(hagsname);


        projectName = view.findViewById(R.id.project_name);
        description = view.findViewById(R.id.project_description);
        mButton = view.findViewById(R.id.button6);
        mButton.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        //send data to ManageMal3abFragment1 to add new request with request status ==3

        mOnAddNewHags.onAddNewProject(projectName.getText().toString() , description.getText().toString());
        this.dismiss();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnAddNewHags = (TeamLeaderMain) getActivity();
        }catch (ClassCastException e){
            Log.d(TAG, "onAttach: exception");
            e.getMessage();
        }
    }


}
