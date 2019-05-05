package com.tekrajchhetri;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class FilterKeyValue {
    String name;
    Double time;

    FilterKeyValue(String name, Double time) {
        this.name = name;
        this.time = time;
    }


    public String getName() {
        return name.toString();
    }

    public Double getTime() {
        return time;
    }
}


class DecimalUtils {

    public static double round(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }
}

class Main implements ExampleChart<CategoryChart> {
    final Settings settings = new Settings();



    public static void main(String[] args) throws IOException {
        Main demo = new Main();
        demo.handleInput(args);
        demo.run();
    }



    void handleInput(String []args){
        JCommander jCommander = new JCommander(settings);

        try{
            jCommander.parse(args);
        }catch (ParameterException e){
            System.out.println("Error Occured. See usage");
            jCommander.usage();

        }

        if(settings.isHelp()){
            showUsageInformation(jCommander);
        }

    }

    void showUsageInformation(JCommander jCommander) {
        jCommander.usage();
        System.exit(0);
    }

    public void run()  {
        //System.out.println("Running Program");

//        System.out.println(settings.getpath());
        try {
            readFile(settings.getpath(),settings.getNumber());
        }catch (Exception ex){

        }

    }


    public static String filterRes(String []x){
        if(x[4].toLowerCase().contains(".") &&  x[4].toLowerCase().contains("/")){
            String res = x[4].split("\\.")[0].split("\\/")[1];
            return res;
        }
        return "";
    }

    public void readFile(Path path, int number) {


        try {
            Instant start = Instant.now(); //to measure time

            Stream<String> rows35 =  null;
            rows35 = Files.lines(path);


            //Add key,value => url, value
            Map<String, List<FilterKeyValue>> grouped = rows35
                    .map(line -> {
                        String[] x = line.split("\\s");
                        return new FilterKeyValue(
                                filterRes(x),
                                Double.parseDouble(x[(x.length) - 1]));
                    })
                    .collect(Collectors.groupingBy(x -> x.getName()));
            rows35.close();


            Map<String,Double> finalResult = new HashMap<String,Double>();

//            AtomicInteger count = new AtomicInteger();

            //calculate Average based on key URL
            grouped
                    .entrySet()
                    .stream()
                    .skip(1)
                    .forEach(x -> {
                        if (!x.getKey().contains("404")) {

                            finalResult.put(x.getKey(), x.getValue().stream().mapToDouble(FilterKeyValue::getTime).summaryStatistics().getAverage());

//                            count.addAndGet(1);
                        }


                    });

            Map<String,Double> sortedAsc = new LinkedHashMap<>();
            List<Map.Entry<String, Double>> toSort = new ArrayList<>(finalResult.entrySet());
            toSort
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(x->
                            sortedAsc.put(x.getKey(),x.getValue()));


            List<Double> data = new ArrayList<Double>();
            List<String> xlable = new ArrayList<String>();

            for (String key : sortedAsc.keySet()) {
//                System.out.println(key+":"+finalResult.get(key));
                double toAdd = DecimalUtils.round(finalResult.get(key), 2);
                xlable.add(key);
                data.add(toAdd);
            }
            if (number > data.size()){
                System.out.println("Requested Number greater than available data "+data.size());
                System.exit(0);
            }

            List<Double> sortedResult = data.subList((data.size() - number), data.size());
            List<String> xdata = xlable.subList((data.size() - number), data.size());
            Instant finish = Instant.now();

            long timeElapsed = Duration.between(start, finish).toMillis();  //in millis

            ExampleChart<CategoryChart> exampleChart = new Main();
            CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Average Time Per Request ( "+timeElapsed+" milliseconds Excluding Plot Time )").xAxisTitle("Request URL").yAxisTitle("Average Time").build();

            // Customize Chart
            chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
            chart.getStyler().setAxisTickLabelsFont(new Font("Arial", Font.PLAIN, 9));
            chart.getStyler().setAxisTickLabelsColor(Color.MAGENTA);
            chart.getStyler().setXAxisLabelRotation(75);
            chart.getStyler().setAvailableSpaceFill(.6);
            chart.getStyler().setOverlapped(false);


            chart.addSeries("Average Time Per Request", xdata, sortedResult);
            // Series
            Histogram histogram1 = new Histogram(sortedResult, number, 0, number);
            new SwingWrapper<CategoryChart>(chart).displayChart();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public CategoryChart getChart() {
        return null;
    }


}
