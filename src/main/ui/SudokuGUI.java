package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import model.Event;
import model.EventLog;
import model.GameBoard;
import model.SudokuBook;
import persistence.JsonReader;
import persistence.JsonWriter;

// source: javadocs: framedemo, menudemo, etc.
// A sudoku player that allows the user to add sudoku games and play them
public class SudokuGUI implements ActionListener {
    private JFrame frame;
    private JLabel emptyLabel;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;
    private JPanel panel;
    private JLabel label;
    private JTextField field;
    private JTextField field2;
    private JButton button;
    private JTabbedPane tabbedPane;

    private SudokuBook boards;

    private static final String JSON_STORE = "./data/sudokugame.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: Creates a new SudokuGUI player, initializes fields and creates the main menu and window
    public SudokuGUI() {
        init();
        createWindow();
        createMenus();
        showWindow();
        showMenus();
    }

    // MODIFIES: this
    // EFFECTS: initializes the application with the starting values
    private void init() {
        this.boards = new SudokuBook("My book");

        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: creates jframe window with 300 width height close on x
    private void createWindow() {
        frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // source: https://tips4java.wordpress.com/2009/05/01/closing-an-application/
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame)e.getSource();
                EventLog el = EventLog.getInstance();
                for (Event event : el) {
                    System.out.println(event.toString());
                }
                frame.dispose();
                System.exit(0);
            }
        });

        emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(300, 30));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: creates jframe window of given width, height no close on x
    private void createWindow(int width, int height) {
        frame = new JFrame("Sudoku");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(width, height));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: show window
    private void showWindow() {
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates menu
    private void createMenus() {
        menuBar = new JMenuBar();
        createMainMenu();
        // createViewMenu();
        // createPlayMenu();
    }

    // MODIFIES: this
    // EFFECTS: show menu
    private void showMenus() {
        frame.setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: creates main menu
    private void createMainMenu() {
        menu = new JMenu("Main");
        createMenuItem("Add a puzzle");
        createMenuItem("View all puzzles");
        createMenuItem("Save boards to file");
        createMenuItem("Load boards from file");
        createMenuItem("View all unfinished puzzles");
        createMenuItem("View all finished puzzles");
        menuBar.add(menu);
    }

    // // MODIFIES: this
    // // EFFECTS: creates view menu
    // private void createViewMenu() {
    //     menu = new JMenu("View");
    //     createMenuItem("Play this board");
    //     createMenuItem("View next board");
    //     createMenuItem("View Previous board");
    //     createMenuItem("Change current board difficulty");
    //     menuBar.add(menu);
    // }

    // // EFFECTS: creates play menu
    // private void createPlayMenu() {
    //     menu = new JMenu("Play");
    //     createMenuItem("Add number");
    //     menuBar.add(menu);
    // }

    // MODIFIES: this
    // EFFECTS: creates menu item with given text
    private void createMenuItem(String text) {
        menuItem = new JMenuItem(text);
        menuItem.addActionListener(this);
        menu.add(menuItem);
    }

    // // EFFECTS: creates panel
    // private void createPanel() {
    //     panel = new JPanel(new BorderLayout());
    //     createTextFields("default");
    //     createTextLabel("lable:");
    //     panel.add(label, BorderLayout.LINE_START);
    //     panel.add(field, BorderLayout.CENTER);
    // }

    // MODIFIES: this
    // EFFECTS: shows panel
    private void showPanel() {
        frame.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: creates text field
    private void createTextFields(String defaultText) {
        field = new JTextField();
        field.setColumns(20);
        field.setText(defaultText);

        field2 = new JTextField();
        field2.setColumns(20);
        field2.setText(defaultText);
    }

    // // EFFECTS: show text field()
    // private void showTextField() {
    //     frame.add(field);
    // }

    // MODIFIES: this
    // EFFECTS: creates text label
    private void createTextLabel(String text) {
        label = new JLabel(text);
        label.setBackground(Color.BLUE);
    }

    // // EFFECTS: shows text field
    // private void showTextLabel() {
    //     frame.add(label);
    // }

    // MODIFIES: this
    // EFFECTS: creates button
    private void createButton(String text) {
        button = new JButton(text);
        button.addActionListener(this);
    }

    // // EFFECTS: shows button
    // private void showButton() {
    //     frame.add(button);
    // }

    // EFFECTS: reads action event to proper response
    @SuppressWarnings("methodlength")
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case ("Add a puzzle"):
                addPuzzlePrompt();
                break;
            case ("View all puzzles"):
                viewPuzzleList();
                break;
            case ("Save boards to file"):
                saveBoards();
                break;
            case ("Load boards from file"):
                loadBoards();
                break;
            case ("View all unfinished puzzles"):
                viewUnfinishedPuzzleList();
                break;
            case ("View all finished puzzles"):
                viewFinishedPuzzleList();
                break;
            case ("Change current board difficulty"):
                changeBoardDifficultyPrompt();
                break;
            case ("Add number"):
                addNumberPrompt();
                break;
            case ("Done"):
                addBoard();
                break;
            case ("Finish"):
                changeBoardDifficulty();
                break;
            case ("Complete"):
                addNumber();
                break;
        }
    }

    // JsonSerializationDemo
    // EFFECTS: saves boards to file
    private void saveBoards() {
        try {
            jsonWriter.open();
            jsonWriter.write(boards);
            jsonWriter.close();
            //System.out.println("Saved sudoku boards to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            //System.out.println("Unable to write file: " + JSON_STORE);
        }
    }

    // Json SerializationDemo
    // EFFECTS: loads boards to file
    private void loadBoards() {
        try {
            boards = jsonReader.read();
            //System.out.println("Loaded sudoku boards from " + JSON_STORE);
        } catch (IOException e) {
            //System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds puzzle prompt window
    private void addPuzzlePrompt() {
        createWindow(70, 100);

        panel = new JPanel(new GridLayout(3, 2));
        createTextLabel("Enter board numbers: ");
        panel.add(label, BorderLayout.LINE_START);
        createTextFields("");
        panel.add(field, BorderLayout.CENTER);
        createTextLabel("Enter board difficulty: ");
        panel.add(label, BorderLayout.LINE_START);
        panel.add(field2, BorderLayout.CENTER);
        createButton("Done");
        panel.add(button);
        showPanel();

        showWindow();
    }

    // MODIFIES: this
    // EFFECTS: adds board to sudokubook and closes add board window
    private void addBoard() {
        String board = field.getText();
        String difficulty = field2.getText();
        this.boards.addBoard(new GameBoard(board, difficulty));
        frame.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: creates new puzzle list view window
    private void viewPuzzleList() {
        createWindow(500, 500);

        addPlayMenu();
        tabbedPane = new JTabbedPane();
        for (int i = 0; i < boards.numBoards(); i++) {
            String boardNum = Integer.toString(i + 1);
            panel = new JPanel(new GridBagLayout());
            displayGivenBoard(boards.get(i));
            createTextLabel("Board " + boardNum);
            tabbedPane.addTab("Board " + boardNum, panel);
        }
        frame.add(tabbedPane);

        showWindow();
    }

    // MODIFIES: this
    // EFFECTS: creates new unfinsihed puzzle list view window
    private void viewUnfinishedPuzzleList() {
        createWindow(500, 500);

        addPlayMenu();
        tabbedPane = new JTabbedPane();
        for (int i = 0; i < boards.numBoards(); i++) {
            String boardNum = Integer.toString(i + 1);
            panel = new JPanel(new GridBagLayout());
            if (!boards.get(i).isCompleted()) {
                displayGivenBoard(boards.get(i));
                createTextLabel("Board " + boardNum);
                tabbedPane.addTab("Board " + boardNum, panel);
            }
            
        }
        frame.add(tabbedPane);

        showWindow();
    }

    // MODIFIES: this
    // EFFECTS: creates new finished puzzle list view window
    private void viewFinishedPuzzleList() {
        createWindow(500, 500);

        addPlayMenu();
        tabbedPane = new JTabbedPane();
        for (int i = 0; i < boards.numBoards(); i++) {
            String boardNum = Integer.toString(i + 1);
            panel = new JPanel(new GridBagLayout());
            if (boards.get(i).isCompleted()) {
                displayGivenBoard(boards.get(i));
                createTextLabel("Board " + boardNum);
                tabbedPane.addTab("Board " + boardNum, panel);
            }
            
        }
        frame.add(tabbedPane);

        showWindow();
    }

    // MODIFIES: this
    // EFFECTS: adds play menu to menu bar
    private void addPlayMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("edit");
        createMenuItem("Change current board difficulty");
        createMenuItem("Add number");
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: changes selected board difficulty
    private void changeBoardDifficulty() {
        boards.get(tabbedPane.getSelectedIndex()).setDifficulty(field.getText());
        viewPuzzleList(); // change so that doesn't create new window
    }

    // MODIFIES: this
    // EFFECTS: adds given board to current panel
    private void displayGivenBoard(GameBoard board) {
        GridBagConstraints c = new GridBagConstraints();
        createDifficultyLabel(board, c);
        createCompletionLabel(board, c);
        c.anchor = GridBagConstraints.CENTER;
        c.ipadx = 20;
        c.ipady = 20;
        c.fill = GridBagConstraints.BOTH;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                createTextLabel(board.getBoard().get(i).get(j));
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                c.gridx = j + 1;
                c.gridy = i + 2;
                panel.add(label, c);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates proper completion label
    private void createCompletionLabel(GameBoard board, GridBagConstraints c) {
        if (board.isCompleted()) {
            createWinnerTextLabel();
        } else {
            createTextLabel("Completed: " + board.isCompleted());
        }
        c.gridx = 0;
        c.gridy = 1;
        panel.add(label, c);
    }

    // MODIFIES: this
    // EFFECTS: creates difficulty label and  adds to panel
    private void createDifficultyLabel(GameBoard board, GridBagConstraints c) {
        createTextLabel("Difficulty: " + board.getDifficulty());
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(label, c);
    }

    // MODIFIES: this
    // EFFECTS: creates winner text label
    private void createWinnerTextLabel() {
        label = new JLabel(new ImageIcon("./src/main/img/winner.png"));
    }

    // MODIFIES: this
    // EFFECTS: adds number prompt window
    private void addNumberPrompt() {
        createWindow(70, 100);

        panel = new JPanel(new GridLayout(2, 2));
        createTextLabel("Enter column, row, number to add:   ");
        panel.add(label, BorderLayout.LINE_START);
        createTextFields("");
        panel.add(field, BorderLayout.CENTER);
        createButton("Complete");
        panel.add(button);
        showPanel();

        showWindow();
    }

    // MODIFIES: this
    // EFFECTS: adds entered number into selected sudoku board
    private void addNumber() {
        boards.get(tabbedPane.getSelectedIndex()).add(field.getText());
        boards.get(tabbedPane.getSelectedIndex()).checkCompletion();
        viewPuzzleList(); // change this..?
    }

    // MODIFIES: this
    // EFFECTS: creates and shows change board difficulty prompt
    private void changeBoardDifficultyPrompt() {
        createWindow(70, 70);

        panel = new JPanel(new GridLayout(2, 2));
        createTextLabel("Enter new difficulty: ");
        panel.add(label, BorderLayout.LINE_START);
        createTextFields("");
        panel.add(field, BorderLayout.CENTER);
        createButton("Finish");
        panel.add(button);
        showPanel();

        showWindow();

    }

}
