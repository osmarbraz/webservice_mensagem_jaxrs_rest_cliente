package com.entidade;

/**
 *
 * @author osmar
 */
public class Mensagem {

    private String mensagem;

    public Mensagem(){
        this("");
    }
    
    public Mensagem(String mensagem) {
        setMensagem(mensagem);
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
