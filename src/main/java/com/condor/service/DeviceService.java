package com.condor.service;

import com.condor.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceService {

  private final DeviceRepository repo;

  public DeviceService(DeviceRepository repo) {
    this.repo = repo;
  }

  @Transactional(readOnly = true)
  public boolean isValidDevice(short plantId, short roleDeviceId, String hostname, String osName) {
    Boolean r = repo.validateDevice(plantId, roleDeviceId, hostname, osName);
    return r != null && r;
  }
}
