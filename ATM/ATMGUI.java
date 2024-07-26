import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }
}

class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public boolean withdraw(double amount) {
        return account.withdraw(amount);
    }

    public boolean deposit(double amount) {
        return account.deposit(amount);
    }

    public double checkBalance() {
        return account.getBalance();
    }
}

public class ATMGUI {
    private ATM atm;
    private JFrame frame;
    private JLabel balanceLabel;
    private JTextField amountField;

    public ATMGUI(ATM atm) {
        this.atm = atm;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("ATM Machine");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel atmLabel = new JLabel("WELCOME TO ATM");
        atmLabel.setFont(new Font("Calibri", Font.BOLD, 35));
        atmLabel.setForeground(Color.RED);
        GridBagConstraints topGbc = new GridBagConstraints();
        topGbc.gridx = 0;
        topGbc.gridy = 0;
        topPanel.add(atmLabel, topGbc);

        frame.add(topPanel, gbc);

        gbc.gridy++;
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints centerGbc = new GridBagConstraints();
        centerGbc.insets = new Insets(10, 10, 10, 10);
        centerGbc.anchor = GridBagConstraints.CENTER;

        JLabel amountLabel = new JLabel("Enter Amount : ");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        centerGbc.gridx = 0;
        centerGbc.gridy = 0;
        centerPanel.add(amountLabel, centerGbc);

        amountField = new JTextField(20);
        amountField.setFont(new Font("Arial", Font.PLAIN, 18));
        centerGbc.gridx = 1;
        centerGbc.gridy = 0;
        centerPanel.add(amountField, centerGbc);

        frame.add(centerPanel, gbc);

        gbc.gridy++;
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bottomGbc = new GridBagConstraints();
        bottomGbc.insets = new Insets(10, 10, 10, 10);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(new Font("Arial", Font.BOLD, 18));
        withdrawButton.setPreferredSize(new Dimension(200, 50));
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });

        bottomGbc.gridx = 0;
        bottomGbc.gridy = 0;
        bottomPanel.add(withdrawButton, bottomGbc);

        JButton depositButton = new JButton("Deposit");
        depositButton.setFont(new Font("Arial", Font.BOLD, 18));
        depositButton.setPreferredSize(new Dimension(200, 50));
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });
        bottomGbc.gridx = 1;
        bottomGbc.gridy = 0;
        bottomPanel.add(depositButton, bottomGbc);

        JButton balanceButton = new JButton("Check Balance");
        balanceButton.setFont(new Font("Arial", Font.BOLD, 18));
        balanceButton.setPreferredSize(new Dimension(200, 50));
        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
        bottomGbc.gridx = 2;
        bottomGbc.gridy = 0;
        bottomPanel.add(balanceButton, bottomGbc);

        frame.add(bottomPanel, gbc);

        frame.setVisible(true);
    }

    private void withdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (atm.withdraw(amount)) {
                JOptionPane.showMessageDialog(frame, "Withdrawal successful!");
            } else {
                JOptionPane.showMessageDialog(frame, "Insufficient funds or invalid amount!");
            }
            updateBalance();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid amount!");
        }
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (atm.deposit(amount)) {
                JOptionPane.showMessageDialog(frame, "Deposit successful!");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid amount!");
            }
            updateBalance();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid amount!");
        }
    }

    private void checkBalance() {
        JOptionPane.showMessageDialog(frame, "Your current balance is Rs. " + atm.checkBalance());
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: Rs. " + atm.checkBalance());
        amountField.setText("");
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);
        ATM atm = new ATM(account);
        new ATMGUI(atm);
    }
}
