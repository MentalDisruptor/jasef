package org.jasef.framework.junit;

import org.assertj.core.api.WithAssertions;

public interface JUnitTestBase extends WithAssertions {
  /*
   * stub interface to provide AssertJ's assertThat() methods in test classes. Test classes should
   * always implement this class to have that functionality.
   */
}
