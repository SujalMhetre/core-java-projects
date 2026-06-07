package com.bank.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {

    JLabel label1, label2, label3;

    JTextField textField1;

    JPasswordField passwordField;

    JButton signInButton, clearButton, signUpButton;

    Login() {
        super("Bank Management System");

        // Bank Image
        ImageIcon a1 = new ImageIcon(ClassLoader.getSystemResource("com/bank/util/icons/bank.png"));
        Image a2 = a1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon a3 = new ImageIcon(a2);
        JLabel imageA = new JLabel(a3);
        imageA.setBounds(370, 10, 100, 100);
        add(imageA);

        //  Card Image
        ImageIcon b1 = new ImageIcon(ClassLoader.getSystemResource("com/bank/util/icons/card.png"));
        Image b2 = b1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(b2);
        JLabel imageB = new JLabel(i3);
        imageB.setBounds(670, 350, 100, 100);
        add(imageB);

        // Welcome, Label
        label1 = new JLabel("WELCOME TO ATM");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Arial", Font.BOLD, 38));
        label1.setBounds(230, 125, 450, 40);
        add(label1);

        // Card Label
        label2 = new JLabel("Card No");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Arial", Font.BOLD, 28));
        label2.setBounds(150, 200, 120, 30);
        add(label2);

        // Text field Label
        textField1 = new JTextField(15);
        textField1.setFont(new Font("Arial", Font.PLAIN, 14));
        textField1.setBounds(325, 200, 230, 30);
        add(textField1);

        // PIN Label
        label3 = new JLabel("PIN");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("Arial", Font.BOLD, 28));
        label3.setBounds(150, 260, 120, 30);
        add(label3);

        // Password textField
        passwordField = new JPasswordField(15);
        passwordField.setBounds(325, 260, 230, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(passwordField);


        // Background Image
        ImageIcon c1 = new ImageIcon(ClassLoader.getSystemResource("com/bank/util/icons/backbg.png"));
        Image c2 = c1.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon c3 = new ImageIcon(c2);
        JLabel imageC = new JLabel(c3);
        imageC.setBounds(0, 0, 850, 480);
        add(imageC);

        // SignIn Button
        signInButton = new JButton("SIGN IN");
        signInButton.setForeground(Color.WHITE);
        signInButton.setBackground(Color.BLACK);
        signInButton.setBounds(300, 320, 100, 35);
        signInButton.setFont(new Font("Arial", Font.BOLD, 14));
        signInButton.addActionListener(this);
        add(signInButton);

        // Clear Button
        clearButton = new JButton("CLEAR");
        clearButton.setForeground(Color.WHITE);
        clearButton.setBackground(Color.BLACK);
        clearButton.setBounds(430, 320, 100, 35);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.addActionListener(this);
        add(clearButton);

        // SignUp Button
        signUpButton = new JButton("SIGN UP");
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setBackground(Color.BLACK);
        signUpButton.setBounds(300, 370, 230, 35);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.addActionListener(this);
        add(signUpButton);


        setLayout(null);
        setSize(850, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == signInButton) {

            } else if (e.getSource() == clearButton) {
                textField1.setText("");
                passwordField.setText("");

            } else if (e.getSource() == signUpButton) {

            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Login();
    }

}
