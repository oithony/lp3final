package controller;

import model.LivroModel;
import repository.LivroRepository;

public class LivroController {
    LivroRepository livroRepository = new LivroRepository();

    public String salvar(LivroModel livro){
        String retornoDaRepository = livroRepository.salvar(livro);
        return retornoDaRepository;
    }
}
