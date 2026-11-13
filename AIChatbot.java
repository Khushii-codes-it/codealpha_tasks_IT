import javax.swing.*;
import java.awt.*;

public class AIChatbot extends JFrame {

    JTextArea chatArea;
    JTextField inputField;
    JButton sendButton;

    public AIChatbot() {

        setTitle("Artificial Intelligence Chatbot");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Chat display area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 15));

        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Input field
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 15));

        // Send button
        sendButton = new JButton("Send");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        chatArea.append("AI Chatbot: Hello! Welcome!\n");
        chatArea.append("AI Chatbot: How can I help you?\n\n");

        // Send button action
        sendButton.addActionListener(e -> sendMessage());

        // Press Enter to send
        inputField.addActionListener(e -> sendMessage());

        setVisible(true);
    }

    // Process user message
    private void sendMessage() {

        String userMessage = inputField.getText().trim();

        if (userMessage.isEmpty()) {
            return;
        }

        chatArea.append("You: " + userMessage + "\n");

        String response = getResponse(userMessage);

        chatArea.append("AI Chatbot: " + response + "\n\n");

        inputField.setText("");
    }

    // Basic NLP and rule-based response system
    private String getResponse(String message) {

        // Convert text to lowercase and remove punctuation
        String input = message.toLowerCase()
                .replaceAll("[^\\p{L}\\p{N}\\s]", " ")
                .trim();

        // Greeting
        if (input.matches(".*\\b(hello|hi|hey)\\b.*")) {
            return "Hello! Nice to meet you.";
        }

        // Name
        else if (input.contains("your name")) {
            return "I am a Java-based AI Chatbot.";
        }

        // How are you
        else if (input.contains("how are you")) {
            return "I am working perfectly! How can I help you?";
        }

        // Java
        else if (input.contains("java")) {
            return "Java is an object-oriented programming language.";
        }

        // Artificial Intelligence
        else if (input.contains("artificial intelligence")
                || input.matches(".*\\bai\\b.*")) {
            return "Artificial Intelligence enables machines to perform intelligent tasks.";
        }

        // Programming
        else if (input.contains("programming")) {
            return "Programming means writing instructions for computers.";
        }

        // College
        else if (input.contains("college")) {
            return "College is a place for learning and developing skills.";
        }

        // Help
        else if (input.contains("help")) {
            return "You can ask me about Java, AI, programming or college.";
        }

        // Thank you
        else if (input.contains("thank")) {
            return "You are welcome!";
        }

        // Goodbye
        else if (input.contains("bye")
                || input.contains("goodbye")) {
            return "Goodbye! Have a nice day.";
        }

        // Default response
        else {
            return "Sorry, I don't understand. Please ask another question.";
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new AIChatbot();
        });
    }
}