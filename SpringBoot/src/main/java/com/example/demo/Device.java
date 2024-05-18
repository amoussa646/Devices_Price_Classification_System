package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//The device entity
@Entity
public class Device {
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Id
    private Long id;
    private int batteryPower;
    private boolean blue;
    private float clockSpeed;
    private boolean dualSim;
    private int fc;
    private boolean fourG;
    private int intMemory;
    private float mDep;
    private int mobileWt;
    private int nCores;
    private int pc;
    private int pxHeight;
    private int pxWidth;
    private int ram;
    private int scH;
    private int scW;
    private int talkTime;
    private boolean threeG;
    private boolean touchScreen;
    private boolean wifi;
    private int priceRange;

    public Device() {

    }
    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBatteryPower() {
        return batteryPower;
    }

    public void setBatteryPower(int batteryPower) {
        this.batteryPower = batteryPower;
    }

    public boolean isBlue() {
        return blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public float getClockSpeed() {
        return clockSpeed;
    }

    public void setClockSpeed(float clockSpeed) {
        this.clockSpeed = clockSpeed;
    }

    public boolean isDualSim() {
        return dualSim;
    }

    public void setDualSim(boolean dualSim) {
        this.dualSim = dualSim;
    }

    public int getFc() {
        return fc;
    }

    public void setFc(int fc) {
        this.fc = fc;
    }

    public boolean isFourG() {
        return fourG;
    }

    public void setFourG(boolean fourG) {
        this.fourG = fourG;
    }

    public int getIntMemory() {
        return intMemory;
    }

    public void setIntMemory(int intMemory) {
        this.intMemory = intMemory;
    }

    public float getmDep() {
        return mDep;
    }

    public void setmDep(float mDep) {
        this.mDep = mDep;
    }

    public int getMobileWt() {
        return mobileWt;
    }

    public void setMobileWt(int mobileWt) {
        this.mobileWt = mobileWt;
    }

    public int getnCores() {
        return nCores;
    }

    public void setnCores(int nCores) {
        this.nCores = nCores;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getPxHeight() {
        return pxHeight;
    }

    public void setPxHeight(int pxHeight) {
        this.pxHeight = pxHeight;
    }

    public int getPxWidth() {
        return pxWidth;
    }

    public void setPxWidth(int pxWidth) {
        this.pxWidth = pxWidth;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getScH() {
        return scH;
    }

    public void setScH(int scH) {
        this.scH = scH;
    }

    public int getScW() {
        return scW;
    }

    public void setScW(int scW) {
        this.scW = scW;
    }

    public int getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(int talkTime) {
        this.talkTime = talkTime;
    }

    public boolean isThreeG() {
        return threeG;
    }

    public void setThreeG(boolean threeG) {
        this.threeG = threeG;
    }

    public boolean isTouchScreen() {
        return touchScreen;
    }

    public void setTouchScreen(boolean touchScreen) {
        this.touchScreen = touchScreen;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public int getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(int priceRange) {
        this.priceRange = priceRange;
    }
}