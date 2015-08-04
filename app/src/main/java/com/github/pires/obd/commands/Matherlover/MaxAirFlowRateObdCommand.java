package com.github.pires.obd.commands.Matherlover;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.enums.AvailableCommandNames;

/**
 * Created by Marcin on 31.07.2015.
 */
public class MaxAirFlowRateObdCommand extends ObdCommand {
    private float value = 0;

    public MaxAirFlowRateObdCommand() {
        super("01 50");
    }

    @Override
    protected void performCalculations() {
        // ignore first two bytes [01 63] of the response
        value = buffer.get(2) * 10;
    }

    @Override
    public String getFormattedResult() {
        return String.format("%.2f g/s", value);
    }

    @Override
    public String getCalculatedResult() {
        return String.valueOf(value);
    }

    @Override
    public String getName() {
        return AvailableCommandNames.MAX_AIR_FLOW.getValue();
    }

}
