package net.kf03w5t5741l.sensorbase.server.service.persistence;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;

import java.util.List;
import java.util.Optional;

@Component
public interface SensorReadingRepository
        extends CrudRepository<SensorReading, Long> {
    public List<SensorReading> findBySensorOrderByTimeDesc(Sensor sensor);

    // JPQL: voordeel - database-agnostic
    @Query("select sr from SensorReading sr where sr.value >= ?1")
    public List<SensorReading> findValueGtEq(Float value);

    @Query("select sr from SensorReading sr order by sr.time desc")
    public List<SensorReading> findAllByTimeDesc();

    // MySQL: dus echt afhankelijk van onderliggende database
    @Query(value = "SELECT * FROM sensor_reading WHERE sensor_sensor_id = ?1"
                    + " ORDER BY time DESC LIMIT 1",
            nativeQuery = true)
    public Optional<SensorReading> findLatest(Sensor sensor);

    public void deleteAllBySensor(Sensor sensor);
}
