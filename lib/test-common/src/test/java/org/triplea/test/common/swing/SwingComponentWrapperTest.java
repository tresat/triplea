package org.triplea.test.common.swing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jetbrains.annotations.NonNls;
import org.junit.jupiter.api.Test;

class SwingComponentWrapperTest {

  @NonNls private static final String CHILD_NAME = "child_name";

  @Test
  void findChildByName() {
    withChildComponentName(CHILD_NAME).findChildByName(CHILD_NAME, JLabel.class);

    Throwable thrownException = null;
    try {
      withChildComponentName("other").findChildByName(CHILD_NAME, JLabel.class);
    } catch (final Throwable e) {
      thrownException = e;
    }
    assertThat("Expected an AssertionError to be thrown", thrownException, instanceOf(AssertionError.class));
  }

  /**
   * Creates a slightly complex child/parent swing component tree with a component at the 'bottom'
   * of that tree having the name supplied by parameter.
   */
  private static SwingComponentWrapper withChildComponentName(final String childName) {
    final JLabel label = new JLabel("text");
    label.setName(childName);

    final JPanel innerPanel = new JPanel();
    innerPanel.add(new JLabel("other"));
    innerPanel.add(label);

    final JPanel panel = new JPanel();
    panel.add(innerPanel);
    panel.add(new JButton("not text type"));

    return SwingComponentWrapper.of(panel);
  }
}
