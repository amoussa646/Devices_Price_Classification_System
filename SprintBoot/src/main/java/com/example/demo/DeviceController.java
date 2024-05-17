package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import com.example.demo.Device;
import com.example.demo.DeviceService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    // to add a device
    @PostMapping
    public Device addDevice(@RequestBody Device device) {
        return deviceService.saveDevice(device);
    }

    // to get all devices
    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    // to get a device by id
    @GetMapping("/{id}")
    public Device getDeviceById(@PathVariable Long id) {
        return deviceService.getDeviceById(id);
    }

    // to predict a device by id
    @PostMapping("/predict/{id}")
    public Device predictDevicePrice(@PathVariable Long id) {
        Device device = deviceService.getDeviceById(id);
        if (device != null) {
            int predictedPriceRange = callPythonApi(device);
            return deviceService.updateDevicePrice(id, predictedPriceRange);
        }
        return null;
    }

    private int callPythonApi(Device device) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://0.0.0.0:7000/predict";
        Map requestBody = new HashMap<>();
        Map features = new LinkedHashMap<>();
        features.put("battery_power", device.getBatteryPower());
        features.put("blue", device.isBlue() ? 1 : 0);
        features.put("clock_speed", device.getClockSpeed());
        features.put("dual_sim", device.isDualSim() ? 1 : 0);
        features.put("fc", device.getFc());
        features.put("four_g", device.isFourG() ? 1 : 0);
        features.put("int_memory", device.getIntMemory());
        features.put("m_dep", device.getmDep());
        features.put("mobile_wt", device.getMobileWt());
        features.put("n_cores", device.getnCores());
        features.put("pc", device.getPc());
        features.put("px_height", device.getPxHeight());
        features.put("px_width", device.getPxWidth());
        features.put("ram", device.getRam());
        features.put("sc_h", device.getScH());
        features.put("sc_w", device.getScW());
        features.put("talk_time", device.getTalkTime());
        features.put("three_g", device.isThreeG() ? 1 : 0);
        features.put("touch_screen", device.isTouchScreen() ? 1 : 0);
        features.put("wifi", device.isWifi() ? 1 : 0);

        requestBody.put("features", features);

        HttpEntity<Map> request = new HttpEntity<>(requestBody);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        Map responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey("prediction")) {
            return (int) responseBody.get("prediction");
        }
        return -1;
    }
}
