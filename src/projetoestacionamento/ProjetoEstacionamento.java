package projetoestacionamento;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjetoEstacionamento {

    public static void main(String[] args) throws SQLException {
        int menu2 = 0;
        int somaEst = 0, rest = 0;
        boolean verif = false;
        ArrayList<Estadia> listaAbertas = new ArrayList(); //Listar somente estadias abertas
        ArrayList<Cliente> listaClientes = new ArrayList(); //Listtar Clientes
        ArrayList<Veiculo> listaVeiculos = new ArrayList(); //Listar Veículos
        ArrayList<Estadia> listaEstadias = new ArrayList(); // Listar todas Estadias (seja ela aberta ou não)
        Dono dono = new Dono("dono", "dono123");
        Funcionario func1 = new Funcionario("func1", "func123");
        Cliente cliente;
        Veiculo veiculo;
        Estadia estadia;
        String nome, telefone, placa, modelo;
        String nomeUser;
        String senha;
        int menuRelat;
        int perm = 0, log = 0;
        int idCliente, idVeiculo, idEstadia;
        Scanner ler = new Scanner(System.in);
        saida:
        do {
            System.out.println("Digite seu login: ");
            nomeUser = ler.nextLine();
            System.out.println("Digite sua senha: ");
            senha = ler.nextLine();
            if (nomeUser.equals(dono.getUser()) && senha.equals(dono.getSenha())) {
                System.out.println("Bem-Vindo " + dono.getUser());
                do {
                    System.out.println("Olá! Escolha uma das opções abaixo: ");
                    System.out.println("1 - Cadastrar Cliente");
                    System.out.println("2 - Cadastrar Veículo");
                    System.out.println("3 - Cadastrar Estadia");
                    System.out.println("4 - Excluir Cliente");
                    System.out.println("5 - Excluir Veículo");
                    System.out.println("6 - Excluir Estadia");
                    System.out.println("7 - Alterar Estadia");
                    System.out.println("8 - Listar Estadias");
                    System.out.println("9 - Listar Clientes");
                    System.out.println("10 - Listar Veículos");
                    System.out.println("11 - Finalizar Estadia");
                    System.out.println("12 - Obter Relatório");
                    System.out.println("13 - Sair");
                    menu2 = ler.nextInt();
                    switch (menu2) {
                        case 1:
                            ler.nextLine();
                            System.out.println("Você escolheu a opção 1 (Cadastrar Cliente)"); //Cadastrar Cliente
                            System.out.println("Digite o nome do cliente: ");
                            nome = ler.nextLine();
                            System.out.println("Digite o telefone do cliente: ");
                            telefone = ler.nextLine();
                            cliente = new Cliente(nome, telefone); //Construtor Cliente
                            cliente.cadastrar();
                            System.out.println("Cliente cadastrado com sucesso! ");
                            break;
                        case 2:
                            ler.nextLine();
                            System.out.println("Você escolheu a opção 2 (Cadastrar Veículo) "); //Cadastrar Veículo
                            System.out.println("Digite a placa do veículo: ");
                            placa = ler.nextLine();
                            System.out.println("Digite o modelo do veículo: ");
                            modelo = ler.nextLine();
                            veiculo = new Veiculo(placa, modelo); //Construtor Veículo
                            veiculo.cadastrar();
                            System.out.println("Veículo cadastrado com sucesso! ");
                            break;
                        case 3:
                            ler.nextLine();
                            String sql = "select count(*) from estadia where encerrado = 0;";
                            ResultSet rs = Conexao.consultar(sql);
                            if (rs != null) {
                                try {
                                    while (rs.next()) {
                                        somaEst = rs.getInt(1);
                                        System.out.println("Existe(m) " + somaEst + " veículo(s) no estacionamento.");
                                        rest = 40 - somaEst;
                                        System.out.println("Resta(m) " + rest + " vaga(s).");
                                        if (rest == 0 || somaEst == 40 || somaEst >= 40) {
                                            verif = false;
                                        } else {
                                            verif = true;
                                        }
                                        if (verif == true) {
                                            estadia = new Estadia();
                                            veiculo = new Veiculo();
                                            cliente = new Cliente();
                                            System.out.println("Você escolheu a opção 3 (Cadastrar Estadia)"); // Cadastrar Estadia
                                            System.out.println("Digite o ID do Cliente que você deseja cadastrar: ");
                                            System.out.println("Lista de Clientes:");
                                            listaClientes = Cliente.getClientes();
                                            for (Cliente c : listaClientes) {
                                                System.out.println(c.getId() + "  -  " + c.getNome());
                                            }
                                            cliente.setId(ler.nextInt()); //Uso dos sets sem construtor
                                            estadia.setDono(cliente);
                                            System.out.println("Digite o ID do Veículo que você deseja cadastrar: ");
                                            System.out.println("Lista de Veículos: ");
                                            listaVeiculos = Veiculo.getVeiculos();
                                            for (Veiculo v : listaVeiculos) {
                                                System.out.println(v.getId() + "  -  " + v.getPlaca() + " - " + v.getModelo());
                                            }
                                            veiculo.setId(ler.nextInt());
                                            estadia.setVeiculo(veiculo);
                                            estadia.setEncerrado(false);
                                            estadia.setValor(20);
                                            estadia.setDataEntrada(LocalDateTime.now());
                                            estadia.cadastrar();
                                            rest -= 1;
                                            System.out.println("Estadia cadastrada com sucesso! Resta(m) ainda " + rest + " vaga(s).");
                                        } else if (verif == false) {
                                            System.out.println("Capacidade Lotada! Não é possível cadastrar mais veículos.");
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                            break;
                        case 4:
                            System.out.println("Você escolheu a opção 4 (Excluir Cliente)"); //Excluir Cliente 
                            listaClientes = Cliente.getClientes();
                            for (Cliente c : listaClientes) {
                                System.out.println(c.getId() + "  -  " + c.getNome());
                            }
                            System.out.print("Digite o ID do Cliente que será excluído: "); //Só poderão ser excluidos clientes que não estejam vinculados a nenhuma estadia ou a nenhum veículo.
                            idCliente = ler.nextInt();
                            Cliente.excluir(idCliente); //Passado por parâmetro por ser um método abstrato.
                            System.out.println("Cliente " + idCliente + " excluido com sucesso!");
                            break;
                        case 5:
                            System.out.println("Você escolheu a opção 5 (Excluir Veículo)"); //Excluir Veículo
                            listaVeiculos = Veiculo.getVeiculos();
                            for (Veiculo v : listaVeiculos) {
                                System.out.println(v.getId() + "  -  " + v.getPlaca() + " - " + v.getModelo());
                            }
                            System.out.print("Digite o ID do Veículo que será excluído: ");  ////Só poderão ser excluidos veículos que não estejam vinculados a nenhuma estadia ou a nenhum cliente.
                            idVeiculo = ler.nextInt();
                            Veiculo.excluir(idVeiculo);
                            System.out.println("Veículo " + idVeiculo + " excluido com sucesso!");
                            break;
                        case 6:
                            System.out.println("Você escolheu a opção 6 (Excluir Estadia)"); //Excluir Estadia
                            System.out.println("Lista de Estadias: ");
                            listaEstadias = Estadia.getEstadias(); //Lista todas as estadias.
                            for (Estadia e : listaEstadias) {
                                System.out.println(e.getId() + "  -  " + e.getDono().getId() + " - " + e.getVeiculo().getId() + " - " + e.getDataEntrada() + " - " + e.getValor());
                            }
                            System.out.println("Digite o ID da Estadia que será excluida: ");
                            idEstadia = ler.nextInt();
                            Estadia.excluir(idEstadia);
                            System.out.println("Estadia " + idEstadia + " excluida com sucesso!");
                            break;
                        case 7:
                            estadia = new Estadia();
                            cliente = new Cliente();
                            veiculo = new Veiculo();
                            ler.nextLine();
                            System.out.println("Você escolheu a opção 7 (Alterar Estadia)"); //Alterar Estadia
                            System.out.println("Lista de Estadias: "); //Lista todas as estadias.
                            listaEstadias = Estadia.getEstadias();
                            for (Estadia e : listaEstadias) {
                                System.out.println(e.getId() + "  -  " + e.getDono().getId() + " - " + e.getVeiculo().getId() + " - " + e.getDataEntrada() + " - " + e.getValor());
                            }
                            System.out.println("Digite o ID da Estadia que será modificada: ");
                            estadia.setId(ler.nextInt());
                            System.out.println("Lista de Clientes:");
                            listaClientes = Cliente.getClientes();
                            for (Cliente c : listaClientes) {
                                System.out.println(c.getId() + "  -  " + c.getNome());
                            }
                            System.out.println("Digite o ID do novo Cliente que será cadastrado: ");
                            cliente.setId(ler.nextInt());
                            estadia.setDono(cliente);
                            System.out.println("Lista de Veículos: ");
                            listaVeiculos = Veiculo.getVeiculos();
                            for (Veiculo v : listaVeiculos) {
                                System.out.println(v.getId() + "  -  " + v.getPlaca() + " - " + v.getModelo());
                            }
                            System.out.println("Digite o ID do novo Veículo que será cadastrado: ");
                            veiculo.setId(ler.nextInt());
                            estadia.setVeiculo(veiculo);
                            estadia.setValor(20);
                            estadia.setDataEntrada(LocalDateTime.now());
                            estadia.editar();
                            System.out.println("Estadia alterada com sucesso! ");
                            break;
                        case 8:
                            ler.nextLine();
                            System.out.println("Você escolheu a opção 8 (Listar Estadia)"); //Lista todas as estadias em aberto.
                            System.out.println("Listar Estadias andamento: ");
                            System.out.println("idEstadia - idCliente - idVeiculo - Data de Entrada - Duração - Valor");
                            listaAbertas = Estadia.abertas();
                            for (Estadia e : listaAbertas) {
                                System.out.println(e.getId() + "  -  " + e.getDono().getId() + " - " + e.getVeiculo().getId() + " - " + e.getDataEntrada() + " - " + e.getValor());
                            }
                            break;
                        case 9:
                            ler.nextLine();
                            System.out.println("Você escolheu a opção 9 (Listar Clientes)");
                            System.out.println("Lista de Clientes:");
                            listaClientes = Cliente.getClientes();
                            for (Cliente c : listaClientes) {
                                System.out.println(c.getId() + "  -  " + c.getNome() + " - " + c.getTelefone());
                            }
                            break;
                        case 10:
                            ler.nextLine();
                            System.out.println("Você escolheu a opção 10 (Listar Veículos)");
                            System.out.println("Lista de Veículos: ");
                            listaVeiculos = Veiculo.getVeiculos();
                            for (Veiculo v : listaVeiculos) {
                                System.out.println(v.getId() + "  -  " + v.getPlaca() + " - " + v.getModelo());
                            }
                            break;
                        case 11:
                            ler.nextLine();
                            estadia = new Estadia();
                            System.out.println("Você escolheu a opção 11 (Finalizar Estadia)"); //Finalizar alguma estadia
                            System.out.println("idEstadia - idCliente - idVeiculo ");
                            listaAbertas = Estadia.abertas(); //Método diferente pois é para mostrar somente as estadias não encerradas.
                            for (Estadia e : listaAbertas) {
                                System.out.println(e.getId() + "  -  " + e.getDono().getId() + " - " + e.getVeiculo().getId());
                            }
                            System.out.println("Digite o ID da Estadia que você deseja finalizar: ");
                            estadia.setId(ler.nextInt());
                            estadia.setDataSaida(LocalDateTime.now());
                            estadia.dataSaida();
                            estadia.calcularValor();
                            System.out.println("O valor de R$ " + estadia.getValor() + " foi pago? (1-SIM)-(2-NÃO)");
                            perm = ler.nextInt();
                            if (perm != 1) {
                                System.out.println("A Estadia de ID  " + estadia.getId() + "só poderá ser finalizada após a confirmação do pagamento!");
                                break;
                            } else {
                                estadia.setEncerrado(true);
                                estadia.finalizarEstadia();
                                System.out.println("A Estadia de ID " + estadia.getId() + " foi encerrada com sucesso!");
                            }

                            break;
                        case 12:
                            ler.nextLine();
                            System.out.println("Você escolheu a opção 12 (Obter Relatório)");
                            System.out.println("Escolha uma das opções: ");
                            System.out.println("1 - Quantidade de estadias em andamento");
                            System.out.println("2 - Quantidade de estadias (em andamento ou já finalizadas)");
                            System.out.println("3 - Valor já recebido");
                            menuRelat = ler.nextInt();
                            switch (menuRelat) {
                                case 1:
                                    dono.relatorioAndamento();
                                    break;
                                case 2:
                                    dono.relatorioEstadia();
                                    break;
                                case 3:
                                    dono.relatorioValor();
                                    break;
                            }
                            break;
                        case 13:
                            System.out.println("Você escolheu a opção 13 (Sair)");
                            break saida;
                        default:
                            System.out.println("Opção Inválida! ");
                            break;

                    }
                } while (menu2 != 13);

            } else if (nomeUser.equals(func1.getUser()) && senha.equals(func1.getSenha())) {
                System.out.println("Bem-Vindo " + func1.getUser());
                do {
                    System.out.println("Olá! Escolha uma das opções abaixo: ");
                    System.out.println("1 - Cadastrar Cliente");
                    System.out.println("2 - Cadastrar Veículo");
                    System.out.println("3 - Cadastrar Estadia");
                    System.out.println("4 - Excluir Cliente");
                    System.out.println("5 - Excluir Veículo");
                    System.out.println("6 - Excluir Estadia");
                    System.out.println("7 - Alterar Estadia");
                    System.out.println("8 - Listar Estadias");
                    System.out.println("9 - Listar Clientes");
                    System.out.println("10 - Listar Veículos");
                    System.out.println("11 - Finalizar Estadia");
                    System.out.println("12 - Sair");
                    menu2 = ler.nextInt();
                    switch (menu2) {
                        case 1:
                            ler.nextLine();
                            System.out.println("Você escolheu a opção 1 (Cadastrar Cliente)"); //Cadastrar Cliente
                            System.out.println("Digite o nome do cliente: ");
                            nome = ler.nextLine();
                            System.out.println("Digite o telefone do cliente: ");
                            telefone = ler.nextLine();
                            cliente = new Cliente(nome, telefone); //Construtor Cliente
                            cliente.cadastrar();
                            System.out.println("Cliente cadastrado com sucesso! ");
                            break;
                        case 2:
                            ler.nextLine();
                            System.out.println("Você escolheu a opção 2 (Cadastrar Veículo) "); //Cadastrar Veículo
                            System.out.println("Digite a placa do veículo: ");
                            placa = ler.nextLine();
                            System.out.println("Digite o modelo do veículo: ");
                            modelo = ler.nextLine();
                            veiculo = new Veiculo(placa, modelo); //Construtor Veículo
                            veiculo.cadastrar();
                            System.out.println("Veículo cadastrado com sucesso! ");
                            break;
                        case 3:
                            ler.nextLine();
                            String sql = "select count(*) from estadia where encerrado = 0;";
                            ResultSet rs = Conexao.consultar(sql);
                            if (rs != null) {
                                try {
                                    while (rs.next()) {
                                        somaEst = rs.getInt(1);
                                        System.out.println("Existe(m) " + somaEst + " veículo(s) no estacionamento.");
                                        rest = 40 - somaEst;
                                        System.out.println("Resta(m) " + rest + " vaga(s).");
                                        if (rest == 0 || somaEst == 40 || somaEst >= 40) {
                                            verif = false;
                                        } else {
                                            verif = true;
                                        }
                                        if (verif == true) {
                                            estadia = new Estadia();
                                            veiculo = new Veiculo();
                                            cliente = new Cliente();
                                            System.out.println("Você escolheu a opção 3 (Cadastrar Estadia)"); // Cadastrar Estadia
                                            System.out.println("Digite o ID do Cliente que você deseja cadastrar: ");
                                            System.out.println("Lista de Clientes:");
                                            listaClientes = Cliente.getClientes();
                                            for (Cliente c : listaClientes) {
                                                System.out.println(c.getId() + "  -  " + c.getNome());
                                            }
                                            cliente.setId(ler.nextInt()); //Uso dos sets sem construtor
                                            estadia.setDono(cliente);
                                            System.out.println("Digite o ID do Veículo que você deseja cadastrar: ");
                                            System.out.println("Lista de Veículos: ");
                                            listaVeiculos = Veiculo.getVeiculos();
                                            for (Veiculo v : listaVeiculos) {
                                                System.out.println(v.getId() + "  -  " + v.getPlaca() + " - " + v.getModelo());
                                            }
                                            veiculo.setId(ler.nextInt());
                                            estadia.setVeiculo(veiculo);
                                            estadia.setEncerrado(false);
                                            estadia.setValor(20);
                                            estadia.setDataEntrada(LocalDateTime.now());
                                            estadia.cadastrar();
                                            rest -= 1;
                                            System.out.println("Estadia cadastrada com sucesso! Resta(m) ainda " + rest + " vaga(s).");
                                        } else if (verif == false) {
                                            System.out.println("Capacidade Lotada! Não é possível cadastrar mais veículos.");
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                            break;
                        case 4:
                            System.out.println("Você escolheu a opção 4 (Excluir Cliente)"); //Excluir Cliente 
                            listaClientes = Cliente.getClientes();
                            for (Cliente c : listaClientes) {
                                System.out.println(c.getId() + "  -  " + c.getNome());
                            }
                            System.out.print("Digite o ID do Cliente que será excluído: "); //Só poderão ser excluidos clientes que não estejam vinculados a nenhuma estadia ou a nenhum veículo.
                            idCliente = ler.nextInt();
                            Cliente.excluir(idCliente); //Passado por parâmetro por ser um método abstrato.
                            System.out.println("Cliente " + idCliente + " excluido com sucesso!");
                            break;
                        case 5:
                            System.out.println("Você escolheu a opção 5 (Excluir Veículo)"); //Excluir Veículo
                            listaVeiculos = Veiculo.getVeiculos();
                            for (Veiculo v : listaVeiculos) {
                                System.out.println(v.getId() + "  -  " + v.getPlaca() + " - " + v.getModelo());
                            }
                            System.out.print("Digite o ID do Veículo que será excluído: ");  ////Só poderão ser excluidos veículos que não estejam vinculados a nenhuma estadia ou a nenhum cliente.
                            idVeiculo = ler.nextInt();
                            Veiculo.excluir(idVeiculo);
                            System.out.println("Veículo " + idVeiculo + " excluido com sucesso!");
                            break;
                        case 6:
                            System.out.println("Você escolheu a opção 6 (Excluir Estadia)"); //Excluir Estadia
                            System.out.println("Lista de Estadias: ");
                            listaEstadias = Estadia.getEstadias(); //Lista todas as estadias.
                            for (Estadia e : listaEstadias) {
                                System.out.println(e.getId() + "  -  " + e.getDono().getId() + " - " + e.getVeiculo().getId() + " - " + e.getDataEntrada() + " - " + e.getValor());
                            }
                            System.out.println("Digite o ID da Estadia que será excluida: ");
                            idEstadia = ler.nextInt();
                            Estadia.excluir(idEstadia);
                            System.out.println("Estadia " + idEstadia + " excluida com sucesso!");
                            break;
                        case 7:
                            estadia = new Estadia();
                            cliente = new Cliente();
                            veiculo = new Veiculo();
                            ler.nextLine();
                            System.out.println("Você escolheu a opção 7 (Alterar Estadia)"); //Alterar Estadia
                            System.out.println("Lista de Estadias: "); //Lista todas as estadias.
                            listaEstadias = Estadia.getEstadias();
                            for (Estadia e : listaEstadias) {
                                System.out.println(e.getId() + "  -  " + e.getDono().getId() + " - " + e.getVeiculo().getId() + " - " + e.getDataEntrada() + " - " + e.getValor());
                            }
                            System.out.println("Digite o ID da Estadia que será modificada: ");
                            estadia.setId(ler.nextInt());
                            System.out.println("Lista de Clientes:");
                            listaClientes = Cliente.getClientes();
                            for (Cliente c : listaClientes) {
                                System.out.println(c.getId() + "  -  " + c.getNome());
                            }
                            System.out.println("Digite o ID do novo Cliente que será cadastrado: ");
                            cliente.setId(ler.nextInt());
                            estadia.setDono(cliente);
                            System.out.println("Lista de Veículos: ");
                            listaVeiculos = Veiculo.getVeiculos();
                            for (Veiculo v : listaVeiculos) {
                                System.out.println(v.getId() + "  -  " + v.getPlaca() + " - " + v.getModelo());
                            }
                            System.out.println("Digite o ID do novo Veículo que será cadastrado: ");
                            veiculo.setId(ler.nextInt());
                            estadia.setVeiculo(veiculo);
                            estadia.setValor(20);
                            estadia.setDataEntrada(LocalDateTime.now());
                            estadia.editar();
                            System.out.println("Estadia alterada com sucesso! ");
                            break;
                        case 8:
                            ler.nextLine();
                            System.out.println("Você escolheu a opção 8 (Listar Estadia)"); //Lista todas as estadias em aberto.
                            System.out.println("Listar Estadias andamento: ");
                            System.out.println("idEstadia - idCliente - idVeiculo - Data de Entrada - Duração - Valor");
                            listaAbertas = Estadia.abertas();
                            for (Estadia e : listaAbertas) {
                                System.out.println(e.getId() + "  -  " + e.getDono().getId() + " - " + e.getVeiculo().getId() + " - " + e.getDataEntrada() + " - " + e.getValor());
                            }
                            break;
                        case 9:
                            ler.nextLine();
                            System.out.println("Você escolheu a opção 9 (Listar Clientes)");
                            System.out.println("Lista de Clientes:");
                            listaClientes = Cliente.getClientes();
                            for (Cliente c : listaClientes) {
                                System.out.println(c.getId() + "  -  " + c.getNome() + " - " + c.getTelefone());
                            }
                            break;
                        case 10:
                            ler.nextLine();
                            System.out.println("Você escolheu a opção 10 (Listar Veículos)");
                            System.out.println("Lista de Veículos: ");
                            listaVeiculos = Veiculo.getVeiculos();
                            for (Veiculo v : listaVeiculos) {
                                System.out.println(v.getId() + "  -  " + v.getPlaca() + " - " + v.getModelo());
                            }
                            break;
                        case 11:
                            ler.nextLine();
                            estadia = new Estadia();
                            System.out.println("Você escolheu a opção 11 (Finalizar Estadia)"); //Finalizar alguma estadia
                            System.out.println("idEstadia - idCliente - idVeiculo ");
                            listaAbertas = Estadia.abertas(); //Método diferente pois é para mostrar somente as estadias não encerradas.
                            for (Estadia e : listaAbertas) {
                                System.out.println(e.getId() + "  -  " + e.getDono().getId() + " - " + e.getVeiculo().getId());
                            }
                            System.out.println("Digite o ID da Estadia que você deseja finalizar: ");
                            estadia.setId(ler.nextInt());
                            estadia.setDataSaida(LocalDateTime.now());
                            estadia.dataSaida();
                            estadia.calcularValor();
                            System.out.println("O valor de R$ " + estadia.getValor() + " foi pago? (1-SIM)-(2-NÃO)");
                            perm = ler.nextInt();
                            if (perm != 1) {
                                System.out.println("A Estadia de ID  " + estadia.getId() + "só poderá ser finalizada após a confirmação do pagamento!");
                                break;
                            } else {
                                estadia.setEncerrado(true);
                                estadia.finalizarEstadia();
                                System.out.println("A Estadia de ID " + estadia.getId() + " foi encerrada com sucesso!");
                            }
                            break;
                        case 12:
                            System.out.println("Você escolheu a opção 12 (Sair)");
                            break saida;
                        default:
                            System.out.println("Opção Inválida! ");
                            break;

                    }
                } while (menu2 != 12);
            } else {
                System.out.println("Login e/ ou senha incorreta!");
            }
        } while (log == 0);
    }
}
