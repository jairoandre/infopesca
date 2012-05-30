/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.business;

import com.jota.infopesca.bean.Viagem;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author 08404235783
 */
public class ViagemBCTest {
    
    public ViagemBCTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of carregarTripulacaoPassada method, of class ViagemBC.
     */
    @Test
    public void testCarregarTripulacaoPassada() {
        System.out.println("carregarTripulacaoPassada");
        Viagem viagem = null;
        ViagemBC instance = new ViagemBC();
        List result = instance.carregarTripulacaoPassada(viagem);
        assertNotNull(result);
    }
}
