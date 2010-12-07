import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Javadoc comment
 */
public class TemplateJavaJUnit4 {
  @Test
  public void doSomething() {
    assertEquals("Hello", "Dog");
  }
  
  @Test
  public void doSomethingElse() {
    assertEquals("Hello", "Bark!");
  }
  
  // A comment
  public void testShouldDoSomething() {
    assertEquals(1,2);
  }
}
