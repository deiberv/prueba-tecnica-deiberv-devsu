package com.devsu.ing.deiberv.ms.cuentamovimiento;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class})
class ApplicationTests {

	@Test
	void contextLoads() {
        Application.main(new String[] {});
        Assertions.assertTrue(true);
	}

}
