import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        List list = new List();
        Scanner scanner = new Scanner(System.in);

        String[] cnome = new String[11];
        int[][] ligacoes = new int[11][11];

        boolean runnig = true;

        while (runnig) {
            menu();
            int option = scanner.nextInt();
            System.out.println("==========================================");

            switch (option) {
                case 1:
                    show(list);
                    break;
                case 2: // ta certo
                    try (BufferedReader br = new BufferedReader(new FileReader("dados.txt"))) {
                        String linha;
                        String separador = null;
                        boolean passouSeparador = false;
                        int contador = 0;
                        int indiceCnome = 0;

                        // Algoritmo para capturar o separador "<->" do arquivo e processar os dados
                        while ((linha = br.readLine()) != null) {
                            // Primeiro: ler tudo antes do "<->" e adicionar ao array cnome
                            if (!passouSeparador && !linha.trim().equals("<->")) {
                                if (indiceCnome < cnome.length) {
                                    cnome[indiceCnome] = linha.trim();
                                    indiceCnome++;
                                }
                                continue;
                            }

                            // Segundo: capturar o separador "<->" do arquivo e colocar na variável
                            // separador
                            if (separador == null && linha.trim().equals("<->")) {
                                separador = Pattern.quote(linha.trim());
                                passouSeparador = true;
                                continue;
                            }

                            // Terceiro: processar os dados após encontrar o separador
                            if (passouSeparador && !linha.trim().isEmpty()) {
                                Vertice vertice = new Vertice();
                                String[] dados = linha.split(",");
                                vertice.C1 = dados[0].trim();
                                vertice.C2 = dados[1].trim();
                                vertice.distance = Double.parseDouble(dados[2].trim());
                                list.add(vertice);
                                contador++;
                            }
                        }

                        System.out.println("Lista preenchida com sucesso! " + contador + " vértices adicionados.");
                    } catch (Exception e) {
                        System.out.println("Erro ao ler arquivo: " + e.getMessage());
                    }
                    break;
                case 3:
                    ligacoes = prencher(ligacoes, list, cnome);
                    System.out.println("    1  2  3  4  5  6  7  8  9  10 11");
                    System.out.println("    |  |  |  |  |  |  |  |  |  |  |");
                    for (int i = 0; i < ligacoes.length; i++) {
                        if (i < 9) {
                            System.out.print(i + 1 + "---");
                        } else {
                            System.out.print(i + 1 + "--");
                        }
                        for (int j = 0; j < ligacoes.length; j++) {
                            System.out.print(ligacoes[i][j] + "  ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                    System.out.println("Indice ===============================");
                    for (int i = 0; i < cnome.length; i++) {
                        System.out.println(i + 1 + " = " + cnome[i]);
                    }
                    break;
                case 4:
                    System.out.println("Informe o nome da primeira creche:");
                    scanner.nextLine(); // Limpa o buffer
                    String creche1 = scanner.nextLine().trim();
                    System.out.println("Informe o nome da segunda creche:");
                    String creche2 = scanner.nextLine().trim();
                    System.out.println("Informe a distância em km:");
                    double distancia = scanner.nextDouble();

                    // Cria e adiciona o novo vértice à lista
                    Vertice novoVertice = new Vertice();
                    novoVertice.C1 = creche1;
                    novoVertice.C2 = creche2;
                    novoVertice.distance = distancia;
                    list.add(novoVertice);

                    // Encontra os índices das creches no array cnome
                    int indiceC1 = -1;
                    int indiceC2 = -1;
                    for (int i = 0; i < cnome.length; i++) {
                        if (cnome[i] != null && cnome[i].equals(creche1)) {
                            indiceC1 = i;
                        }
                        if (cnome[i] != null && cnome[i].equals(creche2)) {
                            indiceC2 = i;
                        }
                    }

                    // Atualiza a matriz de ligações
                    System.out.println("==========================================");
                    if (indiceC1 != -1 && indiceC2 != -1) {
                        ligacoes[indiceC1][indiceC2] = 1;
                        ligacoes[indiceC2][indiceC1] = 1; // Matriz simétrica
                        System.out.println(creche1 + " <- " + distancia + "km -> " + creche2);
                    } else {
                        System.out.println("Erro: Uma ou ambas as creches não foram encontradas no array cnome.");
                    }
                    break;
                case 5:
                    System.out.println("Informe o nome da creche:");
                    String search = scanner.next();
                    List show = list.search(search);
                    show(show);
                    break;
                case 6:
                    System.out.println("Informe o nome da creche:");
                    search = scanner.next();
                    System.out.println("==========================================");
                    int numc = list.count(search);
                    System.out
                            .println("A creche: " + search + " Possui: " + numc + " conexao(oes) com outras creches.");
                    break;
                case 0: // 100% certo
                    runnig = false;
                    break;
                default:
                    break;
            }
        }
    }

    private static void menu() {
        System.out.println("= Menu ===================================");
        System.out.println("1. Mostrar lista conecoes");
        System.out.println("2. Adicionar conexao com arquivo");
        System.out.println("3. Mostrar matriz de ligacao");
        System.out.println("4. Adicionar ligacao entre creche");
        System.out.println("5. Listar conexao de uma creche especifica");
        System.out.println("6. Listar quantas conexoes tem uma creche especifica");
        System.out.println("0. Sair");
        System.out.println("==========================================");
    }

    private static void show(List list) {
        int pos = 0;
        Vertice v = list.get(pos);
        while (v != null) {
            System.out.println(v.C1 + " <- " + v.distance + "km -> " + v.C2);
            pos++;
            v = list.get(pos);
        }
    }

    private static int[][] prencher(int[][] matriz, List list, String[] cnome) {

        // Percorre a lista de vértices e preenche a matriz de ligações
        int pos = 0;
        Vertice v = list.get(pos);
        while (v != null) {
            // Encontra o índice da creche C1 no array cnome
            int indiceC1 = -1;
            for (int i = 0; i < cnome.length; i++) {
                if (cnome[i] != null && cnome[i].equals(v.C1)) {
                    indiceC1 = i;
                    break;
                }
            }

            // Encontra o índice da creche C2 no array cnome
            int indiceC2 = -1;
            for (int i = 0; i < cnome.length; i++) {
                if (cnome[i] != null && cnome[i].equals(v.C2)) {
                    indiceC2 = i;
                    break;
                }
            }

            // Se ambas as creches foram encontradas, marca a conexão na matriz
            if (indiceC1 != -1 && indiceC2 != -1) {
                matriz[indiceC1][indiceC2] = 1;
                matriz[indiceC2][indiceC1] = 1; // Matriz simétrica (grafo não direcionado)
            }

            pos++;
            v = list.get(pos);
        }

        return matriz;
    }
}