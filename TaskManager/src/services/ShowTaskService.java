package services;

import datamanagement.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
  * Author: Santo
  * @Flk
 **/

public class ShowTaskService extends JPanel {
    private JButton backButton;
    private DeleteTask deleteTask;

    public ShowTaskService() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        backButton = createButton("HOME", new Dimension(100, 100));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteTask = new DeleteTask(this);
        updateTaskList();
    }

    public void updateTaskList() {
        SwingUtilities.invokeLater(new Runnable() {
           
            public void run() {
                removeAll();
                add(Box.createRigidArea(new Dimension(0, 10)));
                addPriorityPanel("Alta");
                add(Box.createRigidArea(new Dimension(0, 10)));
                addPriorityPanel("Media");
                add(Box.createRigidArea(new Dimension(0, 10)));
                addPriorityPanel("Baja");
                add(Box.createRigidArea(new Dimension(0, 10)));
                add(backButton);
                add(Box.createRigidArea(new Dimension(0, 10)));
                revalidate();
                repaint();
            }
        });
    }

    private void addPriorityPanel(String priorityValue) {
        String priority;
        if (priorityValue.equals("Alta")) {
            priority = "HIGH";
        } else if (priorityValue.equals("Media")) {
            priority = "MID";
        } else if (priorityValue.equals("Baja")) {
            priority = "LOW";
        } else {
            return;
        }

        JPanel priorityPanel = createPriorityPanel(priority);
        addTasksToPanel(priorityPanel, priorityValue);
        this.add(priorityPanel);
        this.revalidate();
        this.repaint();
    }

    private void addTasksToPanel(JPanel panel, String priorityValue) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        JPanel tasks = new JPanel();
        tasks.setLayout(new BoxLayout(tasks, BoxLayout.X_AXIS));
    
        ArrayList<Task> taskList = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(
                new FileReader(System.getProperty("user.home") + "/Desktop/tasks.txt"))) {
    
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskDetails = line.split(", ");
                if (taskDetails.length == 3) {
                    Task task = new Task(taskDetails[0], taskDetails[1], taskDetails[2]);
                    if (task.getPriority().equals(priorityValue)) {
                        taskList.add(task);
                    }
                }
            }
            tasks.add(Box.createRigidArea(new Dimension(10, 0))); 
            for (Task task : taskList) {
                tasks.add(createTaskPanel(task));
                tasks.add(Box.createRigidArea(new Dimension(10, 0)));
            }
    
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
        JScrollPane scrollPane = new JScrollPane(tasks);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane);
        panel.revalidate();
        panel.repaint();
    }
    

    private JPanel createPriorityPanel(String priority) {
        JPanel priorityPanel = new JPanel();
        priorityPanel.setLayout(new BoxLayout(priorityPanel, BoxLayout.PAGE_AXIS));
        JLabel priorityLabel = createLabel(priority, Font.BOLD, 20);
        priorityPanel.add(priorityLabel);
        return priorityPanel;
    }

    private JPanel createTaskPanel(Task task) {
        JPanel taskPanel = new JPanel();
        taskPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        taskPanel.add(createLabel("Task name: " + task.getTaskId(), Font.PLAIN, 15));
        taskPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        taskPanel.add(createLabel("About: " + task.getTaskDescription(), Font.PLAIN, 15));
        taskPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        taskPanel.add(createLabel("Priority: " + task.getPriority(), Font.PLAIN, 15));
        taskPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JButton deleteButton = createButton("Delete", new Dimension(70, 20));
        deleteButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                deleteTask.deleteTask(task.getTaskId());
            }
        });
        taskPanel.add(deleteButton);
        taskPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        taskPanel.setBackground(Color.WHITE);
        return taskPanel;
    }

    private JLabel createLabel(String text, int style, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", style, size));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setMaximumSize(size);
        button.setBackground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        int fontSize = Math.min(size.width / text.length(), 18);
        button.setFont(new Font("Arial", Font.PLAIN, fontSize));
        return button;
    }

    public JButton getBackButton() {
        return backButton;
    }

}
