package com.condor.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceRepository {
    private final JdbcTemplate jdbcTemplate;
    public DeviceRepository(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
    }
    /**
     * Calls the DB function public.validate_device and returns the boolean result.
     */
    public Boolean validateDevice(Short plantId, Short roleDeviceId, String hostname, String osName) {
      Object result = jdbcTemplate.queryForObject("SELECT public.validate_device(?, ?, ?, ?)",Boolean.class,plantId, roleDeviceId, hostname, osName);
      if (result == null) return Boolean.FALSE;
      if (result instanceof Boolean) return (Boolean) result;
      if (result instanceof Number) return ((Number) result).intValue() != 0;
      return Boolean.FALSE;
    }

    public Long getDeviceId(Short plantId, Short roleDeviceId, String hostname, String osName) {
        return jdbcTemplate.queryForObject(
                """
                SELECT device_id
                FROM devices
                WHERE plant_id = ?
                    AND role_device_id = ?
                    AND device_hostname = ?
                    AND device_os_name = ?
                """,
                Long.class, plantId, roleDeviceId, hostname, osName
        );
    }

}
