import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainGui {

    static JFrame frame;
    static JPanel background;
    static JMenuBar upperPanel;
    static JButton settings;
    static JButton zoomIn;
    static JButton reset;
    static JButton zoomOut;
    static JButton saveImage;
    static JButton changeEquation;
    static JLabel displayLabel;
    private static JLabel chooseArea;
    static JPanel displayPanel;
    static JLayeredPane backPanel;
    static JPanel choosePanel;
    static JLabel timeLabel;

    public void buildMainGUI() {


        settings = new JButton("Settings");//tworzymy po kolei przyciski , i action listenery jak je naciśniemy
        settings.addActionListener(new SettingsListener());
        settings.setFont(Colors.fontButton);
        settings.setBackground(Colors.buttonColor);
        settings.setForeground(Colors.fontColor);
        settings.setPreferredSize(new Dimension(120, 40));


        saveImage = new JButton("Save");
        saveImage.addActionListener(new SaveImageListener());
        saveImage.setFont(Colors.fontButton);
        saveImage.setBackground(Colors.buttonColor);
        saveImage.setForeground(Colors.fontColor);
        saveImage.setPreferredSize(new Dimension(120, 40));


        reset = new JButton("Reset");
        reset.addActionListener(new resetListener());
        reset.setFont(Colors.fontButton);
        reset.setBackground(Colors.buttonColor);
        reset.setForeground(Colors.fontColor);
        reset.setPreferredSize(new Dimension(120, 40));

        changeEquation = new JButton("Equation");
        changeEquation.addActionListener(new ChangeEquationListener());
        changeEquation.setFont(Colors.fontButton);
        changeEquation.setBackground(Colors.buttonColor);
        changeEquation.setForeground(Colors.fontColor);
        changeEquation.setPreferredSize(new Dimension(120, 40));

        timeLabel = new JLabel("");
        timeLabel.setFont(Colors.fontButton);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 10));
        timeLabel.setBackground(Colors.buttonColor);
        timeLabel.setForeground(Colors.fontColor);
        timeLabel.setPreferredSize(new Dimension(120, 40));


        BufferedImage zoomPlus = loadImage("lupa_plus.png");
        ImageIcon zoomInIcon = new ImageIcon(zoomPlus);
        zoomIn = new JButton(zoomInIcon);
        zoomIn.addActionListener(new ZoomInListener());
        zoomIn.setBackground(Colors.buttonColor);


        BufferedImage zoomMinus = loadImage("lupa_minus.png");
        ImageIcon zoomOutIcon = new ImageIcon(zoomMinus);
        zoomOut = new JButton(zoomOutIcon);
        zoomOut.addActionListener(new ZoomOutListener());
        zoomOut.setBackground(Colors.buttonColor);



        upperPanel = new JMenuBar();// tworzymy panele na przyciski i dodajemy je do nich
        upperPanel.setBackground(Colors.panelColor);
        upperPanel.setOpaque(true);

        upperPanel.add(settings);
        upperPanel.add(zoomIn);
        upperPanel.add(zoomOut);
        upperPanel.add(saveImage);
        upperPanel.add(reset);
        upperPanel.add(changeEquation);
        upperPanel.add(timeLabel);
        upperPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        upperPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));



        ImageIcon mainIcon = new ImageIcon();
        displayLabel = new JLabel(mainIcon);
        displayPanel = new JPanel();
        displayPanel.setBackground(Colors.backgroundColor);
        displayPanel.add(BorderLayout.CENTER, displayLabel);
        choosePanel = new JPanel();
        choosePanel.setOpaque(false);
        repaint();

        backPanel = new JLayeredPane(); // na głównym panelu pod spodem są cyfry, na wierzchu przyciski, w razie przegranej komunikat
        backPanel.setLayout(null);
        backPanel.add(displayPanel, Integer.valueOf(1));
        backPanel.add(choosePanel, Integer.valueOf(2));


        background = new JPanel();
        background.setBackground(Colors.panelColor);
        background.add(BorderLayout.NORTH, upperPanel);// dodajemy wszystko do tła, a tło do ramki
        background.add(BorderLayout.CENTER, backPanel);

        frame = new JFrame("MANDELBROT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(background);


        frame.setBounds(0, 0, Parameters.getWidth(), Parameters.getHeight() + 40);

        upperPanel.setBounds(0, 0, displayPanel.getWidth(), 40);
        upperPanel.setPreferredSize(new Dimension(Parameters.getWidth(), 40));
        displayPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        displayPanel.setPreferredSize(new Dimension(Parameters.getWidth(), Parameters.getHeight()));
        choosePanel.setBounds(0, 0, Parameters.getWidth(), Parameters.getHeight());
        choosePanel.setPreferredSize(new Dimension(Parameters.getWidth(), Parameters.getHeight()));
        backPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        backPanel.setPreferredSize(new Dimension(Parameters.getWidth(), Parameters.getHeight()));

        background.setPreferredSize(new Dimension(frame.getWidth(), (frame.getHeight())));

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    private static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class SettingsListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            SettingsGui gui = new SettingsGui();
            gui.buildSettingsGui();

        }
    }
    public static class ChangeEquationListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            SettingsGui gui = new SettingsGui();
            gui.buildEquationSettingsGui();

        }
    }

    public static class resetListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            frame.dispose();
            Parameters.setStep(0.0034);
            Parameters.setWidth(1200);
            Parameters.setHeight(720);
            Parameters.setiMax(1.1);
            Parameters.setxMax(0.6);
            MainGui gui = new MainGui();
            gui.buildMainGUI();

        }
    }

    public static class SaveImageListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {

            JFrame saveFrame = new JFrame("SAVE FILE");

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new PNGFileFilter());


            int result = fileChooser.showOpenDialog(saveFrame);


            if (result == JFileChooser.APPROVE_OPTION) {
                int t = Parameters.getCheckIterations();//zmieniam ilość iteracji na tą do eksportu
                Parameters.setCheckIterations(Parameters.getExportCheckIterations());
                //creating new image of desired export properties


                saveFrame.dispose();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Thread.currentThread().setPriority(3);
                        ParallelImage im = new ParallelImage(Parameters.getExportWidth(), Parameters.getExportHeight(),
                                Parameters.getxMax(), Parameters.getiMax(), Parameters.getStep() * Parameters.getWidth() / Parameters.getExportWidth());

                        BufferedImage image = null;


                        try {
                            image = im.generateImage();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }


                        //przywracam ilość iteracji
                        File output = fileChooser.getSelectedFile();
                        try {

                            ImageIO.write(image, "png", output);
                            System.out.println("Image saved successfully.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }).start();
                Parameters.setCheckIterations(t);


            }
        }

        static class PNGFileFilter extends javax.swing.filechooser.FileFilter {
            @Override
            public boolean accept(File file) {
                // Allow directories and files with .png extension
                return file.isDirectory() || file.getName().toLowerCase().endsWith(".png");
            }

            @Override
            public String getDescription() {
                // Description for the filter
                return "PNG files (*.png)";
            }
        }
    }

    public static class ZoomInListener implements ActionListener {
        static MyMouseListener myMouseListener;

        public void actionPerformed(ActionEvent a) {

            zoomIn.setEnabled(false);
            settings.setEnabled(false);
            zoomOut.setEnabled(false);
            saveImage.setEnabled(false);
            myMouseListener = new MyMouseListener();
            choosePanel.addMouseListener(myMouseListener);

        }

        public static class MyMouseListener extends MouseAdapter {
            private static int x1;
            private static int y1;
            private static int clickCount=0;
            MyMouseTracker tracker;




            @Override
            public void mousePressed(MouseEvent e) {

                clickCount++;
                System.out.println("Mouse Clicked");
                if(clickCount==1) {
                    x1 = e.getX();
                    y1 = e.getY();
                    chooseArea= new JLabel("");
                    chooseArea.setOpaque(true);
                    chooseArea.setBounds(x1,y1,1,1);
                    chooseArea.setPreferredSize(new Dimension(1,1));
                    chooseArea.setBackground(Colors.chooseAreaColor);
                    choosePanel.add(chooseArea);
                    tracker =new MyMouseTracker(x1,y1);
                    choosePanel.addMouseMotionListener(tracker);

                }
                else{
                    choosePanel.removeMouseMotionListener(tracker);
                    choosePanel.remove(chooseArea);
                    clickCount=0;
                    int x2 = e.getX();
                    int y2 = e.getY();
                    System.out.println(x2 + "  " + y2);
                    int xMax = Math.max(x1, x2);
                    int xMin = Math.min(x1, x2);
                    int yMax = Math.max(y1, y2);
                    int yMin = Math.min(y1, y2);

                    MainGui gui = new MainGui();
                    choosePanel.removeMouseListener(myMouseListener);
                    gui.rescale(xMax, yMax, xMin, yMin);
                }




            }


        }
        public static class MyMouseTracker extends MouseMotionAdapter{
            private static int x1;
            private  static int y1;
            public MyMouseTracker(int x1,int y1) {
                MyMouseTracker.x1=x1;
                MyMouseTracker.y1=y1;
            }

            @Override
            public void mouseMoved(MouseEvent e) {

                    int xm=e.getX();
                    int ym=e.getY();
                    chooseArea.setBounds(Math.min(x1,xm),Math.min(y1,ym),Math.abs(xm-x1),Math.abs(ym-y1));
                    chooseArea.setPreferredSize(new Dimension(Math.abs(xm-x1),Math.abs(ym-y1)));


            }
        }



    }

    public void rescale(int xMax, int yMax, int xMin, int yMin) {
        double trueHeight = Parameters.getHeight() * Parameters.getStep();
        double trueWidth = Parameters.getWidth() * Parameters.getStep();

        Parameters.setiMax(Parameters.getiMax() - trueHeight * yMin / Parameters.getHeight());

        Parameters.setxMax(Parameters.getxMax() - trueWidth * (Parameters.getWidth() - xMax) / Parameters.getWidth());

        Parameters.setStep(Parameters.getStep() * (yMax - yMin) / Parameters.getHeight());



        MainGui.repaint();
        zoomIn.setEnabled(true);
        settings.setEnabled(true);
        zoomOut.setEnabled(true);
        saveImage.setEnabled(true);


    }


    public static void repaint() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                ParallelImage im = new ParallelImage(Parameters.getWidth(), Parameters.getHeight(),
                        Parameters.getxMax(), Parameters.getiMax(), Parameters.getStep());
                BufferedImage mainImage;
                try {
                    long startTime = System.currentTimeMillis();
                    mainImage = im.generateImage();
                    long time = System.currentTimeMillis() - startTime;
                    timeLabel.setText("Time of generation: " + (double) time / 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                displayLabel.setIcon(new ImageIcon(mainImage));
                displayPanel.repaint();
            }}).start();
    }

    public static class ZoomOutListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            Parameters.setiMax(Parameters.getiMax() +Parameters.getStep()*Parameters.getHeight()/2);
            Parameters.setxMax(Parameters.getxMax() +Parameters.getStep()*Parameters.getWidth()/2);
            Parameters.setStep(Parameters.getStep() * 2);
            MainGui gui = new MainGui();
            gui.repaint();
        }
    }


}
