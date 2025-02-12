package view;

import model.UsuarioModel;
import repository.UsuarioRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaBuscaUsuario extends JFrame {
    private JPanel painelPrincipal;
    private JTextField textFieldBuscar;
    private JTable tableBuscaUsuario;
    private JButton buscarButton;
    private JScrollPane scrollPaneUsuario;
    private JButton voltarButton;
    private JButton removerButton;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
    private EntityManager entityManager = emf.createEntityManager();
    private UsuarioRepository usuarioRepository = new UsuarioRepository();

    public Styles styles = new Styles();
    public TelaBuscaUsuario() {
        this.setTitle("Tela de Busca de Usuário");
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(painelPrincipal);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        styles.StyleButton(voltarButton);
        styles.StyleButton(buscarButton);
        styles.StyleButton(removerButton);
        styles.StyleTextField(textFieldBuscar);

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new TelaCadastroUsuario().setVisible(true);
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarTabela();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerUsuarioSelecionado();
            }
        });
    }

    private void removerUsuarioSelecionado() {
        int selectedRow = tableBuscaUsuario.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para remover!", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }


        long idUsuario = (long) tableBuscaUsuario.getValueAt(selectedRow, 0);

        try {
            entityManager.getTransaction().begin();
            UsuarioModel usuario = entityManager.find(UsuarioModel.class, idUsuario);

            if (usuario != null) {
                entityManager.remove(usuario);
                entityManager.getTransaction().commit();
                JOptionPane.showMessageDialog(this, "Usuário removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarTabela();  // Atualiza a tabela após a remoção
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this, "Erro ao remover o usuário!", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    private void atualizarTabela() {
        List<UsuarioModel> usuarios = buscar();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nome", "Email"}, 0);

        for (UsuarioModel usuario : usuarios) {
            model.addRow(new Object[]{usuario.getId(), usuario.getNome(), usuario.getEmail()});
        }

        tableBuscaUsuario.setModel(model);
    }

    public List<UsuarioModel> buscar() {
        String idText = textFieldBuscar.getText();
        if (idText == null || idText.isEmpty()) {
            return entityManager.createQuery("from UsuarioModel", UsuarioModel.class).getResultList();
        } else {
            try {
                long id = Long.parseLong(idText);
                return entityManager.createQuery("from UsuarioModel u where u.idUsuario = :id", UsuarioModel.class)
                        .setParameter("id", id)
                        .getResultList();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Digite um ID válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return List.of();
            }
        }
    }
}
