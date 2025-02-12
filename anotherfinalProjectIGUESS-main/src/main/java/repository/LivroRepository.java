package repository;

import model.LivroModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.*;
import java.util.List;

public class LivroRepository {
    private static LivroRepository instance;
    protected EntityManager entityManager;

    public LivroRepository() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static LivroRepository getInstance() {
        if (instance == null) {
            instance = new LivroRepository();
        }
        return instance;
    }

    public String salvar(LivroModel livro){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(livro);
            entityManager.getTransaction().commit();
            return "Salvo com Sucesso";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return "Erro ao tentar salvar..." + e.getMessage();
            }
        }

    public LivroModel buscarPorId(Long idLivro){
        return entityManager.find(LivroModel.class, idLivro);
        }

    public String atualizar(LivroModel livro) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(livro);
            entityManager.getTransaction().commit();
            return "Atualizado com sucesso! YUhuuuuu";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return "Erro ao atualizar: " + e.getMessage();
        }

        }
    public List<LivroModel> listar() {
        return entityManager.createQuery("FROM LivroModel", LivroModel.class).getResultList();
    }

    public List<LivroModel> buscarTodos() {
        return entityManager.createQuery("from LivroModel", LivroModel.class).getResultList();
    }
    }


