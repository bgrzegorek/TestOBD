package com.testobd;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


public class WorkingCommandsActivity extends ActionBarActivity {

    TextView textView;
    String workingCommands;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_commands);

        textView = (TextView) findViewById(R.id.textView);

        textView.setText(getIntent().getStringExtra("WORKING_COMMANDS"));
        writeToFile(getIntent().getStringExtra("CSV_RESULT"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_working_commands, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    private void writeToFile(String data) {
        File myFile = null;
        try {
            myFile = new File("/sdcard/obd_test.csv");
            if(myFile.exists()){
                myFile.delete();
            }
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.close();
            Toast.makeText(getBaseContext(),
                    "Done writing SD '/sdcard/obd_test.csv'",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

        if(myFile != null) {
            // Send file
            String file_location = myFile.getAbsolutePath();
            Toast.makeText(WorkingCommandsActivity.this, file_location, Toast.LENGTH_SHORT).show();
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            // set the type to 'email'
            emailIntent.setType("vnd.android.cursor.dir/email");
            String to[] = {"ecodriverme@gmail.com"};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
            // the attachment
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file_location));
            // the mail subject
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test OBD...");
            // the mail body
            emailIntent.putExtra(Intent.EXTRA_TEXT, ".csv file attached");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));


        }
    }
}
