package com.condor.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository {
    @Query(value = "SELECT public.validate_device(:plantId, :roleDeviceId, :hostname, :osName)", nativeQuery = true)
    Boolean validateDevice(
        @Param("plantId") Short plantId,
        @Param("roleDeviceId") Short roleDeviceId,
        @Param("hostname") String deviceHostname,
        @Param("osName") String deviceOsName
    );
}
