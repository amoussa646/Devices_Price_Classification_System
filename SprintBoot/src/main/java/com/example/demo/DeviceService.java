package com.example.demo;

// import Abdallah.Devices_Price_Classification_System.devicepriceprediction.entity.Device;
// import Abdallah.Devices_Price_Classification_System.devicepriceprediction.repository.DeviceRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    // to get al devices
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    // to get device by Id
    public Device getDeviceById(Long id) {
        return deviceRepository.getById(id);
    }

    // to save a device
    public Device saveDevice(Device device) {
        return deviceRepository.saveAndFlush(device);
    }

    // to update a device's price
    public Device updateDevicePrice(Long id, int priceRange) {
        Device device = getDeviceById(id);
        if (device != null) {
            device.setPriceRange(priceRange);
            return deviceRepository.saveAndFlush(device);
        }
        return null;
    }
}
