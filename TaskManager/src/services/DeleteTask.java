package services;

import java.io.*;
import java.util.stream.Collectors;
import java.util.*;
import java.io.File;
import java.nio.file.Files;
import javax.swing.*;

/**
 * Author: Santo
 * @Flk
 **/

public class DeleteTask {
    private ShowTaskService showTaskService;

    public DeleteTask(ShowTaskService showTaskService) {
        this.showTaskService = showTaskService;
    }

    public void deleteTask(String taskId) {
        File tasksFile = new File(System.getProperty("user.home") + "/Desktop/tasks.txt");
        try {
            List<String> lines = Files.readAllLines(tasksFile.toPath());
            List<String> updatedLines = lines.stream()
                    .filter(line -> !line.startsWith(taskId + ", "))
                    .collect(Collectors.toList());
            Files.write(tasksFile.toPath(), updatedLines);
            SwingUtilities.invokeLater(new Runnable() {
                
                public void run() {
                    showTaskService.updateTaskList();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
