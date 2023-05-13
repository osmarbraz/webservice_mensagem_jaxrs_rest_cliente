
import com.entidade.Mensagem;
import com.fasterxml.jackson.core.JsonProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.JOptionPane;

/**
 *
 * @author osmar
 */
public class Principal {

    public static void main(String args[]) throws JsonProcessingException {

        //Url do serviço
        String url = "http://localhost:8080/webservice_mensagem_jaxrs_rest/rest/mensagem";

        //Criar instância do cliente JAX-RS
        Client clienteJAXRS = ClientBuilder.newClient();

        //Criar o parser do JSON para o objeto Mensagem
        ObjectMapper objectMapper = new ObjectMapper();

        int opcao = -1;
        while (opcao != 9) {
            //Menu de opções
            opcao = Integer.parseInt(JOptionPane.showInputDialog("Menu Mensagem: \n1 - getMensagem \n2 - setMensagem \n9 - Sair"));

            //Opção getMensagem
            if (opcao == 1) {
                //Requisição GET do serviço        
                Response resposta = clienteJAXRS.target(url).request(MediaType.APPLICATION_JSON).get();

                // Verificar o código de resposta
                if (resposta.getStatus() == 200) {
                    //Resposta bruta do servidor
                    String jsonResposta = resposta.readEntity(String.class);

                    System.out.println("Texto resposta do servidor: " + jsonResposta);

                    //Preenche os objeto mensagem com os dados do JSON
                    Mensagem mensagem = objectMapper.readValue(jsonResposta, Mensagem.class);
                    //Mostra os dados do objeto
                    System.out.println("Dados do objeto mensagem:" + mensagem.getMensagem());

                } else {
                    System.out.println("Falha ao obter a mensagem. Código de resposta: " + resposta.getStatus());
                }

            } else {
                //Opção setMensagem
                if (opcao == 2) {
                    //Requisição POST do serviço        
                    String novaMensagem = JOptionPane.showInputDialog("Digite uma nova mensagem");

                    //Instancia o objeto mensagem 
                    Mensagem mensagem = new Mensagem(novaMensagem);

                    // Converter o objeto Mensagem para uma string JSON
                    String jsonInputString = objectMapper.writeValueAsString(mensagem);
                    //System.out.println("jsonstring:" + jsonInputString);

                    // Enviar a requisição POST
                    Response resposta = clienteJAXRS.target(url).request(MediaType.APPLICATION_JSON).post(Entity.json(jsonInputString));

                    // Verificar o código de resposta
                    if (resposta.getStatus() == 200) {
                        System.out.println("Mensagem criada com sucesso!");
                        System.out.println("Resposta do servidor: " + resposta.readEntity(String.class));
                    } else {
                        System.out.println("Falha ao criar a mensagem. Código de resposta: " + resposta.getStatus());
                    }
                }
            }
        }
        //Fechar o cliente
        clienteJAXRS.close();
    }
}
