package taskmanagement;
import datamanagement.Menu;

/**
  * Author: Santo
 * @Override
 **/

public class Application {
    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Menu frame = new Menu();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
