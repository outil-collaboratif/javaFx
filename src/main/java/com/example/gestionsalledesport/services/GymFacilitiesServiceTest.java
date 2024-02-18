package com.example.gestionsalledesport.services;

import com.example.gestionsalledesport.models.GymFacility;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

public class GymFacilitiesServiceTest {

    @Test
    public void test_get_all_facilities_not_null() {
        GymFacilitiesService facilitiesService = new GymFacilitiesService();
        try {
            List<GymFacility> allFacilities = facilitiesService.getAllFacilities();
            assertNotNull(allFacilities);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void test_rent_facility_successful() {
        GymFacilitiesService facilitiesService = new GymFacilitiesService();
        int facilityIdToRent = 1; // Example facility ID
        Date rentalDate = new Date(); // Current date (example)
        try {
            boolean rented = facilitiesService.rentFacilityForDate(facilityIdToRent, rentalDate);
            assertTrue(rented);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void test_rent_facility_invalid_id() {
        GymFacilitiesService facilitiesService = new GymFacilitiesService();
        int invalidFacilityId = -1; // Invalid facility ID
        Date rentalDate = new Date(); // Current date (example)
        try {
            boolean rented = facilitiesService.rentFacilityForDate(invalidFacilityId, rentalDate);
            assertFalse(rented);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void test_insert_facility_successful() {
        GymFacilitiesService facilitiesService = new GymFacilitiesService();
        GymFacility newFacility = new GymFacility(0, "New Facility", "Workout Area", true, null); // Example facility data
        try {
            boolean inserted = facilitiesService.insertFacility(newFacility);
            assertTrue(inserted);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

}
