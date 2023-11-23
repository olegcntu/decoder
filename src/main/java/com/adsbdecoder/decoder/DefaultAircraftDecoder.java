package com.adsbdecoder.decoder;

import com.adsbdecoder.exception.CustomDecoderException;
import com.adsbdecoder.model.Aircraft;
import de.serosystems.lib1090.StatefulModeSDecoder;
import de.serosystems.lib1090.exceptions.BadFormatException;
import de.serosystems.lib1090.exceptions.UnspecifiedFormatError;
import de.serosystems.lib1090.msgs.ModeSDownlinkMsg;
import de.serosystems.lib1090.msgs.adsb.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of AircraftDecoder.
 */
public class DefaultAircraftDecoder implements AircraftDecoder {

    private static final Logger LOGGER = Logger.getLogger(DefaultAircraftDecoder.class.getName());
    private static final int DEFAULT_ALTITUDE = 1;

    private final StatefulModeSDecoder decoder = new StatefulModeSDecoder();

    /**
     * Decode a list of raw messages and return a map of Aircraft objects.
     *
     * @param rawMessages List of raw messages to decode.
     * @return Map of Aircraft objects with ICAO24 as the key.
     */
    @Override
    public Map<String, Aircraft> decodeMessages(List<String> rawMessages) {
        Map<String, Aircraft> aircraftMap = new HashMap<>();

        for (String rawMessage : rawMessages) {
            try {
                Aircraft aircraft = decodeMessage(rawMessage);
                if (aircraft != null) {
                    aircraftMap.merge(aircraft.getIcao24(), aircraft, Aircraft::merge);
                }
            } catch (CustomDecoderException e) {
                LOGGER.log(Level.SEVERE, "Error decoding message: " + rawMessage, e);
            }
        }

        return aircraftMap;
    }

    /**
     * Decode a raw message and return an Aircraft object.
     *
     * @param rawMessage Raw message to decode.
     * @return Decoded Aircraft object.
     * @throws CustomDecoderException If an error occurs during decoding.
     */
    private Aircraft decodeMessage(String rawMessage) throws CustomDecoderException {
        try {
            ModeSDownlinkMsg msg = decoder.decode(rawMessage, DEFAULT_ALTITUDE);
            return createAircraft(msg);
        } catch (BadFormatException | UnspecifiedFormatError e) {
            LOGGER.log(Level.WARNING, "Error decoding message: " + rawMessage, e);
        } catch (Exception e) {
            throw new CustomDecoderException("Error decoding message: " + rawMessage, e);
        }

        return null;
    }

    /**
     * Create and populate an Aircraft object based on the ModeSDownlinkMsg.
     *
     * @param msg ModeSDownlinkMsg to extract information from.
     * @return Populated Aircraft object.
     */
    private Aircraft createAircraft(ModeSDownlinkMsg msg) {
        Aircraft aircraft = new Aircraft();
        String icao24 = msg.getAddress().getHexAddress();
        aircraft.setIcao24(icao24);

        if (msg.getParity() == 0 || msg.checkParity()) {
            switch (msg.getType()) {
                case ADSB_AIRSPEED -> setAirspeed(aircraft, (AirspeedHeadingMsg) msg);
                case ADSB_IDENTIFICATION -> setIdentification(aircraft, (IdentificationMsg) msg);
                case ADSB_VELOCITY -> setVelocity(aircraft, (VelocityOverGroundMsg) msg);
                case ADSB_SURFACE_POSITION_V0, ADSB_SURFACE_POSITION_V1, ADSB_SURFACE_POSITION_V2 ->
                        setSurfacePosition(aircraft, (SurfacePositionV0Msg) msg);
            }
        }
        return aircraft;
    }

    private void setAirspeed(Aircraft aircraft, AirspeedHeadingMsg airspeed) {
        aircraft.setVelocity(airspeed.getAirspeed());
    }

    private void setIdentification(Aircraft aircraft, IdentificationMsg ident) {
        aircraft.setCallsign(new String(ident.getIdentity()));
    }

    private void setVelocity(Aircraft aircraft, VelocityOverGroundMsg veloc) {
        aircraft.setVelocity(veloc.hasVelocityInfo() ? veloc.getGroundSpeed() : -1);
    }

    private void setSurfacePosition(Aircraft aircraft, SurfacePositionV0Msg sp0) {
        if (sp0.hasGroundSpeed()) {
            aircraft.setVelocity(sp0.getGroundSpeed());
        }
    }
}
