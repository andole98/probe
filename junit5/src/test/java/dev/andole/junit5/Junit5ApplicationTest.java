package dev.andole.junit5;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@ContextConfiguration(classes = Junit5Application.class)
@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
class Junit5ApplicationTest {

}