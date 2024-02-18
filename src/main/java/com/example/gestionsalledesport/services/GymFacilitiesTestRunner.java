package com.example.gestionsalledesport.services;

public class GymFacilitiesTestRunner {
    public void runTests() {
        // Instantiate and run Gym Facility tests
        GymFacilitiesServiceTest testClass = new GymFacilitiesServiceTest();
        testClass.test_get_all_facilities_not_null();
        testClass.test_rent_facility_successful();
        testClass.test_rent_facility_invalid_id();
        testClass.test_insert_multiple_facilities_successful();
    }
}

