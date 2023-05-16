import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;

public class GUI {

    private JPanel panel1;
    private JTextArea textArea1;
    private JButton changeBackgroundButton;
    private JButton searchButton;
    private JButton fontButton;
    private JButton replaceButton;
    private JButton changeTextcolorButton;

    public GUI() {
        fontButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] possibilities = {"Courier new", "DialogInput", "JetBrains Mono", "Fira Code", "Consolas"};
                String s = (String)JOptionPane.showInputDialog(
                        null,
                        "Choose a font below:",
                        "Pick a new font",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        "Courier new");

                if ((s != null) && (s.length() > 0)) {
                    String fontName = s;
                    Font font = new Font(fontName, Font.PLAIN, 20);
                    textArea1.setFont(font);
                    return;
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search();
            }
        });
        replaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Replace();
            }
        });
        changeBackgroundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] possibilities = {"White", "LightGray", "Black"};
                String s = (String)JOptionPane.showInputDialog(
                        null,
                        "Choose a color below:",
                        "Pick a new color",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        "0xFFFFFF");

                if ((s != null) && (s.length() > 0)) {
                    if (s == "White") {
                        s = "0xFFFFFF";
                    } else if (s == "LightGray") {
                        s = "0xAAAAAA";
                    } else if (s == "Black") {
                        s = "0x000000";
                    }

                    String pickedColor = s;
                    textArea1.setBackground(Color.decode(pickedColor));
                    return;
                }
            }
        });
        changeTextcolorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] possibilities = {"White", "LightGray", "Black"};
                String z = (String)JOptionPane.showInputDialog(
                        null,
                        "Choose a color below:",
                        "Pick a new color",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        "0xFFFFFF");

                if ((z != null) && (z.length() > 0)) {
                    if (z == "White") {
                        z = "0xFFFFFF";
                    } else if (z == "LightGray") {
                        z = "0xAAAAAA";
                    } else if (z == "Black") {
                        z = "0x000000";
                    }

                    String pickedColor = z;
                    textArea1.setForeground(Color.decode(pickedColor));
                    return;
                }
            }
        });
    }

    private void Open() {
        String filename = JOptionPane.showInputDialog("Write the name of the file you want to open");
        filename = filename + ".txt";
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            return;
        }
        String nextLine = null;
        try {
            nextLine = in.readLine();
            while (nextLine != null) {
                textArea1.append(nextLine + "\n");
                nextLine = in.readLine();
            }
        } catch (IOException ex) {
            textArea1.setText("");
        }

    }

    private void Save() {
        String filename = JOptionPane.showInputDialog("Name your file");
        filename = filename + ".txt";
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to save");
        }
        out.println(textArea1.getText());
        out.flush();
        out.close();
    }

    private void New() {
        ArrayList<String> TextList = new ArrayList<String>();
        String savedtext = textArea1.getText();
        TextList.add(savedtext);


        textArea1.setText("");
    }

    private void Search() {
        String text = textArea1.getText();
        String[] splitText = text.split("\n");
        String searchedPhrase = JOptionPane.showInputDialog("Write what letter/letters you want to search for");
        int i = 0;
        while (i<splitText.length) {
            if (splitText[i].contains(searchedPhrase)) {
                int row = i + 1;
                JOptionPane.showMessageDialog(null, searchedPhrase + " is on row " + row);
            }
            i++;
        }
    }

    private void Replace() {
        String currentText = textArea1.getText();
        String[] splitText = currentText.split("\n");
        String letterToBeReplaced = JOptionPane.showInputDialog("What letter/letters do you want to replace?");
        String newLetter = JOptionPane.showInputDialog("What letter do you want to replace " + letterToBeReplaced + " with?");
        int i = 0;
        String newString = "";
        textArea1.setText("");
        while (i < splitText.length) {
            if (splitText[i].contains(letterToBeReplaced)) {
                int currentPos = 0;
                while (currentPos < splitText[i].length()) {
                    if (splitText[i].charAt(currentPos) == letterToBeReplaced.charAt(0)) {
                        newString = splitText[i].substring(0, currentPos) + newLetter + splitText[i].substring(currentPos + 1);

                    }
                    currentPos++;
                }
                textArea1.append(newString);
            } else {
                textArea1.append(splitText[i]);
            }
            i++;
        }
    }




    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Archive");
        menuBar.add(menu);

        menuItem = new JMenuItem("Open");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Open();
            }
        });

        menu.add(menuItem);

        menuItem = new JMenuItem("Save");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Save();
            }
        });

        menu.add(menuItem);

        menuItem = new JMenuItem("New");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                New();
            }
        });

        menu.add(menuItem);

        return menuBar;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setSize(400,200);
        GUI gui = new GUI();
        frame.setContentPane(gui.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(gui.createMenuBar());
        frame.pack();
        frame.setVisible(true);
    }
}

