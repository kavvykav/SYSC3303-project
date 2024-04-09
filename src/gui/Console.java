package gui;

import common.Direction;

import javax.swing.*;
import java.awt.*;

public class Console {

    private JFrame frame;

    // The panels on the JFrame, this GUI uses a GridLayout
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel errorPanel;

    // Labels indicationg where Elevator 1 - 4 Positions are, as well as the
    // error log.
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel errorLabel;

    // Text fields showing the current position of each elevator.
    private JTextField elevator1Position;
    private JTextField elevator2Position;
    private JTextField elevator3Position;
    private JTextField elevator4Position;

    // Text fields showing the direction of the elevator
    private JTextField elevator1Direction;
    private JTextField elevator2Direction;
    private JTextField elevator3Direction;
    private JTextField elevator4Direction;

    // Text box showing the errors
    private JTextArea errorLog;

    // Font constants
    private static final Font FONT = new Font("Monospaced", Font.BOLD, 22);
    private static final Font ERROR_FONT = new Font("Monospaced", Font.BOLD, 13);

    /**
     * Creates and shows the GUI that will be made. It displays the current
     * position of all four elevators, the direction of movement of each elevator,
     * and any faults.
     */
    public Console() {
        // Set Up JFrame
        frame = new JFrame("Elevator Control System");
        frame.setSize(new Dimension(1920, 1080));
        frame.setResizable(true);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        // Set up the Panels that show each Elevator
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.setSize(new Dimension(300, 200));
        panel1.setBackground(Color.DARK_GRAY);

        panel2 = new JPanel();
        panel2.setSize(new Dimension(300, 200));
        panel2.setBackground(Color.DARK_GRAY);

        panel3 = new JPanel();
        panel3.setSize(new Dimension(300, 200));
        panel3.setBackground(Color.DARK_GRAY);

        panel4 = new JPanel();
        panel4.setSize(new Dimension(300, 200));
        panel4.setBackground(Color.DARK_GRAY);

        errorPanel = new JPanel();
        errorPanel.setSize(new Dimension(900, 1000));
        errorPanel.setBackground(Color.DARK_GRAY);

        // Set up labels
        label1 = new JLabel();
        label1.setText("Elevator 1");
        label1.setFont(FONT);
        label1.setForeground(Color.WHITE);

        label2 = new JLabel();
        label2.setText("Elevator 2");
        label2.setFont(FONT);
        label2.setForeground(Color.WHITE);

        label3 = new JLabel();
        label3.setText("Elevator 3");
        label3.setFont(FONT);
        label3.setForeground(Color.WHITE);

        label4 = new JLabel();
        label4.setText("Elevator 4");
        label4.setFont(FONT);
        label4.setForeground(Color.WHITE);

        errorLabel = new JLabel();
        errorLabel.setText("Errors");
        errorLabel.setFont(FONT);
        errorLabel.setForeground(Color.WHITE);

        // Set up JTextFields to show the current position of each elevator
        elevator1Position = new JTextField();
        elevator1Position.setPreferredSize(new Dimension(80, 50));
        elevator1Position.setFont(FONT);
        elevator1Position.setHorizontalAlignment(JTextField.CENTER);
        elevator1Position.setBackground(Color.BLACK);
        elevator1Position.setForeground(Color.WHITE);
        elevator1Position.setEditable(false);

        elevator2Position = new JTextField();
        elevator2Position.setPreferredSize(new Dimension(80, 50));
        elevator2Position.setHorizontalAlignment(JTextField.CENTER);
        elevator2Position.setBackground(Color.BLACK);
        elevator2Position.setForeground(Color.WHITE);
        elevator2Position.setFont(FONT);
        elevator2Position.setEditable(false);

        elevator3Position = new JTextField();
        elevator3Position.setPreferredSize(new Dimension(80, 50));
        elevator3Position.setHorizontalAlignment(JTextField.CENTER);
        elevator3Position.setBackground(Color.BLACK);
        elevator3Position.setForeground(Color.WHITE);
        elevator3Position.setFont(FONT);
        elevator3Position.setEditable(false);

        elevator4Position = new JTextField();
        elevator4Position.setPreferredSize(new Dimension(80, 50));
        elevator4Position.setHorizontalAlignment(JTextField.CENTER);
        elevator4Position.setBackground(Color.BLACK);
        elevator4Position.setForeground(Color.WHITE);
        elevator4Position.setFont(FONT);
        elevator4Position.setEditable(false);

        elevator1Direction = new JTextField();
        elevator1Direction.setPreferredSize(new Dimension(280, 50));
        elevator1Direction.setHorizontalAlignment(JTextField.CENTER);
        elevator1Direction.setBackground(Color.BLACK);
        elevator1Direction.setForeground(Color.WHITE);
        elevator1Direction.setFont(FONT);
        elevator1Direction.setEditable(false);

        elevator2Direction = new JTextField();
        elevator2Direction.setPreferredSize(new Dimension(280, 50));
        elevator2Direction.setHorizontalAlignment(JTextField.CENTER);
        elevator2Direction.setBackground(Color.BLACK);
        elevator2Direction.setForeground(Color.WHITE);
        elevator2Direction.setFont(FONT);
        elevator2Direction.setEditable(false);

        elevator3Direction = new JTextField();
        elevator3Direction.setPreferredSize(new Dimension(280, 50));
        elevator3Direction.setHorizontalAlignment(JTextField.CENTER);
        elevator3Direction.setBackground(Color.BLACK);
        elevator3Direction.setForeground(Color.WHITE);
        elevator3Direction.setFont(FONT);
        elevator3Direction.setEditable(false);

        elevator4Direction = new JTextField();
        elevator4Direction.setPreferredSize(new Dimension(280, 50));
        elevator4Direction.setHorizontalAlignment(JTextField.CENTER);
        elevator4Direction.setBackground(Color.BLACK);
        elevator4Direction.setForeground(Color.WHITE);
        elevator4Direction.setFont(FONT);
        elevator4Direction.setEditable(false);

        errorLog = new JTextArea();
        errorLog.setPreferredSize(new Dimension(700, 900));
        errorLog.setFont(ERROR_FONT);
        errorLog.setBackground(Color.BLACK);
        errorLog.setEditable(false);
        errorLog.setForeground(Color.RED);

        JScrollPane scroll = new JScrollPane();
        scroll.add(errorLog);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        panel1.add(label1);
        panel1.add(elevator1Position);
        panel1.add(elevator1Direction);

        panel2.add(label2);
        panel2.add(elevator2Position);
        panel2.add(elevator2Direction);

        panel3.add(label3);
        panel3.add(elevator3Position);
        panel3.add(elevator3Direction);

        panel4.add(label4);
        panel4.add(elevator4Position);
        panel4.add(elevator4Direction);

        errorPanel.add(errorLabel);
        errorPanel.add(errorLog);

        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.add(errorPanel);

        frame.setVisible(true);
    }

    /**
     * Method for the floor to call that updates a the JTextField showing the
     * position of the elevator in real time.
     *
     * @param elevator:     The elevator that is currently moving and needs to have
     *                      the current positon updated.
     * @param currentFloor: The floor number that the elevator is currently at.
     */
    public void updateLocationField(int elevator, Integer currentFloor) {
        JTextField selectedField = null;
        switch (elevator) {
            case 1:
                selectedField = elevator1Position;
                break;
            case 2:
                selectedField = elevator2Position;
                break;
            case 3:
                selectedField = elevator3Position;
                break;
            case 4:
                selectedField = elevator4Position;
                break;
        }
        selectedField.setText(currentFloor.toString());
    }

    /**
     * Updates the Direction text box for the specified elevator of the specified Direction.
     * @param elevator: the elevator whose Direction is being updated
     * @param direction: the new Direction of the Elevator.
     */
    public void updateDirectionField(int elevator, Direction direction) {
        JTextField selectedField = null;
        switch (elevator) {
            case 1:
                selectedField = elevator1Direction;
                break;
            case 2:
                selectedField = elevator2Direction;
                break;
            case 3:
                selectedField = elevator3Direction;
                break;
            case 4:
                selectedField = elevator4Direction;
                break;
        }
        String directionToString = null;
        switch (direction) {
            case UP:
                directionToString = "Up";
                break;
            case DOWN:
                directionToString = "Down";
                break;
            case STATIONARY:
                directionToString = "Stationary";
                break;
            case STUCK:
                directionToString = "Stuck";
                break;
            case DOOR_STUCK:
                directionToString = "Door Stuck";
                break;
        }
        selectedField.setText(directionToString);

    }

    /**
     * Appends an error to the error log on the GUI.
     *
     * @param elevator: The elevator that experienced an error.
     * @param msg:      The error message that is being displayed on the GUI.
     */
    public void appendToErrorLog(int elevator, String msg) {
        errorLog.append("Elevator " + elevator + ": " + msg + "\n");
    }

    /**
     * Returns the JFrame for testing purposes.
     * @return: the JFrame of the GUI.
     */
    public JFrame getFrame(){return frame;}

    /**
     * Returns panel1 for testing purposes.
     * @return: panel1
     */
    public JPanel getPanel1() {
        return panel1;
    }

    /**
     * Returns panel2 for testing purposes.
     * @return: panel2
     */
    public JPanel getPanel2() {
        return panel2;
    }

    /**
     * Returns panel3 for testing purposes.
     * @return: panel3
     */
    public JPanel getPanel3() {
        return panel3;
    }

    /**
     * Returns panel4 for testing purposes.
     * @return: panel4
     */
    public JPanel getPanel4() {
        return panel4;
    }

    /**
     * Returns errorPanel for testing purposes.
     * @return: errorPanel
     */
    public JPanel getErrorPanel() {
        return errorPanel;
    }

    /**
     * Returns label1 for testing purposes.
     * @return: label1
     */
    public JLabel getLabel1() {
        return label1;
    }

    /**
     * Returns label2 for testing purposes.
     * @return: label2
     */
    public JLabel getLabel2() {
        return label2;
    }

    /**
     * Returns label3 for testing purposes.
     * @return: label3
     */
    public JLabel getLabel3() {
        return label3;
    }

    /**
     * Returns label4 for testing purposes.
     * @return label4
     */
    public JLabel getLabel4() {
        return label4;
    }

    /**
     * Returns errorLabel for testing purposes.
     * @return: errorLabel
     */
    public JLabel getErrorLabel() {
        return errorLabel;
    }

    /**
     * Sets the elevator1Position text field for testing purposes.
     * @param elevator1Position
     */
    public void setElevator1Position(JTextField elevator1Position) {
        this.elevator1Position = elevator1Position;
    }

    /**
     * Sets the elevator2Position text field for testing purposes.
     * @param elevator2Position
     */
    public void setElevator2Position(JTextField elevator2Position) {
        this.elevator2Position = elevator2Position;
    }

    /**
     * Sets the elevator3Position text field for testing purposes.
     * @param elevator3Position
     */
    public void setElevator3Position(JTextField elevator3Position) {
        this.elevator3Position = elevator3Position;
    }

    /**
     * Sets the elevator4Position text field for testing purposes.
     * @param elevator4Position
     */
    public void setElevator4Position(JTextField elevator4Position) {
        this.elevator4Position = elevator4Position;
    }

    /**
     * Sets the errorLog text area for testing purposes.
     * @param errorLog
     */
    public void setErrorLog(JTextArea errorLog) {
        this.errorLog = errorLog;
    }

    /**
     * Sets the elevator1Direction field for testing purposes.
     * @param elevator1Direction
     */
    public void setElevator1Direction(JTextField elevator1Direction) {
        this.elevator1Direction = elevator1Direction;
    }

    /**
     * Sets the elevator2Direction field for testing purposes.
     * @param elevator2Direction
     */
    public void setElevator2Direction(JTextField elevator2Direction) {
        this.elevator2Direction = elevator2Direction;
    }

    /**
     * Sets the elevator3Direction field for testing purposes.
     * @param elevator3Direction
     */
    public void setElevator3Direction(JTextField elevator3Direction) {
        this.elevator3Direction = elevator3Direction;
    }

    /**
     * Sets the elevator4Direction field for testing purposes.
     * @param elevator4Direction
     */
    public void setElevator4Direction(JTextField elevator4Direction) {
        this.elevator4Direction = elevator4Direction;
    }

    /**
     * Gets the elevator1Position field for testing purposes.
     */
    public JTextField getElevator1Position() {return elevator1Position;}

    /**
     * Gets the elevator2Position field for testing purposes.
     */
    public JTextField getElevator2Position() {return elevator2Position;}

    /**
     * Gets the elevator3Position field for testing purposes.
     */
    public JTextField getElevator3Position() {return elevator3Position;}

    /**
     * Gets the elevator4Position field for testing purposes.
     */
    public JTextField getElevator4Position() {return elevator4Position;}

    /**
     * Gets the elevator1Direction field for testing purposes.
     */
    public JTextField getElevator1Direction() {return elevator1Direction;}

    /**
     * Gets the elevator2Direction field for testing purposes.
     */
    public JTextField getElevator2Direction() {return elevator2Direction;}

    /**
     * Gets the elevator3Direction field for testing purposes.
     */
    public JTextField getElevator3Direction() {return elevator3Direction;}

    /**
     * Gets the elevator4Direction field for testing purposes.
     */
    public JTextField getElevator4Direction() {return elevator4Direction;}

    /**
     * Gets the errorLog field for testing purposes.
     */
    public JTextArea getErrorLog() {return errorLog;}

}