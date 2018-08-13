package air.errornotifier;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import air.database.helper.Constants;

/**
 * Created by Oliver on 22-Jan-18.
 */



public class ResponseDialog extends AppCompatDialogFragment  {

    private Button potvrdi;
    private RadioButton preuzimam;
    private RadioButton odbijam;
    private ResponseDialogListener listener;
    private List<String> odgovori;
    private String appName="";

    @SuppressLint("ValidFragment")
    public ResponseDialog(String appName){
        this.appName = appName;
    }

    public List<String> getOdgovori(){
        return odgovori;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.response_layout,null);

        odgovori = new ArrayList<String>();
        builder.setView(view)
                .setTitle("Problem with "+ appName);

        potvrdi = view.findViewById(R.id.btnPotvrdi);
        potvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preuzimam.isChecked()){
                    String odabir = Constants.STATUS_IN_PROGRESS;
                    listener.applyTexts(odabir);
                    odgovori.add(odabir);
                    ResponseDialog.this.dismiss();
                }else if(odbijam.isChecked()){
                    String odabir = Constants.STATUS_UNSOLVED;
                    listener.applyTexts(odabir);
                    odgovori.add(odabir);
                    ResponseDialog.this.dismiss();
                }
            }
        });
        preuzimam = view.findViewById(R.id.rbtnPreuzimam);
        odbijam = view.findViewById(R.id.rbtnOdbijam);
        final AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ResponseDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "Must implement ExampleDialogListener");
        }
    }

    public interface ResponseDialogListener{
        void applyTexts(String resposne);
    }

}


