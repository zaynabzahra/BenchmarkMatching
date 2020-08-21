package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            Main.matchBenchmark(line);
        }
    }

    public static void matchBenchmark(String input) {

        ArrayList<Asset> portfolioList = new ArrayList<>();
        ArrayList<Asset> benchmarkList = new ArrayList<>();

        String[] collection = input.split(":"); //splits the line into benchmark and portfolio

        String portfolioString = collection[0];
        String benchmarkString = collection[1];

        String[] portfolio = portfolioString.split("\\|");
        String[] benchmark = benchmarkString.split("\\|");

        portfolioList = populateList(portfolio);
        benchmarkList = populateList(benchmark);


        Collections.sort(benchmarkList, Asset.nameComparator); //sorts the benchmark list in ascending order including BOND
        compareLists(portfolioList, benchmarkList);

    }

    public static ArrayList populateList(String[] assets) { //populates the arraylist from the array

        ArrayList<Asset> list = new ArrayList<Asset>();

        for (int i = 0; i < assets.length; i++) {

            String[] assetParts = assets[i].split(",");

            String name = assetParts[0];
            String type = assetParts[1];
            int shares = Integer.valueOf(assetParts[2]);

            list.add(new Asset(name, type, shares));
        }

        return list;

    }

    public static void compareLists(ArrayList<Asset> portfolio, ArrayList<Asset> benchmark) { //compares the lists to give the required output

        String state;

        for (Asset b : benchmark) {
            for (Asset p : portfolio) {

                if (b.getName().equals(p.getName()) && b.getAssetType().equals(p.getAssetType()) && b.getShares() != p.getShares()) {

                    state = (b.getShares() > p.getShares()) ? "BUY" : "SELL";
                    int value = b.getShares() - p.getShares();
                    value = (value < 0) ? -value : value;
                    System.out.println(state + "," + b.getName() + "," + b.getAssetType() + "," + value);
                }

                if (b.getName().equals(p.getName()) && (!b.getAssetType().equals(p.getAssetType()))) {
                    System.out.println("BUY," + b.getName() + "," + b.getAssetType() + "," + b.getShares());
                }

            }
        }

    }

    public static class Asset { //asset class contains information of each asset

        String name;
        String assetType;
        int shares;

        public Asset(String name, String assetType, int shares) {

            this.name = name;
            this.assetType = assetType;
            this.shares = shares;

        }

        public String getName() {

            return this.name;
        }

        public String getAssetType() {
            return this.assetType;
        }

        public int getShares() {
            return this.shares;
        }

        public static Comparator<Asset> nameComparator = new Comparator<Asset>() { //comparator manages sorting of arraylist

            public int compare(Asset a1, Asset a2) {

                String name1 = a1.getName().toUpperCase();
                String name2 = a2.getName().toUpperCase();

                if (name1.equals(name2) && a1.getAssetType().equals("BOND")) {
                    return -1;
                } else {
                    return name1.compareTo(name2);
                }

            }
        };

    }

}