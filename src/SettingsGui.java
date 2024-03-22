import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class SettingsGui {
    private JFrame settingsFrame;
    private JTextField heightText =new JTextField();
    private JTextField widthText=new JTextField();
    private JTextField expHeightText=new JTextField();
    private JTextField expWidthText=new JTextField();
    private JTextField checkIterationsText=new JTextField();
    private JTextField expCheckIterations=new JTextField();

    private JTextField backFadeText=new JTextField();
    JLabel emptyLabel;
    JButton applyButton;
    JPanel gridBackgroundPane;


    public void buildSettingsGui() {


        configureBackgroundPane(8);

        addNewRow("Image height: ",heightText);
        addNewRow("Image width: ",widthText);
        addNewRow("Export image height: ",expHeightText);
        addNewRow("Export image width: ",expWidthText);
        addNewRow("Number of check iterations: ",checkIterationsText);
        addNewRow("Number of check iterations -export ",expCheckIterations);
        addNewRow("Min. satisfied iterations.: ",backFadeText);

        displayCurrentParameters();

        emptyLabel = new JLabel("");
        applyButton = new JButton("APPLY");
        applyButton.addActionListener(new ApplyButtonListener());
        addNewRow(emptyLabel, applyButton);

        configureSettingsFrame(gridBackgroundPane,300,500);

    }

    JButton saveButton;
    JTextField firstExprText=new JTextField();
    JTextField nameText=new JTextField();
    JTextField recurrentExprText=new JTextField();
    private static ArrayList<String> names;

    private static ArrayList<String> firstExpressionList;
    private static ArrayList<String> recurrentExpressionList;


    public void buildEquationSettingsGui() {
        configureBackgroundPane(6);
        emptyLabel = new JLabel("");

        applyButton = new JButton("SET");
        applyButton.addActionListener(new SetButtonListener());
        applyButton.setToolTipText("Apply current configuration");

        saveButton = new JButton("SAVE");
        saveButton.addActionListener(new SaveButtonListener());
        saveButton.setToolTipText("Save current configuration to file");

        addNewRow(new JLabel("Equation parameters: "),emptyLabel);
        addNewRow("Equation name: ",nameText);
        addNewRow(new JLabel("Variable: "),new JLabel("p"));
        addNewRow("First expression z0:",firstExprText);
        addNewRow("Recurrent expression zN:",recurrentExprText);
        addNewRow(applyButton,saveButton);


        displayCurrentEquationParameters();

        recurrentExprText.setToolTipText("Symbol of previous expression is z");
        firstExprText.setToolTipText("Use variable symbol at least once");


        JPanel listPanel = createSavedEquationsList();


        JPanel backPanel = new JPanel();
        backPanel.add(BorderLayout.EAST, gridBackgroundPane);
        backPanel.add(BorderLayout.WEST, listPanel);


        configureSettingsFrame(backPanel,600,300);


    }

    private JPanel createSavedEquationsList() {
        loadEquations();
        DefaultListModel<JLabel> labelListModel = new DefaultListModel<>();
        for(String name:names){
            JLabel label=new JLabel();
            label.setText(name);
            label.setName(String.valueOf(names.indexOf(name)));
            labelListModel.addElement(label);
        }
        JList<JLabel> labelList = createSavedEquationLabels(labelListModel);
        labelList.addListSelectionListener(new LoadListener());
        JScrollPane scroll = new JScrollPane(labelList);

        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(200, 200));

        JPanel listPanel = new JPanel();
        listPanel.add(BorderLayout.CENTER,scroll);
        listPanel.setPreferredSize(new Dimension(200, 200));
        return listPanel;
    }

    private static JList<JLabel> createSavedEquationLabels(DefaultListModel<JLabel> labelListModel) {
        JList<JLabel> labelList = new JList<>(labelListModel);
        labelList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof JLabel label) {
                    setText(label.getText());
                    setIcon(label.getIcon());
                }
                return renderer;
            }
        });

        labelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return labelList;
    }

    private void displayCurrentEquationParameters() {
        nameText.setText(String.valueOf(Parameters.getEquationName()));
        firstExprText.setText(String.valueOf(Parameters.getEquationZ0()));
        recurrentExprText.setText(String.valueOf(Parameters.getEquationZN()));
    }


    public class SetButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            boolean i= checkAndApplyEquationSettings();
            if(!i){
                displayInvalidParametersText();
            }
        }
    }

    private boolean checkAndApplyEquationSettings() {
        String z0= firstExprText.getText();
        String zN= recurrentExprText.getText();
        String name =nameText.getText();

        Equation check = new Equation(z0,zN);
        if(check.checkExpressions()){
            Parameters.setEquationZ0(z0);
            Parameters.setEquationZN(zN);
            Parameters.setEquationName(name);
            settingsFrame.dispose();
            MainGui.repaint();
            return true;
        }
        return false;
    }

    public class LoadListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            JList<JLabel> list=(JList<JLabel>)e.getSource();
            JLabel label=list.getSelectedValue();
            int i=Integer.parseInt(label.getName());
            nameText.setText(names.get(i));

            firstExprText.setText(firstExpressionList.get(i));
            recurrentExprText.setText(recurrentExpressionList.get(i));
        }
    }

    public class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            String z0= firstExprText.getText();
            String zN= recurrentExprText.getText();
            String name = nameText.getText();
            Equation check = new Equation(z0,zN);
            if(check.checkExpressions()){
                try {
                    FileWriter fileWriter = new FileWriter("Equations.txt", true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(name+"#"+"#"+z0+"#"+zN+"#");
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    settingsFrame.repaint();
                    emptyLabel.setText("Saved succesfully");
                    displayCurrentEquationParameters();

                } catch(IOException ex) {
                    displayInvalidParametersText();
                }

            }

            else {
                displayInvalidParametersText();
            }

        }

    }

    public void loadEquations(){
        names = new ArrayList<>();
        firstExpressionList = new ArrayList<>();
        recurrentExpressionList = new ArrayList<>();

        try {


            FileReader fileReader = new FileReader("Equations.txt");

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String temp;


            while((temp=bufferedReader.readLine())!=null) {


                String[] temp2 = temp.split("#");
                if(temp2.length>=3) {
                    System.out.println(temp2[0]+" "+temp2[1]+" "+temp2[2]+" ");
                    names.add(temp2[0]);
                    firstExpressionList.add(temp2[1]);
                    recurrentExpressionList.add(temp2[2]);
                }

            }


            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private <T extends JComponent> void addNewRow(T component1,T component2) {
        gridBackgroundPane.add(component1);
        gridBackgroundPane.add(component2);
    }
    private void addNewRow(String text,JTextField value){
        JLabel label= new JLabel(text);

        gridBackgroundPane.add(label);
        gridBackgroundPane.add(value);
    }

    private void configureBackgroundPane(int rows) {
        GridLayout grid = new GridLayout(rows, 2);
        grid.setVgap(3);
        grid.setHgap(3);
        gridBackgroundPane = new JPanel(grid);
        gridBackgroundPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void configureSettingsFrame(JPanel content, int width, int height) {
        settingsFrame = new JFrame("SETTINGS");
        settingsFrame.setContentPane(content);
        settingsFrame.setResizable(false);
        settingsFrame.setPreferredSize(new Dimension(width, height));
        settingsFrame.setBounds(50, 50, width, height);
        settingsFrame.pack();
        settingsFrame.setVisible(true);
    }


    public class ApplyButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            try {

                boolean valid=checkAndApplySettings();
                if(valid) {
                    settingsFrame.dispose();
                    MainGui.repaint();
                }else {displayInvalidParametersText();}

            } catch (Exception e) {
                displayCurrentParameters();
                displayInvalidParametersText();
            }

        }

        private  boolean checkAndApplySettings() {
            int height = Integer.parseInt(heightText.getText());
            int width = Integer.parseInt(widthText.getText());
            int expHeight = Integer.parseInt(expHeightText.getText());
            int expWidth = Integer.parseInt(expWidthText.getText());
            int checkIterations = Integer.parseInt(checkIterationsText.getText());
            int expCheckIterations = Integer.parseInt(SettingsGui.this.expCheckIterations.getText());
            int backFade = Integer.parseInt(backFadeText.getText());
            if (height > 20 && width > 350 && expHeight > 20 && expWidth > 20 && checkIterations > 5 && expCheckIterations > 5 && backFade >= 0) {
                Parameters.setHeight(height);
                Parameters.setWidth(width);
                Parameters.setExportHeight(expHeight);
                Parameters.setExportWidth(expWidth);
                Parameters.setCheckIterations(checkIterations);
                Parameters.setExportCheckIterations(expCheckIterations);
                Parameters.setBackgroundFade(backFade);
                return true;
            }
            return false;
        }
    }

    private void displayInvalidParametersText() {
        emptyLabel.setText("WRONG PARAMETERS!");
        emptyLabel.setForeground(Color.RED);
        settingsFrame.repaint();
    }

    private void displayCurrentParameters() {
        heightText.setText(String.valueOf(Parameters.getHeight()));
        widthText.setText(String.valueOf(Parameters.getWidth()));
        expHeightText.setText(String.valueOf(Parameters.getExportHeight()));
        expWidthText.setText(String.valueOf(Parameters.getExportWidth()));
        checkIterationsText.setText(String.valueOf(Parameters.getCheckIterations()));
        expCheckIterations.setText(String.valueOf(Parameters.getExportCheckIterations()));
        backFadeText.setText(String.valueOf(Parameters.getBackgroundFade()));
    }
}




