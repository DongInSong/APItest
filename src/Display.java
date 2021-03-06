import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

import javax.swing.*;
import org.json.simple.parser.ParseException;
import ApiPackage.KakaoAPI;

public class Display extends JFrame {

    KakaoAPI kakaoAPI = new KakaoAPI();
    DataLoader dataLoader = new DataLoader();
    DBConnecter dbConnecter = new DBConnecter();

    public String selectedDo = "";
    public String selectedDoCode = "";
    public String selectedSi = "";
    public String location;
    public String campType;
    public String searchWord;

    JLabel locationInput = new JLabel();
    JLabel Result = new JLabel();
    JTextField textField = new JTextField();
    JButton button = new JButton();

    JPanel jPanel = new JPanel();
    JPanel jPanel2 = new JPanel();

    JPanel search = new JPanel();
    JPanel typePanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    String[] doName = dbConnecter.read_Do();
    JComboBox<String> doNameComboBox = new JComboBox<String>(doName);

    String[] doCodeList = dbConnecter.read_DoCode();

    List<String>[] siNameList = dbConnecter.read_Si();
    JComboBox<String> siNameComboBox = new JComboBox<String>();

    String[] type = dataLoader.getType();
    JComboBox<String> typeComboBox = new JComboBox<String>(type);

    ListTest listTest = new ListTest(this);
    // JScrollPane pane1;
    // JScrollPane pane2;

    public int frameWidth = 1000;
    public int frameHeight = 500;

    public Display() {
        // UIManager.put("ComboBoxUI", "com.alee.laf.combobox.WebComboBoxUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(frameWidth, frameHeight);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        listener();

        getSetting();

        jPanel.add(doNameComboBox);
        jPanel.add(siNameComboBox);

        search.add(textField);
        typePanel.add(typeComboBox);
        buttonPanel.add(button);

        jPanel2.add(search);
        jPanel2.add(jPanel);
        jPanel2.add(typePanel);
        jPanel2.add(buttonPanel);
        // jPanel2.add(listTest.campList);
        // jPanel2.add(pane2);
        // add(BorderLayout.WEST, jPanel2);

        Result.setPreferredSize(new Dimension(200, 200));

        getContentPane().add(BorderLayout.CENTER, jPanel2);
        getContentPane().add(BorderLayout.SOUTH, Result);
        setVisible(true);
        pack();
    }

    public void getSetting() {

        jPanel.setBackground(Color.WHITE);
        jPanel.setLayout(new FlowLayout());

        search.setBackground(Color.WHITE);
        search.setLayout(new FlowLayout());

        typePanel.setBackground(Color.WHITE);
        typePanel.setLayout(new FlowLayout());

        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout());

        jPanel2.setBackground(Color.WHITE);
        jPanel2.setLayout(new GridLayout(4, 1, 20, 10));

        // ??? ????????????
        doNameComboBox.setPreferredSize(new Dimension(200, 40));
        doNameComboBox.setBackground(Color.WHITE);
        doNameComboBox.setFont(new Font("??????", Font.BOLD, 20));

        // ??? ????????????
        siNameComboBox.setPreferredSize(new Dimension(200, 40));
        siNameComboBox.setBackground(Color.WHITE);
        siNameComboBox.setFont(new Font("??????", Font.BOLD, 20));
        siNameComboBox.addItem(siNameList[0].get(0));

        // ?????? ????????????
        typeComboBox.setPreferredSize(new Dimension(200, 40));
        typeComboBox.setBackground(Color.WHITE);
        typeComboBox.setFont(new Font("??????", Font.BOLD, 20));

        // ????????????
        textField.setPreferredSize(new Dimension(400, 40));
        textField.setBackground(Color.WHITE);
        textField.setText("????????? ??????");
        textField.setFont(new Font("??????", Font.ITALIC, 20));
        textField.setForeground(Color.gray);

        // ?????? ??????
        button.setPreferredSize(new Dimension(80, 40));
        button.setBackground(Color.WHITE);
        button.setText("??????");
        button.setFont(new Font("??????", Font.BOLD, 20));

        // // ?????? ?????? ?????????
        // pane1 = new JScrollPane(listTest.list1);
        // listTest.list1.setFixedCellHeight(20);
        // listTest.list1.setFixedCellWidth(400);

        // // ?????? ?????? ?????????
        // pane2 = new JScrollPane(listTest.list2);
        // listTest.list2.setFixedCellHeight(20);
        // listTest.list2.setFixedCellWidth(400);

        // ?????????
        Result.setHorizontalAlignment(JLabel.CENTER);
        Result.setFont(new Font("??????", Font.BOLD, 20));
        locationInput.setHorizontalAlignment(JLabel.CENTER);
        locationInput.setFont(new Font("??????", Font.BOLD, 20));

    }

    public void listener() {

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDo = doNameComboBox.getSelectedItem().toString();
                selectedSi = siNameComboBox.getSelectedItem().toString();

                // ?????? ????????? ?????? ??? ??? ''
                if (selectedSi.equals("??????/???/???")) {
                    selectedSi = "";
                }

                // listTest.campList.removeAll();
                // listTest.performList.removeAll();
                try {

                    // ?????? ??????
                    location = selectedDo + " " + selectedSi;
                    campType = typeComboBox.getSelectedItem().toString();
                    if (textField.getText().equals("")) {
                        searchWord = "??????";
                    } else {
                        searchWord = textField.getText();
                    }

                    // ?????? ??? ?????? ???, ?????? ?????? 0
                    if (selectedDo.equals("??????/???")) {
                        listTest.xy = new String[2];
                        listTest.xy[0] = "0";
                        listTest.xy[1] = "0";
                    } else {
                        listTest.xy = kakaoAPI.getPosition(location);
                        listTest.dateLoad();
                    }

                    // ?????? ?????????
                    Result.setText("<html>" +
                            "??????: " + listTest.xy[0] +
                            "<br>" + "??????: " + listTest.xy[1] +
                            "<br>" +
                            "<br>" + "?????????: " + searchWord +
                            "<br>" + "??????: " + location + " " + selectedDoCode +
                            "<br>" + "????????????: " + campType +
                            "</html>");
                    // listTest.repaint();

                } catch (IOException | ParseException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

        });

        doNameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                siNameComboBox.removeAllItems();
                int doNameIndex = doNameComboBox.getSelectedIndex();
                for (int i = 0; i < siNameList[doNameIndex].size(); i++) {
                    siNameComboBox.addItem(siNameList[doNameIndex].get(i));
                }
                selectedDoCode = doCodeList[doNameIndex];
            }
        });

        siNameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                textField.setText(null);
                textField.setFont(new Font("??????", Font.BOLD, 20));
                textField.setForeground(Color.black);
            }

            @Override
            public void focusLost(FocusEvent e) {
                // TODO Auto-generated method stub

            }

        });

    }

}
