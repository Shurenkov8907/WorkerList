package com.example.workerlist;


public class Worker {
    private String idNumber;
    private String fullName;
    private String grade;
    private double hourlyRate;

    public Worker(String idNumber, String fullName, String grade, double hourlyRate) {
        this.idNumber = idNumber;
        this.fullName = fullName;
        this.grade = grade;
        this.hourlyRate = hourlyRate;
    }

    public String getIdNumber() { return idNumber; }
    public String getFullName() { return fullName; }
    public String getGrade() { return grade; }
    public double getHourlyRate() { return hourlyRate; }
}

