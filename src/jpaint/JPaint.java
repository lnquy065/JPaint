package jpaint;

import javax.swing.UIManager;

/**
 *
 * @author LN Quy
 */
public class JPaint {


    public static void main(String args[]) {

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

}
