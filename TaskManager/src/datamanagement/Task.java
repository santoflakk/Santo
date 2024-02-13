package datamanagement;

/**
  * Author: Santo
  * @Flk
 **/

public class Task {
    
    private String task_id = "";
    private String task_description = "";
    private String priority = "";

    public Task(String task_id, String task_description, String priority){
        this.task_id = task_id;
        this.task_description= task_description;
        this.priority = priority;
    }

    public void setTaskId(String task_id){
        this.task_id = task_id;
    }

    public String getTaskId(){
        return task_id;
    }

    public void setTaskDescription(String task_description){
        this.task_description = task_description;
    }

    public String getTaskDescription(){
        return task_description;
    }

    public void setTaskPriority(String priority){
        this.priority = priority;
    }

    public String getPriority(){
        return priority;
    }
}

