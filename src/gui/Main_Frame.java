package gui;

import client.Client;
import common.Constant;
import event.EventChat;
import event.EventMain;
import event.EventWaiting;
import event.PublicEvent;
import java.io.IOException;
import static java.time.zone.ZoneRulesProvider.refresh;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author letoan
 */
public class Main_Frame extends javax.swing.JFrame {

    String receiver;

    public Main_Frame() {
        initComponents();
        initEventWaiting();
        initEventMain();
        initEventChat();
        panel.setLayout(new MigLayout("fillx"));
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(new Main_Frame(),
                        "Bạn có muốn thoát cuộc trò chuyện?", "Thoát",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    try {
                        if(!Client.getInstance().serverIsWorking()){
                        System.exit(0);
                        }
                        Client.getInstance().sendServer("exit"+ Constant.spread+receiver);  // báo cho friend biết là bản thân đã logout                      
                        System.exit(0);                        
                        Client.getInstance().closeSocket();
                    } catch (IOException ex) {
                        Logger.getLogger(Main_Frame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    private void initEventWaiting() {
        PublicEvent.getInstance().addEventWaiting(new EventWaiting() {
            @Override
            public void setNickname(String name) {
                txtMyNickname.setText(name);
                setTitle("Bạn là " + name);
            }

            @Override
            public void waitingPair(String user) {

                Object[] options = {"Có", "Không"};
                int result = JOptionPane.showOptionDialog(null, "Trò chuyện cùng " + user + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                try {
                    if (result == JOptionPane.YES_OPTION) {                        
                        Client.getInstance().sendServer("confirm" + Constant.spread + "true" + Constant.spread + user);
                    } else {
                        Client.getInstance().sendServer("confirm" + Constant.spread + "false" + Constant.spread + user);                        
                    }
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(Main_Frame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            @Override
            public void cancelConverse(String user) {
                txtConfirm.setText("Tiếp tục ghép cặp");
                txtConfirm.setVisible(true);
            }

            @Override
            public void exitConverse() {
                txtConfirm.setText("Người chat đã thoát bạn sẽ quay lại phòng chờ để đợi người chat khác");                
                txtConfirm.setVisible(true);
                panel.removeAll();
            }

        });
    }

    private void initEventMain() {
        PublicEvent.getInstance().addEventMain(new EventMain() {           
            @Override
            public void chat(String user) {
                waitingPane.setVisible(false);
                chatPane.setVisible(true);                
                receiver = user;
                txtFriend.setText(receiver);
            }

            @Override
            public void conserveWaiting() {
                waitingPane.setVisible(true);
                chatPane.setVisible(false);
            }

        });
    }

    private void initEventChat() {
        PublicEvent.getInstance().addEventChat(new EventChat() {
            @Override
            public void sendMessage(String text) {
                ItemRight item = new ItemRight(text.replace(Constant.spread, ""));
                panel.add(item, "wrap, w 80%, al right");
                panel.repaint();
                panel.revalidate();
            }

            @Override
            public void reciveMessage(String user, String text) {
                if (user.equals(receiver)) {
                    ItemLeft item = new ItemLeft(text);
                    panel.add(item, "wrap, w 80%");
                    panel.repaint();
                    panel.revalidate();
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        waitingPane = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtMyNickname = new javax.swing.JLabel();
        pgbLoading = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtConfirm = new javax.swing.JLabel();
        chatPane = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        btnSend = new rojerusan.RSButtonIconI();
        jLabel2 = new javax.swing.JLabel();
        txtFriend = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 600));
        setPreferredSize(new java.awt.Dimension(900, 600));

        jLayeredPane1.setMaximumSize(new java.awt.Dimension(900, 600));
        jLayeredPane1.setMinimumSize(new java.awt.Dimension(900, 600));

        waitingPane.setMaximumSize(new java.awt.Dimension(900, 600));
        waitingPane.setMinimumSize(new java.awt.Dimension(900, 600));
        waitingPane.setPreferredSize(new java.awt.Dimension(900, 600));

        txtMyNickname.setFont(new java.awt.Font("Paytone One", 3, 24)); // NOI18N
        txtMyNickname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtMyNickname.setText("Toàn");
        txtMyNickname.setToolTipText("");

        pgbLoading.setIndeterminate(true);

        jLabel3.setFont(new java.awt.Font("Paytone One", 3, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Vui lòng đợi tí nhé");
        jLabel3.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pgbLoading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMyNickname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMyNickname)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pgbLoading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Paytone One", 3, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Đang ghép cặp");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtConfirm.setFont(new java.awt.Font("Paytone One", 3, 14)); // NOI18N
        txtConfirm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout waitingPaneLayout = new javax.swing.GroupLayout(waitingPane);
        waitingPane.setLayout(waitingPaneLayout);
        waitingPaneLayout.setHorizontalGroup(
            waitingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(waitingPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(waitingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(waitingPaneLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(txtConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        waitingPaneLayout.setVerticalGroup(
            waitingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(waitingPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(182, 182, 182)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(txtConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
        );

        chatPane.setBackground(new java.awt.Color(255, 255, 255));
        chatPane.setMaximumSize(new java.awt.Dimension(900, 600));
        chatPane.setPreferredSize(new java.awt.Dimension(900, 600));
        chatPane.setSize(new java.awt.Dimension(900, 600));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panel);

        btnSend.setBackground(new java.awt.Color(100, 103, 178));
        btnSend.setForeground(new java.awt.Color(100, 103, 178));
        btnSend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-email-send-96.png"))); // NOI18N
        btnSend.setText("Gửi");
        btnSend.setColorHover(new java.awt.Color(100, 103, 178));
        btnSend.setColorTextHover(new java.awt.Color(100, 103, 178));
        btnSend.setFont(new java.awt.Font("Paytone One", 1, 18)); // NOI18N
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Paytone One", 1, 18)); // NOI18N
        jLabel2.setText("Trò chuyện với:");

        txtFriend.setFont(new java.awt.Font("Paytone One", 1, 18)); // NOI18N

        txt.setColumns(20);
        txt.setFont(new java.awt.Font("Paytone One", 0, 13)); // NOI18N
        txt.setLineWrap(true);
        txt.setRows(5);
        txt.setBorder(null);
        jScrollPane2.setViewportView(txt);

        javax.swing.GroupLayout chatPaneLayout = new javax.swing.GroupLayout(chatPane);
        chatPane.setLayout(chatPaneLayout);
        chatPaneLayout.setHorizontalGroup(
            chatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chatPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(chatPaneLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(chatPaneLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        chatPaneLayout.setVerticalGroup(
            chatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chatPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(txtFriend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chatPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jLayeredPane1.setLayer(waitingPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(chatPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addComponent(waitingPane, javax.swing.GroupLayout.DEFAULT_SIZE, 909, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(chatPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 15, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(waitingPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addComponent(chatPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 15, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        String text = txt.getText().trim();
        if (!text.equals("")) {
            PublicEvent.getInstance().getEventChat().sendMessage(text);
            try {
                Client.getInstance().sendServer("sendmessage" + Constant.spread + Client.getInstance().getNickname() + Constant.spread + receiver + Constant.spread + text.replace(Constant.spread, ""));
            } catch (IOException ex) {
                Logger.getLogger(Main_Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
            txt.setText("");
            txt.grabFocus();
            refresh();
        } else {
            txt.grabFocus();
        }
    }//GEN-LAST:event_btnSendActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSButtonIconI btnSend;
    private javax.swing.JPanel chatPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panel;
    private javax.swing.JProgressBar pgbLoading;
    private javax.swing.JTextArea txt;
    private javax.swing.JLabel txtConfirm;
    private javax.swing.JLabel txtFriend;
    private javax.swing.JLabel txtMyNickname;
    private javax.swing.JPanel waitingPane;
    // End of variables declaration//GEN-END:variables
}
