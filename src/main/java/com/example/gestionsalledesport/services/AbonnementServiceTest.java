package com.example.gestionsalledesport.services;

import com.example.gestionsalledesport.models.Abonnement;
import com.example.gestionsalledesport.models.User;
import com.example.gestionsalledesport.services.AbonnementService;

import org.junit.Test;
import static org.junit.Assert.*;

public class AbonnementServiceTest {

    @Test
    public void test_insert_abonnement_null_abonnement() {
        AbonnementService abonnementService = new AbonnementService();
        abonnementService.insertAbonnement(null);

    }

    @Test
    public void test_insert_abonnement_null_user() {
        AbonnementService abonnementService = new AbonnementService();
        Abonnement abonnement = new Abonnement(1L, "2021-01-01", "1 year", 100.0, null);
        abonnementService.insertAbonnement(abonnement);
    }

    @Test
    public void test_get_id_not_null() {
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "1990-01-01");
        Long id = user.getId();
        assertNotNull(id);
        assertEquals(1L, id.longValue());
    }

    @Test
    public void test_get_email_valid_format() {
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "1990-01-01");
        String email = user.getEmail();
        assertNotNull(email);
        assertTrue(email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"));
    }
}
