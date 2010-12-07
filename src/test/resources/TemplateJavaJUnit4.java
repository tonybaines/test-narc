import org.junit.Test;

import static org.junit.Assert.*;

public class TemplateJavaJUnit4 {
  @Test
  public void doSomething() {
    assertEquals("Hello", "Dog");
  }
  
  // A comment
  public void testShouldDoSomething() {
    assertEquals(1,2);
  }
}
