package repository;

import model.LivroModel;
import model.UsuarioModel;

import javax.persistence.*;
import java.util.List;

public class UsuarioRepository
{
    private static UsuarioRepository instance;
    protected EntityManager entityManager;

    public UsuarioRepository() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static UsuarioRepository getInstance() {
        if (instance == null) {
            instance = new UsuarioRepository();
        }
        return instance;
    }

    public String salvar(UsuarioModel usuario){
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
            return "Usuario Salvo! :D";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return "Erro ao tentar salvar o usuario: " + e.getMessage();
        }
    }

    public UsuarioModel buscarPorId(Long idUsuario) {
        UsuarioModel usuario = new UsuarioModel();
        try{
            usuario = entityManager.find(UsuarioModel.class,idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public String atualizar(UsuarioModel usuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();
            return "Usuário atualizado com sucesso!";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return "Erro ao atualizar o usuário: " + e.getMessage();
        }
    }

    public String remover(UsuarioModel usuario) {
        try {
            entityManager.getTransaction().begin();
            UsuarioModel usuarioGerenciado = entityManager.contains(usuario) ? usuario : entityManager.merge(usuario);
            entityManager.remove(usuarioGerenciado);
            entityManager.getTransaction().commit();
            return "Usuário removido com sucesso!";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return "Erro ao remover o usuário: " + e.getMessage();
        }
    }

    public List<UsuarioModel> buscarTodos() {
        return entityManager.createQuery("from UsuarioModel", UsuarioModel.class).getResultList();
    }

}


