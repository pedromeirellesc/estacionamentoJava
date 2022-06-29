package projetoestacionamento;
        
import java.sql.ResultSet;
import java.util.ArrayList;

public class Veiculo {
    private int id;
    private String placa;
    private String modelo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Veiculo() {
    }

    public Veiculo(String placa, String modelo) {
        this.placa = placa;
        this.modelo = modelo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void cadastrar(){
        String sql =  "insert into veiculo (placa, modelo) values ( " + " '" + this.getPlaca()+   "' ,  " + "  '" + this.getModelo()+ "'  ) ";
        Conexao.executar( sql );
    }
       
    public static ArrayList<Veiculo> getVeiculos(){
        ArrayList<Veiculo> lista = new ArrayList<>();
        
        String sql = "select idVeiculo, placa, modelo from veiculo order by idVeiculo ";
        ResultSet rs = Conexao.consultar( sql );
        if( rs != null){
            
            try{
                while ( rs.next() ) {                
                    String placa = rs.getString( 2 );
                    String modelo = rs.getString( 3 );
                    Veiculo veiculo = new Veiculo(placa, modelo);
                    veiculo.setId( rs.getInt( "idVeiculo" ) );
                    lista.add( veiculo );
                }
            }catch(Exception e){
                
            }
            
        }
        return lista;
}
    public static void excluir(int idVeiculo){
        String sql =  "DELETE FROM veiculo WHERE idVeiculo = " + idVeiculo;
        Conexao.executar( sql );
    }
}
