package com.giro.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test; 

public class HolaMundoTest { 
    
    @Test 
    void testHolaMundo() { 
        String mensaje = "Hola Mundo GIRO"; 
        // Verifica que la variable 'mensaje' sea exactamente igual al texto esperado 
        assertEquals("Hola Mundo GIRO", mensaje, "El mensaje debe ser 'Hola Mundo GIRO'"); 
    
    }

}
