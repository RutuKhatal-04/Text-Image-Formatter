import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class EmojiPickerApp extends JFrame {
    private JTextArea textInput;
    private JLabel imageLabel;

    public EmojiPickerApp() {
        setTitle("Text and Image Formatter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // JTextArea on the left
        textInput = new JTextArea(15, 30);
        textInput.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(textInput), BorderLayout.WEST);

        // JPanel to hold the imageLabel with specified size
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(300, 300)); // Set preferred size for the image panel
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        add(imagePanel, BorderLayout.EAST);

        // JPanel for buttons arranged vertically
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        add(buttonPanel, BorderLayout.SOUTH);

        // Create buttons and add them to buttonPanel
        createButton(buttonPanel, "Make for WhatsApp", "*");
        createButton(buttonPanel, "Make for Telegram", "**");
        createButton(buttonPanel, "Upload Image", null);
        createButton(buttonPanel, "Pick Emoji", null);

        pack();
        setLocationRelativeTo(null);
    }

    // Method to create and add buttons to buttonPanel
    private void createButton(JPanel panel, String buttonText, String formatting) {
        JButton button = new JButton(buttonText);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (buttonText.equals("Pick Emoji")) {
                    showEmojiPicker(textInput);
                } else if (formatting != null) {
                    formatText(formatting);
                } else {
                    uploadImage();
                }
            }
        });
        panel.add(button);
        button.setBackground(new Color(173, 216, 230)); // Light blue color for buttons
    }

    private void formatText(String formatting) {
        String text = textInput.getText();
        int start = textInput.getSelectionStart();
        int end = textInput.getSelectionEnd();

        if (start != end) {
            String selectedText = text.substring(start, end);
            String modifiedText = text.substring(0, start) + formatting + selectedText + formatting + text.substring(end);
            textInput.setText(modifiedText);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a part of the text to format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                ImageIcon icon = new ImageIcon(image.getScaledInstance(250, 250, Image.SCALE_SMOOTH)); // Adjusted size
                imageLabel.setIcon(icon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static final String[] WHATSAPP_EMOJIS = {
            "\u263A", // ☺ Smiling Face
            "\uD83D\uDE02", // 😂 Face with Tears of Joy
            "\uD83D\uDE0A", // 😊 Smiling Face with Smiling Eyes
            "\uD83D\uDE0D", // 😍 Smiling Face with Heart-Eyes
            "\uD83D\uDE14", // 😔 Pensive Face
            "\uD83D\uDE01", // 😁 Beaming Face with Smiling Eyes
            "\uD83D\uDE0C", // 😌 Relieved Face
            "\uD83D\uDE2D", // 😭 Loudly Crying Face
            "\uD83D\uDE22", // 😢 Crying Face
            "\uD83D\uDE09", // 😉 Winking Face
            "\uD83D\uDE21", // 😡 Pouting Face
            "\uD83D\uDE33" // 😳 Flushed Face
            // Add more emojis as needed...
    };

    private void showEmojiPicker(JTextArea textArea) {
        JFrame emojiFrame = new JFrame("Emoji Picker");
        JPanel emojiPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        emojiPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (String emoji : WHATSAPP_EMOJIS) {
            JButton emojiButton = new JButton(emoji);
            emojiButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20)); // Use Segoe UI Emoji font
            emojiButton.addActionListener((ActionEvent e) -> {
                textArea.append(emoji);
                emojiFrame.dispose(); // Close the emoji picker window after selection
            });
            emojiPanel.add(emojiButton);
        }

        emojiFrame.add(new JScrollPane(emojiPanel));
        emojiFrame.pack();
        emojiFrame.setLocationRelativeTo(null);
        emojiFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EmojiPickerApp().setVisible(true);
            }
        });
    }
}
