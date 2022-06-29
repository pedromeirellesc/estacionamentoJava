package projetoestacionamento;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Cliente{
    private int id;
    private String nome;
    private String telefone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Cliente(){
    }
    
    public void cadastrar(){
        String sql =  "insert into cliente (nome, telefone) values ( "+ " '" + this.getNome() +   "' ,  " + "  " + this.getTelefone()+"  ) ";
        Conexao.executar( sql );
    }
    
    public static ArrayList<Cliente> getClientes(){
        ArrayList<Cliente> lista = new ArrayList<>();
        
        String sql = "select idCliente, nome, telefone from cliente order by idCliente ";
        ResultSet rs = Conexao.consultar( sql );
        if( rs != null){
            
            try{
                while ( rs.next() ) {                
                    String nome = rs.getString( 2 );
                    String telefone = rs.getString( "telefone" );
                    Cliente cliente = new Cliente(nome, telefone);
                    cliente.setId( rs.getInt( "idCliente" ) );
                    lista.add( cliente );
                }
            }catch(Exception e){
                
            }
            
        }
        return lista;
}
    public static void excluir(int idCliente){
        String sql =  "DELETE FROM cliente WHERE idCliente = " + idCliente;
        Conexao.executar( sql );
    }
}