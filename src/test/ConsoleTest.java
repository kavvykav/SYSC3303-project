package test;

import common.Direction;
import gui.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class ConsoleTest {

    private Console console;

    @Mock
    private JTextArea errorLog;
    @Mock
    private JTextField elevator1Position;
    @Mock
    private JTextField elevator2Position;
    @Mock
    private JTextField elevator3Position;
    @Mock
    private JTextField elevator4Position;
    @Mock
    private JTextField elevator1Direction;
    @Mock
    private JTextField elevator2Direction;
    @Mock
    private JTextField elevator3Direction;
    @Mock
    private JTextField elevator4Direction;
    @Mock
    private JTextField tripsRequestedField;
    @Mock
    private JTextField tripsCompletedField;
    @Mock
    private JTextField faultsField;
    @Mock
    private JTextField timeField;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        console = new Console();
        console.setErrorLog(errorLog);
        console.setElevator1Position(elevator1Position);
        console.setElevator2Position(elevator2Position);
        console.setElevator3Position(elevator3Position);
        console.setElevator4Position(elevator4Position);
        console.setElevator1Direction(elevator1Direction);
        console.setElevator2Direction(elevator2Direction);
        console.setElevator3Direction(elevator3Direction);
        console.setElevator4Direction(elevator4Direction);
        console.setTripsRequestedField(tripsRequestedField);
        console.setTripsCompletedField(tripsCompletedField);
        console.setFaultsField(faultsField);
        console.setTimeField(timeField);
    }

    /**
     * Test the creation of the console
     */
    @Test
    public void testConsole() {
        // Verify that the frame is visible and correct background color
        JFrame frame = console.getFrame();
        assertThat(frame.isVisible()).isTrue();

        // Verify that all panels are added to the frame
        Container contentPane = frame.getContentPane();
        assertThat(contentPane.getComponents()).contains(console.getPanel1(), console.getPanel2(), console.getPanel3(), console.getPanel4(), console.getErrorPanel(), console.getStatsPanel());

        // Verify the layout
        assertThat(contentPane.getLayout()).isInstanceOf(FlowLayout.class);

        // Verify that the labels are initialized
        assertThat(console.getLabel1()).isNotNull();
        assertThat(console.getLabel2()).isNotNull();
        assertThat(console.getLabel3()).isNotNull();
        assertThat(console.getLabel4()).isNotNull();
        assertThat(console.getErrorLabel()).isNotNull();
        assertThat(console.getFaultsLabel()).isNotNull();
        assertThat(console.getTimeLabel()).isNotNull();
        assertThat(console.getTripsRequestedLabel()).isNotNull();
        assertThat(console.getTripsCompletedLabel()).isNotNull();
    }

    /**
     * Test the UpdateLocationField method
     */
    @Test
    public void testUpdateLocationField() {
        JTextField elevator1Position = console.getElevator1Position();
        JTextField elevator2Position = console.getElevator2Position();
        JTextField elevator3Position = console.getElevator3Position();
        JTextField elevator4Position = console.getElevator4Position();

        console.updateLocationField(1, 5);
        verify(elevator1Position).setText("5");

        console.updateLocationField(2, 10);
        verify(elevator2Position).setText("10");

        console.updateLocationField(3, 15);
        verify(elevator3Position).setText("15");

        console.updateLocationField(4, 20);
        verify(elevator4Position).setText("20");
    }

    /**
     * Test the UpdateDirectionField method
     */
    @Test
    public void testUpdateDirectionField() {
        JTextField elevator1Direction = console.getElevator1Direction();
        JTextField elevator2Direction = console.getElevator2Direction();
        JTextField elevator3Direction = console.getElevator3Direction();
        JTextField elevator4Direction = console.getElevator4Direction();

        console.updateDirectionField(1, Direction.UP);
        verify(elevator1Direction).setText("Up");

        console.updateDirectionField(2, Direction.DOWN);
        verify(elevator2Direction).setText("Down");

        console.updateDirectionField(3, Direction.STATIONARY);
        verify(elevator3Direction).setText("Stationary");

        console.updateDirectionField(4, Direction.STUCK);
        verify(elevator4Direction).setText("Stuck");
    }

    /**
     * Test the UpdateRequestsReceivedField method
     */
    @Test
    public void testUpdateRequestsReceivedField(){
        JTextField tripsRequestedField = console.getTripsRequestedField();

        console.updateRequestsReceivedField(9);
        verify(tripsRequestedField).setText("9");

        console.updateRequestsReceivedField(-16);
        verify(tripsRequestedField).setText("-16");

        console.updateRequestsReceivedField(100);
        verify(tripsRequestedField).setText("100");

        console.updateRequestsReceivedField(0);
        verify(tripsRequestedField).setText("0");
    }

    /**
     * Test the UpdateRequestsServedField method
     */
    @Test
    public void testUpdateRequestsServedField(){
        JTextField tripsCompletedField = console.getTripsCompletedField();

        console.updateRequestsServedField(9);
        verify(tripsCompletedField).setText("9");

        console.updateRequestsServedField(-16);
        verify(tripsCompletedField).setText("-16");

        console.updateRequestsServedField(100);
        verify(tripsCompletedField).setText("100");

        console.updateRequestsServedField(0);
        verify(tripsCompletedField).setText("0");
    }

    /**
     * Test the UpdateRequestsFailedField method
     */
    @Test
    public void testUpdateRequestsFailedField() {
        JTextField faultsField = console.getFaultsField();

        console.updateRequestsFailedField(9);
        verify(faultsField).setText("9");

        console.updateRequestsFailedField(-16);
        verify(faultsField).setText("-16");

        console.updateRequestsFailedField(100);
        verify(faultsField).setText("100");

        console.updateRequestsFailedField(0);
        verify(faultsField).setText("0");
    }

    /**
     * Test the UpdateTimeField method
     */
    @Test
    public void testUpdateTimeField() {
        JTextField timeField = console.getTimeField();

        console.updateTimeField("Hello");
        verify(timeField).setText("Hello sec");

        console.updateTimeField("-16");
        verify(timeField).setText("-16 sec");

        console.updateTimeField("100000");
        verify(timeField).setText("100000 sec");

        console.updateTimeField("0");
        verify(timeField).setText("0 sec");
    }

    /**
     * Test the appendToErrorLog method
     */
    @Test
    public void testAppendToErrorLog() {
        JTextArea errorLog = console.getErrorLog();

        console.appendToErrorLog(1, "Error message 1");
        verify(errorLog).append("Elevator 1: Error message 1\n");

        console.appendToErrorLog(2, "Error message 2");
        verify(errorLog).append("Elevator 2: Error message 2\n");

        console.appendToErrorLog(3, "Error message 3");
        verify(errorLog).append("Elevator 3: Error message 3\n");
    }
}