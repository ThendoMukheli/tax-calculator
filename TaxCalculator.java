import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaxCalculator extends JFrame {
    private JTextField incomeField;
    private JRadioButton oldRegimeRadio, newRegimeRadio;
    private JTextArea resultArea;

    public TaxCalculator() {
        setTitle("Income Tax Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Annual Income:"));
        incomeField = new JTextField();
        inputPanel.add(incomeField);
        
        // Radio Buttons Panel
        JPanel radioPanel = new JPanel(new GridLayout(1, 2));
        oldRegimeRadio = new JRadioButton("Old Regime", true);
        newRegimeRadio = new JRadioButton("New Regime");
        ButtonGroup regimeGroup = new ButtonGroup();
        regimeGroup.add(oldRegimeRadio);
        regimeGroup.add(newRegimeRadio);
        radioPanel.add(oldRegimeRadio);
        radioPanel.add(newRegimeRadio);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton calculateButton = new JButton("Calculate Tax");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);

        // Result Area
        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(radioPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(new JScrollPane(resultArea), BorderLayout.EAST);

        // Event Listeners
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTax();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incomeField.setText("");
                resultArea.setText("");
                oldRegimeRadio.setSelected(true);
            }
        });
    }

    private void calculateTax() {
        try {
            double income = Double.parseDouble(incomeField.getText());
            double tax = 0;
            
            if (oldRegimeRadio.isSelected()) {
                tax = calculateOldRegimeTax(income);
            } else {
                tax = calculateNewRegimeTax(income);
            }

            resultArea.setText(String.format("Taxable Income: ₹%.2f\n", income) +
                    String.format("Tax Regime: %s\n", 
                            (oldRegimeRadio.isSelected() ? "Old" : "New")) +
                    String.format("Tax Payable: ₹%.2f", tax));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid income amount!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double calculateOldRegimeTax(double income) {
        // Sample tax slabs for old regime
        double tax = 0;
        if (income > 1000000) {
            tax += (income - 1000000) * 0.3;
            income = 1000000;
        }
        if (income > 500000) {
            tax += (income - 500000) * 0.2;
            income = 500000;
        }
        if (income > 250000) {
            tax += (income - 250000) * 0.05;
        }
        return tax;
    }

    private double calculateNewRegimeTax(double income) {
        // Sample tax slabs for new regime
        double tax = 0;
        if (income > 1500000) {
            tax += (income - 1500000) * 0.3;
            income = 1500000;
        }
        if (income > 1200000) {
            tax += (income - 1200000) * 0.2;
            income = 1200000;
        }
        if (income > 900000) {
            tax += (income - 900000) * 0.15;
            income = 900000;
        }
        if (income > 600000) {
            tax += (income - 600000) * 0.1;
            income = 600000;
        }
        if (income > 300000) {
            tax += (income - 300000) * 0.05;
        }
        return tax;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaxCalculator().setVisible(true);
            }
        });
    }
}