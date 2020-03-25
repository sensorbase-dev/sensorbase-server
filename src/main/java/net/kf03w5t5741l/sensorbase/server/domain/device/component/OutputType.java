package net.kf03w5t5741l.sensorbase.server.domain.device.component;

/* Component data types as specified by Cayenne LPP:
 * https://developers.mydevices.com/cayenne/docs/lora/#lora-cayenne-low-power-payload
 */
public enum OutputType implements ReadingType {
    DIGITAL_OUTPUT( (byte) 0x01),
    ANALOG_OUTPUT(  (byte) 0x03);

    private byte id;

    private OutputType(byte id) {
        this.id = id;
    }

    public byte getId() {
        return this.id;
    }

    public void setId(byte id) {
        this.id = id;
    }
}
