package com.adsbdecoder.decoder;

import com.adsbdecoder.exception.CustomDecoderException;
import com.adsbdecoder.model.Aircraft;

import java.util.List;
import java.util.Map;

/**
 * Interface for decoding raw ADS-B messages into Aircraft objects.
 */
public interface AircraftDecoder {

    /**
     * Decode a list of raw messages and return a map of Aircraft objects.
     *
     * @param rawMessages List of raw messages to decode.
     * @return Map of Aircraft objects with ICAO24 as the key.
     * @throws CustomDecoderException If an error occurs during decoding.
     */
    Map<String, Aircraft> decodeMessages(List<String> rawMessages) throws CustomDecoderException;
}
