package com.example.gestionsalledesport.services;

import com.example.gestionsalledesport.models.GymFacility;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Calendar;
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
    public void test_insert_multiple_facilities_successful() {
        GymFacilitiesService facilitiesService = new GymFacilitiesService();
        boolean allInserted = true;
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.FEBRUARY, 18);
        GymFacility[] newFacilities = {
                new GymFacility(1, "Workout Area 1", GymFacility.FacilityType.WORKOUT_AREA, true, null),
                new GymFacility(2, "Workout Area 2", GymFacility.FacilityType.WORKOUT_AREA, true, null),
                new GymFacility(3, "Workout Area 3", GymFacility.FacilityType.WORKOUT_AREA, false, cal.getTime()),
                new GymFacility(4, "Workout Area 4", GymFacility.FacilityType.WORKOUT_AREA, true, null),
                new GymFacility(5, "Workout Area 5", GymFacility.FacilityType.DANCE_STUDIO, true, null),
                new GymFacility(6, "Dance Studio 1", GymFacility.FacilityType.DANCE_STUDIO, true, null),
                new GymFacility(7, "Dance Studio 2", GymFacility.FacilityType.DANCE_STUDIO, false, cal.getTime()),
                new GymFacility(8, "Pool", GymFacility.FacilityType.POOL, false, cal.getTime()),
        };
        for (GymFacility newFacility : newFacilities) {
            try {
                boolean inserted = facilitiesService.insertFacility(newFacility);
                allInserted &= inserted;
            } catch (Exception e) {
                allInserted = false;
                System.err.println("Error inserting facility: " + e.getMessage());
            }
        }
        assertTrue(allInserted);
    }


}
