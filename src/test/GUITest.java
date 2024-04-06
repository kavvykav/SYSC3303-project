package test;

import gui.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class GUITest {

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

    private GUI gui;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        gui = new GUI(InetAddress.getLoopbackAddress(), 1234);
        gui.setErrorLog(errorLog);
        gui.setElevator1Position(elevator1Position);
        gui.setElevator2Position(elevator2Position);
        gui.setElevator3Position(elevator3Position);
        gui.setElevator4Position(elevator4Position);
    }

    @Test
    public void testCreateAndShowGUI() {
        gui.createAndShowGUI();

        // Verify that the frame is visible and correct background color
        JFrame frame = gui.getFrame();
        assertThat(frame.isVisible()).isTrue();
        assertThat(frame.getBackground()).isEqualTo(Color.BLACK);

        // Verify that all panels are added to the frame
        Container contentPane = frame.getContentPane();
        assertThat(contentPane.getComponents()).contains(gui.getPanel1(), gui.getPanel2(), gui.getPanel3(), gui.getPanel4(), gui.getErrorPanel());

        // Verify the layout and background color
        assertThat(contentPane.getLayout()).isInstanceOf(GridLayout.class);

        // Verify that the labels are initialized
        assertThat(gui.getLabel1()).isNotNull();
        assertThat(gui.getLabel2()).isNotNull();
        assertThat(gui.getLabel3()).isNotNull();
        assertThat(gui.getLabel4()).isNotNull();
        assertThat(gui.getErrorLabel()).isNotNull();
    }

    @Test
    public void testUpdateLocationField() {
        gui.updateLocationField(1, 5);
        verify(elevator1Position).setText("5");

        gui.updateLocationField(2, 10);
        verify(elevator2Position).setText("10");

        gui.updateLocationField(3, 15);
        verify(elevator3Position).setText("15");

        gui.updateLocationField(4, 20);
        verify(elevator4Position).setText("20");
    }

    @Test
    public void testAppendToErrorLog() {
        gui.appendToErrorLog(1, "Error message 1");
        verify(errorLog).append("Elevator 1: Error message 1\n");

        gui.appendToErrorLog(2, "Error message 2");
        verify(errorLog).append("Elevator 2: Error message 2\n");

        gui.appendToErrorLog(3, "Error message 3");
        verify(errorLog).append("Elevator 3: Error message 3\n");

        gui.appendToErrorLog(4, "Error message 4");
        verify(errorLog).append("Elevator 4: Error message 4\n");
    }
}