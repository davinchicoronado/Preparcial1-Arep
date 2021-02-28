package edu.eci.arsw.moneylaundering;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoneyLaundering
{
    private TransactionAnalyzer transactionAnalyzer;
    private TransactionReader transactionReader;
    private int amountOfFilesTotal;
    private AtomicInteger amountOfFilesProcessed;
    private List<ThreadAnalizer> hilos;
    private MonitorController controlador;
    

    public MoneyLaundering()
    {
        transactionAnalyzer = new TransactionAnalyzer();
        transactionReader = new TransactionReader();
        amountOfFilesProcessed = new AtomicInteger();
        hilos=new ArrayList<>();
        controlador = new MonitorController();
    }

    public void processTransactionData()
    {
        
        amountOfFilesProcessed.set(0);
        List<File> transactionFiles = getTransactionFileList();
        amountOfFilesTotal = transactionFiles.size();
        createThreads(22);
        for(ThreadAnalizer h:hilos){
            h.setTransactionFiles(transactionFiles);
        }
        for(ThreadAnalizer h:hilos){
            h.start();
        }
        
    }

    public List<String> getOffendingAccounts()
    {
        return transactionAnalyzer.listOffendingAccounts();
    }

    private List<File> getTransactionFileList()
    {
        List<File> csvFiles = new ArrayList<>();
        try (Stream<Path> csvFilePaths = Files.walk(Paths.get("src/main/resources/")).filter(path -> path.getFileName().toString().endsWith(".csv"))) {
            csvFiles = csvFilePaths.map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFiles;
    }

    public static void main(String[] args)
    {
        MoneyLaundering moneyLaundering = new MoneyLaundering();
        moneyLaundering.processTransactionData();
        
        
        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if(line.contains("exit"))
                break;
            moneyLaundering.setPuaseThreads();
            String message = "Processed %d out of %d files.\nFound %d suspect accounts:\n%s";
            List<String> offendingAccounts = moneyLaundering.getOffendingAccounts();
            String suspectAccounts = offendingAccounts.stream().reduce("", (s1, s2)-> s1 + "\n"+s2);
            message = String.format(message, moneyLaundering.amountOfFilesProcessed.get(), moneyLaundering.amountOfFilesTotal, offendingAccounts.size(), suspectAccounts);
            System.out.println(message);
        }

    }
    public void setPuaseThreads(){
        controlador.setPause();
    
    }
    
    private void createThreads(int numOfThreads){
        if (numOfThreads>amountOfFilesTotal){
            ThreadAnalizer hilo;
            for(int i=1;i<=amountOfFilesTotal;i++){
               hilo = new ThreadAnalizer(i,i,transactionAnalyzer,transactionReader,amountOfFilesProcessed,controlador);
               hilos.add(hilo);
            }
            
        }
        else{
            ThreadAnalizer hilo;
            int cont=0;
            int range=(amountOfFilesTotal/numOfThreads)-1;
            for(int i=1;i<=numOfThreads;i++){
                hilo=new ThreadAnalizer(cont,cont+range,transactionAnalyzer,transactionReader,amountOfFilesProcessed,controlador);
                hilos.add(hilo);
                cont+=range+1;
            }
            if(hilos.get(numOfThreads-1).getX2()!=(amountOfFilesTotal-1)){
                hilos.get(numOfThreads-1).setX2(amountOfFilesTotal-1);
            }
        }
    
    
    
    }
}
