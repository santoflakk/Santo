package datamanagement;

import services.AddTask;
import services.ShowTaskService;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Santo
 * @Flk
 * 
 **/

public class Menu extends JFrame {
    private JPanel cards;
    private CardLayout c1;
    private AddTask add_tasks;
    private ShowTaskService show_tasks;
    private JPanel menuPanel;

    public Menu() {
        show_tasks = new ShowTaskService();
        add_tasks = new AddTask(show_tasks); 

        menuPanel = createMenuPanel();

        setSize(800, 500);
        setTitle("Task Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        c1 = new CardLayout();
        cards = new JPanel(c1);
        cards.add(menuPanel, "Menu");
        cards.add(add_tasks, "ADD TASK");
        cards.add(show_tasks, "SHOW TASK"); 

        add(cards);
        add_tasks.getBackButton().addActionListener(e -> {
            c1.show(cards, "Menu");
            show_tasks.updateTaskList();
        });
        add_tasks.getBackButton().addActionListener(e -> c1.show(cards, "Menu"));
        show_tasks.getBackButton().addActionListener(e -> c1.show(cards, "Menu")); 
    }

    
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        JLabel title_Label = new JLabel("MENU");
        title_Label.setFont(new Font("Arial", Font.BOLD, 40));
        title_Label.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        JButton button1 = createButton("Add Tasks", new Dimension(200, 50));
        JButton button2 = createButton("Show Tasks", new Dimension(200, 50)); 
        JButton button3 = createButton("Exit", new Dimension(200, 50));
    
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(title_Label);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(button1);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(button2);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(button3);
    
        button1.addActionListener(e -> c1.show(cards, "ADD TASK"));
        button2.addActionListener(e -> c1.show(cards, "SHOW TASK"));
        button3.addActionListener(e -> System.exit(0));

        return panel;
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

}
