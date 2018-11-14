import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI extends JFrame implements FocusListener, ActionListener {
    private static JTextField input;
    private static JTextField output;
    private static JButton translate;
    private static JComboBox dropdown;

    private int NUM_ROWS = 3;

    private int WIDTH = 250;
    private int HEIGHT = 45;

    public GUI(){
        setLayout(new GridLayout(NUM_ROWS, 1));

        input =  new JTextField("Input URL");
        input.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        input.addFocusListener(this);

        String[] langs = {"Amharic", "French", "German", "Spanish"};

        dropdown = new JComboBox(langs);
        dropdown.setSelectedIndex(0);
        dropdown.addActionListener(this);
        translate = new JButton("Translate");
        translate.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        translate.addActionListener(e -> {
            String targetLang = "";
            switch (dropdown.getSelectedIndex()){
                case 0:
                    targetLang = "am";
                    break;
                case 1:
                    targetLang = "fr";
                    break;
                case 2:
                    targetLang = "de";
                    break;
                case 3:
                    targetLang = "es";
                    break;
            }
            ThreadHandler.add(input.getText(), "tmp.html", targetLang);
        });

        add(input);
        add(dropdown);
        add(translate);

        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static  void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        try{
            new GUI().setVisible(true);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        JTextField field = (JTextField)e.getSource();
        field.setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
