package com.testobd;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pires.obd.commands.Marcin.ActualEnginePercentTorque;
import com.github.pires.obd.commands.Marcin.AuxInputOutput;
import com.github.pires.obd.commands.Marcin.BoostPreasureControl;
import com.github.pires.obd.commands.Marcin.ChargeAirCoolerTemperature;
import com.github.pires.obd.commands.Marcin.CommandedDieselIntakeAirFlowControl;
import com.github.pires.obd.commands.Marcin.CommandedEgrAndEgrError;
import com.github.pires.obd.commands.Marcin.CommandedThrottleActuatorControl;
import com.github.pires.obd.commands.Marcin.DieselParticulateFilter1;
import com.github.pires.obd.commands.Marcin.DieselParticulateFilter2;
import com.github.pires.obd.commands.Marcin.DieselParticulateFilterTemperature;
import com.github.pires.obd.commands.Marcin.DriversDemandEnginePercentTorque;
import com.github.pires.obd.commands.Marcin.EmissionRequirementsObdCommand;
import com.github.pires.obd.commands.Marcin.EngineCoolantTemperature;
import com.github.pires.obd.commands.Marcin.EnginePercentTorqueData;
import com.github.pires.obd.commands.Marcin.EngineReferenceTorque;
import com.github.pires.obd.commands.Marcin.ExhaustGasRecirculationTemperature;
import com.github.pires.obd.commands.Marcin.ExhaustGasTemperatureBank1;
import com.github.pires.obd.commands.Marcin.ExhaustGasTemperatureBank2;
import com.github.pires.obd.commands.Marcin.ExhaustPressure;
import com.github.pires.obd.commands.Marcin.FuelPressureControlSystem;
import com.github.pires.obd.commands.Marcin.InjectionPressureControlSystem;
import com.github.pires.obd.commands.Marcin.IntakeAirTemperatureSensor;
import com.github.pires.obd.commands.Marcin.MaxAirFlowRateObdCommand;
import com.github.pires.obd.commands.Marcin.MaxValForSensorsObdCommand;
import com.github.pires.obd.commands.Marcin.MonitorDriveCycleObdCommand;
import com.github.pires.obd.commands.Marcin.TurbochargerCompressorInletPressure;
import com.github.pires.obd.commands.Marcin.TurbochargerRPM;
import com.github.pires.obd.commands.Marcin.TurbochargerTemperature1;
import com.github.pires.obd.commands.Marcin.TurbochargerTemperature2;
import com.github.pires.obd.commands.Marcin.VariableGeometryTurboControl;
import com.github.pires.obd.commands.Marcin.WastegateControl;
import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.control.CommandControlModuleVoltageObdCommand;
import com.github.pires.obd.commands.control.CommandEquivRatioObdCommand;
import com.github.pires.obd.commands.control.DistanceTraveledSinceCodesClearedObdCommand;
import com.github.pires.obd.commands.control.DistanceTraveledWithMILOnObdCommand;
import com.github.pires.obd.commands.control.DtcNumberObdCommand;
import com.github.pires.obd.commands.control.FuelInjectionTimingObdCommand;
import com.github.pires.obd.commands.control.TimingAdvanceObdCommand;
import com.github.pires.obd.commands.control.TroubleCodesObdCommand;
import com.github.pires.obd.commands.control.VinObdCommand;
import com.github.pires.obd.commands.engine.AbsoluteLoadObdCommand;
import com.github.pires.obd.commands.engine.AbsoluteThrottlePositionBObdCommand;
import com.github.pires.obd.commands.engine.AbsoluteThrottlePositionCObdCommand;
import com.github.pires.obd.commands.engine.AbsoluteThrottlePositionDObdCommand;
import com.github.pires.obd.commands.engine.AbsoluteThrottlePositionEObdCommand;
import com.github.pires.obd.commands.engine.AbsoluteThrottlePositionFObdCommand;
import com.github.pires.obd.commands.engine.CommandedThrottleActuatorObdCommand;
import com.github.pires.obd.commands.engine.EngineFuelRate;
import com.github.pires.obd.commands.engine.EngineLoadObdCommand;
import com.github.pires.obd.commands.engine.EngineOilTempObdCommand;
import com.github.pires.obd.commands.engine.EngineRPMObdCommand;
import com.github.pires.obd.commands.engine.EngineRuntimeObdCommand;
import com.github.pires.obd.commands.engine.EngineRuntimeWithMILOnObdCommand;
import com.github.pires.obd.commands.engine.EthanolFuelObdCommand;
import com.github.pires.obd.commands.engine.HybridBatteryRemainingObdCommand;
import com.github.pires.obd.commands.engine.LongTermSecondaryOxygenB1And3ObdCommand;
import com.github.pires.obd.commands.engine.LongTermSecondaryOxygenB2And4ObdCommand;
import com.github.pires.obd.commands.engine.MassAirFlowObdCommand;
import com.github.pires.obd.commands.engine.RelativeAcceleratorPedalPositionObdCommand;
import com.github.pires.obd.commands.engine.RelativeThrottlePositionObdCommand;
import com.github.pires.obd.commands.engine.ShortTermSecondaryOxygenB1And3ObdCommand;
import com.github.pires.obd.commands.engine.ShortTermSecondaryOxygenB2And4ObdCommand;
import com.github.pires.obd.commands.engine.ThrottlePositionObdCommand;
import com.github.pires.obd.commands.engine.TimeSinceTroubleCodesClearedObdCommand;
import com.github.pires.obd.commands.fuel.FuelAirCommanded;
import com.github.pires.obd.commands.fuel.FuelAirWidebandCommanded;
import com.github.pires.obd.commands.fuel.FuelAirWidebandS2Commanded;
import com.github.pires.obd.commands.fuel.FuelAirWidebandS3Commanded;
import com.github.pires.obd.commands.fuel.FuelAirWidebandS4Commanded;
import com.github.pires.obd.commands.fuel.FuelAirWidebandS5Commanded;
import com.github.pires.obd.commands.fuel.FuelAirWidebandS6Commanded;
import com.github.pires.obd.commands.fuel.FuelAirWidebandS7Commanded;
import com.github.pires.obd.commands.fuel.FuelAirWidebandS8Commanded;
import com.github.pires.obd.commands.fuel.FuelConsumptionRateObdCommand;
import com.github.pires.obd.commands.fuel.FuelEconomyObdCommand;
import com.github.pires.obd.commands.fuel.FuelLevelObdCommand;
import com.github.pires.obd.commands.fuel.FuelTrimObdCommand;
import com.github.pires.obd.commands.mikolaj.FindFuelSystemObdCommand;
import com.github.pires.obd.commands.pressure.AbsoluteEvapObdCommand;
import com.github.pires.obd.commands.pressure.EvapObdCommand;
import com.github.pires.obd.commands.pressure.FuelRailAbsoluteObdCommand;
import com.github.pires.obd.commands.protocol.AvailablePids3ObdCommand;
import com.github.pires.obd.commands.protocol.AvailablePids4ObdCommand;
import com.github.pires.obd.commands.temperature.CatalystTemperatureB1S1ObdCommand;
import com.github.pires.obd.commands.temperature.CatalystTemperatureB1S2ObdCommand;
import com.github.pires.obd.commands.temperature.CatalystTemperatureB2S1ObdCommand;
import com.github.pires.obd.commands.temperature.CatalystTemperatureB2S2ObdCommand;
import com.github.pires.obd.exceptions.MisunderstoodCommandException;
import com.github.pires.obd.exceptions.ObdResponseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;


public class TestOBDActivity extends ActionBarActivity {

    private String name, address;
    TextView txt_name;
    TextView txt_commands;

    String csv_result;
    String command_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_obd);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_commands = (TextView) findViewById(R.id.txt_commands);
        txt_commands.setEnabled(false);

        name = getIntent().getStringExtra("NAME");
        address = getIntent().getStringExtra("ADDRESS");
        txt_name.setText(name);
        new OBDAsyncTask().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_obd, menu);
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


    private class OBDAsyncTask extends AsyncTask<Void, String, Boolean>{

        BluetoothSocket socket;
        public OBDAsyncTask(){
            Log.d("OBD:: ", "constructor");
            this.socket = new BluetoothConnection(TestOBDActivity.this, address).getSocket();
            Log.d("OBD:: ", "constructor finished");

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            txt_commands.setText("Starting connection\n");
            try {
                new com.github.pires.obd.commands.protocol.EchoOffObdCommand().run(socket.getInputStream(), socket.getOutputStream());
                new com.github.pires.obd.commands.protocol.LineFeedOffObdCommand().run(socket.getInputStream(), socket.getOutputStream());
                new com.github.pires.obd.commands.protocol.TimeoutObdCommand(255).run(socket.getInputStream(), socket.getOutputStream());
                new com.github.pires.obd.commands.protocol.SelectProtocolObdCommand(com.github.pires.obd.enums.ObdProtocols.AUTO).run(socket.getInputStream(), socket.getOutputStream());
                publishProgress("Connected");
                Log.d("OBD_TEST: ", "Connected");
            } catch (IOException e) {
                Log.d("OBD_TEST: ", "IOException: " + e.toString());
                publishProgress("IO Exception");
                e.printStackTrace();
            } catch (InterruptedException e) {
                Log.d("OBD_TEST: ", "InterruptedException: " + e.toString());
                publishProgress("InterruptedException");
                e.printStackTrace();
            }

            /**
             *      Test Commands
             */


            if(socket != null) {
                /*******************************************************************************/
                csv_result += "\n Control commands: \n";
                publishProgress(" Control commands... \n");
                testObdcommand(new CommandControlModuleVoltageObdCommand());
                testObdcommand(new CommandEquivRatioObdCommand());
                testObdcommand(new DistanceTraveledSinceCodesClearedObdCommand());
                testObdcommand(new DistanceTraveledWithMILOnObdCommand());
                testObdcommand(new DtcNumberObdCommand());
                testObdcommand(new TimingAdvanceObdCommand());
                testObdcommand(new TroubleCodesObdCommand());
                testObdcommand(new VinObdCommand());


                /*******************************************************************************/
                csv_result += "\n Engine commands: \n";
                publishProgress(" Engine commands... \n");
                testObdcommand(new AbsoluteLoadObdCommand());
                testObdcommand(new EngineFuelRate());
                testObdcommand(new EngineLoadObdCommand());
                testObdcommand(new EngineOilTempObdCommand());
                testObdcommand(new EngineRPMObdCommand());
                testObdcommand(new EngineRuntimeObdCommand());
                testObdcommand(new MassAirFlowObdCommand());
                testObdcommand(new ThrottlePositionObdCommand());

                /*******************************************************************************/
                csv_result += "\n Fuel commands: \n";
                publishProgress(" Fuel commands... \n");
                testObdcommand(new FindFuelSystemObdCommand());
                testObdcommand(new FuelAirCommanded());
                testObdcommand(new FuelAirWidebandCommanded());
                testObdcommand(new FuelConsumptionRateObdCommand());
                testObdcommand(new FuelEconomyObdCommand());
                testObdcommand(new FuelLevelObdCommand());
                testObdcommand(new FuelTrimObdCommand());

                /*******************************************************************************/
                csv_result += "\n Marcin commands: \n";
                publishProgress(" Marcin commands... \n");
                testObdcommand(new ActualEnginePercentTorque());
                testObdcommand(new AuxInputOutput());
                testObdcommand(new BoostPreasureControl());
                testObdcommand(new ChargeAirCoolerTemperature());
                testObdcommand(new CommandedDieselIntakeAirFlowControl());
                testObdcommand(new CommandedEgrAndEgrError());
                testObdcommand(new CommandedThrottleActuatorControl());
                testObdcommand(new DieselParticulateFilter1());
                testObdcommand(new DieselParticulateFilter2());
                testObdcommand(new DieselParticulateFilterTemperature());
                testObdcommand(new DriversDemandEnginePercentTorque());
                testObdcommand(new EngineCoolantTemperature());
                testObdcommand(new EnginePercentTorqueData());
                testObdcommand(new EngineReferenceTorque());
                testObdcommand(new ExhaustGasRecirculationTemperature());
                testObdcommand(new ExhaustGasTemperatureBank1());
                testObdcommand(new ExhaustGasTemperatureBank2());
                testObdcommand(new ExhaustPressure());
                testObdcommand(new FuelPressureControlSystem());
                testObdcommand(new InjectionPressureControlSystem());
                testObdcommand(new IntakeAirTemperatureSensor());
                testObdcommand(new MassAirFlowObdCommand());
                testObdcommand(new TurbochargerCompressorInletPressure());
                testObdcommand(new TurbochargerRPM());
                testObdcommand(new TurbochargerTemperature1());
                testObdcommand(new TurbochargerTemperature2());
                testObdcommand(new VariableGeometryTurboControl());
                testObdcommand(new WastegateControl());

                /*******************************************************************************/
                csv_result += "\n MatherLover commands: \n";
                publishProgress(" MatherLover commands... \n");
                testObdcommand(new AbsoluteEvapObdCommand());
                testObdcommand(new AbsoluteThrottlePositionBObdCommand());
                testObdcommand(new AbsoluteThrottlePositionCObdCommand());
                testObdcommand(new AbsoluteThrottlePositionDObdCommand());
                testObdcommand(new AbsoluteThrottlePositionEObdCommand());
                testObdcommand(new AbsoluteThrottlePositionFObdCommand());
                testObdcommand(new AvailablePids3ObdCommand());
                testObdcommand(new AvailablePids4ObdCommand());
                testObdcommand(new CatalystTemperatureB1S1ObdCommand());
                testObdcommand(new CatalystTemperatureB1S2ObdCommand());
                testObdcommand(new CatalystTemperatureB2S1ObdCommand());
                testObdcommand(new CatalystTemperatureB2S2ObdCommand());
                testObdcommand(new CommandedThrottleActuatorObdCommand());
                testObdcommand(new EmissionRequirementsObdCommand());
                testObdcommand(new EngineRuntimeWithMILOnObdCommand());
                testObdcommand(new EthanolFuelObdCommand());
                testObdcommand(new EvapObdCommand());
                testObdcommand(new FuelAirWidebandS2Commanded());
                testObdcommand(new FuelAirWidebandS3Commanded());
                testObdcommand(new FuelAirWidebandS4Commanded());
                testObdcommand(new FuelAirWidebandS5Commanded());
                testObdcommand(new FuelAirWidebandS6Commanded());
                testObdcommand(new FuelAirWidebandS7Commanded());
                testObdcommand(new FuelAirWidebandS8Commanded());
                testObdcommand(new FuelInjectionTimingObdCommand());
                testObdcommand(new FuelRailAbsoluteObdCommand());
                testObdcommand(new HybridBatteryRemainingObdCommand());
                testObdcommand(new LongTermSecondaryOxygenB1And3ObdCommand());
                testObdcommand(new LongTermSecondaryOxygenB2And4ObdCommand());
                testObdcommand(new MaxAirFlowRateObdCommand());
                testObdcommand(new MaxValForSensorsObdCommand());
                testObdcommand(new MonitorDriveCycleObdCommand());
                testObdcommand(new RelativeAcceleratorPedalPositionObdCommand());
                testObdcommand(new RelativeThrottlePositionObdCommand());
                testObdcommand(new ShortTermSecondaryOxygenB1And3ObdCommand());
                testObdcommand(new ShortTermSecondaryOxygenB2And4ObdCommand());
                testObdcommand(new TimeSinceTroubleCodesClearedObdCommand());




            } else {
                return Boolean.FALSE;
            }

            return Boolean.TRUE;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            txt_commands.append("\n" + values[0]);
            //csv_result += command_result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result == Boolean.TRUE) {
                writeToFile(csv_result);
            } else {
                Toast.makeText(TestOBDActivity.this, "bluetooth socket == null", Toast.LENGTH_LONG);
            }
        }

        private void testObdcommand(ObdCommand command){

            if(socket == null) {
                Toast.makeText(TestOBDActivity.this, "bluetooth socket == null", Toast.LENGTH_LONG);
            } else {
                csv_result += command.getName();
                csv_result += ";";
                try {
                    command.run(socket.getInputStream(), socket.getOutputStream());
                    csv_result += command.getResult();
                } catch (ObdResponseException e) {
                    csv_result += e.getMessage();
                } catch (Exception e) {
                    csv_result += e.getMessage();
                }
                csv_result += "\n";
            }
        }
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
            Toast.makeText(TestOBDActivity.this, file_location, Toast.LENGTH_SHORT).show();
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
