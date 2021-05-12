package gui

import java.awt.Dimension
import javax.swing.*

class ClientFrame : JFrame() {
    private var connect = false
    private val connectButton = JButton("Подключиться").apply {
        this.isEnabled = !connect
    }
    private val username = JTextField().apply {
        this.isEnabled = !connect
    }
    private val chatText = JTextArea().apply {
        this.isEditable = false
    }
    private val scroll = JScrollPane(chatText)
    private val text = JTextField().apply {
        this.isEnabled = connect
    }
    private val sendButton = JButton("Отправить").apply {
        this.isEnabled = connect
    }

    init {
        isVisible = true
        title = "Chat"
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(500, 400)
        val connectPanel = JPanel().apply {
            layout = GroupLayout(this).apply {
                setVerticalGroup(createSequentialGroup()
                    .addGap(5)
                    .addGroup(createParallelGroup()
                        .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(connectButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    )
                    .addGap(5)
                )
                setHorizontalGroup(createSequentialGroup()
                    .addGap(5)
                    .addComponent(username, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(10)
                    .addComponent(connectButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(5)
                )
            }
        }
        val sendPanel = JPanel().apply {
            layout = GroupLayout(this).apply {
                setVerticalGroup(createSequentialGroup()
                    .addGap(5)
                    .addGroup(createParallelGroup()
                        .addComponent(text, 50, 50, 50)
                        .addGap(10)
                        .addComponent(sendButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    )
                    .addGap(5)
                )
                setHorizontalGroup(createSequentialGroup()
                    .addGap(5)
                    .addComponent(text, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(10)
                    .addComponent(sendButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(5)
                )
            }
        }
        layout = GroupLayout(contentPane).apply {
            setVerticalGroup(createSequentialGroup()
                .addGap(5)
                .addComponent(connectPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(5)
                .addComponent(scroll, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(5)
                .addComponent(sendPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(5)
            )
            setHorizontalGroup(createSequentialGroup()
                .addGap(5)
                .addGroup(createParallelGroup()
                    .addComponent(connectPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(5)
                    .addComponent(scroll, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(5)
                    .addComponent(sendPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                )
                .addGap(5)
            )
        }
        pack()
    }
}