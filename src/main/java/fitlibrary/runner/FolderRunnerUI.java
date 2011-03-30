/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.runner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FolderRunnerUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final JTextField pageCounts = new JTextField();
	private final JTextField assertionCounts = new JTextField();
	private final JButton stopButton = new JButton("Quit");
	private final JTextArea textArea = new JTextArea();

	public static void main(String[] args) {
		new FolderRunnerUI(new FolderRunner(args));
	}

	public FolderRunnerUI(final FolderRunner runner) {
		super("FolderRunner");
		runner.addTestListener(new StoryTestListener() {
			// @Override
			public void testComplete(boolean failing, String thePageCounts, String theAssertionCounts) {
				giveFeedbackToUser(failing, thePageCounts, theAssertionCounts);
			}

			// @Override
			public void reportOutput(String name, String out, String output) {
				reportOut(name, out, output);
			}

			// @Override
			public void suiteComplete() {
				setTitle(getTitle() + " -- finished");
			}
		});
		setLayout(new BorderLayout());
		getContentPane().add(makeTextFields(), BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
		getContentPane().add(stopButton, BorderLayout.SOUTH);
		stopButton.addActionListener(new ActionListener() {
			// @Override
			public void actionPerformed(ActionEvent ev) {
				runner.exit();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		setVisible(true);
		try {
			runner.run();
		} catch (Exception e) {
			textArea.setText(textArea.getText() + "\n\n" + e.getMessage());
		}
	}

	private JPanel makeTextFields() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(pageCounts, BorderLayout.NORTH);
		panel.add(assertionCounts, BorderLayout.SOUTH);
		pageCounts.setFont(new Font("Arial", Font.BOLD, 16));
		assertionCounts.setFont(new Font("Arial", Font.BOLD, 16));
		return panel;
	}

	public void giveFeedbackToUser(boolean failing, String pageCounts2, String assertionCounts2) {
		pageCounts.setText(pageCounts2);
		assertionCounts.setText(assertionCounts2);
		Color color = Color.GREEN;
		if (failing)
			color = Color.RED;
		pageCounts.setBackground(color);
		assertionCounts.setBackground(color);
		validate();
	}

	public void reportOut(String name, String out, String output) {
		if (!output.trim().equals(""))
			textArea.setText(textArea.getText() + "\n\n--------Output of " + out + " of " + name + ":--------\n"
					+ output);
	}
}
