package A3;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Quick sort
        // Bubble sort
        // Shell sort
        // final int[] array = { 8, 44, 7, 10, 8, 40, 100, 0, 1, 20 };

        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> array = new ArrayList<>();

        while (running) {
            menu();
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    show(array);
                    break;
                case 2:
                    array = charge(array, scanner, null);
                    break;
                case 3:
                    bubbleSort(array, true);
                    break;
                case 4:
                    quickSort(array, true);
                    break;
                case 5:
                    shellSort(array, true);
                    break;
                case 6:
                    benchMarkTriple(array, scanner);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Opcao invalida");
                    break;
            }
        }
    }

    private static void bubbleSort(ArrayList<Integer> vetor, boolean show) {
        BenchMark bench = new BenchMark();
        MemoryUsage mem = new MemoryUsage();
        int comparisons = 0;
        mem.start();
        bench.start();

        int n = vetor.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (vetor.get(j) > vetor.get(j + 1)) {
                    int temp = vetor.get(j);
                    vetor.set(j, vetor.get(j + 1));
                    vetor.set(j + 1, temp);
                }
            }
        }

        bench.stop();
        mem.stop();
        if (show) {
            show(vetor);
        } else {
            System.out.println("==========================================");
            System.out.println("Bubble Sort");
        }
        time(bench);
        memory(mem);
        showComparisons(comparisons);
    }

    private static void quickSort(ArrayList<Integer> vetor, boolean show) {
        BenchMark bench = new BenchMark();
        MemoryUsage mem = new MemoryUsage();

        mem.start();
        bench.start();

        int comparisons = quickSort(vetor, 0, vetor.size() - 1); // chama o método recursivo já existente

        bench.stop();
        mem.stop();
        if (show) {
            show(vetor);
        } else {
            System.out.println("==========================================");
            System.out.println("Quick Sort");
        }
        time(bench);
        memory(mem);
        showComparisons(comparisons);
    }

    // Método recursivo interno - recebe início e fim e retorna o número de
    // comparações
    private static int quickSort(ArrayList<Integer> vetor, int inicio, int fim) {
        int comparisons = 0;
        if (inicio < fim) {
            // Lógica de particionamento integrada
            int pivo = vetor.get(fim); // Escolhe o último como pivô
            int i = (inicio - 1);

            for (int j = inicio; j < fim; j++) {
                comparisons++; // Conta a comparação
                if (vetor.get(j) <= pivo) {
                    i++;
                    // Troca vetor[i] com vetor[j]
                    int temp = vetor.get(i);
                    vetor.set(i, vetor.get(j));
                    vetor.set(j, temp);
                }
            }
            // Coloca o pivô no lugar certo
            int temp = vetor.get(i + 1);
            vetor.set(i + 1, vetor.get(fim));
            vetor.set(fim, temp);

            int indicePivo = i + 1;

            // Chamada Recursiva (chama a si mesmo) e soma as comparações
            comparisons += quickSort(vetor, inicio, indicePivo - 1); // Lado esquerdo
            comparisons += quickSort(vetor, indicePivo + 1, fim); // Lado direito
        }
        return comparisons;
    }

    private static void shellSort(ArrayList<Integer> vetor, boolean show) {
        BenchMark bench = new BenchMark();
        MemoryUsage mem = new MemoryUsage();
        int comparisons = 0;
        mem.start();
        bench.start();
        int n = vetor.size();

        // Começa com um gap grande e divide por 2 a cada loop
        for (int gap = n / 2; gap > 0; gap /= 2) {

            // Faz um Insertion Sort gapped (com intervalo)
            for (int i = gap; i < n; i++) {
                int temp = vetor.get(i);
                int j;

                for (j = i; j >= gap; j -= gap) {
                    comparisons++; // Conta a comparação
                    if (vetor.get(j - gap) > temp) {
                        vetor.set(j, vetor.get(j - gap));
                    } else {
                        break; // Sai do loop se não precisa trocar
                    }
                }
                vetor.set(j, temp);
            }
        }

        bench.stop();
        mem.stop();
        if (show) {
            show(vetor);
        } else {
            System.out.println("==========================================");
            System.out.println("Shell Sort");
        }
        time(bench);
        memory(mem);
        showComparisons(comparisons);
    }

    private static ArrayList<Integer> charge(ArrayList<Integer> array, Scanner scanner, String name) {
        array.clear();
        boolean condition = false;
        if (name == null) {
            System.out.println("Informe o nome do arquivo a ser carregado:");
            name = scanner.next();
            condition = true;
        }
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("A3/" + name + ".txt"))) {
            String linha;
            int indice = 0;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    int valor = Integer.parseInt(linha.trim());
                    if (indice < array.size()) {
                        array.set(indice, valor);
                    } else {
                        array.add(valor);
                    }
                    indice++;
                }
            }
            if (condition) {
                System.out.println("Arquivo carregado com sucesso! Total de valores: " + array.size());
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return array;
    }

    private static void benchMarkTriple(ArrayList<Integer> array, Scanner scanner) {
        System.out.println("Informe o nome do arquivo a ser carregado:");
        String name = scanner.next();
        array.clear();
        array = charge(array, scanner, name);
        bubbleSort(array, false);
        array.clear();
        array = charge(array, scanner, name);
        quickSort(array, false);
        array.clear();
        array = charge(array, scanner, name);
        shellSort(array, false);
    }

    private static void show(ArrayList<Integer> lista) {
        System.out.println("==========================================");
        for (int i = 0; i < lista.size(); i++) {
            System.out.print(lista.get(i) + " ");
        }
        System.out.println();
    }

    private static void time(BenchMark bench) {
        System.out.println("==========================================");
        System.out.println("Tempo de execucao: " + bench.getTime());
    }

    private static void memory(MemoryUsage mem) {
        System.out.println(
                "Memória adicional utilizada: " + mem.getUsedBytes() + " bytes (" + mem.getUsedKilobytes() + " KB)");
    }

    private static void showComparisons(int comparisons) {
        System.out.println("Quantidade de comparacoes feitas pelo ordenador: " + comparisons);
    }

    private static void menu() {
        System.out.println("= Menu ===================================");
        System.out.println("1. Mostrar array inicial");
        System.out.println("2. Preencher array inicial com arquivos");
        System.out.println("3. Ordenar por Bubble Sort");
        System.out.println("4. Ordenar por Quick Sort");
        System.out.println("5. Ordenar por Shell Sort");
        System.out.println("6. BenchMark dos 3 algoritimos");
        System.out.println("0. Sair");
        System.out.println("==========================================");
    }
}
