package com.testobd;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pires.obd.commands.Marcin.ActualEnginePercentTorqueObdCommand;
import com.github.pires.obd.commands.Marcin.AuxInputOutputObdCommand;
import com.github.pires.obd.commands.Marcin.AvailablePids8ObdCommand;
import com.github.pires.obd.commands.Marcin.BoostPreasureControlObdCommand;
import com.github.pires.obd.commands.Marcin.ChargeAirCoolerTemperatureObdCommand;
import com.github.pires.obd.commands.Marcin.CommandedDieselIntakeAirFlowControlObdCommand;
import com.github.pires.obd.commands.Marcin.CommandedEgrAndEgrErrorObdCommand;
import com.github.pires.obd.commands.Marcin.CommandedThrottleActuatorControlObdCommand;
import com.github.pires.obd.commands.Marcin.DieselParticulateFilter1ObdCommand;
import com.github.pires.obd.commands.Marcin.DieselParticulateFilter2ObdCommand;
import com.github.pires.obd.commands.Marcin.DieselParticulateFilterTemperatureObdCommand;
import com.github.pires.obd.commands.Marcin.DriversDemandEnginePercentTorqueObdCommand;
import com.github.pires.obd.commands.Marcin.EmissionRequirementsObdCommand;
import com.github.pires.obd.commands.Marcin.EnginePercentTorqueDataObdCommand;
import com.github.pires.obd.commands.Marcin.EngineReferenceTorqueObdCommand;
import com.github.pires.obd.commands.Marcin.EngineRunTimeForAuxiliaryEmissionsControlDevice1ObdCommand;
import com.github.pires.obd.commands.Marcin.EngineRunTimeForAuxiliaryEmissionsControlDevice2ObdCommand;
import com.github.pires.obd.commands.Marcin.EngineRunTimeObdCommand;
import com.github.pires.obd.commands.Marcin.ExhaustGasRecirculationTemperatureObdCommand;
import com.github.pires.obd.commands.Marcin.ExhaustGasTemperatureBank1ObdCommand;
import com.github.pires.obd.commands.Marcin.ExhaustGasTemperatureBank2ObdCommand;
import com.github.pires.obd.commands.Marcin.ExhaustPressureObdCommand;
import com.github.pires.obd.commands.Marcin.FuelPressureControlSystemObdCommand;
import com.github.pires.obd.commands.Marcin.InjectionPressureControlSystemObdCommand;
import com.github.pires.obd.commands.Marcin.IntakeAirTemperatureSensorObdCommand;
import com.github.pires.obd.commands.Marcin.IntakeManifoldAbsolutePressureObdCommand;
import com.github.pires.obd.commands.Marcin.ManifoldSurfaceTemperatureObdCommand;
import com.github.pires.obd.commands.Marcin.MassAirFlowSensorObdCommand;
import com.github.pires.obd.commands.Marcin.MaxAirFlowRateObdCommand;
import com.github.pires.obd.commands.Marcin.MaxValForSensorsObdCommand;
import com.github.pires.obd.commands.Marcin.MonitorDriveCycleObdCommand;
import com.github.pires.obd.commands.Marcin.NOxNTEControlAreaStatusObdCommand;
import com.github.pires.obd.commands.Marcin.NOxReagentSystemObdCommand;
import com.github.pires.obd.commands.Marcin.NOxSensorObdCommand;
import com.github.pires.obd.commands.Marcin.ParticulateMatterSensorObdCommand;
import com.github.pires.obd.commands.Marcin.PmNTEControlAreaStatusObdCommand;
import com.github.pires.obd.commands.Marcin.TurbochargerCompressorInletPressureObdCommand;
import com.github.pires.obd.commands.Marcin.TurbochargerRPMObdCommand;
import com.github.pires.obd.commands.Marcin.TurbochargerTemperature1ObdCommand;
import com.github.pires.obd.commands.Marcin.TurbochargerTemperature2ObdCommand;
import com.github.pires.obd.commands.Marcin.VariableGeometryTurboControlObdCommand;
import com.github.pires.obd.commands.Marcin.WastegateControlObdCommand;
import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.SpeedObdCommand;
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
import com.github.pires.obd.commands.fuel.FuelAirWidebandS2CommandedObdCommand;
import com.github.pires.obd.commands.fuel.FuelAirWidebandS3CommandedObdCommand;
import com.github.pires.obd.commands.fuel.FuelAirWidebandS4CommandedObdCommand;
import com.github.pires.obd.commands.fuel.FuelAirWidebandS5CommandedObdCommand;
import com.github.pires.obd.commands.fuel.FuelAirWidebandS6CommandedObdCommand;
import com.github.pires.obd.commands.fuel.FuelAirWidebandS7CommandedObdCommand;
import com.github.pires.obd.commands.fuel.FuelAirWidebandS8CommandedObdCommand;
import com.github.pires.obd.commands.fuel.FuelConsumptionRateObdCommand;
import com.github.pires.obd.commands.fuel.FuelEconomyObdCommand;
import com.github.pires.obd.commands.fuel.FuelLevelObdCommand;
import com.github.pires.obd.commands.fuel.FuelTrimObdCommand;
import com.github.pires.obd.commands.mikolaj.AuxillaryInputStatusObdCommand;
import com.github.pires.obd.commands.mikolaj.AvailablePids2ObdCommand;
import com.github.pires.obd.commands.mikolaj.BankSensorShortTerm.Bank1Sensor1ShortBankSensorShortTermFuelObdCommand;
import com.github.pires.obd.commands.mikolaj.BankSensorShortTerm.Bank1Sensor2ShortBankSensorShortTermFuelObdCommand;
import com.github.pires.obd.commands.mikolaj.BankSensorShortTerm.Bank1Sensor3ShortBankSensorShortTermFuelObdCommand;
import com.github.pires.obd.commands.mikolaj.BankSensorShortTerm.Bank1Sensor4ShortBankSensorShortTermFuelObdCommand;
import com.github.pires.obd.commands.mikolaj.BankSensorShortTerm.Bank2Sensor1ShortBankSensorShortTermFuelObdCommand;
import com.github.pires.obd.commands.mikolaj.BankSensorShortTerm.Bank2Sensor2ShortBankSensorShortTermFuelObdCommand;
import com.github.pires.obd.commands.mikolaj.BankSensorShortTerm.Bank2Sensor3ShortTermBankSensorTermFuelObdCommand;
import com.github.pires.obd.commands.mikolaj.BankSensorShortTerm.Bank2Sensor4ShortBankSensorShortTermFuelObdCommand;
import com.github.pires.obd.commands.mikolaj.CommandedERGObdCommand;
import com.github.pires.obd.commands.mikolaj.CommandedEvaporativePurgeObdCommand;
import com.github.pires.obd.commands.mikolaj.CommandedSecondaryAirStatusObdCommand;
import com.github.pires.obd.commands.mikolaj.EGRErrorObdCommand;
import com.github.pires.obd.commands.mikolaj.EvapSystemVaporPressureObdCommand;
import com.github.pires.obd.commands.mikolaj.FindFuelSystemObdCommand;
import com.github.pires.obd.commands.mikolaj.FreezeDTCObdCommand;
import com.github.pires.obd.commands.mikolaj.FuelRailPressure2ObdCommand;
import com.github.pires.obd.commands.mikolaj.ObdStandardObdCommand;
import com.github.pires.obd.commands.mikolaj.OxygenSensor.Bank1Sensor1OxygenSensorObdCommand;
import com.github.pires.obd.commands.mikolaj.OxygenSensor.Bank1Sensor2OxygenSensorObdCommand;
import com.github.pires.obd.commands.mikolaj.OxygenSensor.Bank1Sensor3OxygenSensorObdCommand;
import com.github.pires.obd.commands.mikolaj.OxygenSensor.Bank1Sensor4OxygenSensorObdCommand;
import com.github.pires.obd.commands.mikolaj.OxygenSensor.Bank2Sensor1OxygenSensorObdCommand;
import com.github.pires.obd.commands.mikolaj.OxygenSensor.Bank2Sensor2OxygenSensorObdCommand;
import com.github.pires.obd.commands.mikolaj.OxygenSensor.Bank2Sensor3OxygenSensorObdCommand;
import com.github.pires.obd.commands.mikolaj.OxygenSensor.Bank2Sensor4OxygenSensorObdCommand;
import com.github.pires.obd.commands.mikolaj.OxygenSensorsPresent2ObdCommand;
import com.github.pires.obd.commands.mikolaj.OxygenSensorsPresentObdCommand;
import com.github.pires.obd.commands.mikolaj.TermFuel.Bank1LongTermFuelObdCommand;
import com.github.pires.obd.commands.mikolaj.TermFuel.Bank1ShortTermFuelObdCommand;
import com.github.pires.obd.commands.mikolaj.TermFuel.Bank2LongTermFuelObdCommand;
import com.github.pires.obd.commands.mikolaj.TermFuel.Bank2ShortTermFuelObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaEquivalenceRatio.S1WRLambdaEquivalenceRatioObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaEquivalenceRatio.S2WRLambdaEquivalenceRatioObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaEquivalenceRatio.S3WRLambdaEquivalenceRatioObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaEquivalenceRatio.S4WRLambdaEquivalenceRatioObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaEquivalenceRatio.S5WRLambdaEquivalenceRatioObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaEquivalenceRatio.S6WRLambdaEquivalenceRatioObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaEquivalenceRatio.S7WRLambdaEquivalenceRatioObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaEquivalenceRatio.S8WRLambdaEquivalenceRatioObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaVoltage.S1WRLambdaVoltageObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaVoltage.S2WRLambdaVoltageObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaVoltage.S3WRLambdaVoltageObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaVoltage.S4WRLambdaVoltageObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaVoltage.S5WRLambdaVoltageObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaVoltage.S6WRLambdaVoltageObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaVoltage.S7WRLambdaVoltageObdCommand;
import com.github.pires.obd.commands.mikolaj.WRLambdaVoltage.S8WRLambdaVoltageObdCommand;
import com.github.pires.obd.commands.mikolaj.WarmUpsObdCommand;
import com.github.pires.obd.commands.pressure.AbsoluteEvapObdCommand;
import com.github.pires.obd.commands.pressure.BarometricPressureObdCommand;
import com.github.pires.obd.commands.pressure.EvapObdCommand;
import com.github.pires.obd.commands.pressure.FuelPressureObdCommand;
import com.github.pires.obd.commands.pressure.FuelRailAbsoluteObdCommand;
import com.github.pires.obd.commands.pressure.FuelRailPressureObdCommand;
import com.github.pires.obd.commands.pressure.IntakeManifoldPressureObdCommand;
import com.github.pires.obd.commands.protocol.AvailablePids3ObdCommand;
import com.github.pires.obd.commands.protocol.AvailablePids4ObdCommand;
import com.github.pires.obd.commands.temperature.AirIntakeTemperatureObdCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureObdCommand;
import com.github.pires.obd.commands.temperature.CatalystTemperatureB1S1ObdCommand;
import com.github.pires.obd.commands.temperature.CatalystTemperatureB1S2ObdCommand;
import com.github.pires.obd.commands.temperature.CatalystTemperatureB2S1ObdCommand;
import com.github.pires.obd.commands.temperature.CatalystTemperatureB2S2ObdCommand;
import com.github.pires.obd.commands.temperature.EngineCoolantTemperatureObdCommand;
import com.github.pires.obd.exceptions.ObdResponseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


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
                testObdcommand(new ActualEnginePercentTorqueObdCommand());
                testObdcommand(new AuxInputOutputObdCommand());
                testObdcommand(new AvailablePids8ObdCommand());
                testObdcommand(new BoostPreasureControlObdCommand());
                testObdcommand(new ChargeAirCoolerTemperatureObdCommand());
                testObdcommand(new CommandedDieselIntakeAirFlowControlObdCommand());
                testObdcommand(new CommandedEgrAndEgrErrorObdCommand());
                testObdcommand(new CommandedThrottleActuatorControlObdCommand());
                testObdcommand(new DieselParticulateFilter1ObdCommand());
                testObdcommand(new DieselParticulateFilter2ObdCommand());
                testObdcommand(new DieselParticulateFilterTemperatureObdCommand());
                testObdcommand(new DriversDemandEnginePercentTorqueObdCommand());
                testObdcommand(new EngineCoolantTemperatureObdCommand());
                testObdcommand(new EnginePercentTorqueDataObdCommand());
                testObdcommand(new EngineReferenceTorqueObdCommand());
                testObdcommand(new EngineRunTimeForAuxiliaryEmissionsControlDevice1ObdCommand());
                testObdcommand(new EngineRunTimeForAuxiliaryEmissionsControlDevice2ObdCommand());
                testObdcommand(new EngineRunTimeObdCommand());
                testObdcommand(new ExhaustGasRecirculationTemperatureObdCommand());
                testObdcommand(new ExhaustGasTemperatureBank1ObdCommand());
                testObdcommand(new ExhaustGasTemperatureBank2ObdCommand());
                testObdcommand(new ExhaustPressureObdCommand());
                testObdcommand(new FuelPressureControlSystemObdCommand());
                testObdcommand(new InjectionPressureControlSystemObdCommand());
                testObdcommand(new IntakeAirTemperatureSensorObdCommand());
                testObdcommand(new IntakeManifoldAbsolutePressureObdCommand());
                testObdcommand(new ManifoldSurfaceTemperatureObdCommand());
                testObdcommand(new MassAirFlowSensorObdCommand());
                testObdcommand(new NOxNTEControlAreaStatusObdCommand());
                testObdcommand(new NOxReagentSystemObdCommand());
                testObdcommand(new NOxSensorObdCommand());
                testObdcommand(new ParticulateMatterSensorObdCommand());
                testObdcommand(new PmNTEControlAreaStatusObdCommand());
                testObdcommand(new TurbochargerCompressorInletPressureObdCommand());
                testObdcommand(new TurbochargerRPMObdCommand());
                testObdcommand(new TurbochargerTemperature1ObdCommand());
                testObdcommand(new TurbochargerTemperature2ObdCommand());
                testObdcommand(new VariableGeometryTurboControlObdCommand());
                testObdcommand(new WastegateControlObdCommand());

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
                testObdcommand(new FuelAirWidebandS2CommandedObdCommand());
                testObdcommand(new FuelAirWidebandS3CommandedObdCommand());
                testObdcommand(new FuelAirWidebandS4CommandedObdCommand());
                testObdcommand(new FuelAirWidebandS5CommandedObdCommand());
                testObdcommand(new FuelAirWidebandS6CommandedObdCommand());
                testObdcommand(new FuelAirWidebandS7CommandedObdCommand());
                testObdcommand(new FuelAirWidebandS8CommandedObdCommand());
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

                csv_result += "\n mikolaj commands: \n";
                publishProgress(" mikolaj commands... \n");
                testObdcommand(new AuxillaryInputStatusObdCommand());
                testObdcommand(new AvailablePids2ObdCommand());
                testObdcommand(new Bank1Sensor1ShortBankSensorShortTermFuelObdCommand());
                testObdcommand(new Bank1Sensor2ShortBankSensorShortTermFuelObdCommand());
                testObdcommand(new Bank1Sensor3ShortBankSensorShortTermFuelObdCommand());
                testObdcommand(new Bank1Sensor4ShortBankSensorShortTermFuelObdCommand());
                testObdcommand(new Bank2Sensor1ShortBankSensorShortTermFuelObdCommand());
                testObdcommand(new Bank2Sensor2ShortBankSensorShortTermFuelObdCommand());
                testObdcommand(new Bank2Sensor3ShortTermBankSensorTermFuelObdCommand());
                testObdcommand(new Bank2Sensor4ShortBankSensorShortTermFuelObdCommand());
                testObdcommand(new CommandedERGObdCommand());
                testObdcommand(new CommandedEvaporativePurgeObdCommand());
                testObdcommand(new CommandedSecondaryAirStatusObdCommand());
                testObdcommand(new EGRErrorObdCommand());
                testObdcommand(new EvapSystemVaporPressureObdCommand());
                testObdcommand(new FindFuelSystemObdCommand());
                testObdcommand(new FreezeDTCObdCommand());
                testObdcommand(new FuelRailPressure2ObdCommand());
                testObdcommand(new ObdStandardObdCommand());
                testObdcommand(new Bank1Sensor1OxygenSensorObdCommand());
                testObdcommand(new Bank1Sensor2OxygenSensorObdCommand());
                testObdcommand(new Bank1Sensor3OxygenSensorObdCommand());
                testObdcommand(new Bank1Sensor4OxygenSensorObdCommand());
                testObdcommand(new Bank2Sensor1OxygenSensorObdCommand());
                testObdcommand(new Bank2Sensor2OxygenSensorObdCommand());
                testObdcommand(new Bank2Sensor3OxygenSensorObdCommand());
                testObdcommand(new Bank2Sensor4OxygenSensorObdCommand());
                testObdcommand(new OxygenSensorsPresent2ObdCommand());
                testObdcommand(new OxygenSensorsPresentObdCommand());
                testObdcommand(new Bank1LongTermFuelObdCommand());
                testObdcommand(new Bank1ShortTermFuelObdCommand());
                testObdcommand(new Bank2LongTermFuelObdCommand());
                testObdcommand(new Bank2ShortTermFuelObdCommand());
                testObdcommand(new WarmUpsObdCommand());
                testObdcommand(new S1WRLambdaEquivalenceRatioObdCommand());
                testObdcommand(new S2WRLambdaEquivalenceRatioObdCommand());
                testObdcommand(new S3WRLambdaEquivalenceRatioObdCommand());
                testObdcommand(new S4WRLambdaEquivalenceRatioObdCommand());
                testObdcommand(new S5WRLambdaEquivalenceRatioObdCommand());
                testObdcommand(new S6WRLambdaEquivalenceRatioObdCommand());
                testObdcommand(new S7WRLambdaEquivalenceRatioObdCommand());
                testObdcommand(new S8WRLambdaEquivalenceRatioObdCommand());
                testObdcommand(new S1WRLambdaVoltageObdCommand());
                testObdcommand(new S2WRLambdaVoltageObdCommand());
                testObdcommand(new S3WRLambdaVoltageObdCommand());
                testObdcommand(new S4WRLambdaVoltageObdCommand());
                testObdcommand(new S5WRLambdaVoltageObdCommand());
                testObdcommand(new S6WRLambdaVoltageObdCommand());
                testObdcommand(new S7WRLambdaVoltageObdCommand());
                testObdcommand(new S8WRLambdaVoltageObdCommand());


                /*******************************************************************************/
                csv_result += "\n pressure commands: \n";
                publishProgress(" pressure commands... \n");
                testObdcommand(new BarometricPressureObdCommand());
                testObdcommand(new FuelPressureObdCommand());
                testObdcommand(new FuelRailPressureObdCommand());
                testObdcommand(new IntakeManifoldPressureObdCommand());


                csv_result += "\n temperature commands: \n";
                publishProgress(" temperature commands... \n");
                testObdcommand(new AirIntakeTemperatureObdCommand());
                testObdcommand(new AmbientAirTemperatureObdCommand());
                testObdcommand(new EngineCoolantTemperatureObdCommand());

                csv_result += "\n rest commands: \n";
                publishProgress(" rest commands... \n");
                testObdcommand(new SpeedObdCommand());

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
                    csv_result += command.getFormattedResult();
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
