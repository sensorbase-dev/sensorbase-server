package net.kf03w5t5741l.sensorbase.server.domain.device.component;

/* Component data types as specified by Cayenne LPP:
 * https://developers.mydevices.com/cayenne/docs/lora/#lora-cayenne-low-power-payload
 */
public enum InputType implements ReadingType {
    DIGITAL_INPUT(  (byte) 0x00),
    ANALOG_INPUT(   (byte) 0x02),
    ILLUMINANCE(    (byte) 0x65),
    PRESENCE(       (byte) 0x66),
    TEMPERATURE(    (byte) 0x67),
    HUMIDITY(       (byte) 0x68),
    ACCELEROMETER(  (byte) 0x71),
    BAROMETER(      (byte) 0x73),
    GYROMETER(      (byte) 0x86),
    GPS_LOCATION(   (byte) 0x88);

    private byte id;

    private InputType(byte id) {
        this.id = id;
    }

    public byte getId() {
        return this.id;
    }

    public void setId(byte id) {
        this.id = id;
    }
}
