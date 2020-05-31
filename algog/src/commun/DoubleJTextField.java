package commun;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class DoubleJTextField extends JTextField {
	static int ok = 0;
	private static final long serialVersionUID = 1L;

	public DoubleJTextField() {
		addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();

				if (((ch < '0') || (ch > '9')) && (ch != '-') && (ch != '.')) {
					e.consume();
				}
				if (ch == '-') {
					if (getText().equals("")) {
						setText("-");
						e.consume();
					} else {
						e.consume();
					}
				}
				if (ch == '0') {
					if (getText().equals("") || getText().equals("-")) {
						setText(getText() + "0");
						e.consume();
					} else {
						if (getText().equals("0") || getText().equals("-0")) {
							e.consume();
						}
					}
				}
				if (ch == '.') {
					if (getText().equals("") || getText().equals("-")) {

						setText(getText() + "0.");
						e.consume();
					} else {
						if (getText().contains(".")) {
							e.consume();
						}

					}

				}

			}
		});
	}

}
