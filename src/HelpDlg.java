
import java.awt.Frame;
import javax.swing.JOptionPane;
/**
 *
 * @author Gautham
 */
class HelpDlg {

    public HelpDlg(np aThis) {
        Frame frame=new Frame("Whoa !");
        JOptionPane.showMessageDialog(frame,
    "Fee..Foo Fum..It's 2012 and the world is going to end and If you don't know how to use this then you are an alien\n GAPO TECHNOLOGIES \n proudly presents \n NAN - A writing experience \n  Created by\n Gautham Ponnu",
    "Help !!! It's 2012",
    JOptionPane.WARNING_MESSAGE);
    }

}
