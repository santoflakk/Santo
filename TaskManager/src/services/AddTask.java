package services;
import datamanagement.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
  * Author: Santo
  * @Flk
 **/

public class AddTask extends JPanel {

    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 18);
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 30);
    private static final Dimension FIELD_SIZE = new Dimension(150, 30);

    private JTextField nameField;
    private JTextField descriptionField;
    private JComboBox<String> priorityBox;
    private JButton backButton;

    private ShowTaskService showTasks;

    public AddTask(ShowTaskService showTasks) {
        this.showTasks = showTasks;
        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addLabel("ADD TASK", HEADER_FONT);
        nameField = addField("TASK:");
        descriptionField = addField("DESCRIPTION:");
        priorityBox = addComboBox("PRIORITY:", new String[]{"Alta", "Media", "Baja"});
        priorityBox.setBackground(Color.WHITE);

        add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        backButton = addButton("HOME");
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.setBackground(Color.WHITE);
        buttonPanel.add(backButton);

        JButton addButton = addButton("ADD");
        addButton.setPreferredSize(new Dimension(100, 50));
        addButton.setBackground(Color.WHITE);
        addButton.addActionListener(createAddActionListener());
        buttonPanel.add(addButton);

        add(buttonPanel);
    }

    private ActionListener createAddActionListener() {
        return new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    Task task = createTaskFromFields();
                    if (doesTaskExist(task)) {
                        JOptionPane.showMessageDialog(null, "A file already existes with that name", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        writeTaskToFile(task);
                        clearFields();
                        SwingUtilities.invokeLater(new Runnable() {
                           
                            public void run() {
                                showTasks.updateTaskList();
                                showTasks.revalidate();
                                showTasks.repaint();
                            }
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You must fill all the box", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    private boolean doesTaskExist(Task task) {
        File file = new File(System.getProperty("user.home") + "/Desktop/tasks.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(task.getTaskId())) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty() || descriptionField.getText().trim().isEmpty() || priorityBox.getSelectedItem() == null) {
            return false;
        }
        return true;
    }

    private Task createTaskFromFields() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String priority = (String) priorityBox.getSelectedItem();
        return new Task(name, description, priority);
    }

    private void writeTaskToFile(Task task) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(System.getProperty("user.home") + "/Desktop/tasks.txt", true))) {
            writer.write(task.getTaskId() + ", " + task.getTaskDescription() + ", " + task.getPriority() + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private JComboBox<String> addComboBox(String labelText, String[] options) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(LABEL_FONT);
        comboBox.setPreferredSize(FIELD_SIZE);
        comboBox.setMaximumSize(FIELD_SIZE);
        addSpacing(15);
        add(createFieldPanel(labelText, comboBox));
        addSpacing(15);
        return comboBox;
    }

    private JLabel addLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        addSpacing(20);
        add(label);
        return label;
    }

    private JTextField addField(String labelText) {
        JTextField field = new JTextField(10);
        field.setFont(LABEL_FONT);
        field.setPreferredSize(FIELD_SIZE);
        field.setMaximumSize(FIELD_SIZE);
        addSpacing(15);
        add(createFieldPanel(labelText, field));
        addSpacing(15);
        return field;
    }

    private JPanel createFieldPanel(String labelText, Component field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel(labelText));
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(field);
        return panel;
    }

    private JButton addButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(button);
        return button;
    }

    private void addSpacing(int size) {
        add(Box.createRigidArea(new Dimension(0, size)));
    }

    private void clearFields() {
        nameField.setText("");
        descriptionField.setText("");
        priorityBox.setSelectedIndex(-1);
    }

    public JButton getBackButton() {
        return backButton;
    }
}

