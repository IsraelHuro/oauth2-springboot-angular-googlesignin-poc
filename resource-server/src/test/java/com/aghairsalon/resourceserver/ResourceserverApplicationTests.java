package com.aghairsalon.resourceserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ResourceserverApplicationTests {

  @Test
  void contextLoads() {
    // POC: solo verifica que el contexto arranca con el perfil "test"
  }
}
