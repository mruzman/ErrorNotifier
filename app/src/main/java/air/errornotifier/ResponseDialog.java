package air.errornotifier;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import air.database.Bean.Response;

/**
 * Created by Oliver on 22-Jan-18.
 */



public class ResponseDialog extends AppCompatDialogFragment  {

    private Button potvrdi;
    private RadioButton preuzimam;
    private RadioButton kasnije;
    private RadioButton odbijam;
    private EditText odgovor;
    private ResponseDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.response_layout,null);

        builder.setView(view)
                .setTitle("Problem with (naziv aplikacije)");

        potvrdi = view.findViewById(R.id.btnPotvrdi);
        potvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preuzimam.isChecked()){
                    String odabir = preuzimam.getText().toString();
                    listener.applyTexts(odabir);
                    ResponseDialog.this.dismiss();
                }else if(odbijam.isChecked()){
                    String odabir2 = odbijam.getText().toString();
                    listener.applyTexts(odabir2);
                    ResponseDialog.this.dismiss();
                }else if(kasnije.isChecked()){
                    String odabir3 = kasnije.getText().toString();
                    listener.applyTexts(odabir3);
                    ResponseDialog.this.dismiss();
                }else {
                    String odg = odgovor.getText().toString();
                    listener.applyTexts(odg);
                    ResponseDialog.this.dismiss();
                }
            }
        });
        preuzimam = view.findViewById(R.id.rbtnPreuzimam);
        odbijam = view.findViewById(R.id.rbtnOdbijam);
        kasnije = view.findViewById(R.id.rbtnKasnije);
        odgovor = view.findViewById(R.id.txtPoruka);
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


