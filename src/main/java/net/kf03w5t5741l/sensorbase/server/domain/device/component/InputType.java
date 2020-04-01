package net.kf03w5t5741l.sensorbase.server.domain.device.component;

/* Component data types as specified by Cayenne LPP:
 * https://developers.mydevices.com/cayenne/docs/lora/#lora-cayenne-low-power-payload
 */
public enum InputType implements ReadingType {
    DIGITAL_INPUT(  (byte) 0x00, (byte) 1),
    ANALOG_INPUT(   (byte) 0x02, (byte) 2),
    ILLUMINANCE(    (byte) 0x65, (byte) 2),
    PRESENCE(       (byte) 0x66, (byte) 1),
    TEMPERATURE(    (byte) 0x67, (byte) 2),
    HUMIDITY(       (byte) 0x68, (byte) 1),
    ACCELEROMETER(  (byte) 0x71, (byte) 6),
    BAROMETER(      (byte) 0x73, (byte) 2),
    GYROMETER(      (byte) 0x86, (byte) 6),
    GPS_LOCATION(   (byte) 0x88, (byte) 9);

    private byte id;
    private byte dataSize;

    private InputType(byte id, byte dataSize) {
        this.id = id;
        this.dataSize = dataSize;
    }

    public byte getId() {
        return this.id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public byte getDataSize() {
        return this.dataSize;
    }

    public void setDataSize(byte dataSize) {
        this.dataSize = dataSize;
    }

    @Override
    public String toString() {
        return "InputType 0x"
                + Integer.toHexString(this.getId())
                +", name: "
                + this.name()
                + ", datasize: "
                + this.getDataSize();
    }
}
