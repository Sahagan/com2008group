package src.ui.teacher;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Abstract class for all menu items, handles calls to the parent UI element as
 * well as logging off.
 */
public abstract class Menu extends JPanel {

    private TeacherGUI teacherUI;
    protected JButton backButton;
    private ActionListener backButtonActionListener;

    public Menu(TeacherGUI teacherUI) {

        this.teacherUI = teacherUI;

        setBackButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //hideMenu();
            }
        });

        setVisible(false);
    }
    public void hideMenu() {
        setVisible(false);
        teacherUI.getMainMenu().setVisible(true);
    }
    public void setBackButtonActionListener(ActionListener actionListener) {
        backButton = new JButton();
        backButton.setText("Back");
        backButton.addActionListener(actionListener);
    }
    public TeacherGUI getTeacherUI() {
        return teacherUI;
    }
    protected abstract void placeComponents();
}
